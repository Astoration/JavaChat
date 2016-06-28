import java.io.IOException;
import java.net.SocketTimeoutException;

public interface Network { //네트워크 인터페이스입니다.
	public void send(String Message); //전송을 추상화 합니다.
	public String recv(); //수신을 추상화 합니다.
}