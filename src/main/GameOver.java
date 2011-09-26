package main;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.TextBox;
import javax.microedition.lcdui.TextField;
import javax.microedition.midlet.MIDlet;

public class GameOver extends Form implements CommandListener{

	private TextField textField;
	private final int TEXT_LIMIT=10;
	private Command ok;
	private int i;
	private GameOver2 gameOver2;
	private mainMIDlet m;
	
	public GameOver(String title, int i, mainMIDlet m) {
		super(title);
		this.m=m;
		// TODO Auto-generated constructor stub
		ok = new Command("OK",Command.OK,1);
		this.i=i;
		if(i<0)
		{
			this.append("You Lost.");
		}
		else
		{
			this.append("You Won. You took "+i+" seconds.");
			textField=new TextField("Name","",TEXT_LIMIT,TextField.ANY);
			this.append(textField);
		}
		
		this.addCommand(ok);
		setCommandListener(this);
	}

	public void commandAction(Command c, Displayable d) {
		// TODO Auto-generated method stub
		if(d.equals(this) && c.getLabel().equals("OK"))
		{
			System.out.println("okay given");
			
			if(i<0)
			{
				gameOver2=new GameOver2("High Scores",i,"none");
				Display dis= Display.getDisplay(m);
				dis.setCurrent(gameOver2);
			}
			if(i>0 && !("".equals(textField.getString())))
			{
				gameOver2=new GameOver2("High Scores",i,textField.getString());
				Display dis= Display.getDisplay(m);
				dis.setCurrent(gameOver2);
			}
		}
	}

}
