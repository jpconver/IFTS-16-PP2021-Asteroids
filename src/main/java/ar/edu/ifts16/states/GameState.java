package ar.edu.ifts16.states;

import ar.edu.ifts16.gameObjects.*;
import ar.edu.ifts16.graphics.Animation;
import ar.edu.ifts16.graphics.Assets;
import ar.edu.ifts16.graphics.Sound;
import ar.edu.ifts16.graphics.Text;
import ar.edu.ifts16.vector.Vector2D;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class GameState extends State {
    private Player player;
    private ArrayList<MovingObject> movingObjects = new ArrayList<>();
    private ArrayList<Animation> explosions = new ArrayList<>();
    private int meteors;
    private int score = 0;
    private int lives = Parameters.LIVES;
    private int levels = 1;
    private ArrayList<Message> messages = new ArrayList<>();
    private Sound backgroundMusic;
    private Chronometer gameOverTime;
    private boolean gameOver;
    private Chronometer ufoSpawner;
    public static final Vector2D PLAYER_POSITION = new Vector2D(Parameters.WIDTH / 2 - Assets.player.getWidth() / 2,
            Parameters.HEIGHT / 2 - Assets.player.getHeight() / 2);


    public GameState() {
        player = new Player(PLAYER_POSITION, new Vector2D(),
                Parameters.PLAYER_MAX_VEL, Assets.player, this);
        movingObjects.add(player);
        meteors = 10;
        starRain();
        backgroundMusic = new Sound(Assets.backgroundMusic);
        backgroundMusic.loop();
        backgroundMusic.changeVolume(-30.0F);
        ufoSpawner = new Chronometer();
        ufoSpawner.run(Parameters.UFO_CREATE);
        gameOverTime = new Chronometer();

    }

    public void update() {
        for (int i = 0; i < movingObjects.size(); i++) {
            MovingObject mo = movingObjects.get(i);
            mo.update();
            if (mo.isDead()){
                movingObjects.remove(i);
                i--;
            }
        }
        for (int i = 0; i < explosions.size(); i++) {
            Animation animation = explosions.get(i);
            animation.update();
            if (!animation.isRunning()) {
                explosions.remove(i);
            }
        }
        if (gameOver && !gameOverTime.isRunning()) {
            State.changeState(new MenuState());
        }
        if (!ufoSpawner.isRunning()) {
            ufoSpawner.run(Parameters.UFO_CREATE);
            showUfo();
        }
        gameOverTime.update();
        ufoSpawner.update();

        for (int i = 0; i < movingObjects.size(); i++)
            if (movingObjects.get(i) instanceof Meteor)
                return;
        starRain();
    }

    public void draw(Graphics g) {
        // castea a Graphics2D y aplica anti-aliasing para suavizar el render de la imagen
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        //--------------------------------------------------------------------------------------------------
        for (int i = 0; i < messages.size(); i++){
            messages.get(i).draw(g2d);
            if (messages.get(i).isDead())
                messages.remove(i);
        }

        for (int i = 0; i < movingObjects.size(); i++)
            movingObjects.get(i).draw(g);

        for (int i = 0; i < explosions.size(); i++) {
            Animation animation = explosions.get(i);
            g2d.drawImage(animation.getCurrentFrame(), (int) animation.getPosition().getX(),
                    (int) animation.getPosition().getY(), null);
        }
        drawScore(g);
        drawLives(g);
    }

    public void addScore(int value, Vector2D position) {
        score += value;
        messages.add(new Message(position, true, "+" + value + " score", Color.WHITE, false,
                Assets.fontMed));
    }

    private void drawScore(Graphics g) {
        Vector2D position = new Vector2D(850, 25);
        String scoreToString = Integer.toString(score);
        for (int i = 0; i < scoreToString.length(); i++) {
            g.drawImage(Assets.numbers[Integer.parseInt(scoreToString.substring(i, i + 1))],
                    (int) position.getX(), (int) position.getY(), null);
            position.setX(position.getX() + 20);
        }
    }

    private void drawLives(Graphics g) {
        if (lives < 1)
            return;
        Vector2D livePosition = new Vector2D(25, 25);
        g.drawImage(Assets.life, (int) livePosition.getX(), (int) livePosition.getY(), null);
        g.drawImage(Assets.numbers[10], (int) livePosition.getX() + 40,
                (int) livePosition.getY() + 5, null);
        String livesToString = Integer.toString(lives);
        Vector2D pos = new Vector2D(livePosition.getX(), livePosition.getY());
        for (int i = 0; i < livesToString.length(); i++) {
            int number = Integer.parseInt(livesToString.substring(i, i + 1));
            if (number <= 0)
                break;
            g.drawImage(Assets.numbers[number],
                    (int) pos.getX() + 60, (int) pos.getY() + 5, null);
            pos.setX(pos.getX() + 20);
        }
    }

    public void dividemeteor(Meteor meteor) {
        Size size = meteor.getSize();
        BufferedImage[] textures = size.textures;
        Size newSize = null;
        switch (size) {
            case BIG:
                newSize = Size.MED;
                break;
            case MED:
                newSize = Size.SMALL;
                break;
            case SMALL:
                newSize = Size.TINY;
                break;
            default:
                return;
        }
        for (int i = 0; i < size.quantity; i++) {
            movingObjects.add(new Meteor(
                    meteor.getPosition(),
                    new Vector2D(0, 1).setDirection(Math.random() * Math.PI * 2),
                    Parameters.METEOR_VEL * Math.random() + 1,
                    textures[(int) (Math.random() * textures.length)],
                    this,
                    newSize));
        }
    }

    private void starRain() {
        messages.add(new Message(new Vector2D(Parameters.WIDTH / 2, Parameters.WIDTH / 2), true,
                "LEVEL " + levels, Color.WHITE, true, Assets.fontBig));
        double x, y;
        for (int i = 0; i < meteors; i++) {
            x = 1 % 2 == 0 ? Math.random() * Parameters.WIDTH : 0;
            y = i % 2 == 0 ? 0 : Math.random() * Parameters.HEIGHT;

            BufferedImage texture = Assets.bigs[(int) (Math.random() * Assets.bigs.length)];
            movingObjects.add(new Meteor(
                    new Vector2D(x, y),
                    new Vector2D(0, 1).setDirection(Math.random() * Math.PI * 2),
                    Parameters.METEOR_VEL * Math.random() + 1,
                    texture,
                    this,
                    Size.BIG));
        }
        meteors+=6;
        levels++;
        showUfo();
    }

    public void starExplosion(Vector2D position) {
        explosions.add(new Animation(Assets.explosions, 50, position.subtrac(
                // a la posicion, le resto un vector que va a ser la posicion que esta en el centro de la imagen
                new Vector2D(Assets.explosions[0].getWidth() / 2, Assets.explosions[0].getHeight() / 2))));
    }

    private void showUfo() {
        int rand = (int) (Math.random() * 2);
        double x = rand == 0 ? (Math.random() * Parameters.WIDTH) : 0;
        double y = rand == 0 ? 0 : (Math.random() * Parameters.HEIGHT);
        ArrayList<Vector2D> path = new ArrayList<>();

        double posX, posY;

        // creo posisiones dentro de 4 cuadrantes de la pantalla
        posX = Math.random() * Parameters.WIDTH / 2;
        posY = Math.random() * Parameters.HEIGHT / 2;
        path.add(new Vector2D(posX, posY));

        posX = Math.random() * Parameters.WIDTH / 2;
        posY = Math.random() * Parameters.HEIGHT / 2;
        path.add(new Vector2D(posX, posY));

        posX = Math.random() * (Parameters.WIDTH / 2) + Parameters.WIDTH / 2;
        posY = Math.random() * Parameters.HEIGHT / 2;
        path.add(new Vector2D(posX, posY));

        posX = Math.random() * Parameters.WIDTH / 2;
        posY = Math.random() * (Parameters.HEIGHT / 2) + Parameters.HEIGHT / 2;
        path.add(new Vector2D(posX, posY));

        posX = Math.random() * (Parameters.WIDTH / 2) + Parameters.WIDTH / 2;
        posY = Math.random() * (Parameters.HEIGHT / 2) + Parameters.HEIGHT / 2;
        path.add(new Vector2D(posX, posY));
        path.add(new Vector2D(posX, posY));

        movingObjects.add(new Ufo(new Vector2D(x, y), new Vector2D(), Parameters.UFO_MAX_VEL, Assets.ufo, path, this));

    }

    public void gameOver() {
        Message gameOverMsg = new Message(
                PLAYER_POSITION,
                true,
                "GAME OVER",
                Color.WHITE,
                true,
                Assets.fontBig);

        this.messages.add(gameOverMsg);
        gameOverTime.run(Parameters.GAME_OVER_TIME);
        gameOver = true;
    }

    public ArrayList<MovingObject> getMovingObjects() {
        return movingObjects;
    }

    public Player getPlayer() {
        return player;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public boolean subtractLife() {
        lives--;
        return lives > 0;
    }
}
