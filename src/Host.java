import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class Host extends DataSocket implements Network { //소켓을 상속받고 네트워크를 구현하는 클래스입니다
	ServerSocket servSock; //서버를 관리할 Host 소켓을 갖습니다.
	
	public Host(int PORT){ //포트를 받아서 소켓을 생성하고 바인딩합니다.
		try{
			socket=null; //클라이언트소켓은 비어져 있습니다
			servSock = new ServerSocket(PORT); //서버소켓을 바인딩 합니다
		}catch(IOException e){ //입출력 에러를 예외처리합니다
			e.printStackTrace();
		}
	}
	
	@Override
	protected void finalize() throws Throwable{ //소멸자를 오버라이딩하여 사용한 포트와 입출력 스트림을 닫고 소켓을 닫습니다
		subIn.close();
		subOut.close();
		socket.close();
	}
	
	public void send(String Message){ //메세지를 전송합니다
		try{
			subOut.writeUTF(Message);
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	public String recv(){ //메세지를 수신합니다
		String result = null;
		try{
			result = subIn.readUTF();
		}catch(IOException e){
			e.printStackTrace();
		}
		return result;
	}
	int listen(){ //클라이언트의 접속을 대기하는 함수입니다.
		try{
			socket = servSock.accept(); //클라이언트의 접속을 수락합니다. Time Out은 0으로 설정되어, 접속이 수락될때까지 함수는 반환되지 않습니다.
			return 0;
		}catch(IOException e){
			e.printStackTrace();
			return -1;
		}
	}
	@Override
	int init(){ //소켓 바인딩 함수입니다. 입출력 스트림을 이니셜라이징 합니다.
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
		return 0;
	}

}