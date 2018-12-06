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
			
			
			String[] homeDirList=homeDir.list(); //繝輔ぃ繧､繝ｫ繧ｪ繝悶ず繧ｧ繧ｯ繝医�ｮ繝ｪ繧ｹ繝医Γ繧ｽ繝�繝峨ｒ縺､縺九≧
			//邨先棡繧偵せ繝医Μ繝ｳ繧ｰ繧ｹ驟榊�励↓譬ｼ邏�
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
		}//jls縺翫ｏ繧�
	
	//jcat繝｡繧ｽ繝�繝峨ｒ譖ｸ縺�
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

	//繝ｪ繧ｹ繝医�ｮ譁�蟄怜�励ｒ縺吶∋縺ｦ陦ｨ遉ｺ縺吶ｋ
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
	
	public static Object load(File file) throws IOException, ClassNotFoundException
	{
		FileInputStream fis=new FileInputStream(file);
		ObjectInputStream ois = new ObjectInputStream(fis);
		//serializableObject myBoxAgain = (SerializationPractice01) ois.readObject();
		return ois.readObject();
		
		//println("Read from serialized file \"bar.ser\" " + myBoxAgain.getWidth());
	}
	//繝�繧ｹ繝育畑縺ｮ繝｡繧､繝ｳ繝｡繧ｽ繝�繝�
	public static void main(String[] args) {
		//jcat("/home/masa/test.txt");
		//jls("/home/masa");
	}
}
