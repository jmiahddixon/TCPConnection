 
import java.io.IOException;

public class TCPClientDriver {
    

	public static void main(String[] args) {
		final int MAX = 100;
		TCPClient client = new TCPClient();
		try {
			for (int i = 1; i <= MAX; i++) {
				client.sendFile();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
