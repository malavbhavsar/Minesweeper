package main;

import java.io.IOException;

import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

public class smileySprite extends Sprite{

	public smileySprite() throws IOException {
		super(Image.createImage("/smiley.png"),50,50);
		// TODO Auto-generated constructor stub
		this.setFrame(0);
		this.setPosition(95,5);
	}
}
