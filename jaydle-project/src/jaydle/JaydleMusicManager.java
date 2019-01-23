package jaydle;

import java.awt.BorderLayout;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.*;

import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.Mp3File;

import static jaydle.Utilities.printListFile;
import static jaydle.Utilities.println;
import static jaydle.Utilities.regexFilterFileArray;
import static jaydle.Utilities.regexFilterList;
import static jaydle.Utilities.*;
import static jaydle.JaydlePrototype.*;
import static jaydle.BackgroundTask.*;


public class JaydleMusicManager
{
	/*
	 * 
	 */
	static File dirMp3=new File("/media/masa/Share/Music/ydlAudio/");
	static List<File> mp3FilesList=regexFilterFileArray(dirMp3,"\\.mp3");
	
	//GUI components
	//static JFrame frame = new JFrame("ID3Tagger");
	static JFrame frameTagger = new JFrame("ID3Tagger");
	static JTextArea jTextArea=new JTextArea();
	static JPanel panelTagger =new JPanel();
	private boolean DEBUG = true;	
	
	/////////////////////////////////////////////////////////////////////////////////////////////
	
	public void initGuiId3Tagger() 
	{

		try 
		{
		
			//Initialize data for JTable
			//Item number * 3 row (filename,artist id,title)

			data=new Object[mp3FilesList.size()][3];
			//printList(ydlMusicDirString);
			
			//For for statement
			int i=0;
			for (File mp3:mp3FilesList) 
			{
				//Editting source code changed var name i to ydlMusicDirString[i]
				//and it's content file name to absolute path
                Mp3File mp3file=new Mp3File(mp3); 
                ID3v2 v2Tag=mp3file.getId3v2Tag();
                
				/*
				 * Make Object[][] for JTable
				 */
				dataRow=new Object[]{mp3.getName(),
						v2Tag.getArtist(),
						v2Tag.getTitle(),
						};
				data[i]=dataRow;

                println("v2Tag.getArtist() "+v2Tag.getArtist());	// Saving not yet
                println("v2Tag.getTitle() "+v2Tag.getTitle());

                println("");
                i++;
			}//End of for
	
			printDoubleDimentionArray();
			
			println("");
			
		}	// End of Try	
		catch(Exception exc)
		{
			println("Exception caught"+exc);
			
		}
		
		JTable jTable=new JTable(data,columnNames);
		JScrollPane scrollpane4JTable=new JScrollPane(jTable);
		frameTagger.getContentPane().add(BorderLayout.NORTH,scrollpane4JTable);
		//frameTagger.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameTagger.getContentPane().add(panelTagger);
		frameTagger.setSize(800,600);
		frameTagger.setVisible(true);
	}
	
	public void printDoubleDimentionArray()
	{
		for(int i=0;i<data.length;i++)
		{
			for(int j=0;j<data[i].length;j++)
			{
				println(data[i][j]);
			}
		}
	}
	
    public static void main(String[] args) 
    {
    	//cutFirstQuotation(dirMp3);
		JaydleMusicManager gui=new JaydleMusicManager();
		gui.initGuiId3Tagger();
    }

 // do at Main.java
/*	public static void main(String[] args) 
	{

		List<File> fileList=new ArrayList<File>();
		fileList = regexFilterFileArray(new File("/media/masa/Share/Music/ydlAudio"), new String("\\.mp3"));
		printListFileName(fileList);
	}*/

}