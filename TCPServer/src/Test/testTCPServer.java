package Test;
import java.io.*;
import java.net.*;
import java.math.*;

import static org.junit.Assert.*;

import org.junit.Test;
public class testTCPServer {

	@Test
	public static void main(String[] args) {
		InputStream in = null;
		OutputStream out = null;
		try {
			ServerSocket ss = new ServerSocket();
			Socket socket = ss.accept();
			in = socket.getInputStream();
			out = socket.getOutputStream();
			DataInputStream din = new DataInputStream(in);
			DataOutputStream dout = new DataOutputStream(out);
			String s = null;
			if((s = din.readUTF()) != null) System.out.println(s);
			dout.writeUTF("JOJO");
			din.close();
			dout.close();
			socket.close();
			
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}

}
