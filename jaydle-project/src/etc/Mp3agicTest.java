package etc;
import java.io.*;

import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.ID3v23Tag;
import com.mpatric.mp3agic.Mp3File;

import static jaydle.Utilities.*;


public class Mp3agicTest 
{


	public static void main (String[] args)
	{
		println("test");
		try
		{
		File file=new File("/home/masa/ydlAudio/'B.o.B - Nothin' On You (feat. Bruno Mars) [Official Video].mp3");
		//File fileRename=new File("/home/masa/ydlAudio/DS Isabella.mp3");
		//File file=new File(fileRename.toString());
		println("exist? "+file.exists());
		//Mp3File mp3file=new Mp3File("")
		Mp3File mp3file=new Mp3File(file); 
		println("Has Id3v1Tag? "+mp3file.hasId3v1Tag());
		println("Has Id3v2Tag? "+mp3file.hasId3v2Tag());
		ID3v2 v2Tag=mp3file.getId3v2Tag();
		println(v2Tag.getTrack());
		println(v2Tag.getTitle());
		println(v2Tag.getArtist());
		
		//set data
		v2Tag.setArtist("Demonstration Synthesis");
		v2Tag.setTitle("Isabella");
		println("v2Tag.getArtist() "+v2Tag.getArtist());	// Saving not yet
		println("v2Tag.getTitle() "+v2Tag.getTitle());
		//File fileNew=new File("/home/masa/ydlAudio/Demonstration Synthesis - Isabella - Phinery.mp3");
		//file.renameTo(fileNew);
		
		//mp3file.save("/home/masa/ydlAudio/DS Isabella.mp3");
		//println()
		}
		catch (Exception exc)
		{
			;
		}
	}

}