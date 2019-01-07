package jaydle;

import static jaydle.Utilities.*;
import static jaydle.JaydlePrototype.*;
import static jaydle.BackgroundTask.*;

import java.awt.EventQueue;
import java.io.File;
import java.io.IOException;
import java.util.Properties;


public class Main 
{

	public static void main(String[] args) 
	{

		// JaydleProto01SwingWorker guiApp=new JaydleProto01SwingWorker();
		// BackgroundTask<Integer,String> bgt=new BackgroundTask<Integer,String>();
		// guiApp.initGuiApp();
		// bgt.execute();

		EventQueue.invokeLater(new Runnable() 
		{//Double Brace Initialization
			@Override
			public void run() 
			{
				new JaydlePrototype().initGuiApp();
			}
		});
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
				serFile.createNewFile();
			}
		} 
		catch (IOException exc) 
		{
			println("IOE");
		} 
		catch (ClassNotFoundException exc2) 
		{
			println("CNFE");
		}
		println("Return of serObj.getPath().toString() " + serObj.getPath().toString());
		// saveDirectory=new File(saveDirString);
		// serObj.setPath(saveDirectory);
		// serObj.setPath(serObj.getPath()); //saveDirSeriarizable

		Properties properties = System.getProperties();
		properties.list(System.out);
		
		
/*		//from Id3Tagger
		Id3Tagger gui=new Id3Tagger();
		gui.initGuiId3Tagger();*/

	}

}
