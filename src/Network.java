import java.io.IOException;
import java.net.SocketTimeoutException;

public interface Network {
	public void send(String Message);
	public String recv();
}
