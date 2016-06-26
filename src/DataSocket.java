import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public abstract class DataSocket {
	protected InputStream inStream;
	protected OutputStream outStream;
	protected DataInputStream subIn;
	protected DataOutputStream subOut;
	protected Socket socket;
	int PORT;
	abstract int init();
}
