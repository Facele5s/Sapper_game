import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class Main extends JFrame implements ActionListener {
    Area area;

    JPanel p_info;
    JPanel p_game;

    JLabel l_time;
    JLabel l_marks;

    JButton smile;

    Font seven_segment;

    public Main() {
        super("Sapper");
        setSize(500, 600);
        setResizable(false);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        area = new Area(1);
        area.gameStart();

        try {
            seven_segment = Font.createFont(Font.TRUETYPE_FONT, new File("Seven Segment.ttf"));
        }
        catch (IOException e) {
            System.out.println(e);
        }
        catch (FontFormatException e) {
            System.out.println(e);
        }

        p_info = new JPanel();
        p_info.setBackground(new Color(191, 191, 191));
        p_info.setBorder(BorderFactory.createLineBorder(new Color(114, 114, 114), 4));
        p_info.setPreferredSize(new Dimension(500, 100));
        p_info.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 10));
        add(p_info, BorderLayout.PAGE_START);

        p_game = new JPanel();
        p_game.setPreferredSize(new Dimension(500, 500));
        p_game.setLayout(new GridLayout(10, 10));
        add(p_game, BorderLayout.PAGE_END);

        for(int i = 0; i < 100; i++) {
            Button_square b = new Button_square(i);
            b.setBorderPainted(false);
            b.setIcon(new ImageIcon("img/btn.png"));
            b.setPressedIcon(new ImageIcon("img/btn_pressed.png"));
            b.addActionListener(this);
            p_game.add(b);
        }

        l_time = new JLabel("00:00");
        l_time.setForeground(Color.RED);
        l_time.setBackground(Color.BLACK);
        l_time.setFont(seven_segment.deriveFont(62f));
        l_time.setOpaque(true);
        p_info.add(l_time);

        smile = new JButton();
        smile.setIcon(new ImageIcon("img/face_smile.png"));
        smile.setPreferredSize(new Dimension(50, 50));
        p_info.add(smile);

        l_marks = new JLabel("20");
        l_marks.setForeground(Color.RED);
        l_marks.setBackground(Color.BLACK);
        l_marks.setFont(seven_segment.deriveFont(62f));
        l_marks.setOpaque(true);
        p_info.add(l_marks);

        pack();
        setVisible(true);
    }

    public static void main(String[] args) throws InterruptedException {
        Main window = new Main();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Button_square b = (Button_square) e.getSource();
        b.setEnabled(false);

        int x = b.getId() % 10;
        int y = b.getId() / 10;
        int val = area.openCell(x, y);

        if(val == 9) {
            b.setDisabledIcon(new ImageIcon("img/expl.png"));
            smile.setIcon(new ImageIcon("img/face_dead.png"));
        } else if(val == 0) {
            b.setDisabledIcon(new ImageIcon("img/blank.png"));
        } else {
            String path = String.format("img/btn_%d.png", val);
            b.setDisabledIcon(new ImageIcon(path));
        }
    }
}