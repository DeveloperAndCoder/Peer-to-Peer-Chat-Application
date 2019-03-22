import java.io.IOException;
import java.net.*;
import java.util.*;

public class Receiver
{
    public Map<String,String> peers;
    public static void main(String[] args) throws IOException
    {
        int port = args.length == 0 ? 4000 : Integer.parseInt(args[0]);
        new Receiver().run(port);
    }

    public void run(int port) throws IOException
    {    
        try
        {
            InetAddress addr = InetAddress.getByName("255.255.255.255");
            DatagramSocket serverSocket = new DatagramSocket(port, addr);
            DatagramSocket senderSocket = new DatagramSocket(4001);
            byte[] receiveData = new byte[1000];
            System.out.printf("Listening on udp:%s:%d%n", InetAddress.getLocalHost().getHostAddress(), port);     
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            peers = new HashMap<String,String>();
            String name = "";
            String senderIP = "";
            while(true)
            {
                serverSocket.receive(receivePacket);
                name = new String (receivePacket.getData(), 0, receivePacket.getLength());
                senderIP = receivePacket.getAddress().toString();
                if(!peers.containsKey(name))
                {
                    System.out.println(name+ " joined with IP address: " + senderIP);
                    //peers.put(name, senderIP);
                    //System.out.println("C1");
                    
                    // now send acknowledgement packet back to sender     
                    InetAddress IPAddress = receivePacket.getAddress();
                    String sendString = "accepted";
                    byte[] sendData = sendString.getBytes();
                    DatagramPacket sendPacket = new DatagramPacket (sendData, sendData.length, IPAddress, /*receivePacket.getPort()*/4001);
                    //System.out.println(receivePacket.getPort());
                    senderSocket.send(sendPacket);
                    //System.out.println("Sent");
                }
                else
                {
                    InetAddress IPAddress = receivePacket.getAddress();
                    String sendString = "exists";
                    byte[] sendData = sendString.getBytes();
                    DatagramPacket sendPacket = new DatagramPacket (sendData, sendData.length, IPAddress, receivePacket.getPort());
                    senderSocket.send(sendPacket);
                }
            }
      }
      catch (IOException e) {
              System.out.println(e);
      }
      // should close serverSocket in finally block
    }
}
