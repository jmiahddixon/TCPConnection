 
import java.io.*;
import java.net.*;
import java.time.*;
import java.security.*;

public class TCPServer {
    final int PORT = 6789;
    long all = 0;
    long average = 0;
    
    public void readFile() throws NoSuchAlgorithmException{
        int bytesRead;
        int nTimes = 1;
        int timesWrong = 0;
        ServerSocket serverSocket;
        byte[] originalBuffer = fileByteArray();
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("I am ready for any client side request.");
            while (true) {
                Socket clientSocket = null;
                clientSocket = serverSocket.accept();
                Instant before = Instant.now();
                
                InputStream in = clientSocket.getInputStream();
                DataInputStream clientData = new DataInputStream(in);
    
                String fileName = clientData.readUTF();
                System.out.println("Recieving file " + fileName +" for the " + nTimes + "th time.");
                OutputStream output = new FileOutputStream(fileName);
                long size = clientData.readLong();
                byte[] buffer = new byte[1024];
                while (size > 0 && (bytesRead = clientData.read(buffer, 0, (int) Math.min(buffer.length, size))) != -1) {
                    output.write(buffer, 0, bytesRead);
                    size -= bytesRead;
                }
                
                if(!checkDigest(buffer, originalBuffer)){
                    timesWrong++;
                    System.out.println("The file has been sent " + timesWrong + " times with failed integrity.");
                }
                System.out.println("Finishing recieving file " + fileName +" for the " + nTimes + "th time.");
                
                // Closing the FileOutputStream handle
                in.close();
                clientData.close();
                output.close();
                Instant after = Instant.now();
                long delta = Duration.between(before, after).toMillis();
                all += delta;
                average = all / nTimes;
                System.out.println("The time used in millisecond to receive "+ fileName + " for " + nTimes + " is " + delta);
                System.out.println("The average time to receive file " + fileName +" in millisecond is " + average);
                nTimes++;
                System.out.println("I am done.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public boolean checkDigest(byte[] buffer, byte[] originalBuffer) throws NoSuchAlgorithmException{
        boolean result = false;
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] currentDigest = md.digest(buffer);
        byte[] originalDigest = md.digest(originalBuffer);
        
        if(MessageDigest.isEqual(currentDigest, originalDigest)){
            result = true;
        }
        
        return result;
    }
    
    public byte[] fileByteArray(){
	File myFile = new File("/Users/jeremiahdixon/Desktop/Test1 - large.txt");
	byte[] mybytearray = new byte[(int) myFile.length()]; 
	FileInputStream fis;
	DataInputStream dis;
	try {
	    fis = new FileInputStream(myFile);
	    BufferedInputStream bis = new BufferedInputStream(fis);
              
            dis = new DataInputStream(bis);     
            dis.readFully(mybytearray, 0, mybytearray.length);
	    //fis.read(mybytearray);
        }catch (IOException e) {
             e.printStackTrace();
        }
        return mybytearray;
    }
}
