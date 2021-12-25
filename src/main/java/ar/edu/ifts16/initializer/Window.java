package ar.edu.ifts16.initializer;

import ar.edu.ifts16.gameObjects.Parameters;
import ar.edu.ifts16.graphics.Assets;
import ar.edu.ifts16.input.KeyBoard;
import ar.edu.ifts16.input.MouseInput;
import ar.edu.ifts16.states.MenuState;
import ar.edu.ifts16.states.State;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class Window extends JFrame implements Runnable{

    private Canvas canvas;
    private Thread thread;
    private boolean running = false;
    private BufferStrategy bs;
    private Graphics g;
    private final int FPS = 60;
    private double TARGETTIME = 1000000000/FPS;
    private double delta = 0;
    private int AVERAGEFPS = FPS;
    private KeyBoard keyBoard;
    private MouseInput mouseInput;


    public Window()
    {
        setTitle("Galactic Ship");
        setSize(Parameters.WIDTH, Parameters.HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        canvas = new Canvas();
        keyBoard = new KeyBoard();
        mouseInput = new MouseInput();
        canvas.setPreferredSize(new Dimension(Parameters.WIDTH, Parameters.HEIGHT));
        canvas.setMaximumSize(new Dimension(Parameters.WIDTH, Parameters.HEIGHT));
        canvas.setMinimumSize(new Dimension(Parameters.WIDTH, Parameters.HEIGHT));
        canvas.setFocusable(true);
        add(canvas);
        canvas.addKeyListener(keyBoard);
        canvas.addMouseListener(mouseInput);
        canvas.addMouseMotionListener(mouseInput);
        setVisible(true);
    }

    private void draw(){
        bs = canvas.getBufferStrategy();
        if(bs == null)
        {
            canvas.createBufferStrategy(3);
            return;
        }
        g = bs.getDrawGraphics();
        //-----------------------
        g.setColor(Color.BLACK);
        g.fillRect(0,0,Parameters.WIDTH, Parameters.HEIGHT);
        //gameState.draw(g);
        State.getCurrentState().draw(g);
        g.drawString(""+AVERAGEFPS, 10, 20);
        //---------------------
        g.dispose();
        bs.show();
    }

    private void update(){
        keyBoard.update();
        State.getCurrentState().update();
    }

    private void start(){
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    private void stop(){
        try {
            thread.join();
            running = false;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private  void init() {
        Assets.init();
        State.changeState(new MenuState());
    }

    @Override
    public void run() {
        long now = 0;
        long lastTime = System.nanoTime();
        int frames = 0;
        long time = 0;
        init();
        while(running)
        {
            // calculo y establezco los FPS para que el juego corra igual en todas las pc
            now = System.nanoTime();
            delta += (now - lastTime)/TARGETTIME;
            time += (now - lastTime);
            lastTime = now;
            if(delta >= 1)
            {
                update();
                draw();
                delta --;
                frames ++;
            }
            if(time >= 1000000000)
            {
                AVERAGEFPS = frames;
                frames = 0;
                time = 0;
            }
        }
        stop();
    }

    public static void main(String[] args) {
        new Window().start();
    }
}
