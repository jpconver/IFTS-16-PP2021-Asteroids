package ar.edu.ifts16.gameObjects;

import ar.edu.ifts16.graphics.Text;
import ar.edu.ifts16.states.GameState;
import ar.edu.ifts16.vector.Vector2D;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class Message {
	private float transparency;
	private String text;
	private Vector2D position;
	private Color color;
	private boolean center;
	private boolean fade;
	private Font font;
	private final float deltaTransparency = 0.01f;
	private boolean dead;
	
	public Message(Vector2D position, boolean fade, String text, Color color,
			boolean center, Font font) {
		this.font = font;
		this.text = text;
		this.position = position;
		this.fade = fade;
		this.color = color;
		this.center = center;
		this.dead = false;
		
		if(fade)
			transparency = 1;
		else
			transparency = 0;
		
	}
	
	public void draw(Graphics2D g2d) {
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, transparency));
		Text.drawText(g2d, text, position, center, color, font);
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
		position.setY(position.getY() - 1);
		if(fade)
			transparency -= deltaTransparency;
		else
			transparency += deltaTransparency;
		if(fade && transparency < 0) {
			dead = true;
		}
		if(!fade && transparency > 1) {
			fade = true;
			transparency = 1;
		}
	}

	public boolean isDead() {
		return dead;
	}
}
