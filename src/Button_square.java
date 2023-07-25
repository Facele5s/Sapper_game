import javax.swing.*;
import java.awt.*;

public class Button_square extends JButton {

    private final static ImageIcon ico_btn;
    private final static ImageIcon ico_btn_pressed;
    private final static ImageIcon ico_blank;
    private final static ImageIcon ico_1;
    private final static ImageIcon ico_2;
    private final static ImageIcon ico_3;
    private final static ImageIcon ico_4;
    private final static ImageIcon ico_5;
    private final static ImageIcon ico_6;
    private final static ImageIcon ico_7;
    private final static ImageIcon ico_8;
    private final static ImageIcon ico_expl;
    private final static ImageIcon ico_bomb;
    private final static ImageIcon ico_bomb_def;
    private final static ImageIcon ico_mark;

    private boolean marked = false;

    static {
        ico_btn = new ImageIcon("img/btn.png");
        ico_btn_pressed = new ImageIcon("img/btn_pressed.png");
        ico_blank = new ImageIcon("img/blank.png");
        ico_1 = new ImageIcon("img/btn_1.png");
        ico_2 = new ImageIcon("img/btn_2.png");
        ico_3 = new ImageIcon("img/btn_3.png");
        ico_4 = new ImageIcon("img/btn_4.png");
        ico_5 = new ImageIcon("img/btn_5.png");
        ico_6 = new ImageIcon("img/btn_6.png");
        ico_7 = new ImageIcon("img/btn_7.png");
        ico_8 = new ImageIcon("img/btn_8.png");
        ico_expl = new ImageIcon("img/expl.png");
        ico_bomb = new ImageIcon("img/bomb.png");
        ico_bomb_def = new ImageIcon("img/bomb_def.png");
        ico_mark = new ImageIcon("img/mark.png");
    }

    private final int id;

    public Button_square(int id) {
        setPreferredSize(new Dimension(50, 50));
        setBorderPainted(false);
        setIcon(ico_btn);
        setPressedIcon(ico_btn_pressed);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void show(int val) {
        switch (val) {
            case -1, 0 -> {
                setDisabledIcon(ico_blank);
                setEnabled(false);
            }
            case 1 -> {
                setDisabledIcon(ico_1);
                setEnabled(false);
            }
            case 2 -> {
                setDisabledIcon(ico_2);
                setEnabled(false);
            }
            case 3 -> {
                setDisabledIcon(ico_3);
                setEnabled(false);
            }
            case 4 -> {
                setDisabledIcon(ico_4);
                setEnabled(false);
            }
            case 5 -> {
                setDisabledIcon(ico_5);
                setEnabled(false);
            }
            case 6 -> {
                setDisabledIcon(ico_6);
                setEnabled(false);
            }
            case 7 -> {
                setDisabledIcon(ico_7);
                setEnabled(false);
            }
            case 8 -> {
                setDisabledIcon(ico_8);
                setEnabled(false);
            }
            case 9 -> {
                setDisabledIcon(ico_expl);
                setEnabled(false);
            }
            case 10 -> {
                setDisabledIcon(ico_bomb);
                setEnabled(false);
            }
        }

        setEnabled(false);
    }

    public boolean isMarked() {
        return marked;
    }

    public void swapMark() {
        marked = !marked;
        if(marked) {
            setIcon(ico_mark);
        } else setIcon(ico_btn);
    }

    public void setDefused() {
        setDisabledIcon(ico_bomb_def);
        setEnabled(false);
    }
}
