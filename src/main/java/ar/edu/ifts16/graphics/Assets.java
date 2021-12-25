package ar.edu.ifts16.graphics;

import javax.sound.sampled.Clip;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Assets {
    //player
    public static BufferedImage player;
    public static BufferedImage speed;
    public static BufferedImage blueLaser, greenLaser, redLaser;
    //asteroids
    public static BufferedImage[] bigs = new BufferedImage[4];
    public static BufferedImage[] meds = new BufferedImage[2];
    public static BufferedImage[] smalls = new BufferedImage[2];
    public static BufferedImage[] tinies = new BufferedImage[2];
    // explosion
    public static BufferedImage[] explosions = new BufferedImage[9];

    // enemies
    public static BufferedImage ufo;

    //life
    public static BufferedImage life;

    //numbers
    public static BufferedImage[] numbers = new BufferedImage[11];

    //fonts
    public static Font fontBig;
    public static Font fontMed;

    //sounds
    public static Clip backgroundMusic, explosion, playerLoose, playerShoot, ufoShoot;

    //menu
    public static BufferedImage blueBtn;
    public static BufferedImage greyBtn;



    public static void init() {
        player = Loader.ImageLoader("images/player.png");
        speed = Loader.ImageLoader("effects/fire08.png");

        blueLaser = Loader.ImageLoader("lasers/laserBlue01.png");
        greenLaser = Loader.ImageLoader("lasers/laserGreen11.png");
        redLaser = Loader.ImageLoader("lasers/laserRed01.png");

        ufo = Loader.ImageLoader("images/ufo.png");

        life = Loader.ImageLoader("others/life.png");

        fontBig = Loader.loadFont("fonts/futureFont.ttf", 42);
        fontMed = Loader.loadFont("fonts/futureFont.ttf", 20);

        backgroundMusic = Loader.loadSound("sounds/backgroundMusic.wav");
        explosion = Loader.loadSound("sounds/explosion.wav");
        playerLoose = Loader.loadSound("sounds/playerLoose.wav");
        playerShoot = Loader.loadSound("sounds/playerShoot.wav");
        ufoShoot = Loader.loadSound("sounds/ufoShoot.wav");

        for (int i = 0; i < bigs.length; i++)
            bigs[i] = Loader.ImageLoader("meteors/big" + (i + 1) + ".png");
        for (int i = 0; i < meds.length; i++)
            meds[i] = Loader.ImageLoader("meteors/med" + (i + 1) + ".png");
        for (int i = 0; i < smalls.length; i++)
            smalls[i] = Loader.ImageLoader("meteors/small" + (i + 1) + ".png");
        for (int i = 0; i < tinies.length; i++)
            tinies[i] = Loader.ImageLoader("meteors/tiny" + (i + 1) + ".png");
        for (int i = 0; i < explosions.length; i++)
            explosions[i] = Loader.ImageLoader("explosion/" + i + ".png");
        for (int i = 0; i < numbers.length; i++)
            numbers[i] = Loader.ImageLoader("numbers/" + i + ".png");

        greyBtn = Loader.ImageLoader("menu/grey_button.png");
        blueBtn = Loader.ImageLoader("menu/blue_button.png");
    }
}
