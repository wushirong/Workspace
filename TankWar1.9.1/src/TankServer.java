import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class TankServer {
	private static int ID = 100;
	public static final int TCP_PORT = 8888;
	List<Client> clients = new ArrayList<Client>();
	
	@SuppressWarnings("resource")
	public void start() {
		ServerSocket ss = null;
		
		try {
			ss = new ServerSocket(TCP_PORT);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		while(true) {
			Socket socket = null;
			try {
				socket = ss.accept();
				DataInputStream dis = new DataInputStream(socket.getInputStream());
				String IP = ss.getInetAddress().getHostAddress();
				int udpPort = dis.readInt();
				Client c = new Client(IP, udpPort);
				clients.add(c);
				DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
				dos.writeInt(ID++); 
				socket.close();
				System.out.println("A Client is connected " + ss.getInetAddress() + ":" + socket.getPort());
			}
			catch(IOException e) {
				e.printStackTrace();
			}
			finally {
				if(socket != null) {
					try {
						socket.close();
						socket = null;
					}
					catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	public static void main(String[] args) {
		new TankServer().start();
		
	}
	private class Client {
		String IP;
		int udpPort;
		public Client(String IP, int port) {
			this.IP = IP;
			this.udpPort = port;
		}
	}

}
