package jaydle;

import static jaydle.Utilities.*;
import static jaydle.JaydlePrototype.*;
import static jaydle.BackgroundTask.*;

import java.awt.EventQueue;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

import jaydle.JaydlePrototype.saveDirClass;


public class Main 
{
	public static saveDirClass serObj=new saveDirClass();
	static String saveDirString=System.getProperty("user.home");//use home dir
	//public  static File saveDirectory=new File(saveDirString);
	public  static File saveDirectory;
	
	public static void main(String[] args) 
	{

		// JaydleProto01SwingWorker guiApp=new JaydleProto01SwingWorker();
		// BackgroundTask<Integer,String> bgt=new BackgroundTask<Integer,String>();
		// guiApp.initGuiApp();
		// bgt.execute();
		saveDirectory=new File(saveDirString);
		try 
		{
			File serFile = new File("jaydle.ser");
			// If file exists, load past data and substitute to saveDirectory.
			// else make file.

			if (serFile.exists()) 
			{
				serObj = (saveDirClass) load(serFile);
				saveDirectory = serObj.getPath();
				println("Return of serObj.getPath().toString() " + serObj.getPath().toString());
			} else 
			{
				println("No serFile.");
				serFile.createNewFile();
				serObj.setPath(saveDirectory);
			}
		} 
		catch (IOException exc) 
		{
			println("IOException");
		} 
		catch (ClassNotFoundException exc2) 
		{
			println("CNFE");
		}
		/*
		 * this statement generated an error when no jaydle.ser file exists.
		//println("Return of serObj.getPath().toString() " + serObj.getPath().toString());
		*/
		// saveDirectory=new File(saveDirString);
		// serObj.setPath(saveDirectory);
		// serObj.setPath(serObj.getPath()); //saveDirSeriarizable
		
		EventQueue.invokeLater(new Runnable() 
		{//Double Brace Initialization
			@Override
			public void run() 
			{
				new JaydlePrototype().initGuiApp();
			}
		});


		Properties properties = System.getProperties();
		properties.list(System.out);
		
		
/*		//from Id3Tagger
		Id3Tagger gui=new Id3Tagger();
		gui.initGuiId3Tagger();*/

	}

}
