import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Main extends JFrame implements ActionListener, MouseListener {
    Area area;

    JPanel p_info;
    JPanel p_game;

    JLabel l_time;
    JLabel l_marks;

    JButton smile;

    Font seven_segment;

    Map<Integer, Button_square> btns = new HashMap<>();

    ImageIcon ico_face_dead = new ImageIcon("img/face_dead.png");
    ImageIcon ico_face_o = new ImageIcon("img/face_o.png");
    ImageIcon ico_face_smile = new ImageIcon("img/face_smile.png");

    int game_status = 0; //0 - waiting, 1 - started, 2 - finished

    public Main() {
        super("Sapper");
        setSize(500, 600);
        setResizable(false);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        try {
            seven_segment = Font.createFont(Font.TRUETYPE_FONT, new File("Seven Segment.ttf"));
        }
        catch (Exception e) {
            e.printStackTrace();
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
            b.addActionListener(this);
            p_game.add(b);
            btns.put(i, b);
            b.addMouseListener(this);
        }

        l_time = new JLabel("00:00");
        l_time.setForeground(Color.RED);
        l_time.setBackground(Color.BLACK);
        l_time.setFont(seven_segment.deriveFont(62f));
        l_time.setOpaque(true);
        p_info.add(l_time);

        smile = new JButton();
        smile.setIcon(ico_face_smile);
        smile.setPressedIcon(ico_face_o);
        smile.setPreferredSize(new Dimension(50, 50));
        smile.addActionListener(this);
        p_info.add(smile);

        l_marks = new JLabel("--");
        l_marks.setForeground(Color.RED);
        l_marks.setBackground(Color.BLACK);
        l_marks.setFont(seven_segment.deriveFont(62f));
        l_marks.setOpaque(true);
        p_info.add(l_marks);

        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        Main window = new Main();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == smile) { //Reset game
            game_reset();
            return;
        }

        Button_square b = (Button_square) e.getSource();

        if(b.isMarked()) return;

        int x = b.getId() % 10;
        int y = b.getId() / 10;

        if(game_status == 0) {
            game_status = 1;

            area = new Area(x, y);
            l_marks.setText(Integer.toString(area.marksLeft()));
        }

        int val = area.getCellVal(x, y);
        b.show(val);

        if(val == 9) {
            game_lose();
        } else if(val == 0) {
            area.openSpace(x, y, btns);
        } else {
            area.getOpened_cells().add(10 * y + x);
        }

        if(area.getOpened_cells().size() == 100) game_win();
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
        if(game_status == 0 || game_status == 2) return;
        Button_square btn = (Button_square) e.getSource();
        if(btn.isEnabled()) {
            if(!btn.isMarked() && area.marksLeft() == 0) return;

            btn.swapMark();

            if(btn.isMarked()) {
                area.getOpened_cells().add(btn.getId());
                area.mark(-1);
            } else {
                area.getOpened_cells().remove(btn.getId());
                area.mark(1);
            }
            l_marks.setText(Integer.toString(area.marksLeft()));

            if(area.getOpened_cells().size() == 100) game_win();
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    public void game_win() {
        l_time.setText("YOU WIN!");

        for(Button_square btn: btns.values()) {
            if(btn.isMarked()) btn.setDefused();
        }

        smile.setIcon(ico_face_o);
        game_status = 2;
    }

    public void game_lose() {
        smile.setIcon(ico_face_dead);

        for(Button_square btn: btns.values()) {
            if(btn.isEnabled()) {
                if(btn.isMarked()) {
                    btn.setDefused();
                } else {
                    int x = btn.getId() % 10;
                    int y = btn.getId() / 10;
                    int val = area.getCellVal(x, y);
                    if(val == 9) val++;
                    btn.show(val);
                }
            }

        }
        game_status = 2;
    }

    public void game_reset() {
        smile.setIcon(ico_face_smile);

        for(Button_square btn: btns.values()) {
            btn.setEnabled(true);
            if(btn.isMarked()) btn.swapMark();
        }

        game_status = 0;
    }

}