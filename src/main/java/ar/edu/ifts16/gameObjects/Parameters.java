package ar.edu.ifts16.gameObjects;

public class Parameters {

    // window
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 1000;

    // player
    public static final int FIRERATE = 300;
    public static final double DELTAANGLE = 0.1;
    public static final double ACC = 0.2;
    public static final double PLAYER_MAX_VEL = 7.0;
    public static final long FLICKER_TIME = 200;
    public static final long SPAWNING_TIME = 3000;
    public static final long GAME_OVER_TIME = 3000;

    // laser
    public static final double LASER_VEL = 15.0;

    // meteors
    public static  final double METEOR_VEL = 2.0;


    //enemies
    public static final int NODE_RADIUS = 160;
    public static final double UFO_MASS = 60;
    public static final int UFO_MAX_VEL = 3;
    public static final long UFO_FIRE_RATE = 1000;
    public static double UFO_ANGLE_RANGE = Math.PI / 2;
    public static final long UFO_CREATE = 10000;

    //score
    public static final int UFO_SCORE = 40;
    public static final int METEOR_SCORE = 20;
    public static final int LIVES = 3;

    //menu
    public static final String PLAY = "PLAY";
    public static final String EXIT = "EXIT";
}
