package jaydle;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.Mp3File;

import java.io.*;
import static jaydle.Utilities.*;

public class RegexTest {
	static String TargetText;
	static String TargetTextNohead;
	static String TargetTextNoheadNoTail;
	static String patternStr;
	static String patternStrHead;
	static String patternStrTail;
	static String[] TargetTextSplitArray;
	static File file;
	static File fileStore;
	static int n=1;
	public static void main(String[] args) {
		
		try
		{
		File ydlMusicDir=new File("/home/masa/ydlAudio");
		
		//jls("/home/masa/ydlAudio");
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
		
		//println("Text is \n"+TargetText+"\npattern is \n"+patternStr);
		//Pattern pat=matcher.pattern();
		//println(matcher.matches());
		//TargetTextSplitArray=pattern.split(TargetText);
		//printList(TargetTextSplitArray);
		
		///////////////////////////////////////////////
		String[] ydlMusicDirString=ydlMusicDir.list(); //ファイルオブジェクトのリストメソッドをつかう
		//結果をストリングス配列に格納
		
		// Before ID3tag didn't treat file directory, just file name
		Arrays.sort(ydlMusicDirString);
		for (String i : ydlMusicDirString) 
		{
			File fileMp3=new File("/home/masa/ydlAudio/"+i);	
			//fileStore=new File(fileMp3.toString()+".backup");
			if (fileMp3.isFile()) 
			{
			if (i.charAt(0) == '.') ; // start from . file ignore
			else 
			{
		
				TargetText=i;// i is file name
				//file=new File(i);
				
				Mp3File mp3file=new Mp3File(fileMp3); 
				ID3v2 v2Tag=mp3file.getId3v2Tag();
				matcher=pattern.matcher(TargetText);
				matcherHead=patternHead.matcher(TargetText);
				//matcherTail=patternTail.matcher(TargetText);
				println("List of ydlMusicDir :"+n+"\n "+i);
				
				//println("");
				if(matcher.find())
				{
					TargetTextNohead=matcherHead.replaceFirst("");// Need to store TargetText temporary
					matcherTail=patternTail.matcher(TargetTextNohead);
					//TargetText=matcherTail.replaceFirst("");
					TargetTextNoheadNoTail=matcherTail.replaceFirst("");
					TargetTextSplitArray=pattern.split(TargetTextNoheadNoTail);
					printList(TargetTextSplitArray);
					v2Tag.setArtist(TargetTextSplitArray[0]);
					v2Tag.setTitle(TargetTextSplitArray[1]);
					
					//fileMp3.renameTo(fileStore);
					println(fileMp3.toString());
					println("v2Tag.getArtist() "+v2Tag.getArtist());	// Saving not yet
					println("v2Tag.getTitle() "+v2Tag.getTitle());
					//List<String> list=Arrays.asList(TargetTextSplitArray);
					mp3file.save(ydlMusicDir.toString()+"/new "+i);
					File fileMp3New=new File(ydlMusicDir.toString()+"/new "+i);
					if(fileMp3New.exists())
					{
						println("fileMp3New = "+fileMp3New.toString()+" exists.");
						fileMp3.delete();
						fileMp3New.renameTo(fileMp3);
					}
					
				//println(i);
				//println("matches? "+matcher.matches());
				//println("find? "+matcher.find());
				println("");
				}
			} 
			n++;
		}
		println("");
		}
	}		
	catch(Exception exc)
	{
		println("Exception caught"+exc);
	}
		
		
		String str="Masa's test for Regex";
		String patternStr=".*regex.*";
		Pattern pattern=Pattern.compile(patternStr,Pattern.CASE_INSENSITIVE);
		Matcher matcher=pattern.matcher(str);
		println("Text is \n"+str+"\npattern is \n"+patternStr);

		Pattern pat=matcher.pattern();
		println("pat.pattern() returns "+pat.pattern());
		println(pattern.flags());
		println(matcher.matches());
		
		if(matcher.matches()) {
			println("Matched. " + str);
		}
		else {
			println("Not matched. ");
		}
	}

}