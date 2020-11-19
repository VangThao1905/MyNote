package vangthao.tav.mynote;

public class CongViec {
    private int IdCV;
    private String tenCV;

    public CongViec(int idCV, String tenCV) {
        IdCV = idCV;
        this.tenCV = tenCV;
    }

    public int getIdCV() {
        return IdCV;
    }

    public void setIdCV(int idCV) {
        IdCV = idCV;
    }

    public String getTenCV() {
        return tenCV;
    }

    public void setTenCV(String tenCV) {
        this.tenCV = tenCV;
    }
}
