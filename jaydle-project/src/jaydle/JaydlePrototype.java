package jaydle;
/*180512(Sat)
 * Youtube-Dl-Extended用のプロセスビルダーを書く
 * 
 */
import java.lang.ProcessBuilder;

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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingWorker;


/*180809Thu 
 *181023Tue Try to add JMenuBar
 */
public class JaydlePrototype extends JFrame 
{
	 static JTextArea textOut=new JTextArea();
	 JScrollPane scrollpane=new JScrollPane(textOut,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	 static String initText=("Please input URL");
	 static JTextField textIn=new JTextField(initText);
	 static JPanel panel =new JPanel();
	 static String strIn=null;
	 static List<String> cmdList= new ArrayList<>(Arrays.asList("youtube-dl","--no-playlist","--extract-audio","--audio-format","mp3","-o","'%(title)s.%(ext)s'"));
	 static String url;//="https://www.youtube.com/watch?v=FqVTzr3CfEg";
	 BackgroundTask bgt; //Child class of SwingWorker
	 static File saveDirectory; 
	 static ProcessBuilder pbNew;
	 static String saveDirString=System.getProperty("user.home");//use home dir
	 static saveDirClass serObj=new saveDirClass();
	 static File jaydleSerFile=new File("jaydle.ser");

	 //static File dir=new File ("");
	 
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
	
	public class BackgroundTask extends SwingWorker<Integer, String>
	{
		@Override
		public Integer doInBackground() throws Exception 
		{//Use publish() method at here
			try 
			{
				ProcessBuilder pb=new ProcessBuilder(cmdList);

				pb.directory(saveDirectory);//これはFile型を返却する
				
				Process p=pb.start();
				BufferedReader bfr2=new BufferedReader(new InputStreamReader(p.getInputStream()));
				BufferedReader bfrErr=new BufferedReader(new InputStreamReader(p.getErrorStream()));
				
				//textOut.append(saveDirectory.toString());

				String line=null;
					while((line=bfr2.readLine())!=null)
				//最後まで読み込むと、lineがnullになることを利用している
					{
						println(line);
						//textOut.append(line+"\n");
						publish(line);
					}
					while((line=bfrErr.readLine())!=null)
					//最後まで読み込むと、lineがnullになることを利用している
					{
						println(line);
						//textOut.append(line+"\n");
						publish(line);
					}
			}
			//try statement end.

			/*catch(Exception e)
			{
				e.printStackTrace();
			}*/
			catch(NullPointerException e)
			{
				println("NullPo!!!");
				e.printStackTrace();
				
			}
			finally
			{
			//cmdList.clear();
				cmdList.remove(5);
			}
			
			return null;
		}
	
		@Override
		protected void process(List<String> lines)
		{
			try
			{
				for(String line:lines)
				{
					textOut.append(line+"\n");
				}
			}
			catch(NullPointerException e)
			{
				println("NullPo!!!");
				e.printStackTrace();
				
			}
		}
		
		@Override
		protected void done()
		{
			try
			{
				int result=get();
				textOut.append("result is "+result);
				cmdList.clear();
			}
			catch(Exception ex)
			{}
			
		}
	}
	//End of BackgroundTask
	
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
	
	

	
	public static void main(String[] args) 
	{
		//JaydleProto01SwingWorker guiApp=new JaydleProto01SwingWorker();
		//BackgroundTask<Integer,String> bgt=new BackgroundTask<Integer,String>();
		//guiApp.initGuiApp();
		//bgt.execute();
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				new JaydlePrototype().initGuiApp();
			}
		});
		
		saveDirectory=new File(saveDirString);
		serObj.setPath(saveDirectory);
        Properties properties = System.getProperties();
        properties.list(System.out);
		
	}//end of main method
	
	public void initGuiApp() 
	{
		
		JLabel text =new JLabel("\"JAva Youtube-DL Exteded implementation by Masataka Nakamura\"");
		JFrame frame = new JFrame("Jaydle");
		JMenuBar menuBar=new JMenuBar();
		JMenu menu1=new JMenu("Store");
		menuBar.add(menu1);
		JMenuItem menuItem1=new JMenuItem("Change save directory");
		JMenuItem menuItem2Display=new JMenuItem("Display current saving directory");
		menu1.add(menuItem1);
		menu1.add(menuItem2Display);
		menuItem1.addActionListener(new MenuListenerSaveDir());
		menuItem2Display.addActionListener(new MenuListenerDisplayCurrentDir());
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
					 println(serObj.getPath().toString());
					 store(serObj, jaydleSerFile);
				 }
				 catch(IOException ex)
				 {
					 println("IOException, jaydle.ser around wrong...");
				 }
			//  label.setText(file.getName());
			//  text2.setText(jcat2(file.getAbsolutePath()));
			//  println(jcat2(file.getAbsolutePath()));
			  //text.setText(file.getAbsolutePath());
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
			try{
				//saveDirectory=pbNew.directory();
					//println("saveDirectory is "+saveDirectory.toString()); This statement made error.
				//saveDirectory.toString(); this also bad.
					textOut.append(saveDirString+"\n");
			}
			catch(NullPointerException exc){
			    println("File type something null pointer.");
				exc.printStackTrace();
			}


		}
	}

}
//end of class ProcessBuild

