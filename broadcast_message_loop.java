import java.net.*;
import java.io.*;

public class broadcast_message_loop {
    private static DatagramSocket socket = null;
 
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String msg = "";
        while(!(msg = br.readLine()).equalsIgnoreCase("exit"))
        {   
            broadcast("Qazi: "+msg, InetAddress.getByName("255.255.255.255"));
            //System.out.println("Sent");
        }
    }
 
    public static void broadcast(String broadcastMessage, InetAddress address) throws IOException {
        socket = new DatagramSocket();
        socket.setBroadcast(true);
 
        byte[] buffer = broadcastMessage.getBytes();
 
        DatagramPacket packet 
          = new DatagramPacket(buffer, buffer.length, address, 4000);
        socket.send(packet);
        socket.close();
    }
}
