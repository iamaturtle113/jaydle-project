package jaydle;
import static jaydle.Utilities.println;
import static jaydle.JaydlePrototype.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utilities {
	
	public static void print(Object a){
		System.out.print(a);
	}
	
	public static void println(Object a){
		System.out.println(a);
	}

	static void jls(String file) {
		try {
			
			File homeDir = new File(file);
			//File p = new File("/home/masa");
			
			
			String[] homeDirList=homeDir.list(); //ファイルオブジェクトのリストメソッドをつかう
			//結果をストリングス配列に格納
			Arrays.sort(homeDirList);
			for (String i : homeDirList) {
				if (i.charAt(0) == '.') ;
				else {
				System.out.print(i + ",");
				System.out.println();
				} 
			}
			System.out.println();
			
		}		catch(NullPointerException e) {
			e.printStackTrace(); // IMPORTANT
		}
		}//jlsおわり
	
	//jcatメソッドを書く
	static void jcat(String filename) {
		try {
			File textfile=new File(filename);
			FileReader streamOut=new FileReader(textfile);
			BufferedReader box=new BufferedReader(streamOut);
			String eachLine=null;
			
				while(  (eachLine=box.readLine()) !=null ) {
					println(eachLine);
				}
			print("\n");	
			box.close();
			
		}catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	//リストの文字列をすべて表示する
	public static void printList(String[] list) {
		int i=0;
		for (String element:list) {
			println("["+i+"]: "+element);
			i++;
		}
	}
	public static void printList(int[] list) {
		int i=0;
		for (int element:list) {
			println("["+i+"]: "+element);
			i++;
		}
	}
	
	public static void printList(List<String> list) {
		int i=0;
		for (String element:list) {
			println("["+i+"]: "+element);
			i++;
		}
	}
	
	
	public static void printListFile(List<File> list) {
		int i=0;
		for (Object element:list) {
			println("["+i+"]: "+element.toString());
			i++;
		}
	}
	
	public static void printListFileName(List<File> list) {
		int i=0;
		for (File element:list) {
			println("["+i+"]: "+element.getName());
			i++;
		}
	}
	
	
	//Serialization methods.
	//-------------------------
	
	//store method
	public static void store(Serializable serializableObject, File file) throws IOException
	{
		try {
			FileOutputStream fos = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(serializableObject);
			oos.close();
		} catch(IOException ex) {
			println("IOExcepiton occured. Check exists jaydle.ser");
			ex.printStackTrace();
		}
	}
	
	public static Object load(File file) throws IOException, ClassNotFoundException
	{
		FileInputStream fis=new FileInputStream(file);
		ObjectInputStream ois = new ObjectInputStream(fis);
		//serializableObject myBoxAgain = (SerializationPractice01) ois.readObject();
		return ois.readObject();
		
		//println("Read from serialized file \"bar.ser\" " + myBoxAgain.getWidth());
	}
	
	
	//Returns List of filtered File objects
	public static List<File> regexFilterFileArray(File searchDirectory, String patternStr)
	{
		//Init List
		List<File> filteredFileList=new ArrayList<File>();
		try 
		{
			
			//make pattern object
			Pattern pattern=Pattern.compile(patternStr);
			//make File[] of searchDirectory's files
			File[] contentFileOfDirectory=searchDirectory.listFiles();
			Arrays.sort(contentFileOfDirectory);
			
			//make matcher object
			for(int i=0;i<contentFileOfDirectory.length;i++)
			{
				Matcher matcher=pattern.matcher(contentFileOfDirectory[i].getName());
				if(matcher.find())
				{
					filteredFileList.add(contentFileOfDirectory[i]);
				}
			}//rof
		} //yrt
		catch(Exception exc)
		{
			println("Exception occured at regexFilterFileArray.");
			exc.printStackTrace();
		}
		return filteredFileList;
	}// End of regexFilterFileArray
	/////////////////////////////////////////////////////////////////////////
	//Returns List of filtered File objects
		public static List<File> regexFilterList(List<File> fileList, String patternStr)
		{
			//Init List
			List<File> filteredFileList=new ArrayList<File>();
			try 
			{
				
				//make pattern object
				Pattern pattern=Pattern.compile(patternStr);
				//cast List<File> to File[]
				File[] contentFileOfDirectory=(File[])fileList.toArray(new File[fileList.size()]);
				//make File[] of searchDirectory's files
				//File[] contentFileOfDirectory=searchDirectory.listFiles();
				Arrays.sort(contentFileOfDirectory);
				
				//make matcher object
				for(int i=0;i<contentFileOfDirectory.length;i++)
				{
					Matcher matcher=pattern.matcher(contentFileOfDirectory[i].getName());
					if(matcher.find())
					{
						filteredFileList.add(contentFileOfDirectory[i]);
					}
				}//rof
			} //yrt
			catch(Exception exc)
			{
				println("Exception occured at regexFilterList.");
				exc.printStackTrace();
			}
			return filteredFileList;
		}// End of regexFilterFileArray
	/*
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
*/
	//テスト用のメインメソッド
	public static void main(String[] args) {
		//jcat("/home/masa/test.txt");
//		jls("/home/masa");
		File dirMp3=new File("/home/masa/Public/workspace/Play");
		List<File> mp3FilesList=new ArrayList<File>();
		List<File> mp3FilesList2=new ArrayList<File>();
		mp3FilesList=regexFilterFileArray(dirMp3,"\\.mp3");
		printListFile(mp3FilesList);
		mp3FilesList2=regexFilterList(mp3FilesList, "\\-");
		printListFile(mp3FilesList2);
		
	}

	
}
