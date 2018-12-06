package jaydle;
import static jaydle.Utilities.*;
import java.io.*;


public class WindowsProcessBuilderTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			
			File fileTest=new File("F:\\SampleCode");
			System.out.println(fileTest.exists());
			File youtubeDlExe=new File("resources\\youtube-dl.exe");
			println(youtubeDlExe.exists());
			 String cd = System.getProperty("user.dir");
		        System.out.println(cd);
		}catch(Exception exc)
		{
			;
		}

	}

}
