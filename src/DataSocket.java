import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public abstract class DataSocket { //Socket 통신 추상클래스입니다.
	protected InputStream inStream; //입력 스트림을 갖습니다
	protected OutputStream outStream; //출력 스트림을 갖습니다
	protected DataInputStream subIn; //데이터 입력 스트림을 갖습니다
	protected DataOutputStream subOut; //데이터 출력 스트림을 갖습니다
	protected Socket socket; //소켓객체를 갖습니다
	int PORT; //포트를 갖습니다
	abstract int init(); //소켓 바인딩과정을 갖습니다.
}
