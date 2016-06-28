import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public abstract class DataSocket { //Socket ��� �߻�Ŭ�����Դϴ�.
	protected InputStream inStream; //�Է� ��Ʈ���� �����ϴ�
	protected OutputStream outStream; //��� ��Ʈ���� �����ϴ�
	protected DataInputStream subIn; //������ �Է� ��Ʈ���� �����ϴ�
	protected DataOutputStream subOut; //������ ��� ��Ʈ���� �����ϴ�
	protected Socket socket; //���ϰ�ü�� �����ϴ�
	int PORT; //��Ʈ�� �����ϴ�
	abstract int init(); //���� ���ε������� �����ϴ�.
}
