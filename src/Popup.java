import javax.swing.*;
public class Popup extends JOptionPane {
    public Popup() {

    }

    public static int promptNumPlayers() {
        String in = showInputDialog("Enter Number Of Players");
        try {
            int out = Integer.parseInt(in);
            if (out <= 0) {
                throw new IllegalAccessError("negative number");
            }
            System.out.println(out);
            return out;
        } catch (NumberFormatException e) {
            throw new IllegalAccessError("not an int");
        }
    }
}
