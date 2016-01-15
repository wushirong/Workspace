package Test;
import java.io.*;
import java.net.*;

import static org.junit.Assert.*;

import org.junit.Test;

public class testTCPClientTest {

	@Test
	public static void main(String[]  args) {
		InputStream is = null;
		OutputStream os = null;
		try{
			Socket socket = new Socket("localhost", 5432);
			is = socket.getInputStream();
			os = socket.getOutputStream();
			DataInputStream dis = new DataInputStream(is);
			DataOutputStream dos = new DataOutputStream(os);
			dos.writeUTF("Hello");
			String s = null;
			if((s = dis.readUTF()) != null) System.out.println(s);
			dos.close();
			dis.close();
			socket.close();
		}
		catch(UnknownHostException e) {
			e.printStackTrace();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}

}
