import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.RandomAccessFile;

public class writetxt
{

  public static void method(String file, String conent) {
	   BufferedWriter out = null;
	   try {
	   out = new BufferedWriter(new OutputStreamWriter(
	   new FileOutputStream(file, true)));
	   out.write(conent);
	   } catch (Exception e) {
	   e.printStackTrace();
	   } finally {
	   try {
	   out.close();
	   } catch (IOException e) {
	   e.printStackTrace();
	   }
	 }
	}
  public static void main(String[] args) {
	  writetxt a = new writetxt();
	  writetxt b = new writetxt();

	  //a.method3("dd.txt", "33333333333");
	  }
	 
}
