package main;

import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.Sprite;

import javax.microedition.lcdui.Font;
import java.lang.Integer;


import java.io.IOException;
import java.util.Random;
import java.util.Date;
import java.util.Vector;
import java.lang.Long;


public class minesweeperGame extends GameCanvas implements Runnable{

	private Graphics g;
	private int[][] matrix;
	private Vector v = new Vector(81);
	private minesweeperBlock Block,Block2;
	private redSquare redSquare;
	public static final int SOFT_LEFT = -6; // Nokia specific
	public static final int SOFT_RIGHT = -7; // Nokia specific
	private static int THISISOPEN =15;
	private bottomMenu bottomMenu;
	private smileySprite smileySprite;
	private Sprite grid;
	private boolean gameOver;
	private boolean wonGame;
	private int flagsRemaining;
	private mainMIDlet m;
	private long timetaken;
	private boolean firstClick;
	private Date d1,d2;
	private long d_one;
	private Long x1;
	
	public minesweeperGame(mainMIDlet m){
		super(false);
		setFullScreenMode(true);
		matrix=new int[9][9];
		try {
			redSquare= new redSquare();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			bottomMenu=new bottomMenu();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			smileySprite=new smileySprite();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			grid= new Sprite(Image.createImage("/grid.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		grid.setPosition(7, 60);
		this.m=m;
		g=this.getGraphics();
		gameOver=false;
		wonGame=false;
		flagsRemaining=10;
		timetaken=0;
		firstClick=false;
	}
	
	public void start()
	{
		setMinesAndBlockValues();
		for(int i=0;i<=80;i++)
		{
			try {
				v.addElement(new minesweeperBlock());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Block = (minesweeperBlock) v.elementAt(i);
			Block.setValue(matrix[i/9][i%9]);
			//Block.testFunction();
			Block.setPosition(7+((i%9)*25),60+((i/9)*25));			
		}
			Block2 = (minesweeperBlock) v.elementAt(4*9 + 4);
			printBoard();
			
			Thread runner = new Thread(this);
			runner.start();
	}

	public void run() {
		// TODO Auto-generated method stub
		while(true)
		{
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			update();
		}
	}
	
	private void update()
	{
		
		
		paint(g);
	}
	
	public void paint(Graphics g)
	{
		if((Block2.getStatus()).equals("NONE"))
		{
			bottomMenu.setFrame(0);
		}
		else if((Block2.getStatus()).equals("FLAG"))
		{
			bottomMenu.setFrame(1);
		}
		else if((Block2.getStatus()).equals("QMARK"))
		{
			bottomMenu.setFrame(2);
		}
		
		
		g.setColor(0xffffff);
		g.fillRect(0, 0, 240, 320);
		Integer x = new Integer(flagsRemaining);
		g.setColor(0xff0000);
		
		String s = new String(x.toString());
		
		g.drawString(s, 195,25,Graphics.TOP|Graphics.LEFT);
		g.setColor(0x000000);
			
		if(firstClick==true)
		{
		d2=new Date();
		if(d2==null)
		{
			System.out.println("nulld2");
		}
		
		x1=new Long((d2.getTime()-d_one)/1000);
		s = new String(x1.toString());
		}
		else
		{
			s="0";
		}
		g.drawString(s,45,25,Graphics.TOP|Graphics.LEFT);
		int i=0;
		for(i=0;i<=80;i++)
		{
			Block = (minesweeperBlock) v.elementAt(i);
			Block.paint(g);
		}
		redSquare.paint(g);
		bottomMenu.paint(g);
		smileySprite.paint(g);
		grid.paint(g);
		flushGraphics();
	}
	
	public synchronized void keyPressed(int keycode)
	{
		int code = this.getGameAction(keycode);
		
		if(gameOver==false)
		{
			
		if(code==GameCanvas.DOWN)
		{
			redSquare.moveDown();
			Block2= (minesweeperBlock) v.elementAt(redSquare.getSelectedX() + 9 * redSquare.getSelectedY());
		}
		if(code==GameCanvas.UP)
		{
			redSquare.moveUp();
			Block2= (minesweeperBlock) v.elementAt(redSquare.getSelectedX() + 9 * redSquare.getSelectedY());
		}
		if(code==GameCanvas.RIGHT)
		{
			redSquare.moveRight();
			Block2= (minesweeperBlock) v.elementAt(redSquare.getSelectedX() + 9 * redSquare.getSelectedY());
		}
		if(code==GameCanvas.LEFT)
		{
			redSquare.moveLeft();
			Block2= (minesweeperBlock) v.elementAt(redSquare.getSelectedX() + 9 * redSquare.getSelectedY());
		}
		if(code==GameCanvas.FIRE)
		{
			int i;
			
			if(firstClick==false)
			{
				Date d1=new Date();
				d_one=d1.getTime();
				firstClick=true;
			}
			
			Block= (minesweeperBlock) v.elementAt(redSquare.getSelectedX() + 9 * redSquare.getSelectedY());
			
			i=Block.revealBrick();
			if(i==0)
			{
				zeroRevealed(redSquare.getSelectedX(),redSquare.getSelectedY());
				smileySprite.setFrame(0);
			}
			else if(i==-1)
			{
				
				for(i=0;i<=80;i++)
				{
					Block = (minesweeperBlock) v.elementAt(i);
					Block.gameOverLost();
				}
				smileySprite.setFrame(1);
				gameOver=true;
				wonGame=false;
				try {
					this.wait(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				m.gameOver(wonGame,0);
			}
			
			if(gameOver==false && checkGameWin()==true)
			{
				gameOver=true;
				wonGame=true;
				try {
					this.wait(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				m.gameOver(wonGame,(int)(x1.longValue()-2));
			}
		}
		if(code==-7 || code==GameCanvas.GAME_B)
		{
			Block= (minesweeperBlock) v.elementAt(redSquare.getSelectedX() + 9 * redSquare.getSelectedY());
			if((Block.getStatus()).equals("NONE"))
			{
				
				Block.flag();
				flagsRemaining--;
			}
			else if((Block.getStatus()).equals("FLAG"))
			{
				Block.qmark();
				flagsRemaining++;
			}
			else if((Block.getStatus()).equals("QMARK"))
			{
				Block.unflag();
			}
		}
		}
		
	}

	public void increaseFlag()
	{
		flagsRemaining++;
	}
	
	public void decreaseFlag()
	{
		flagsRemaining--;
	}
	
	private void revealCheck(int x, int y)
	{
		
		Block= (minesweeperBlock) v.elementAt(x + 9 * y);
		if(Block.getValue()!=-1)
		{
			if((Block.getStatus()).equals("NONE") && Block.revealBrick()==0)
			{
				zeroRevealed(x,y);
			}
		}
	}

	private void zeroRevealed(int x, int y)
	{
		if(x==0 && y==0)
		{
			revealCheck(0,1);
			revealCheck(1,0);
			revealCheck(1,1);
		}
		else if(x==8 && y==8)
		{
			revealCheck(7,8);
			revealCheck(8,7);
			revealCheck(7,7);
		}
		else if(x==0 && y==8)
		{
			revealCheck(0,7);
			revealCheck(1,8);
			revealCheck(1,7);
		}
		else if(x==8 && y==0)
		{
			revealCheck(7,0);
			revealCheck(8,1);
			revealCheck(7,1);
		}
		else if(x==0 && y!=0 && y!=8)
		{
			revealCheck(x,y-1);
			revealCheck(x+1,y-1);
			revealCheck(x+1,y);
			revealCheck(x+1,y+1);
			revealCheck(x,y+1);
		}
		else if(x==8 && y!=0 && y!=8)
		{
			revealCheck(x,y-1);
			revealCheck(x-1,y-1);
			revealCheck(x-1,y);
			revealCheck(x-1,y+1);
			revealCheck(x,y+1);
		}
		else if(y==0 && x!=0 && x!=8)
		{
			revealCheck(x-1,y);
			revealCheck(x-1,y+1);
			revealCheck(x,y+1);
			revealCheck(x+1,y+1);
			revealCheck(x+1,y);
		}
		else if(y==8 && x!=0 && x!=8)
		{
			revealCheck(x-1,y);
			revealCheck(x-1,y-1);
			revealCheck(x,y-1);
			revealCheck(x+1,y-1);
			revealCheck(x+1,y);
		}
		else if(x!=0 && x!=8 && y!=0 && y!=8)
		{
			revealCheck(x-1,y-1);
			revealCheck(x-1,y);
			revealCheck(x-1,y+1);
			revealCheck(x,y+1);
			revealCheck(x,y-1);
			revealCheck(x+1,y-1);
			revealCheck(x+1,y);
			revealCheck(x+1,y+1);
		}
		else
		{
			System.out.println(10);
			System.out.println(x+" and "+y);
		}
	}
	
	private boolean checkGameWin()
	{
		int x=0;
		
		for(int i=0;i<=80;i++)
		{
			Block = (minesweeperBlock) v.elementAt(i);
			if(Block.getStatus().equals("FLAG")||Block.getStatus().equals("NONE")||Block.getStatus().equals("QMARK"))
			{
				if(Block.getValue()==-1)
				{
					x=1;
				}
				else
				{
					x=0;
					break;
				}
			}
		}
		boolean toreturn=false;
		if(x==0)
		{
			toreturn=false;
		}
		if(x==1)
		{
			toreturn=true;
		}
		return toreturn;
	}

	private void setMinesAndBlockValues()
	{
		int minesSet=0;
		Date d = new Date();
		Random r = new Random();
		r.setSeed(d.getTime());
		while(minesSet<10)
		{
			int proposedMine=0;
			proposedMine=r.nextInt(80);
			if(matrix[proposedMine/9][proposedMine%9]==-1)
			{
				
			}
			else
			{
				matrix[proposedMine/9][proposedMine%9]=-1;
				minesSet++;
			}
		}
		
	//----------------Now setting Block values-------------------
	//----------------Taking care of special cases---------------
		if(matrix[0][0]==-1)
		{
			increaseMatrixValue(0,1);
			increaseMatrixValue(1,0);
			increaseMatrixValue(1,1);
		}
		if(matrix[0][8]==-1)
		{
			increaseMatrixValue(0,7);
			increaseMatrixValue(1,7);
			increaseMatrixValue(1,8);
		}
		if(matrix[8][0]==-1)
		{
			increaseMatrixValue(7,0);
			increaseMatrixValue(7,1);
			increaseMatrixValue(8,1);
		}
		if(matrix[8][8]==-1)
		{
			increaseMatrixValue(7,8);
			increaseMatrixValue(8,7);
			increaseMatrixValue(7,7);
		}
		int i=0;
		for(i=1;i<=7;i++)
		{
			if(matrix[0][i]==-1)
			{
				increaseMatrixValue(0,i-1);
				increaseMatrixValue(1,i-1);
				increaseMatrixValue(1,i);
				increaseMatrixValue(1,i+1);
				increaseMatrixValue(0,i+1);
			}
			if(matrix[8][i]==-1)
			{
				increaseMatrixValue(8,i-1);
				increaseMatrixValue(7,i-1);
				increaseMatrixValue(7,i);
				increaseMatrixValue(7,i+1);
				increaseMatrixValue(8,i+1);
			}
			if(matrix[i][0]==-1)
			{
				increaseMatrixValue(i-1,0);
				increaseMatrixValue(i-1,1);
				increaseMatrixValue(i,1);
				increaseMatrixValue(i+1,1);
				increaseMatrixValue(i+1,0);
			}
			if(matrix[i][8]==-1)
			{
				increaseMatrixValue(i-1,8);
				increaseMatrixValue(i-1,7);
				increaseMatrixValue(i,7);
				increaseMatrixValue(i+1,7);
				increaseMatrixValue(i+1,8);
			}
		}
		int j=0;
		for(i=1;i<=7;i++)
		{
			for(j=1;j<=7;j++)
			{
				if(matrix[i][j]==-1)
				{
					increaseMatrixValue(i-1,j-1);
					increaseMatrixValue(i-1,j);
					increaseMatrixValue(i-1,j+1);
					increaseMatrixValue(i,j-1);
					increaseMatrixValue(i,j+1);
					increaseMatrixValue(i+1,j-1);
					increaseMatrixValue(i+1,j);
					increaseMatrixValue(i+1,j+1);
				}
			}
		}
	}
	
	private void printBoard()
	{
		int i=0;
		int j=0;
		for(i=0;i<=8;i++)
		{
			for(j=0;j<=8;j++)
			{
				System.out.print(matrix[i][j]+"  ");
				if(j==8)
				{
					System.out.println();
				}
			}
		}
		
	}

	private void increaseMatrixValue(int row, int col)
	{
		if(matrix[row][col]!=-1)
		{
			matrix[row][col]++;
		}
	}
}
