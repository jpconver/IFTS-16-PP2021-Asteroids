package ar.edu.ifts16.graphics;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

public class Loader {

    public static BufferedImage ImageLoader(String path)
    {
        try {
            return ImageIO.read(Loader.class.getClassLoader().getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Font loadFont(String path, int size) {
        try {
            return Font.createFont(Font.TRUETYPE_FONT, Loader.class.getClassLoader().getResourceAsStream(path)).deriveFont(Font.PLAIN, size);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /*
    public static Clip loadSound(String path) {
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(Loader.class.getResource(path)));
            return clip;
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
        return null;
    }
    */
    
	public static Clip loadSound(String direction) {
		try {
	        AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(Loader.class.getClassLoader().getResource(direction).getPath()));
	        DataLine.Info info = new DataLine.Info(Clip.class, inputStream.getFormat());
	        Clip clip = (Clip)AudioSystem.getLine(info);
	        clip.open(inputStream);
			return clip;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
