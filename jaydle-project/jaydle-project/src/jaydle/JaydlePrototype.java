package jaydle;
/*180512(Sat)
 * Youtube-Dl-Extended用のプロセスビルダーを書く
 * 
 */
import java.lang.ProcessBuilder;

import static jaydle.JaydlePrototype.*;
import static jaydle.BackgroundTask.*;
import static jaydle.ActionListenerClass.*;

//import static com.wordpress.lavilleeternell.IOMethods.println;
import static jaydle.Utilities.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.lang.Process;
import java.lang.Runtime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.security.auth.callback.TextOutputCallback;
import javax.sql.rowset.serial.SerialJavaObject;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.Mp3File;


/*180809Thu 
 *181023Tue Try to add JMenuBar
 */
public class JaydlePrototype extends JFrame 
{
	 static JFrame frame;
	 static JTextArea textOut=new JTextArea();
	 static JScrollPane scrollpane=new JScrollPane(
			 textOut,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
			 JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	 static String initText=("Please input URL");
	 static JTextField textIn=new JTextField(initText);
	 static JPanel panel =new JPanel();
	 static String strIn=null;
	 static List<String> cmdList= new ArrayList<>
	 (Arrays.asList(
			 "youtube-dl",
			 "--no-playlist",
			 "--extract-audio",
			 "--audio-format",
			 "mp3",
			 "-o",
			 "'%(title)s.%(ext)s'"
			 ));
	 static String url;//="https://www.youtube.com/watch?v=FqVTzr3CfEg";
	 static BackgroundTask bgt; //Child class of SwingWorker
	 static File saveDirectory; 
	 static ProcessBuilder pbNew;
	 static String saveDirString=System.getProperty("user.home");//use home dir
	 static saveDirClass serObj=new saveDirClass();
	 static File jaydleSerFile=new File("jaydle.ser");
	 
	 //Variables for ListMp3Files Method
		static String TargetText;
		static String TargetTextNohead;
		static String TargetTextNoheadNoTail;
		static String patternStr;
		static String patternStrHead;
		static String patternStrTail;
		static String[] TargetTextSplitArray;
		static File file;
		static File fileStore;
		static int n=0;
		//For JTable
		static Object[][] data;
		static Object dataRow[];
		static String[] columnNames= {
				"FileName",
				"Artist",
				"Title",
		};
//
	 
	// serObj=load("jaydle.ser");

	 //static File dir=new File ("");
		
		public void initGuiApp() 
		{
			
			JLabel text =new JLabel("\"JAva Youtube-DL Exteded implementation by Masataka Nakamura\"");
			frame = new JFrame("Jaydle");
			JMenuBar menuBar=new JMenuBar();
			JMenu menu1=new JMenu("Store");
			menuBar.add(menu1);
			JMenuItem menuItem1=new JMenuItem("Change save directory");
			JMenuItem menuItem2Display=new JMenuItem("Display current saving directory");
			JMenuItem menuItem3ClearDisplay=new JMenuItem("Clear Display");
			JMenuItem menuItem4ListDirectory=new JMenuItem("List Directory");
			JMenuItem menuItem5ListMp3Files=new JMenuItem("List MP3 Files");
			menu1.add(menuItem1);
			menu1.add(menuItem2Display);
			menu1.add(menuItem3ClearDisplay);
			menu1.add(menuItem4ListDirectory);
			menu1.add(menuItem5ListMp3Files);
			menuItem1.addActionListener(new MenuListenerSaveDir());
			menuItem2Display.addActionListener(new MenuListenerDisplayCurrentDir());
			menuItem3ClearDisplay.addActionListener(new MenuListenerClearDisplay());
			menuItem4ListDirectory.addActionListener(new MenuListenerListDirectory());
			menuItem5ListMp3Files.addActionListener(new MenuListenerListMp3Files());
			frame.setJMenuBar(menuBar);
			
			textOut.setLineWrap(true);
			JButton button=new JButton("Download");
			button.setPreferredSize(new Dimension(100, 30));
			button.addActionListener(new ButtonListener());	
			panel.setLayout(new BorderLayout());//このコードがパネルのレイアウト変更には必要
			panel.add(BorderLayout.NORTH,text);//"Youtube-DL Exteded Java Implementation by Masataka Nakamura"
			panel.add(BorderLayout.SOUTH,textIn);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.getContentPane().add(BorderLayout.CENTER,scrollpane);
			frame.getContentPane().add(BorderLayout.NORTH,panel);
			frame.getContentPane().add(BorderLayout.SOUTH,button);
			frame.setSize(800,600);
			frame.setVisible(true);
		}
		//End of init()
	 
	 public static class saveDirClass implements Serializable // Named as serObj instance  
	 {
		  private File saveDirSerializable;
		  
		  public void setPath(File path)
		  {
			  saveDirSerializable=path;
		  }
		  
		  public File getPath()
		  {
			  return saveDirSerializable;
		  }
	 }
	

	class ButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
				strIn=textIn.getText();
				if(strIn.length()>13)
					url=strIn;
					cmdListSetter(strIn);
					printList(cmdList);
					
					//dlAudio(strIn);
				bgt=new BackgroundTask();
				
					bgt.execute();
					
					
		}
	}

	class MenuListenerSaveDir implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			JFileChooser filechooser=new JFileChooser();
			filechooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			//int retFileChooser=filechooser.showOpenDialog(this);
			//(this)だとエラーになる
			int retFileChooser=filechooser.showOpenDialog(panel);
			//https://www.javadrive.jp/tutorial/jfilechooser/index2.html
			//からコピペ
			 if (retFileChooser == JFileChooser.APPROVE_OPTION)
			 {
				 saveDirectory = filechooser.getSelectedFile();
				 saveDirString=filechooser.getSelectedFile().toString();
				 serObj.setPath(saveDirectory);
				 try
				 {
					 println("serObj.getPath().toString returns "+serObj.getPath().toString());
					 store(serObj, jaydleSerFile);
				 }
				 catch(IOException ex)
				 {
					 println("IOException, jaydle.ser around wrong...");
				 }
			}
			 else if (retFileChooser == JFileChooser.CANCEL_OPTION)
			 {
			      //label.setText("キャンセルされました");
			    }
			 else if (retFileChooser == JFileChooser.ERROR_OPTION)
			 {
			      //label.setText("エラー又は取消しがありました");
			    }
			
			 //println(saveDirectory.toString());
		}
	}
	
	class MenuListenerDisplayCurrentDir implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			println("Display current saving directory clicked!"); 
			try
			{
				//saveDirectory=pbNew.directory();
					//println("saveDirectory is "+saveDirectory.toString()); This statement made error.
				//saveDirectory.toString(); this also bad.
					textOut.append(serObj.getPath().toString()+"\n");
			}
			catch(NullPointerException exc){
			    println("File type something null pointer.");
				exc.printStackTrace();
			}
		}
	}

	class MenuListenerClearDisplay implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			println("Clear Display command clicked!"); 
			try
			{
				textOut.selectAll();
				textOut.replaceSelection("");
			}
			catch(NullPointerException exc)
			{
			    println("File type something null pointer.");
				exc.printStackTrace();
			}


		}
	}
	
	class MenuListenerListDirectory implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			println("List Directory command clicked!"); 
			try
			{
				jls(serObj.getPath().toString());
			}
			catch(NullPointerException exc)
			{
			    println("File type something null pointer.");
				exc.printStackTrace();
			}


		}
	}
	
	class MenuListenerListMp3Files implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			Id3Tagger gui=new Id3Tagger();
			gui.initGuiId3Tagger();
			
