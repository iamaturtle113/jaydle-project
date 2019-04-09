package jaydle;
/*180512(Sat)
 * Youtube-Dl-Extended用のプロセスビルダーを書く
 * 
 */
import java.lang.ProcessBuilder;

import static jaydle.Main.*;
import static jaydle.Utilities.*;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

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
	// static String path = JaydlePrototype.class.getProtectionDomain().getCodeSource().getLocation().getPath(); 
	 //static String ydl= (path+"/resources/youtube-dl");
	 static List<String> cmdList= new ArrayList<>
	 (Arrays.asList(
			 "youtube-dl",// made of path variable and relative path 
			 "--no-playlist",
			 "--extract-audio",
			 "--audio-format",
			 "mp3",
			 "-o",
			 "'%(title)s.%(ext)s'"
			 ));
	 static String url;//="https://www.youtube.com/watch?v=FqVTzr3CfEg";
	 static BackgroundTask bgt; //Child class of SwingWorker
	 static ProcessBuilder pbNew;
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
		}
	}
	

	
	static void jls(String file) 
	{
		try 
		{
			File homeDir = new File(file);
			
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




