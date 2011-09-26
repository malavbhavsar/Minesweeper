package main;

import java.io.IOException;

import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

public class bottomMenu extends Sprite{

	private static int FLAG=0;
	private static int QMARK=1;
	private static int UNMARK=2;
	public bottomMenu() throws IOException {
		super(Image.createImage("/click-flag-qmark-unmark.png"), 240, 25);
		// TODO Auto-generated constructor stub
		this.setPosition(0,290);
		this.setFrame(0);
	}

}
