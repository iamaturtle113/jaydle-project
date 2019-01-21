package jaydle;
import static jaydle.Utilities.*;

import java.io.*;
import java.util.*;

import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.Mp3File;

import static jaydle.JaydlePrototype.*;
/*
 * Find mp3 files
 * Does contain "\\-\\ "?
 * 
 */

public class Id3TagMachine 
{
	
	static File dirMp3=new File("/media/masa/Share/Music/ydlAudio");
	static List<File> mp3FilesList=regexFilterFileArray(dirMp3,"\\.mp3$");
    static List<File> mp3FilesList2=regexFilterList(mp3FilesList, "\\ \\-\\ ");


    public static void main(String[] args) 
    {
        //jcat("/home/masa/test.txt");
    //	jls("/home/masa");


        printListFile(mp3FilesList2);
        
        try
        {
            for (File filteredFiles:mp3FilesList2)
            {
                    
                Mp3File mp3file=new Mp3File(filteredFiles); 
                ID3v2 v2Tag=mp3file.getId3v2Tag();
                String baseName=filteredFiles.getName();
                    if(baseName.charAt(0)=='\'')
                    {
                        baseName=baseName.replaceFirst("'", "");
                    }
                println(baseName);
            }
        }
        catch(Exception exc)
        {
            println("Exception caught"+exc);
            
        }
    }

}
