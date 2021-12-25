package ar.edu.ifts16.gameObjects;

import ar.edu.ifts16.states.GameState;
import ar.edu.ifts16.vector.Vector2D;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Laser extends MovingObject {
    public Laser(Vector2D position, Vector2D velocity, double maxVel, double angle, BufferedImage texture, GameState gameState) {
        super(position, velocity, maxVel, texture, gameState);
        this.angle = angle;
        this.velocity = velocity.scale(maxVel);
    }

    @Override
    public void update() {
        position = position.add(velocity);
        // condicion para que el laser no se actualice al infinito
        if (position.getX() < 0 || position.getX() > Parameters.WIDTH
                || position.getY() < 0 || position.getY() > Parameters.HEIGHT) {
            destroy();
        }
        collidedWith();
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        at = AffineTransform.getTranslateInstance(position.getX() - width / 2, position.getY());
        at.rotate(angle, width / 2, 0);
        g2d.drawImage(texture, at, null);
    }

    // para laser, debo mover el centro del objeto, al extremo que es donde se va a dar la colision
    @Override
    protected Vector2D getCenter() {
        return new Vector2D(position.getX() + width / 2, position.getY() + width / 2);
    }
}
