 
import java.io.*;
import java.net.*;
import java.time.*;
import java.security.*;
public class TCPServerDriver { 
	public static void main(String[] args) throws NoSuchAlgorithmException{
		TCPServer server = new TCPServer();
		server.readFile();
	}
}
