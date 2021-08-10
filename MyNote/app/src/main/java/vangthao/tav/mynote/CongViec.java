package vangthao.tav.mynote;

public class CongViec {
    private int IdCV;
    private int done;
    private String tenCV;
    private String timeAdded;

    public CongViec(int idCV, int done, String tenCV, String timeAdded) {
        this.IdCV = idCV;
        this.done = done;
        this.tenCV = tenCV;
        this.timeAdded = timeAdded;
    }

    public int getIdCV() {
        return IdCV;
    }

    public void setIdCV(int idCV) {
        IdCV = idCV;
    }

    public int getDone() {
        return done;
    }

    public void setDone(int done) {
        this.done = done;
    }

    public String getTenCV() {
        return tenCV;
    }

    public void setTenCV(String tenCV) {
        this.tenCV = tenCV;
    }

    public String getTimeAdded() {
        return timeAdded;
    }

    public void setTimeAdded(String timeAdded) {
        this.timeAdded = timeAdded;
    }
}
