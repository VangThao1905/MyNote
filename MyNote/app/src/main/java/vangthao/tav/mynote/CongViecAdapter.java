package vangthao.tav.mynote;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.zip.Inflater;

public class CongViecAdapter extends BaseAdapter {
    private MainActivity context;
    private int layout;
    private List<CongViec> congViecList;

    public CongViecAdapter(MainActivity context, int layout, List<CongViec> congViecList) {
        this.context = context;
        this.layout = layout;
        this.congViecList = congViecList;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(MainActivity context) {
        this.context = context;
    }

    public int getLayout() {
        return layout;
    }

    public void setLayout(int layout) {
        this.layout = layout;
    }

    public List<CongViec> getCongViecList() {
        return congViecList;
    }

    public void setCongViecList(List<CongViec> congViecList) {
        this.congViecList = congViecList;
    }

    @Override
    public int getCount() {
        return congViecList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class ViewHolder {
        TextView txtTen;
        ImageView imgDelete, imageEdit;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final ViewHolder holder;
        if(view == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,null);
            holder.txtTen    = (TextView) view.findViewById(R.id.textViewTen);
            holder.imgDelete = (ImageView) view.findViewById(R.id.imageViewDelete);
            holder.imageEdit = (ImageView) view.findViewById(R.id.imageViewEdit);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        final CongViec congViec = congViecList.get(i);
        holder.txtTen.setText(congViec.getTenCV());

        //bắt sự kiện xóa và sửa cv
        holder.imageEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.DialogSuaCongViec(congViec.getTenCV(), congViec.getIdCV());
            }
        });

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.DialogXoaCV(congViec.getTenCV(),congViec.getIdCV());
            }
        });
        return view;
    }
}
