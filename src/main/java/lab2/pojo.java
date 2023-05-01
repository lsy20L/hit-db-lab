package lab2;

/**
 * <h3>JavaSwing</h3>
 * <p></p>
 * @author : hit-lsy
 * @date : 2023/5/1 20:35
 **/
public class pojo implements Comparable<pojo> {
    static String charSet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    static StringBuilder sb =  new StringBuilder();
    int a;
    String b;
    public pojo(){
        a = (int)(Math.random()*40000);
        randomString(10);
        b = sb.toString();
    }
    public pojo(int a, String b) {
        this.a = a;
        this.b = b;
    }
    private void randomString(int n){
        sb.setLength(0);
        int l = charSet.length();
        for (int i = 0; i < n; i++) {
            sb.append(charSet.charAt((int)(Math.random()*l)));
        }
    }
    @Override
    public String toString(){
        return a+" "+b+" ";
    }


    @Override
    public int compareTo(pojo o) {
        return a-o.a;
    }
}
