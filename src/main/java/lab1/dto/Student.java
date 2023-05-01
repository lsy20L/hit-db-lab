package lab1.dto;

public class Student {
    private String Snumber;
    private String Sname;
    private String Ssex;
    private int Sage;
    private String SClass;

    public Student(String snumber, String sname, String ssex, int sage, String SClass) {
        Snumber = snumber;
        Sname = sname;
        Ssex = ssex;
        Sage = sage;
        this.SClass = SClass;
    }

    public Student() {
    }

    public String getSnumber() {
        return Snumber;
    }

    public void setSnumber(String snumber) {
        Snumber = snumber;
    }

    public String getSname() {
        return Sname;
    }

    public void setSname(String sname) {
        Sname = sname;
    }

    public String getSsex() {
        return Ssex;
    }

    public void setSsex(String ssex) {
        Ssex = ssex;
    }

    public int getSage() {
        return Sage;
    }

    public void setSage(int sage) {
        Sage = sage;
    }

    public String getSClass() {
        return SClass;
    }

    public void setSClass(String SClass) {
        this.SClass = SClass;
    }
}
