package main;

import java.io.IOException;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

public class redSquare extends Sprite{

	private int x;
	private int y;
	
	public redSquare() throws IOException {
		super(Image.createImage("/red_square.png"));
		x=4;
		y=4;
		updatePosition();
		// TODO Auto-generated constructor stub
	}
	
	public void moveRight()
	{
		if(x!=8)
		{
			x++;
			updatePosition();
		}
	}
	
	public void moveLeft()
	{
		if(x!=0)
		{
			x--;
			updatePosition();
		}
	}
	
	public void moveUp()
	{
		if(y!=0)
		{
			y--;
			updatePosition();
		}
	}
	
	public void moveDown()
	{
		if(y!=8)
		{
			y++;
			updatePosition();
		}
	}
	
	protected void updatePosition()
	{
		this.setPosition(7+(x*25),60+(y*25));
	}
	
	public int  getSelectedX()
	{
		return x;
	}
	
	public int getSelectedY()
	{
		return y;
	}

}