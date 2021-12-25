package ar.edu.ifts16.states;

import ar.edu.ifts16.gameObjects.Parameters;
import ar.edu.ifts16.graphics.Assets;
import ar.edu.ifts16.ui.Action;
import ar.edu.ifts16.ui.Button;

import java.awt.Graphics;
import java.util.ArrayList;

public class MenuState extends State{
	
	private ArrayList<Button> buttons;
	
	public MenuState() {
		buttons = new ArrayList<Button>();
		
		buttons.add(new Button(
				Assets.greyBtn,
				Assets.blueBtn,
				Parameters.WIDTH / 2 - Assets.greyBtn.getWidth()/2,
				Parameters.HEIGHT / 2 - Assets.greyBtn.getHeight(),
				Parameters.PLAY,
				new Action() {
					@Override
					public void doAction() {
						State.changeState(new GameState());
					}
				}
				));
		
		buttons.add(new Button(
				Assets.greyBtn,
				Assets.blueBtn,
				Parameters.WIDTH / 2 - Assets.greyBtn.getWidth()/2,
				Parameters.HEIGHT / 2 + Assets.greyBtn.getHeight()/2 ,
				Parameters.EXIT,
				new Action() {
					@Override
					public void doAction() {
						System.exit(0);
					}
				}
				));
		
	}
	
	
	@Override
	public void update() {
		for(Button b: buttons) {
			b.update();
		}
	}

	@Override
	public void draw(Graphics g) {
		for(Button b: buttons) {
			b.draw(g);
		}
	}

}
