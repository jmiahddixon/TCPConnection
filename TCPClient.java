import java.io.*;
import java.net.*;
import java.time.*;

public class TCPClient {
    OutputStream os;
    Socket sock;
    DataInputStream dis;
    DataOutputStream dos;
    final String HOST = "168.27.161.212";
    final int PORT = 6789;
    int howMany = 1;
    long all = 0;
    long average = 0;
    
    public void sendFile() throws IOException {
        File myFile = new File("/Users/jeremiahdixon/Desktop/Test1 - large.txt");
        byte[] mybytearray = new byte[(int) myFile.length()]; 
        FileInputStream fis;
        Instant before = Instant.now();
        try {
            fis = new FileInputStream(myFile);
            BufferedInputStream bis = new BufferedInputStream(fis);
              
            dis = new DataInputStream(bis);     
            dis.readFully(mybytearray, 0, mybytearray.length); 
              
            sock = new Socket(HOST, PORT);
            System.out.println("I am connecting to server side: " + HOST);
        
            os = sock.getOutputStream();
            
            System.out.println("I am sending file " + myFile.getName() + " for the " + howMany + "th time.");
            
            dos = new DataOutputStream(os);      
            dos.write(mybytearray, 0, mybytearray.length);
            dos.flush();
            System.out.println("I am finishing sending file " + myFile.getName() + " for the " + howMany + "th time.");
          } catch(FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (os != null)
                os.close();
            if (dos != null)
                dos.close();  
            if (sock != null)
                sock.close();  
            if (dis != null)
                dis.close();
            Instant after = Instant.now();
            long delta = Duration.between(before, after).toMillis();
            all += delta;
            average = all / howMany;
            System.out.println("The time used in millisecond to send "+ myFile.getName() + " for " + howMany + " is " + delta);
            System.out.println("The average time to send file " + myFile.getName() +" in millisecond is " + average);
            howMany++;
            System.out.println("I am done");
        }
    }
}
