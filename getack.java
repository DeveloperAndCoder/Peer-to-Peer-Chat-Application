import java.io.IOException;
import java.net.*;

public class GetAck
{
    public static void main(String[] args) throws IOException
    {
        int port = args.length == 0 ? 4001 : Integer.parseInt(args[0]);
        new GetAck().run(port);
    }

    public void run(int port) throws IOException
    {   
        try
        {
            DatagramSocket serverSocket = new DatagramSocket(port);
            byte[] receiveData = new byte[1000];
            System.out.println("Listening for on udp:%s:%d%n", InetAddress.getLocalHost().getHostAddress(), port);     
            DatagramPacket receivePacket = new DatagramPacket(receiveData,receiveData.length);
            String ack = "";
            String senderIP = "";
            while(true)
            {
                serverSocket.receive(receivePacket);
                ack = new String( receivePacket.getData(), 0, receivePacket.getLength());
                senderIP = receivePacket.getAddress().toString();
                System.out.println("RECEIVED: " + ack + " FROM " + senderIP);
                if(ack.equals("exists"))
                {
                    System.out.println("Name already exists, try again with a different name");
                    break;
                }
            }
        }
        catch (IOException e)
        {
            System.out.println(e);
        }
      // should close serverSocket in finally block
    }
}
