public class Area {
    private int[][] cells = new int[10][10];    //Game field

    //0 - clear, 1-8 - number of bombs near, 9 - bomb
    private int number_bombs = 10;  //Number of bombs. Depends on game difficulty
    private int bombs_left = 10;    //Number of remaining bombs
    private int difficulty = 1; //1 - Easy (10 bombs), 2 - medium (15 bombs), 3 - hard (20 bombs)

    public Area(int difficulty) {
        this.difficulty = difficulty;

        number_bombs = difficulty * 10 + 10;
        bombs_left = number_bombs;

        for(int i = 0; i < number_bombs;) { //Generate bombs in random cells
            int x = (int) (Math.random() * 10);
            int y = (int) (Math.random() * 10);

            if(cells[y][x] != 9) {  //Generate new bomb
                cells[y][x] = 9;
                i++;
            }
        }

        for(int i = 0; i < 10; i++) {   //Count bombs near every cell
            for(int j = 0; j < 10; j++) {
                if(cells[i][j] != 9) cells[i][j] = countBombs(j, i);
            }
        }
    }

    public int countBombs(int x, int y) {
        int count = 0;

        if(x - 1 >= 0 && y - 1 >= 0 && cells[y-1][x-1] == 9) count++;
        if(y - 1 >= 0 && cells[y-1][x] == 9) count++;
        if(x + 1 < 10 && y - 1 >= 0 && cells[y-1][x+1] == 9) count++;
        if(x + 1 < 10 && cells[y][x+1] == 9) count++;
        if(x + 1 < 10 && y + 1 < 10 && cells[y+1][x+1] == 9) count++;
        if(y + 1 < 10 && cells[y+1][x] == 9) count++;
        if(x - 1 >= 0 && y + 1 < 10 && cells[y+1][x-1] == 9) count++;
        if(x - 1 >= 0 && cells[y][x-1] == 9) count++;

        return count;
    }

    public void openCell() {

    }

    public void markCell() {

    }

    public void gameStart() {

    }

    public void gameStop() {

    }

    public void gameFinish() {

    }

    public void gameRestart() {

    }


}
