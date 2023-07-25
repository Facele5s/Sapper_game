public class Timer extends Thread {
    private final Main game;
    private boolean stopped = false;

    public Timer(Main game) {
        this.game = game;
    }

    @Override
    public void run() {
        int min = 0;    //Minutes
        int sec = 0;    //Seconds

        while (!stopped) {  //Run until interrupted
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                stopped = true;
            }

            if (min != 99) {    //Count minutes up to 99, then stop //Count seconds up to 60
                if (sec == 59) {
                    min++;
                    sec = 0;
                }
            }
            if (sec != 99) sec++;   //Count seconds up to 99, then stop

            String seconds = sec < 10 ? "0" + sec : Integer.toString(sec); //Formatting
            String minutes = min < 10 ? "0" + min : Integer.toString(min);

            String timer = minutes + ":" + seconds;
            game.updateTime(timer); //Update game timer

            if (sec == 99) {    //Stop timer if it reached 99:99
                interrupt();
                game.updateTime("FACELESS");   //Easter egg
            }
        }
    }
}