//			try 
//			{
//				File ydlMusicDir=JaydlePrototype.saveDirectory;
//				println("ydlMusicDir.exists() ? "+ydlMusicDir.exists());
//				println("");
//				/////////////////////////////////////////////////
//				TargetText="";
//				patternStr="\\-\\ ";
//				patternStrHead="'";
//				patternStrTail="\\.mp3";
//				//Compile patterns
//				Pattern pattern=Pattern.compile(patternStr);
//				Pattern patternHead=Pattern.compile(patternStrHead);
//				Pattern patternTail=Pattern.compile(patternStrTail);
//				//Initialize matchers
//				Matcher matcher=pattern.matcher(TargetText);
//				Matcher matcherHead=patternHead.matcher(TargetText);
//				Matcher matcherTail=patternTail.matcher(TargetText);
//	
//				///////////////////////////////////////////////
//				String[] ydlMusicDirString=ydlMusicDir.list(); //ファイルオブジェクトのリストメソッドをつかう
//				//結果をストリングス配列に格納
//				
//				// Before ID3tag didn't treat file directory, just file name
//				Arrays.sort(ydlMusicDirString);
//				println(ydlMusicDirString.length);
//				data=new Object[ydlMusicDirString.length][3];
//				//printList(ydlMusicDirString);
//				for (int i=0; i<ydlMusicDirString.length;i++) 
//				{
//					//Editting source code changed var name i to ydlMusicDirString[i]
//					//and it's content file name to absolute path
//					TargetText=ydlMusicDirString[i];
//					// i is file name
//					matcher=pattern.matcher(TargetText);
//					matcherHead=patternHead.matcher(TargetText);
//					matcherTail=patternTail.matcher(TargetText);
//					if (matcherTail.find())
//					{
//						
//						File fileMp3=new File(ydlMusicDir.toString()+"/"+ydlMusicDirString[i]);	
//						println("This is "+n+"th mp3 file:");
//						println(fileMp3.getName());
//						textOut.append("This is "+n+"th mp3 file:\n");
//						textOut.append(fileMp3.getName()+"\n");
//
//
//							
//							Mp3File mp3file=new Mp3File(fileMp3); 
//							ID3v2 v2Tag=mp3file.getId3v2Tag();
//
//								//remove ' if filename has it at head
//								TargetTextNohead=matcherHead.replaceFirst("");// Need to store TargetText temporary
//								matcherTail=patternTail.matcher(TargetTextNohead);
//							//	TargetText=matcherTail.replaceFirst("");
//								//remove .mp3
//								TargetTextNoheadNoTail=matcherTail.replaceFirst("");
//								TargetTextSplitArray=pattern.split(TargetTextNoheadNoTail);
//								//printList(TargetTextSplitArray);
//							//	v2Tag.setArtist(TargetTextSplitArray[0]);
//							//	v2Tag.setTitle(TargetTextSplitArray[1]);
//								
//								//fileMp3.renameTo(fileStore);
//								//println(fileMp3.toString());
//								println("v2Tag.getArtist() "+v2Tag.getArtist());	// Saving not yet
//								println("v2Tag.getTitle() "+v2Tag.getTitle());
//								textOut.append("Artist:"+v2Tag.getArtist()+"\n");	// Saving not yet
//								textOut.append("Title:"+v2Tag.getTitle()+"\n");
//								textOut.append("\n\n");
//								
//								/*
//								 * Make Object[][] for JTable
//								 */
//								dataRow=new Object[]{fileMp3.getName(),
//										v2Tag.getArtist(),
//										v2Tag.getTitle(),
//										};
//								data[i]=dataRow;
//
//							println("");
//							
//							//}//end of if
//						}//End of if (matcherTail.find())
//					n++;
//					}//End of for
//					printDoubleDimentionArray();
//				println("");
//			}	// End of Try	
//			catch(Exception exc)
//			{
//				println("Exception caught"+exc);
//			}
//			JTable jTable=new JTable(data,columnNames);
//			JScrollPane scrollpane4JTable=new JScrollPane(jTable);
//			JFrame frameTagger=new JFrame();
//			frameTagger.getContentPane().add(BorderLayout.NORTH,scrollpane4JTable);
//			frameTagger.setSize(800,600);
//			frameTagger.setVisible(true);
		}
	}
	

	
	static void jls(String file) 
	{
		try 
		{
			
			File homeDir = new File(file);
			//File p = new File("/home/masa");
			
			
			String[] homeDirList=homeDir.list(); //ファイルオブジェクトのリストメソッドをつかう
			//結果をストリングス配列に格納
			Arrays.sort(homeDirList);
			for (String i : homeDirList) 
			{
				if (i.charAt(0) == '.') ;
				else 
				{
				System.out.print(i + ",");
				System.out.println();
				textOut.append(i);
				textOut.append("\n");
				} 
			}
			System.out.println();
			
		}		
		catch(NullPointerException e) 
		{
			e.printStackTrace(); // IMPORTANT
		}
	}//jlsおわり
	
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
	
	public static List<String> toList(String[] strArray)
	{
		try
		{
		//List<String> cmdList=new ArrayList<String>();
		for(String s:strArray)
			{
				cmdList.add(s);
			}
		return cmdList;
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	public static void cmdListSetter(String url)
	{
		//String[] audioDl= {"youtube-dl","--no-playlist","--extract-audio","--audio-format","mp3",url,"-o","'%(title)s.%(ext)s'"};
		println("From cmdListGenerator: content of variable url = "+url);
		//cmdList.set(5,url);
		//printList(audioDl);
		cmdList.add(5,url);
	}
}
//end of class ProcessBuild




