import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


public class NetClient {
	public static int UDP_PORT_START = 2333;
	public int udpPort;
	TankClient tc;
	
	public NetClient(TankClient tc) {
		udpPort = UDP_PORT_START++;
		this.tc = tc;
	}
	
	public void connect(String IP, int port) {
		Socket s = null;
		try {
			s = new Socket(IP, port);
			DataOutputStream dos = new DataOutputStream(s.getOutputStream());
			dos.writeInt(udpPort);
			DataInputStream dis = new DataInputStream(s.getInputStream());
			int id = dis.readInt();
			tc.myTank.id = id;
			System.out.println("Connected to server, and server offerd id:" + id );
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(s != null) {
				try {
					s.close();
					s = null;
				} catch (IOException e) {
			
					e.printStackTrace();
				}
			}
			
		}
		
	}
}
