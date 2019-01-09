package jaydle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.Mp3File;

import java.awt.BorderLayout;
import java.io.*;
import static jaydle.Utilities.*;
import static jaydle.JaydlePrototype.*;
import static jaydle.BackgroundTask.*;

public class Id3Tagger extends JFrame
{
	//GUI components
	//static JFrame frame = new JFrame("ID3Tagger");
	static JFrame frameTagger = new JFrame("ID3Tagger");
	static JTextArea jTextArea=new JTextArea();
	static JPanel panelTagger =new JPanel();
	private boolean DEBUG = true;	
	
	/////////////////////////////////////////////////////////////////////////////////////////////
	
	public void initGuiId3Tagger() 
	{
		/////////////////////////////////////////////////
		TargetText="";
		patternStr="\\-\\ ";
		patternStrHead="'";
		patternStrTail="\\.mp3";
		//Compile patterns
		Pattern pattern=Pattern.compile(patternStr);
		Pattern patternHead=Pattern.compile(patternStrHead);
		Pattern patternTail=Pattern.compile(patternStrTail);
		//Initialize matchers
		
		Matcher matcher=pattern.matcher(TargetText);
		Matcher matcherHead=patternHead.matcher(TargetText);
		Matcher matcherTail=patternTail.matcher(TargetText);
		try 
		{
			
			File ydlMusicDir=JaydlePrototype.saveDirectory;
			println("ydlMusicDir.exists() ? "+ydlMusicDir.exists());
			println("");


			///////////////////////////////////////////////
			String[] ydlMusicDirString=ydlMusicDir.list(); //ファイルオブジェクトのリストメソッドをつかう
			//結果をストリングス配列に格納
			
			// Before ID3tag didn't treat file directory, just file name
			Arrays.sort(ydlMusicDirString);
			println(ydlMusicDirString.length);
			//Initialize data for JTable
			data=new Object[ydlMusicDirString.length][3];
			//printList(ydlMusicDirString);
			for (int i=0; i<ydlMusicDirString.length;i++) 
			{
				//Editting source code changed var name i to ydlMusicDirString[i]
				//and it's content file name to absolute path
				TargetText=ydlMusicDirString[i];
				// i is file name
				matcher=pattern.matcher(TargetText);
				matcherHead=patternHead.matcher(TargetText);
				matcherTail=patternTail.matcher(TargetText);
				if (matcherTail.find())
				{
					
					File fileMp3=new File(ydlMusicDir.toString()+"/"+ydlMusicDirString[i]);	
					println("This is "+n+"th mp3 file:");
					println(fileMp3.getName());
//					textOut.append("This is "+n+"th mp3 file:\n");
//					textOut.append(fileMp3.getName()+"\n");
					
					//Moved to in if(matcherTail.find())
					////////////////////////////////////////////////
				//	if (fileMp3.isFile()) 
				//	{
					//if (ydlMusicDirString[i].charAt(0) == '.') ; // start from . file ignore
					//else 
					//{
				

						//file=new File(i);
						
						Mp3File mp3file=new Mp3File(fileMp3); 
						ID3v2 v2Tag=mp3file.getId3v2Tag();

						//matcherTail=patternTail.matcher(TargetText);
						//println("List of ydlMusicDir :"+n+"\n "+i);
						
						//println("");
						//matcher.find() means found "\\-\\ "
						//if(matcher.find())
						//{
							//remove ' if filename has it at head
							TargetTextNohead=matcherHead.replaceFirst("");// Need to store TargetText temporary
							matcherTail=patternTail.matcher(TargetTextNohead);
						//	TargetText=matcherTail.replaceFirst("");
							//remove .mp3
							TargetTextNoheadNoTail=matcherTail.replaceFirst("");
							TargetTextSplitArray=pattern.split(TargetTextNoheadNoTail);
							//printList(TargetTextSplitArray);
						//	v2Tag.setArtist(TargetTextSplitArray[0]);
						//	v2Tag.setTitle(TargetTextSplitArray[1]);
							
							//fileMp3.renameTo(fileStore);
							//println(fileMp3.toString());
							println("v2Tag.getArtist() "+v2Tag.getArtist());	// Saving not yet
							println("v2Tag.getTitle() "+v2Tag.getTitle());
//							textOut.append("Artist:"+v2Tag.getArtist()+"\n");	// Saving not yet
//							textOut.append("Title:"+v2Tag.getTitle()+"\n");
//							textOut.append("\n\n");
							
							/*
							 * Make Object[][] for JTable
							 */
							dataRow=new Object[]{fileMp3.getName(),
									v2Tag.getArtist(),
									v2Tag.getTitle(),
									};
							data[i]=dataRow;
							
							//List<String> list=Arrays.asList(TargetTextSplitArray);
							
							//mp3file.save(ydlMusicDir.toString()+"/new "+i);
							//File fileMp3New=new File(ydlMusicDir.toString()+"/new "+i);
							/*
							if(fileMp3New.exists())
							{
								println("fileMp3New = "+fileMp3New.toString()+" exists.");
								fileMp3.delete();
								fileMp3New.renameTo(fileMp3);
							}
							*/
							
							
						//println(i);
						//println("matches? "+matcher.matches());
						//println("find? "+matcher.find());
						println("");
						
						//}//end of if
					}//End of if (matcherTail.find())
				n++;
				}//End of for
				n=0;
		
				//fileStore=new File(fileMp3.toString()+".backup");
				//println(fileMp3.exists());
				//File fileMp3=new File(ydlMusicDir.toString()+"/"+ydlMusicDirString[i]);	
				
			
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

 // do at Main.java
/*	public static void main(String[] args) 
	{

		List<File> fileList=new ArrayList<File>();
		fileList = regexFilterFileArray(new File("/media/masa/Share/Music/ydlAudio"), new String("\\.mp3"));
		printListFileName(fileList);
	}*/

}