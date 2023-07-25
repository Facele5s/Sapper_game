import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Area {
    //0 - clear, 1-8 - number of bombs near, 9 - bomb
    private final int[][] cells = new int[10][10];    //Game field
    private final Set<Integer> opened_cells = new HashSet<>();  //Set with opened cells. Stop game, when size == 100

    private int marks_left;    //Number of remaining bombs

    public Area(int x_start, int y_start) {

        cells[y_start][x_start] = 0;    //Start cell must be blank

        int number_bombs = 30;
        marks_left = number_bombs;

        for (int i = 0; i < number_bombs; ) { //Generate bombs in random cells
            int x = (int) (Math.random() * 9); //Random x coordinate
            int y = (int) (Math.random() * 9); //Random y coordinate

            if (cells[y][x] != 9 && Math.abs(x - x_start) + Math.abs(y - y_start) > 2) {  //Generate new bomb
                //Not near the start cell
                //Not in the same cell with the other bomb
                cells[y][x] = 9;
                i++;
            }
        }

        for (int i = 0; i < 10; i++) {   //Count bombs near every cell
            for (int j = 0; j < 10; j++) {
                if (cells[i][j] != 9) cells[i][j] = countBombs(j, i);
            }
        }
    }

    public int countBombs(int x, int y) {   //Count bombs around selected cell
        int count = 0;

        if (x - 1 >= 0 && y - 1 >= 0 && cells[y - 1][x - 1] == 9) count++;
        if (y - 1 >= 0 && cells[y - 1][x] == 9) count++;
        if (x + 1 < 10 && y - 1 >= 0 && cells[y - 1][x + 1] == 9) count++;
        if (x + 1 < 10 && cells[y][x + 1] == 9) count++;
        if (x + 1 < 10 && y + 1 < 10 && cells[y + 1][x + 1] == 9) count++;
        if (y + 1 < 10 && cells[y + 1][x] == 9) count++;
        if (x - 1 >= 0 && y + 1 < 10 && cells[y + 1][x - 1] == 9) count++;
        if (x - 1 >= 0 && cells[y][x - 1] == 9) count++;

        return count;
    }

    public int getCellVal(int x, int y) {
        return cells[y][x];
    }

    public void openSpace(int x, int y, Map<Integer, Button_square> btns) {
        //If blank cell was chosen, open space with border of numbers

        if (cells[y][x] == 0 && !opened_cells.contains(10 * y + x)) {   //10 * y + x == button id
            opened_cells.add(10 * y + x);
            btns.get(y * 10 + x).show(cells[y][x]); //Open cell if it is blank

            //Check cells around
            if (x - 1 >= 0) openSpace(x - 1, y, btns);
            if (x + 1 < 10) openSpace(x + 1, y, btns);
            if (y - 1 >= 0) openSpace(x, y - 1, btns);
            if (y + 1 < 10) openSpace(x, y + 1, btns);

            if (x - 1 >= 0 && y - 1 >= 0) openSpace(x - 1, y - 1, btns);
            if (x - 1 >= 0 && y + 1 < 10) openSpace(x - 1, y + 1, btns);
            if (x + 1 < 10 && y - 1 >= 0) openSpace(x + 1, y - 1, btns);
            if (x + 1 < 10 && y + 1 < 10) openSpace(x + 1, y + 1, btns);
        } else {
            btns.get(y * 10 + x).show(cells[y][x]); //If cell contains number, open it and stop checking
            opened_cells.add(10 * y + x);
        }
    }

    public Set<Integer> getOpened_cells() {
        return opened_cells;
    }

    public int marksLeft() {
        return marks_left;
    }

    public void mark(int n) {
        marks_left += n;
    } //Take or put marks
}
