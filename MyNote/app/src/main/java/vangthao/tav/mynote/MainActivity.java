package vangthao.tav.mynote;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Database database;
    ListView lvCongViec;
    ArrayList<CongViec> arrayCongViec;
    CongViecAdapter adapter;
    TextView txtJobProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadViews();
        lvCongViec = findViewById(R.id.listViewCongViec);
        arrayCongViec = new ArrayList<>();
        adapter = new CongViecAdapter(this, R.layout.dong_cong_viec, arrayCongViec);
        lvCongViec.setAdapter(adapter);

        //tao database ghichu
        database = new Database(this, "ghichu.sqlite", null, 1);
        //tạo bảng CongViec
        database.QueryData("CREATE TABLE IF NOT EXISTS CongViec(Id INTEGER PRIMARY KEY AUTOINCREMENT,Done INTEGER,TenCV VARCHAR(200), TimeAdded VARCHAR(200))");
        GetDataCongViec();
    }

    private void loadViews() {
        txtJobProgress = findViewById(R.id.txt_job_progress);
    }

    public void DialogXoaCV(final String tencv, final int id) {
        AlertDialog.Builder dialogXoa = new AlertDialog.Builder(this);
        dialogXoa.setMessage(String.format(getString(R.string.do_you_want_delete_this_job), tencv));

        dialogXoa.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialogXoa.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                database.QueryData("DELETE FROM CongViec WHERE Id = '" + id + "'");
                Toast.makeText(MainActivity.this, getString(R.string.deleted_job) + tencv + getString(R.string.exclamation_mark), Toast.LENGTH_SHORT).show();
                GetDataCongViec();
            }
        });
        dialogXoa.show();
    }

    public void updateJobStatus(int done, int id) {
        database.QueryData("UPDATE CongViec SET Done = '" + done + "' WHERE Id = '" + id + "'");
        GetDataCongViec();
    }

    public void DialogSuaCongViec(String ten, final int id) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_sua);

        final EditText edtTenCV = dialog.findViewById(R.id.editTextTenCVEdit);
        Button btnXacNhan = dialog.findViewById(R.id.buttonXacNhan);
        Button btnHuy = dialog.findViewById(R.id.buttonHuyEdit);

        edtTenCV.setText(ten);

        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenMoi = edtTenCV.getText().toString().trim();
                database.QueryData("UPDATE CongViec SET TenCV = '" + tenMoi + "' WHERE Id = '" + id + "'");
                Toast.makeText(MainActivity.this, getString(R.string.updated_job), Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                GetDataCongViec();
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @SuppressLint("DefaultLocale")
    private void GetDataCongViec() {
        int countJobDone = 0;
        //select data
        Cursor dataCongViec = database.GetData("SELECT * FROM CongViec");
        arrayCongViec.clear();
        while (dataCongViec.moveToNext()) {
            int id = dataCongViec.getInt(0);
            int done = dataCongViec.getInt(1);
            if (done == 1) countJobDone++;
            String ten = dataCongViec.getString(2);
            String timeAdded = dataCongViec.getString(3);
            arrayCongViec.add(new CongViec(id, done, ten, timeAdded));
        }
        txtJobProgress.setText(String.format("(%d/%d)", countJobDone, arrayCongViec.size()));
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_congviec, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuAdd) {
            DialogThem();
        }
        return super.onOptionsItemSelected(item);
    }

    private void DialogThem() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_them_cong_viec);

        final EditText edtTen = dialog.findViewById(R.id.editTextTenCV);
        Button btnThem = dialog.findViewById(R.id.buttonThem);
        Button btnHuy = dialog.findViewById(R.id.buttonHuy);

        btnThem.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                String tenCV = edtTen.getText().toString().trim();
                LocalDateTime currentTime = LocalDateTime.now();
                String timeAdded = currentTime.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL, FormatStyle.MEDIUM));
                if (tenCV.equals("")) {
                    Toast.makeText(MainActivity.this, getString(R.string.please_type_job_name), Toast.LENGTH_SHORT).show();
                } else {
                    //insert data
                    database.QueryData("INSERT INTO CongViec VALUES(null,'" + 0 + "','" + tenCV + "','" + timeAdded + "')");
                    Toast.makeText(MainActivity.this, getString(R.string.added_new_job), Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    GetDataCongViec();
                }
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
