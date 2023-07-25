public class Timer extends Thread {
    private Main game;
    private boolean stopped = false;

    public Timer(Main game) {
        this.game = game;
    }

    @Override
    public void run() {
        int min = 0;
        int sec = 0;

        while(!stopped) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                stopped = true;
            }

            if(min != 99) {
                if(sec == 59) {
                    min++;
                    sec = 0;
                }
            }
            if(sec != 99) sec++;

            String seconds = sec < 10 ? "0"+sec : Integer.toString(sec);
            String minutes = min < 10 ? "0"+min : Integer.toString(min);

            String timer = minutes + ":" + seconds;
            game.updateTime(timer);

            if(sec == 99) interrupt();
        }
    }
}
