package jaydle;
import java.io.*;
import java.util.Arrays;
import java.util.List;

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
	
	//テスト用のメインメソッド
	public static void main(String[] args) {
		//jcat("/home/masa/test.txt");
		//jls("/home/masa");
	}
}
