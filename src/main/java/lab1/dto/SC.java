package lab1.dto;

public class SC {
    private String Snumber;
    private String Cnumber;
    private float score;

    public SC() {
    }

    public SC(String snumber, String cnumber, float score) {
        Snumber = snumber;
        Cnumber = cnumber;
        this.score = score;
    }

    public String getSnumber() {
        return Snumber;
    }

    public void setSnumber(String snumber) {
        Snumber = snumber;
    }

    public String getCnumber() {
        return Cnumber;
    }

    public void setCnumber(String cnumber) {
        Cnumber = cnumber;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }
}
