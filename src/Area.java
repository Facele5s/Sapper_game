public class Area {
    private int[][] cells = new int[10][10];    //Game field

    //0 - clear, 1-8 - number of bombs near, 9 - bomb
    private int number_bombs = 10;  //Number of bombs. Depends on game difficulty
    private int bombs_left = 10;    //Number of remaining bombs
    private int difficulty = 1; //1 - Easy (10 bombs), 2 - medium (15 bombs), 3 - hard (20 bombs)

    public Area(int difficulty) {
        this.difficulty = difficulty;

        number_bombs = difficulty * 5 + 5;
        bombs_left = number_bombs;

    }

    public int countBombs() {
        return 0;
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
