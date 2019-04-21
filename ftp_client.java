 
import java.net.*;
import java.io.*;
public class ftp_client implements Runnable {
        static NewJDialog dia;
        static public void init(NewJDialog d) {
            dia=d;
        }
	public void receive() throws IOException {
            ServerSocket serverSocket = new ServerSocket(15001);
            int filecnt = 1;
            while(true) {
                int filesize=1022386; 
                int bytesRead;
                int currentTot = 0;
                Socket socket = serverSocket.accept();
                System.out.println("Accepted connection : " + socket + " from " + socket.getInetAddress().toString());
                byte [] bytearray  = new byte [filesize];
                InputStream is = socket.getInputStream();
                FileOutputStream fos = new FileOutputStream("File" + filecnt  + ".java");
                BufferedOutputStream bos = new BufferedOutputStream(fos);
                bytesRead = is.read(bytearray,0,bytearray.length);
                currentTot = bytesRead;

                do {
                   bytesRead =
                      is.read(bytearray, currentTot, (bytearray.length-currentTot));
                   if(bytesRead >= 0) currentTot += bytesRead;
                } while(bytesRead > -1);
                NewJDialog.receive("File"+filecnt+".java", socket.getInetAddress().toString().substring(1), dia);
                bos.write(bytearray, 0 , currentTot);
                bos.flush();
                bos.close();
                socket.close();
                filecnt++;
              }
        }

    @Override
    public void run() {
        try{ 
            receive();
        }
        catch(Exception e) {
            System.err.println("Error in ftp_client " + e.getMessage());
        }
    }
}
