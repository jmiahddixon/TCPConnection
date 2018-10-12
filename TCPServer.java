import java.io.*;
import java.net.*;
import java.time.*;
import java.security.*;

public class TCPServer {
    final int PORT = 6789;
    long all = 0;
    long average = 0;
    File originalFile = new File("/Users/jeremiahdixon/Desktop/Test1 - large.txt");
    public void readFile() throws Exception{
        int bytesRead;
        int nTimes = 1;
        int timesWrong = 0;
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("I am ready for any client side request.");
            while (true) {
                Socket clientSocket = null;
                clientSocket = serverSocket.accept();
                Instant before = Instant.now();
                
                byte[] contents = new byte[10000];
                FileOutputStream fos;
                BufferedOutputStream bos;
                InputStream is;
                fos = new FileOutputStream("/Users/jeremiahdixon/Desktop/new/Test1 - large" +nTimes+ ".txt");
                bos = new BufferedOutputStream(fos);
                is = clientSocket.getInputStream();
                System.out.println("Recieving file Test1 - large.txt for the " + nTimes + "th time.");
                while((bytesRead=is.read(contents)) != -1)
                bos.write(contents, 0, bytesRead);
                bos.flush();
                System.out.println("Finishing recieving file Test1 - large.txt for the " + nTimes + "th time.");
                
                // Closing
                is.close();
                File newFile = new File("/Users/jeremiahdixon/Desktop/new/Test1 - large" +nTimes+ ".txt");
            
                boolean filesMatch = CompareFiles(originalFile, newFile);
                if(filesMatch == false)
                    timesWrong += 1;
                Instant after = Instant.now();
                long delta = Duration.between(before, after).toMillis();
                all += delta;
                average = all / nTimes;
                System.out.println("The time used in millisecond to receive Test1 - large.txt for " + nTimes + " is " + delta);
                System.out.println("The average time to receive file Test1 - large.txt in millisecond is " + average);
                System.out.println("The file has sent " + timesWrong + " times with failed integrity.");
                nTimes++;
                System.out.println("I am done.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static boolean CompareFiles(File file1, File file2) throws Exception{
        FileInputStream fis1 = new FileInputStream(file1);
        FileInputStream fis2 = new FileInputStream(file2);
        
        DataInputStream dis1 = new DataInputStream(fis1);
        DataInputStream dis2 = new DataInputStream(fis2);
        
        BufferedReader br1 = new BufferedReader(new InputStreamReader(dis1));
        BufferedReader br2 = new BufferedReader(new InputStreamReader(dis2));
        
        String strLine1, strLine2;
        
        while((strLine1 = br1.readLine()) != null && (strLine2 = br2.readLine()) != null){
            if(strLine1.equals(strLine2)){
                continue;
            }
            else{
                return false;
            }
            
        }
        return true;
    }
    
}
