package jaydle;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;
import static jaydle.Utilities.*;

public class RegexTest {
	static String TargetText;
	static String patternStr;
	static String[] TargetTextSplitArray;
	public static void main(String[] args) {
		
		try
		{
		File ydlMusicDir=new File("/home/masa/ydlAudio");
		//jls("/home/masa/ydlAudio");
		/////////////////////////////////////////////////
		TargetText="Bill Evans - Waltz For Debby.mp3";
		patternStr="\\-\\ ";
		Pattern pattern=Pattern.compile(patternStr);
		Matcher matcher=pattern.matcher(TargetText);
		println("Text is \n"+TargetText+"\npattern is \n"+patternStr);
		//Pattern pat=matcher.pattern();
		println(matcher.matches());
		TargetTextSplitArray=pattern.split(TargetText);
		printList(TargetTextSplitArray);
		
		///////////////////////////////////////////////
		String[] ydlMusicDirString=ydlMusicDir.list(); //ファイルオブジェクトのリストメソッドをつかう
		//結果をストリングス配列に格納
		Arrays.sort(ydlMusicDirString);
		for (String i : ydlMusicDirString) 
		{
			if (i.charAt(0) == '.') ; // start from . file ignore
			else 
			{
				TargetText=i;
				matcher=pattern.matcher(TargetText);
				println(i);
				println("");
				if(matcher.find())
				{
					TargetTextSplitArray=pattern.split(TargetText);
					printList(TargetTextSplitArray);
					List<String> list=Arrays.asList(TargetTextSplitArray);
					
				println(i);
				//println("matches? "+matcher.matches());
				//println("find? "+matcher.find());
				println("");
				}
			} 
		}
		println("");
		
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