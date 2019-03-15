package jaydle;
import static jaydle.Utilities.println;
import static jaydle.Utilities.regexFilterFileArray;
import static jaydle.Utilities.regexFilterList;
import static jaydle.JaydlePrototype.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.Mp3File;

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
		/*
		 * Rename mp3 filename which has "'" at the head of filename, discard this.
		 */
	    public static void cutFirstQuotation(File dirContainsMp3)
	    {
	    	//Filter files which are start from ' and mp3 files
	        List<File> mp3FilesList=regexFilterFileArray(dirContainsMp3,"\\.mp3$");
	        List<File> mp3FilesList2=regexFilterList(mp3FilesList, "^'");
	        
	        //Rename, cut down quotation
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
	    
	    public static void cutFirstQuotation(List<File> mp3FilesList)
	    {
	        //Rename, cut down quotation
	        try
	        {
	            for (File filteredFiles:mp3FilesList)
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
	    
		public static Object[] makeMp3TagTable3Rows(List<File> fileList)
		{		
			try 
			{
			
			//Initialize data for JTable
			//Item number * 3 rows (filename,artist id,title)
			data=new Object[fileList.size()][3];
			//For for statement
			int i=0;

	            for (File mp3:fileList) 
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

			println("");
			
	        }	// End of Try	
	        catch(Exception exc)
	        {
	            println("Exception caught"+exc);
	        }
			return data;
			
		}
		
		public static Object[] makeMp3TagTable3RowsAndTagging(List<File> fileList)
		{		
			try 
			{
			
			//Initialize data for JTable
			//Item number * 3 rows (filename,artist id,title)
			data=new Object[fileList.size()][3];
			//For for statement
			int i=0;

	            for (File mp3:fileList) 
	            {
	                //Editting source code changed var name i to ydlMusicDirString[i]
	                //and it's content file name to absolute path
	                Mp3File mp3file=new Mp3File(mp3); 
	                ID3v2 v2Tag=mp3file.getId3v2Tag();
	                String[] stringArrayForTag=splitToArtistAndTitle(mp3.getName(), "\\ -\\ ");
	        		v2Tag.setArtist(stringArrayForTag[0]);
					v2Tag.setTitle(stringArrayForTag[1]);
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

	                println("string of mp3 object"+mp3);
	                i++;
	            }//End of for

			println("");
			
	        }	// End of Try	
	        catch(Exception exc)
	        {
	            println("Exception caught"+exc);
	        }
			return data;
			
		}
		
		public static Object[] makeMp3TagTable3RowsAndTaggingExecute(List<File> fileList,File dirMp3)
		{		
			try 
			{
			
			//Initialize data for JTable
			//Item number * 3 rows (filename,artist id,title)
			data=new Object[fileList.size()][3];
			//For for statement
			int i=0;

	            for (File mp3:fileList) 
	            {
	                //Editting source code changed var name i to ydlMusicDirString[i]
	                //and it's content file name to absolute path
	                Mp3File mp3file=new Mp3File(mp3); 
	                ID3v2 v2Tag=mp3file.getId3v2Tag();
	                String[] stringArrayForTag=splitToArtistAndTitle(mp3.getName(), "\\ -\\ ");
	        		v2Tag.setArtist(stringArrayForTag[0]);
					v2Tag.setTitle(stringArrayForTag[1]);
					v2Tag.setGenreDescription("JaydleMusic");
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
	    			//v2Tag.setArtist(stringArrayForTag[0]);
					//v2Tag.setTitle(stringArrayForTag[1]);
	                mp3file.save(dirMp3.toString()+"/new "+mp3.getName());
	                File fileMp3New=new File(dirMp3.toString()+"/new "+mp3.getName());
					if(fileMp3New.exists())
					{
						println("fileMp3New = "+fileMp3New.toString()+" exists.");
						mp3.delete();
						fileMp3New.renameTo(mp3);
					}
	                println("string of mp3 object"+mp3);
	                i++;
	            }//End of for

			println("");
			
	        }	// End of Try	
	        catch(Exception exc)
	        {
	            println("Exception caught"+exc);
	        }
			return data;
			
		}
		//Split artist name and title
		public static String[] splitToArtistAndTitle(String filename,String regex)
		{
			String[] stringArrayForTag;
//			try 
//			{
				//make pattern object
				Pattern pattern=Pattern.compile(regex);
				//Matcher matcher=pattern.matcher(filename);
				stringArrayForTag=pattern.split(filename);
//			}
//	        catch(Exception exc)
//	        {
//	            println("Exception caught"+exc);
//	        }
			return stringArrayForTag;
		}
	    
		public static List<File> filterTaggedMp3List(File dirMp3)
		{
			List<File> list=regexFilterFileArray(dirMp3, "\\.mp3$");
			List<File> taggedList = new ArrayList<>();
			try 
			{
                for (File mp3:list) 
                {
                    Mp3File mp3file=new Mp3File(mp3); 
                    ID3v2 v2Tag=mp3file.getId3v2Tag();
                    if (v2Tag.getArtist()!=null && v2Tag.getTitle()!=null)
                    {
                    	println(mp3.getName() +" has tags.");
                    taggedList.add(mp3);
                    }
                }
			}
	        catch(Exception exc)
	        {
	            println("Exception caught"+exc);
	        }
			return taggedList;
		}
		
		public static void moveTaggedItems(List<File> list,File destination)
		{
			for (File mp3:list)
			{
				File dest=new File(destination.toString()+"/"+mp3.getName());
				mp3.renameTo(dest);
			}
		}
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
