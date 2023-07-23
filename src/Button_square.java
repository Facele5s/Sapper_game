import javax.swing.*;
import java.awt.*;

public class Button_square extends JButton {
    private final int width = 50;
    private final int height = 50;
    private int id;

    public Button_square(int id) {
        setPreferredSize(new Dimension(width, height));
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
