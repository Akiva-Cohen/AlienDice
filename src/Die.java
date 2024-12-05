import java.lang.Math.*;
public class Die {
    public static int roll() {
        int out = 0;
        out = (int)(Math.random() * 6);
        if (out == 5) {
            out = 0;
        }
        return out;
    }
    public static String rollString() {
        switch(roll()) {
            case 0: return "Death Ray";
            //break;
            case 1: return "Tank";
            //break;
            case 2: return "Human";
            //break;
            case 3: return "Cow";
            //break;
            case 4: return "Chicken";
            //break;
            default: return "";
        }
        //return "";
    }
}