package ar.edu.ifts16.gameObjects;

import ar.edu.ifts16.graphics.Assets;
import ar.edu.ifts16.graphics.Sound;
import ar.edu.ifts16.states.GameState;
import ar.edu.ifts16.vector.Vector2D;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Ufo extends MovingObject {
    private ArrayList<Vector2D> road;
    private Vector2D currentNode;
    private int index;
    private boolean following;
    private Chronometer fireRate;
    private Sound shoot;

    public Ufo(Vector2D position, Vector2D velocity, double maxVel, BufferedImage texture, ArrayList<Vector2D> road, GameState gameState) {
        super(position, velocity, maxVel, texture, gameState);
        this.road = road;
        index = 0;
        following = true;
        fireRate = new Chronometer();
        fireRate.run(Parameters.UFO_FIRE_RATE);
        shoot = new Sound(Assets.ufoShoot);
    }

    private Vector2D roadFollowing() {
        currentNode = road.get(index);
        double distanceToNode = currentNode.subtrac(getCenter()).getMagnitude();
        if (distanceToNode < Parameters.NODE_RADIUS) {
            index++;
            if (index >= road.size()) {
                following = false;
            }
        }
        return seekForce(currentNode);
    }

    private Vector2D seekForce(Vector2D target) {
        Vector2D desiredVelocity = target.subtrac(getCenter());
        desiredVelocity = desiredVelocity.normalize().scale(maxVel);
        return desiredVelocity.subtrac(velocity);
    }

    @Override
    protected void destroy() {
        gameState.addScore(Parameters.UFO_SCORE, position);
        super.destroy();
    }

    @Override
    public void update() {
        Vector2D pathFollowing;
        if(following)
            pathFollowing = roadFollowing();
        else
            pathFollowing = new Vector2D();
        pathFollowing = pathFollowing.scale(1/Parameters.UFO_MASS);
        velocity = velocity.add(pathFollowing);
        velocity = velocity.limit(maxVel);
        position = position.add(velocity);
        if(position.getX() > Parameters.WIDTH || position.getY() > Parameters.HEIGHT
                || position.getX() < 0 || position.getY() < 0)
            destroy();

         //shoot
        if(!fireRate.isRunning()) {
            Vector2D toPlayer = gameState.getPlayer().getCenter().subtrac(getCenter());
            toPlayer = toPlayer.normalize();
            double currentAngle = toPlayer.getAngle();
            currentAngle += Math.random()*Parameters.UFO_ANGLE_RANGE - Parameters.UFO_ANGLE_RANGE / 2;
            // si coordenada x, es negativa, para calcular el angulo de disparo, debo, obtener el adyasente (currentAngle*-1) y restarle 180°
            if (toPlayer.getX() < 0){
                currentAngle = -currentAngle + Math.PI;
            }
            toPlayer = toPlayer.setDirection(currentAngle);
            Laser laser = new Laser(
                    getCenter().add(toPlayer.scale(width)),
                    toPlayer,
                    Parameters.LASER_VEL,
                    currentAngle + Math.PI/2,
                    Assets.redLaser,
                    gameState
            );
            gameState.getMovingObjects().add(0, laser);
            fireRate.run(Parameters.UFO_FIRE_RATE);
            shoot.play();
        }
        if (shoot.getFramePosition() > 8500) {
            shoot.stop();
        }
        angle += 0.05;
        collidedWith();
        fireRate.update();
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        at = AffineTransform.getTranslateInstance(position.getX(), position.getY());
        at.rotate(angle, width/2, height/2);
        g2d.drawImage(texture, at, null);
//        g.setColor(Color.RED);
//        for (int i = 0; i < road.size(); i++){
//            g.drawRect((int)road.get(i).getX(), (int)road.get(i).getY(), 5, 5);
//        }
    }
}
