import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Main extends JFrame implements ActionListener, MouseListener, Runnable {
    Area area;

    JPanel p_info;
    JPanel p_game;

    JLabel l_time;
    JLabel l_marks;

    JButton smile;

    Font seven_segment;

    Map<Integer, Button_square> btns = new HashMap<>(); //All buttons in game panel

    //Face button icons
    ImageIcon ico_face_dead = new ImageIcon("img/face_dead.png");
    ImageIcon ico_face_o = new ImageIcon("img/face_o.png");
    ImageIcon ico_face_smile = new ImageIcon("img/face_smile.png");

    int game_status = 0; //0 - waiting, 1 - started, 2 - finished

    Timer timer;

    public Main() {
        super("The impossible game");
        setSize(500, 600);
        setResizable(false);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setIconImage(new ImageIcon("img/icon.png").getImage());

        try {   //Read seven segment font from file
            seven_segment = Font.createFont(Font.TRUETYPE_FONT, new File("Seven Segment.ttf"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Panel with timer, face button and number of remaining marks
        p_info = new JPanel();
        p_info.setBackground(new Color(191, 191, 191));
        p_info.setBorder(BorderFactory.createLineBorder(new Color(114, 114, 114), 4));
        p_info.setPreferredSize(new Dimension(500, 100));
        p_info.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 10));
        add(p_info, BorderLayout.PAGE_START);

        //Panel with game buttons
        p_game = new JPanel();
        p_game.setPreferredSize(new Dimension(500, 500));
        p_game.setLayout(new GridLayout(10, 10));
        add(p_game, BorderLayout.PAGE_END);

        //Fill game panel with buttons
        for (int i = 0; i < 100; i++) {
            Button_square b = new Button_square(i);
            b.addActionListener(this);
            p_game.add(b);
            btns.put(i, b);
            b.addMouseListener(this);
        }

        //Timer display
        l_time = new JLabel("00:00");
        l_time.setForeground(Color.RED);
        l_time.setBackground(Color.BLACK);
        l_time.setFont(seven_segment.deriveFont(62f));
        l_time.setOpaque(true);
        p_info.add(l_time);

        //Face button
        smile = new JButton();
        smile.setIcon(ico_face_smile);
        smile.setPressedIcon(ico_face_o);
        smile.setPreferredSize(new Dimension(50, 50));
        smile.addActionListener(this);
        p_info.add(smile);

        //Number of remaining marks
        l_marks = new JLabel("--");
        l_marks.setForeground(Color.RED);
        l_marks.setBackground(Color.BLACK);
        l_marks.setFont(seven_segment.deriveFont(62f));
        l_marks.setOpaque(true);
        p_info.add(l_marks);

        pack();
        setVisible(true);
    }

    @Override
    public void run() {

    }

    public static void main(String[] args) {

        //The game and game timer are running in different threads
        //Timer thread starts after game starts (first cell is opened)
        Thread game = new Thread(new Main(), "Game");
        game.start();
    }


    @Override
    public void actionPerformed(ActionEvent e) {    //Some button was pressed
        if (e.getSource() == smile) { //Face smile was pressed
            //Reset game
            if (game_status > 0) game_reset();
            return;
        }

        //<----- Game button was pressed ----->

        Button_square b = (Button_square) e.getSource();

        //You can open marked cell
        if (b.isMarked()) return;

        //Coordinates of button
        int x = b.getId() % 10;
        int y = b.getId() / 10;

        //If game hasn't started yet
        if (game_status == 0) {
            game_status = 1;    //Start the game
            timer = new Timer(this);    //Initialize the game timer thread
            timer.start();  //Start the timer thread

            area = new Area(x, y);  //Initialize game area from start cell
            l_marks.setText(Integer.toString(area.marksLeft()));    //Display the number of remaining marks
        }

        int val = area.getCellVal(x, y);    //Get cell value
        b.show(val);    //Open the cell according to it's value

        if (val == 9) { //If player opens cell with bomb
            //The game is lost
            game_lose();
        } else if (val == 0) {  //If opened cell is blank
            //Open space near the blank cell with border of numbers
            area.openSpace(x, y, btns);
        } else {
            //In this case cell has a number. Show it
            //10 * y + x == button id
            area.getOpened_cells().add(10 * y + x);
        }

        //If all cells were opened, the game is won
        if (area.getOpened_cells().size() == 100) game_win();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //If there was a click on a button, but button wasn't pressed
        //Than it was right click

        //Player can't mark the cell if the game wasn't started or was lost
        if (game_status == 0 || game_status == 2) return;

        Button_square btn = (Button_square) e.getSource();
        if (btn.isEnabled()) {  //If button isn't pressed after click

            //Player can't mark the cell if they run out
            if (!btn.isMarked() && area.marksLeft() == 0) return;

            //Swap mark on the button
            btn.swapMark();

            if (btn.isMarked()) {
                area.getOpened_cells().add(btn.getId());
                area.mark(-1);  //Take one mark
            } else {
                area.getOpened_cells().remove(btn.getId());
                area.mark(1);   //Put mark back
            }
            //Update remaining marks number
            l_marks.setText(Integer.toString(area.marksLeft()));

            //If all cells were opened, the game is won
            if (area.getOpened_cells().size() == 100) game_win();
        }
    }

    //They are here only for implementing MouseListener interface
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
        smile.setIcon(ico_face_o);
        //Stop the timer
        timer.interrupt();

        //Show cells with defused bombs
        for (Button_square btn : btns.values()) {
            if (btn.isMarked()) btn.setDefused();
        }

        l_time.setText("YOU WIN!");
        game_status = 2;    //The game is stopped
    }

    public void game_lose() {
        smile.setIcon(ico_face_dead);
        //Stop the timer
        timer.interrupt();

        for (Button_square btn : btns.values()) {
            if (btn.isEnabled()) {
                if (btn.isMarked()) {   //Show cells with defused bombs
                    btn.setDefused();
                } else {
                    //Show cells with number and bombs
                    int x = btn.getId() % 10;
                    int y = btn.getId() / 10;
                    int val = area.getCellVal(x, y);
                    if (val == 9) val++;    //10 - not defused bomb
                    btn.show(val);
                }
            }

        }
        game_status = 2;    //The game is stopped
    }

    public void game_reset() {
        smile.setIcon(ico_face_smile);
        //Stop the timer
        timer.interrupt();

        for (Button_square btn : btns.values()) {
            //Close all cells
            btn.setEnabled(true);
            //Remove all marks
            if (btn.isMarked()) btn.swapMark();
        }

        game_status = 0;    //Reset the game state
        l_time.setText("00:00");
        l_marks.setText("--");
    }

    public void updateTime(String time) {
        l_time.setText(time);
    }

}