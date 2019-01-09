package jaydle;

import static jaydle.Utilities.println;
import static jaydle.JaydlePrototype.*;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import javax.swing.SwingWorker;



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
				//Open stream for ProcessBuilder result
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

