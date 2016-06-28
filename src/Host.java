import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class Host extends DataSocket implements Network { //������ ��ӹް� ��Ʈ��ũ�� �����ϴ� Ŭ�����Դϴ�
	ServerSocket servSock; //������ ������ Host ������ �����ϴ�.
	
	public Host(int PORT){ //��Ʈ�� �޾Ƽ� ������ �����ϰ� ���ε��մϴ�.
		try{
			socket=null; //Ŭ���̾�Ʈ������ ����� �ֽ��ϴ�
			servSock = new ServerSocket(PORT); //���������� ���ε� �մϴ�
		}catch(IOException e){ //����� ������ ����ó���մϴ�
			e.printStackTrace();
		}
	}
	
	@Override
	protected void finalize() throws Throwable{ //�Ҹ��ڸ� �������̵��Ͽ� ����� ��Ʈ�� ����� ��Ʈ���� �ݰ� ������ �ݽ��ϴ�
		subIn.close();
		subOut.close();
		socket.close();
	}
	
	public void send(String Message){ //�޼����� �����մϴ�
		try{
			subOut.writeUTF(Message);
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	public String recv(){ //�޼����� �����մϴ�
		String result = null;
		try{
			result = subIn.readUTF();
		}catch(IOException e){
			e.printStackTrace();
		}
		return result;
	}
	int listen(){ //Ŭ���̾�Ʈ�� ������ ����ϴ� �Լ��Դϴ�.
		try{
			socket = servSock.accept(); //Ŭ���̾�Ʈ�� ������ �����մϴ�. Time Out�� 0���� �����Ǿ�, ������ �����ɶ����� �Լ��� ��ȯ���� �ʽ��ϴ�.
			return 0;
		}catch(IOException e){
			e.printStackTrace();
			return -1;
		}
	}
	@Override
	int init(){ //���� ���ε� �Լ��Դϴ�. ����� ��Ʈ���� �̴ϼȶ���¡ �մϴ�.
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