package ar.edu.ifts16.gameObjects;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import ar.edu.ifts16.graphics.Assets;
import ar.edu.ifts16.graphics.Sound;
import ar.edu.ifts16.input.KeyBoard;
import ar.edu.ifts16.states.GameState;
import ar.edu.ifts16.vector.Vector2D;

public class Player extends MovingObject {

    private Vector2D heading;
    private Vector2D acceleration;
    private boolean accelereting = false;
    private Chronometer fireRate;
    private boolean spawning, visible;
    private Chronometer spawnTime, flickerTime;
    private Sound shoot, loose;

    public Player(Vector2D position, Vector2D velocity, double maxVel, BufferedImage texture, GameState gameState) {
        super(position, velocity, maxVel, texture, gameState);
        heading = new Vector2D(0, 1);
        acceleration = new Vector2D();
        fireRate = new Chronometer();
        spawnTime = new Chronometer();
        flickerTime = new Chronometer();
        shoot = new Sound(Assets.playerShoot);
        loose = new Sound(Assets.playerLoose);
    }

    @Override
    protected void destroy() {
        spawning = true;
        spawnTime.run(Parameters.SPAWNING_TIME);
        loose.play();
        if (!gameState.subtractLife()){
            gameState.gameOver();
            super.destroy();
        }
        resetValues();
    }

    private void resetValues() {
        angle = 0;
        velocity = new Vector2D();
        position = new Vector2D(Parameters.WIDTH / 2 - Assets.player.getWidth() / 2,
                Parameters.HEIGHT / 2 - Assets.player.getHeight() / 2);
    }

    @Override
    public void update() {
        if (!spawnTime.isRunning()) {
            spawning = false;
            visible = true;
        }
        if (spawning) {
            if (!flickerTime.isRunning()) {
                flickerTime.run(Parameters.FLICKER_TIME);
                visible = !visible;
            }
        }
        // pregunto si dispara y pregunto si 'time' es mayor al tiempo de espera que le pongo para disparar
        if (KeyBoard.SHOOT && !fireRate.isRunning() && !spawning) {
            gameState.getMovingObjects().add(0, new Laser(getCenter().add(heading.scale(width)), heading, Parameters.LASER_VEL,
                    angle, Assets.redLaser, gameState));
            fireRate.run(Parameters.FIRERATE);
            shoot.play();
        }
        //recorta sonido
        if (shoot.getFramePosition() > 8500) {
            shoot.stop();
        }
        if (KeyBoard.RIGHT)
            angle += Parameters.DELTAANGLE;
        if (KeyBoard.LEFT)
            angle -= Parameters.DELTAANGLE;
        if (KeyBoard.UP) {
            accelereting = true;
            acceleration = heading.scale(Parameters.ACC);
        } else {
            if (velocity.getMagnitude() != 0) // implementa desaceleracion
                acceleration = (velocity.scale(-1).normalize()).scale(Parameters.ACC / 2);
            accelereting = false;
        }
        velocity = velocity.add(acceleration);
        velocity = velocity.limit(maxVel);
        heading = heading.setDirection(angle - Math.PI / 2);
        position = position.add(velocity);

        // seteo los limites de la posicion sobre los limites de la ventana
        if (position.getX() > Parameters.WIDTH)
            position.setX(0);
        if (position.getY() > Parameters.HEIGHT)
            position.setY(0);
        if (position.getX() < 0)
            position.setX(Parameters.WIDTH);
        if (position.getY() < 0)
            position.setY(Parameters.HEIGHT);

        //actiaizo cronometros
        fireRate.update(); // disparo
        spawnTime.update(); // revivir
        flickerTime.update(); // invisible

        collidedWith();
    }


    @Override
    public void draw(Graphics g) {
        if (!visible)
            return;
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform at1 = AffineTransform.getTranslateInstance(position.getX() + width / 2 + 5,
                position.getY() + height / 2 + 10);
        AffineTransform at2 = AffineTransform.getTranslateInstance(position.getX() + 5, position.getY() + height / 2 + 10);
        at1.rotate(angle, -5, -10);
        at2.rotate(angle, width / 2 - 5, -10);
        if (accelereting) {
            g2d.drawImage(Assets.speed, at1, null);
            g2d.drawImage(Assets.speed, at2, null);
        }
        at = AffineTransform.getTranslateInstance(position.getX(), position.getY());
        at.rotate(angle, width / 2, height / 2);
        g2d.drawImage(texture, at, null);
    }

    public boolean isSpawning() {
        return spawning;
    }
}
