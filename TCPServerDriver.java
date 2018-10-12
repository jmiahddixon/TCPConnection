 
import java.io.*;
import java.net.*;
import java.time.*;
import java.security.*;
public class TCPServerDriver { 
	public static void main(String[] args) throws Exception{
		TCPServer server = new TCPServer();
		server.readFile();
	}
}
