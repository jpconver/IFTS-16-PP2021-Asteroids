package ar.edu.ifts16.gameObjects;

import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import ar.edu.ifts16.graphics.Assets;
import ar.edu.ifts16.graphics.Sound;
import ar.edu.ifts16.states.GameState;
import ar.edu.ifts16.vector.Vector2D;

public abstract class MovingObject extends GameObject {

    protected Vector2D velocity;
    protected AffineTransform at;
    protected double angle;
    protected double maxVel;
    protected int width;
    protected  int height;
    protected GameState gameState;
    private Sound explosion;
    protected boolean dead;


    public MovingObject(Vector2D position, Vector2D velocity, double maxVel, BufferedImage texture, GameState gameState) {
        super(position, texture);
        this.velocity = velocity;
        this.maxVel = maxVel;
        this.gameState = gameState;
        width = texture.getWidth();
        height = texture.getHeight();
        angle = 0;
        explosion = new Sound(Assets.explosion);
        dead = false;
    }

    protected void collidedWith(){
        ArrayList<MovingObject> movingObjects = gameState.getMovingObjects();
        for (int i = 0; i<movingObjects.size(); i++){
            MovingObject m = movingObjects.get(i);
            if (m.equals(this))
                continue;
            double distance = m.getCenter().subtrac(getCenter()).getMagnitude();
            if (distance < m.width/2 + width/2 && movingObjects.contains(this) && !m.dead && !dead) {
                objectCollission(m, this);
            }
        }
    }

    private void objectCollission(MovingObject a, MovingObject b){
        if(a instanceof Player && ((Player)a).isSpawning()) {
            return;
        }
        if(b instanceof Player && ((Player)b).isSpawning()) {
            return;
        }
        if (!(a instanceof Meteor && b instanceof Meteor)){
            gameState.starExplosion(getCenter());
            a.destroy();
            b.destroy();
        }
    }

    protected void destroy(){
        dead = true;
        if(!(this instanceof Laser))
            explosion.play();
    }

    protected Vector2D getCenter() {
        return new Vector2D(position.getX() + width / 2, position.getY() + height / 2);
    }

    public boolean isDead() {
        return dead;
    }
}
