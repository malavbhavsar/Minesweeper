package main;

import java.io.IOException;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.List;
import javax.microedition.lcdui.Choice;
import javax.microedition.lcdui.Display;


public class mainMIDlet extends MIDlet implements CommandListener{


	private List gameList;
	private Command ok;
	private Command exit;
	private Form about;
	private Form credits;
	//private GameOver gameOver;
	
	private GameOver gameOver;
	private minesweeperGame game;
	private int timetaken;
	
	public mainMIDlet() {
		
		gameList = new List("Select", Choice.IMPLICIT);
		gameList.append("New Game",null);
		gameList.append("About",null);
		gameList.append("Credits",null);
		ok = new Command("OK",Command.OK,1);
		exit = new Command("EXIT",Command.EXIT,1);
		gameList.addCommand(ok);
		gameList.addCommand(exit);
		gameList.addCommand(exit);
		gameList.setCommandListener(this);
		// TODO Auto-generated constructor stub
	}
	


	protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
		// TODO Auto-generated method stub

	}

	protected void pauseApp() {
		// TODO Auto-generated method stub
	}

	protected void startApp() throws MIDletStateChangeException {
		Display display = Display.getDisplay(this);
		display.setCurrent(gameList);		
		// TODO Auto-generated method stub

	}

	public void gameOver(boolean wonGame, int i)
	{
		if(wonGame==true)
		{
			gameOver = new GameOver("Game Over",i,this);
		}
		else
		{
			gameOver= new GameOver("Game Over",-1,this);
		}
			Display display = Display.getDisplay(this);
			display.setCurrent(gameOver);
	}


	public void commandAction(Command c, Displayable d) {
		
		String label = c.getLabel();
		if(d.equals(gameList))
		{
			if("EXIT".equals(label)) {
				notifyDestroyed();
			}
		
			else if("OK".equals(label) && gameList.isSelected(0)) {
				game = new minesweeperGame(this);
				Display display = Display.getDisplay(this);
				game.start();
				display.setCurrent(game);
			}
			
			else if("OK".equals(label) && gameList.isSelected(1)) {
			
				about = new Form("About");
				about.append("Classic minesweeper Game for Nokia DefaultColorPhone");
				about.addCommand(new Command("BACK",Command.BACK,1));
				about.setCommandListener(this);
				Display display = Display.getDisplay(this);
				display.setCurrent(about);		
			}
		
			else if("OK".equals(label) && gameList.isSelected(2)) {
			
				credits = new Form("Credits");
				credits.append("Programmer-Malav Bhavsar");
				credits.addCommand(new Command("BACK",Command.BACK,2));
				credits.setCommandListener(this);
				Display display = Display.getDisplay(this);
				display.setCurrent(credits);
			}			
		}
		else if("BACK".equals(label) && (d.equals(credits)||d.equals(about))) {
			Display display = Display.getDisplay(this);
			display.setCurrent(gameList);
		}
	}
}
		

		// TODO Auto-generated method stub
		



