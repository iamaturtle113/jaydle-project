package jaydle;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.Mp3File;

import static jaydle.Utilities.printListFile;
import static jaydle.Utilities.println;
import static jaydle.Utilities.regexFilterFileArray;
import static jaydle.Utilities.regexFilterList;

public class TestRegexFilter
{
	/*
	 * Rename mp3 filename which has "'" at the head of filename, discard this.
	 */

	static File dirMp3=new File("/media/masa/Share/Music/ydlAudio/");
	static List<File> mp3FilesList=regexFilterFileArray(dirMp3,"\\.mp3$");
    static List<File> mp3FilesList2=regexFilterList(mp3FilesList, "^'");


    public static void main(String[] args) 
    {

        try
        {
            for (File filteredFiles:mp3FilesList2)
            {
                String baseName=filteredFiles.getName();
                if(baseName.charAt(0)=='\'')
                {
                    baseName=baseName.replaceFirst("'", "");
                }
                File newFileName=new File(filteredFiles.getParent()+"/"+baseName);
                println(newFileName.getName());
            	filteredFiles.renameTo(newFileName);

            }
        }
        catch(Exception exc)
        {
            println("Exception caught"+exc);
            
        }
    }

}