package ar.edu.ifts16.gameObjects;

public class Chronometer {
    private long delta, lasTime, time;
    private boolean running;

    public Chronometer() {
        delta = 0;
        lasTime = System.currentTimeMillis();
        running = false;
    }

    public void run(long time) {
        running = true;
        this.time = time;
    }

    public void update() {
        if (running)
            delta += System.currentTimeMillis() - lasTime;
        if (delta >= time){
            running = false;
            delta = 0;
        }
        lasTime = System.currentTimeMillis();
    }

    public  boolean isRunning(){
        return running;
    }
}
