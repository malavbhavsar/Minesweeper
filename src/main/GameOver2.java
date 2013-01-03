package main;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
import javax.microedition.rms.RecordStoreNotOpenException;

public class GameOver2 extends Form implements CommandListener{

	private static RecordStore db; //This sets the variable db to be a recordStore, we will use db as the variable to access the record store.
	private static int score; //This is the persons score variable, should have his final score when the game is over.
	private static String highSName[], highSScore[]; //this will be the list of high score names and scores in String representation.
	private static int highSValue[]; //this is the list of high score values in integer representation.
	private static String name;
	private Command exit;
	private mainMIDlet m;
	
	public GameOver2(String title, int playerScore, String playerName, mainMIDlet m) {
		super(title);
		// TODO Auto-generated constructor stub
		score=playerScore;
		name=playerName;
		this.m = m;
		highSName= new String[11];
		highSScore= new String[11];
		highSValue= new int[11];
		
		getHighScores();
		
		System.out.println(playerName+playerScore);
		
		this.append("High Scores"+'\n');
		
		if(score!=-1)
		{
		for (int p=0;p<10;p++) { //first we loop through the 10 highscores.

			if (highSValue[p]>score || highSValue[p]==0)
			{
			for (int t=9;t>(p);t--) { // we set scores 9 to 8, 8 to 7, 7 to 6, so on untill we get to the score where the new score will be inserted
			highSValue[t+1]=highSValue[t]; //here we copy the scores on down the list
			highSScore[t+1]=highSScore[t];
			highSName[t+1]=highSName[t];
			giveStatus();
			}//t
			highSValue[p]=score;
			highSScore[p]=""+score; //set the first highscore lower than the player score, to the players name and score.
			highSName[p]=name;
			}
			p=11; //end the loop, dont want to insert it more than once.
			}//highSValue

		}//p 
	
		giveStatus();
		
		for (int p=0;p<10;p++) 
		{
			if(!highSName[p].equals("none"))
			{
				this.append(highSName[p]+" "+highSScore[p]+'\n');
			}
		}
				
		try {
			db=RecordStore.openRecordStore("highScores1", true); //we open the record store again.
			writeHighS (db); //this is our method to write the high scores, it is below; we pass it the record store variable.
			closeRecStore (db); //then we close the record store.
			} catch (Exception e) {System.out.println (e);} 
		
			exit = new Command("EXIT",Command.EXIT,1);
			this.addCommand(exit);
			this.setCommandListener(this);
	}
	
	private static void getHighScores () {

		int p=0;

		for (p=0;p<10;p++) {
		highSName[p]="none";
		highSScore[p]="0"; //first we set the highscore list to 0, encase there are no records read the list will have 0 for the highscores.
		highSValue[p]=0;
		}

		try {
		db=RecordStore.openRecordStore("highScores1", true); //here we open the record store, were using the db variable, with the record store name of "highScores"
		readRecords (db); //this calls a method we will make below to read the records of the recordstore
		closeRecStore(db); //we read the records, lets close the record store.
		} catch (Exception e) {System.out.println (""+e+" getHighs");}


		}///gethighscores
	

	public static void readRecords(RecordStore recStore)
	{
	try
	{
	byte[] recData = new byte[1]; //here we declare a byte array to keep each record data, used arbitrary length of array to 1, because were going to resize it later.
	int len; //keeps the length of the record.



	for (int i = 1; i <= recStore.getNumRecords() && i<=10; i++) //we loop to read all the records in the record store, or the first 10 whichever comes first.
	{
	// Allocate more storage if necessary
	if (recStore.getRecordSize(i) > recData.length)
	recData = new byte[recStore.getRecordSize(i)]; //here we check to see if the recData byte variable is big enough to hold the record that will be read, and resize it if not.

	len = recStore.getRecord(i, recData, 0); //here we read the next record, the data of the record goes into recData, and the length of the record goes into len.
	String tempp=new String(recData, 0, len); //here we convert the byte data of the record to a string.
	highSName[(i-1)]=tempp.substring (0,tempp.indexOf (",")); // we pull the name out of the temp string, the name will be the first letters up to the first comma
	highSScore[(i-1)]=tempp.substring (tempp.indexOf(",")+1,tempp.length()); // we pull the score out of the temp string, the score starts after the first comma and runs to the end of the string.
	highSValue[(i-1)]=Integer.parseInt(highSScore[(i-1)]); // here we parse the highSScore string to an integer value and keep it in highSValue array.

	}//i
	}//try
	catch (Exception e)
	{
	System.err.println(e.toString()+"reading");
	}
	giveStatus();
	} //readrecords 
	
	private static void writeHighS (RecordStore recStore) {

		int i=1,p=0;
		for (p=0;p<10;p++) { //we loop 10 times to write the 10 high scores.

		String tempp=highSName[p]+","+highSScore[p]; // we put the high score name and score together in a string, seperated by a comma.
		
		
		
		byte[] rec = tempp.getBytes(); // next we take the temporary string that contains the name and score and get the byte data of it., We will write the byte data to the record store, instead of the string.

		System.out.println(rec+"is record");
		
		try
		{
		if (recStore.getNextRecordID()>i) { //we use this to see if there is a record allready there, if there is we overwrite it, if not we add a new record.

		recStore.setRecord(i++,rec, 0, rec.length); // over write the current record with the name and score.

		} //if
		else
		{
			System.out.println("rec added");
		recStore.addRecord (rec,0,rec.length); //no record to overwrite, so we add the name and score as a new record.
		i++;
		} //else

		}// try
		catch (Exception e) {
		System.err.println(e.toString());
		}
		} // for p

		}//write high scores
	
	private static void closeRecStore(RecordStore db)
	{
		try {
			db.closeRecordStore();
		} catch (RecordStoreNotOpenException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RecordStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void commandAction(Command c, Displayable d) {
			
			String label = c.getLabel();
			if(d.equals(this))
			{
				System.out.println(label);
				if("EXIT".equals(label)) {
					m.notifyDestroyed();
				}
			}
	}
	
	private static void giveStatus()
	{
		for(int i=0;i<=10;i++)
		{
			System.out.println(highSName[i]+highSValue[i]);
		}
	}

}
