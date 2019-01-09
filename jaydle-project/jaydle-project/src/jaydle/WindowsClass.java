package jaydle;
import java.io.*;
import static jaydle.Utilities.*;

public class WindowsClass {
	
		public static void main(String[] args) {
			// TODO Auto-generated method stub
			println("hello starbucks!");
			try
			{
				File DirUsers=new File("C:\\Users");
				println(DirUsers.exists());
				File youtubeDlExe=new File("F:\\youtube-dl.exe");
				String command=("F:\\youtube-dl.exe");
				println(youtubeDlExe.exists());
				
				ProcessBuilder processBuilder=new ProcessBuilder("C:\\Users\\masa\\Downloads\\youtube-dl.exe","https://www.youtube.com/watch?v=9Nz0bxwoqQE");
				Process process=processBuilder.start();
				BufferedReader bfr2=new BufferedReader(new InputStreamReader(process.getInputStream()));
				BufferedReader bfrErr=new BufferedReader(new InputStreamReader(process.getErrorStream()));
				
				//textOut.append(saveDirectory.toString());

				String line=null;
					while((line=bfr2.readLine())!=null)
					{
						println(line);
						//textOut.append(line+"\n");
						//publish(line);
					}
					while((line=bfrErr.readLine())!=null)
					{
						println(line);
						//textOut.append(line+"\n");
						//publish(line);
					}
			}
			catch(Exception exc)
			{
				println("Exception caught...");
			}
		}

}

