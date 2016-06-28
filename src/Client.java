import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class Client extends DataSocket implements Network{ //소켓을 상속받아 네트워크를 구현하는 클래스입니다.
	private String IP; //접속할 IP를 갖습니다.
	
	public Client(String IP,int PORT){ //IP와 포트를 받아서 할당합니다.
		this.IP=IP;
		this.PORT=PORT;
	}
	
	@Override
	protected void finalize() throws Throwable{ //소멸자를 오버라이드 하여서 입출력 스트림을 닫고 소켓을 닫습니다.
		subIn.close();
		subOut.close();
		socket.close();
	}
	
	public void send(String Message){ //메세지를 전송합니다.
		try{
			subOut.writeUTF(Message);
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	public String recv(){ //메세지를 받습니다.
		String result = null;
		try{
			result = subIn.readUTF();
		}catch(IOException e){
			e.printStackTrace();
		}
		return result;
	}

	@Override
	int init(){ //서버에 접속하여 소켓을 할당하고 입출력 스트림을 관리합니다.
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
			e.printStackTrace();
			return -1;
		}
		subIn = new DataInputStream(inStream);
		try {
			outStream =socket.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
			return -1;
		}
		subOut = new DataOutputStream(outStream);
		try {
			socket.setSoTimeout(0);
		} catch (SocketException e) {
			e.printStackTrace();
		}
		return 0;
	}

}