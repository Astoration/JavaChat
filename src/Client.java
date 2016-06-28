import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class Client extends DataSocket implements Network{ //������ ��ӹ޾� ��Ʈ��ũ�� �����ϴ� Ŭ�����Դϴ�.
	private String IP; //������ IP�� �����ϴ�.
	
	public Client(String IP,int PORT){ //IP�� ��Ʈ�� �޾Ƽ� �Ҵ��մϴ�.
		this.IP=IP;
		this.PORT=PORT;
	}
	
	@Override
	protected void finalize() throws Throwable{ //�Ҹ��ڸ� �������̵� �Ͽ��� ����� ��Ʈ���� �ݰ� ������ �ݽ��ϴ�.
		subIn.close();
		subOut.close();
		socket.close();
	}
	
	public void send(String Message){ //�޼����� �����մϴ�.
		try{
			subOut.writeUTF(Message);
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	public String recv(){ //�޼����� �޽��ϴ�.
		String result = null;
		try{
			result = subIn.readUTF();
		}catch(IOException e){
			e.printStackTrace();
		}
		return result;
	}

	@Override
	int init(){ //������ �����Ͽ� ������ �Ҵ��ϰ� ����� ��Ʈ���� �����մϴ�.
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