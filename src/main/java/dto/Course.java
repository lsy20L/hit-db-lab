package dto;

public class Course {
    private String Cnumber;
    private String Cname;
    private float Credit;
    private int Chours;
    private String Tnumber;

    public Course() {
    }

    public Course(String cnumber, String cname, float credit, int chours, String tnumber) {
        Cnumber = cnumber;
        Cname = cname;
        Credit = credit;
        Chours = chours;
        Tnumber = tnumber;
    }

    public String getCnumber() {
        return Cnumber;
    }

    public void setCnumber(String cnumber) {
        Cnumber = cnumber;
    }

    public String getCname() {
        return Cname;
    }

    public void setCname(String cname) {
        Cname = cname;
    }

    public float getCredit() {
        return Credit;
    }

    public void setCredit(float credit) {
        Credit = credit;
    }

    public int getChours() {
        return Chours;
    }

    public void setChours(int chours) {
        Chours = chours;
    }

    public String getTnumber() {
        return Tnumber;
    }

    public void setTnumber(String tnumber) {
        Tnumber = tnumber;
    }
}
