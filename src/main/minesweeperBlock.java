package main;

import java.io.IOException;

import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;
import javax.microedition.lcdui.Image;

public class minesweeperBlock extends Sprite{

	private static int N0 = 0;
	private static int N1 = 1;
	private static int N2 = 2;
	private static int N3 = 3;
	private static int N4 = 4;
	private static int N5 = 5;
	private static int N6 = 6;
	private static int N7 = 7;
	private static int N8 = 8;
	private static int NONE = 9;
	private static int FLAG = 10;
	private static int QMARK = 11;
	private static int EXPLOSION = 12;
	private static int FLAGGED_EMPTY = 13;
	private static int BOMB =14;
	
	private static int THISISOPEN =15;
	private String status;
	int value;
	
	public minesweeperBlock() throws IOException{
		super(Image.createImage("/minesweeper.png"),25,25);
		this.setFrame(NONE);
		status="NONE";
		value=0;
		// TODO Auto-generated constructor stub
	}
	
	public void setValue(int value)
	{
		this.value=value;
	}
	
	public int revealBrick()
	{
	if(this.status.equals("NONE"))
	{
		if(value>=0 && value<=8)
		{
			
			this.setFrame(value);
			this.status="OPEN";
		}
		if(value==-1)
		{
			this.setFrame(EXPLOSION);
			this.status="EXPLOSION";
		}	
		return value;
	}
	else 
		return THISISOPEN;
	}
	
	public void flag()
	{
		this.setFrame(FLAG);
		
		status="FLAG";
	}
	
	public void unflag()
	{
		this.setFrame(NONE);
		status="NONE";
	}
	
	public void qmark()
	{
		this.setFrame(QMARK);
		status="QMARK";
	}
	
	public String getStatus()
	{
		return status;
	}
	
	public void gameOverLost()
	{		
		if(status.equals("FLAG") && value!=-1)
		{
			this.setFrame(FLAGGED_EMPTY);
		}
		
		if(status.equals("EXPLOSION"))
		{
			this.setFrame(EXPLOSION);
		}
		
		if( (status.equals("QMARK") || status.equals("NONE")) && value==-1)
		{
			this.setFrame(BOMB);
		}
	}
	
	public void setPositon(int x, int y)
	{
		this.setPosition(x, y);
	}
	
	public void testFunction()
	{
		if(this.value==-1)
		{
			this.setFrame(14);
		}
		else
		{
		this.setFrame(this.value);
		}

	}
	public int getValue()
	{
		return value;
	}
	
}

