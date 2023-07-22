import javax.swing.*;
import java.awt.*;

public class Button_square extends JButton {
    private final int width = 50;
    private final int height = 50;

    public Button_square() {
        setPreferredSize(new Dimension(width, height));
    }
}
