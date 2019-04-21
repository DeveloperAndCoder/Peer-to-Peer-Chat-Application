import java.net.*;
import java.io.*;

public class BroadcastName {
    private static DatagramSocket socket = null;
 
    public static void main(String[] args) throws IOException {
    }
    
    public void send(String name) throws IOException{
//       BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//        System.out.println("Enter your name!");
//        String name = br.readLine();
        System.out.println(name);
        broadcast(name, InetAddress.getByName("255.255.255.255"));
        System.out.println("Sent");
    }
 
    public static void broadcast(String broadcastMessage, InetAddress address) throws IOException {
        socket = new DatagramSocket();
        socket.setBroadcast(true);
        byte[] buffer = broadcastMessage.getBytes();
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, 4000);
        socket.send(packet);
        socket.close();
    }
}
