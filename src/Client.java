import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class Client extends DataSocket implements Network{
	private String IP;
	
	public Client(String IP,int PORT){
		this.IP=IP;
		this.PORT=PORT;
	}
	
	@Override
	protected void finalize() throws Throwable{
		subIn.close();
		subOut.close();
		socket.close();
	}
	
	public void send(String Message){
		try{
			subOut.writeUTF(Message);
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	public String recv(){
		String result = null;
		try{
			result = subIn.readUTF();
		}catch(IOException e){
			e.printStackTrace();
		}
		return result;
	}

	@Override
	int init(){
		try{
			socket = new Socket(IP,PORT);
		}catch(UnknownHostException e){
			e.printStackTrace();
			return -2;
		}catch(IOException e){
			e.printStackTrace();
			return -1;
		}
		try {
			inStream = socket.getInputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
		subIn = new DataInputStream(inStream);
		try {
			outStream =socket.getOutputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
		subOut = new DataOutputStream(outStream);
		try {
			socket.setSoTimeout(0);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

}
