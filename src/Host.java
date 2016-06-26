import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class Host extends DataSocket implements Network {
	ServerSocket servSock;
	
	public Host(int PORT){
		try{
			socket=null;
			servSock = new ServerSocket(PORT);
		}catch(IOException e){
			e.printStackTrace();
		}
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
	int listen(){
		try{
			socket = servSock.accept();
			return 0;
		}catch(IOException e){
			e.printStackTrace();
			return -1;
		}
	}
	@Override
	int init(){
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
		return 0;
	}

}
