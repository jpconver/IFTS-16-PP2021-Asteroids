package ar.edu.ifts16.gameObjects;

import ar.edu.ifts16.graphics.Assets;
import ar.edu.ifts16.states.GameState;
import ar.edu.ifts16.vector.Vector2D;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Meteor extends MovingObject{

    private Size size;

    public Meteor(Vector2D position, Vector2D velocity, double maxVel, BufferedImage texture, GameState gameState, Size size) {
        super(position, velocity, maxVel, texture, gameState);
        this.size = size;
        this.velocity = velocity.scale(maxVel);
    }

    @Override
    protected void destroy() {
        gameState.dividemeteor(this);
        gameState.addScore(Parameters.METEOR_SCORE, position);
        super.destroy();
    }

    @Override
    public void update() {
        position = position.add(velocity);
        // seteo los limites de la posicion sobre los limites de la ventana
        if (position.getX() > Parameters.WIDTH)
            position.setX(-width);
        if (position.getY() > Parameters.HEIGHT)
            position.setY(-height);
        if (position.getX() < -width)
            position.setX(Parameters.WIDTH);
        if (position.getY() < -height)
            position.setY(Parameters.HEIGHT);

        angle += Parameters.DELTAANGLE/2;
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        at = AffineTransform.getTranslateInstance(position.getX(), position.getY());
        at.rotate(angle, width / 2, height / 2);
        g2d.drawImage(texture, at, null);
    }

    public Size getSize() {
        return size;
    }
}
