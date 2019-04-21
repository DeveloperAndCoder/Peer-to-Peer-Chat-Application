import java.net.*;
import java.io.*;
public class ftp_server {

        static public void send(String path, String ip) throws IOException {

              Socket socket = new Socket(ip,15001);
              File transferFile = new File (path);
              byte [] bytearray  = new byte [(int)transferFile.length()];
              FileInputStream fin = new FileInputStream(transferFile);
              BufferedInputStream bin = new BufferedInputStream(fin);
              bin.read(bytearray,0,bytearray.length);
              OutputStream os = socket.getOutputStream();
              System.out.println("Sending Files...");
              os.write(bytearray,0,bytearray.length);
              os.flush();
              socket.close();
              System.out.println("File transfer complete");
            }
}
