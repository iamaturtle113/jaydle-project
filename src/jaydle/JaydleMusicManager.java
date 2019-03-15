package jaydle;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.*;

import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.Mp3File;

import jaydle.JaydlePrototype.ButtonListener;

import static jaydle.Utilities.*;
import static jaydle.JaydlePrototype.*;
import static jaydle.BackgroundTask.*;


public class JaydleMusicManager
{
	/*
	 * 
	 */
	static JaydleMusicManager gui=new JaydleMusicManager();
	// comment out are for filechooser implementation
	static File dirMp3;//=new File("/media/masa/Share/Music/ydlAudio/");
	static File dirMp3Initial=new File("/mnt/Share/Music/ydlAudio/");
	static File destination;
	static List<File> mp3FilesList;//=regexFilterFileArray(dirMp3,"\\.mp3");
	static List<File> taggedMp3FilesList;
	
	//GUI components
	//static JFrame frame = new JFrame("ID3Tagger");
	static JFrame frameTagger = new JFrame("ID3Tagger");
	static JTextArea jTextArea=new JTextArea();
	//static JPanel panelTagger =new JPanel();
	static JPanel panelButton =new JPanel();
	static JButton executeButton=new JButton("Execute");
	static JButton taggingButton=new JButton("Auto Tagging");
	static JButton cutQuotationButton=new JButton("Cut ' of the head");
	static JButton moveTaggedItemsButton=new JButton("Move tagged items");
	private boolean DEBUG = true;	
	
	/////////////////////////////////////////////////////////////////////////////////////////////
	
	public void initGuiId3Tagger() 
	{
		JTable jTable=new JTable(data,columnNames);//data is two dimensional array
		JScrollPane scrollpane4JTable=new JScrollPane(jTable);
		panelButton.add(cutQuotationButton);
		panelButton.add(taggingButton);
		panelButton.add(executeButton);
		panelButton.add(moveTaggedItemsButton);
		taggingButton.addActionListener(new taggingButtonListener());	
		cutQuotationButton.addActionListener(new cutQuotationButtonListener());
		executeButton.addActionListener(new executionButtonListener());
		moveTaggedItemsButton.addActionListener(new moveTaggedItemsButtonListener());
		frameTagger.getContentPane().add(BorderLayout.NORTH,scrollpane4JTable);
		frameTagger.getContentPane().add(BorderLayout.EAST,panelButton);
		//frameTagger.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frameTagger.getContentPane().add(panelTagger);
		frameTagger.setSize(900,700);
		frameTagger.setVisible(true);
	}
	
	public static void initTable()
	{
		makeMp3TagTable3Rows(mp3FilesList);
		printDoubleDimentionArray();
		println("");
	}
	
	public void initTable2()
	{
		makeMp3TagTable3RowsAndTagging(mp3FilesList);
		printDoubleDimentionArray();
		println("");
	}
	
	public void initTable3()
	{
		makeMp3TagTable3RowsAndTaggingExecute(mp3FilesList,dirMp3);
		printDoubleDimentionArray();
		println("");
	}

	

	
	public static void printDoubleDimentionArray()
	{
		for(int i=0;i<data.length;i++)
		{
			for(int j=0;j<data[i].length;j++)
			{
				println(data[i][j]);
			}
		}
	}
	
	class cutQuotationButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			cutFirstQuotation(mp3FilesList);
			mp3FilesList=regexFilterFileArray(dirMp3,"\\.mp3$");
			gui.initGuiId3Tagger();
					
		}
	}

	
	class taggingButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			mp3FilesList=regexFilterList(mp3FilesList,"\\ -\\ ");
			initTable2();
			gui.initGuiId3Tagger();
					
		}
	}
	
	class executionButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			mp3FilesList=regexFilterList(mp3FilesList,"\\ -\\ ");
			initTable3();
			gui.initGuiId3Tagger();
					
		}
	}
	
	class moveTaggedItemsButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
	    	JFileChooser filechooser=new JFileChooser(dirMp3Initial);
			filechooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int retFileChooser=filechooser.showOpenDialog(frameTagger);
			if (retFileChooser == JFileChooser.APPROVE_OPTION)
			 {
				 destination=filechooser.getSelectedFile();
				 taggedMp3FilesList=filterTaggedMp3List(dirMp3);
			 }
			//printListFile(taggedMp3FilesList);
			moveTaggedItems(taggedMp3FilesList,destination);
			gui.initGuiId3Tagger();
					
		}
	}
	
    public static void main(String[] args) 
    {
    	JFileChooser filechooser=new JFileChooser(dirMp3Initial);
		filechooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int retFileChooser=filechooser.showOpenDialog(frameTagger);
		//https://www.javadrive.jp/tutorial/jfilechooser/index2.html
		//からコピペ
		 if (retFileChooser == JFileChooser.APPROVE_OPTION)
		 {
			 dirMp3 = filechooser.getSelectedFile();
			 mp3FilesList=regexFilterFileArray(dirMp3,"\\.mp3$");
		 }
		initTable();
		gui.initGuiId3Tagger();
		


    }

}