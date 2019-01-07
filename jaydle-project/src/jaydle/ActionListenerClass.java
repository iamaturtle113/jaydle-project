package jaydle;
import javax.swing.*;
import static jaydle.JaydlePrototype.*;
import static jaydle.Utilities.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static jaydle.BackgroundTask.*;


/*
 * I don't know how to separate inner class contents
 */

public class ActionListenerClass 
{
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

}
