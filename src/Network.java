import java.io.IOException;
import java.net.SocketTimeoutException;

public interface Network { //��Ʈ��ũ �������̽��Դϴ�.
	public void send(String Message); //������ �߻�ȭ �մϴ�.
	public String recv(); //������ �߻�ȭ �մϴ�.
}