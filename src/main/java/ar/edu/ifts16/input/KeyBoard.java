package ar.edu.ifts16.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyBoard implements KeyListener {
    private boolean[] keys = new boolean[256];
    public static boolean UP, LEFT, RIGHT, SHOOT;

    public KeyBoard() {
        UP = false;
        LEFT = false;
        RIGHT = false;
        SHOOT = false;
    }

    public void update() {
        UP = this.keys[KeyEvent.VK_UP];
        LEFT = this.keys[KeyEvent.VK_LEFT];
        RIGHT = this.keys[KeyEvent.VK_RIGHT];
        SHOOT = this.keys[KeyEvent.VK_SPACE];
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        this.keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        this.keys[e.getKeyCode()] = false;
    }
}
