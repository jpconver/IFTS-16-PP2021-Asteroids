package ar.edu.ifts16.graphics;

import ar.edu.ifts16.vector.Vector2D;

import java.awt.image.BufferedImage;

public class Animation {

    private BufferedImage[] frames;
    private int velocity;
    private int index;
    private boolean running;
    private Vector2D position;
    private long time, lasTime;

    public Animation(BufferedImage[] frames, int velocity, Vector2D position) {
        this.frames = frames;
        this.velocity = velocity;
        this.position = position;
        index = 0;
        running = true;
        time = 0;
        lasTime = System.currentTimeMillis();
    }

    public void update() {
        time += System.currentTimeMillis() - lasTime;
        lasTime = System.currentTimeMillis();
        if (time > velocity){
            time = 0;
            index ++;
            if (index >= frames.length) {
                running = false;
            }
        }
    }

    public boolean isRunning() {
        return running;
    }

    public Vector2D getPosition() {
        return position;
    }

    public BufferedImage getCurrentFrame() {
        return frames[index];
    }
}
