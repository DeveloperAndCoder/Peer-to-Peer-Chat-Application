import java.io.IOException;
import java.net.*;
import javax.swing.DefaultListModel;


public class GetAck
{
    public static void main(String[] args) throws IOException
    {
//        int port = args.length == 0 ? 4001 : Integer.parseInt(args[0]);
//        new GetAck().run(port);
    }
    
    static DefaultListModel mod = NewJDialog.model;
    static NewJDialog dia;
    
    public static void init(NewJDialog d) {
        dia = d;
    }

    public static void run(int port) throws IOException
    {   
        Reciever.init(mod,dia);
        try
        {
        //InetAddress addr = InetAddress.getByName("255.255.255.255");
            DatagramSocket serverSocket = new DatagramSocket(port);
            byte[] receiveData = new byte[1000];
            System.out.printf("Listening for on udp:%s:%d%n", InetAddress.getLocalHost().getHostAddress(), port);     
            DatagramPacket receivePacket = new DatagramPacket(receiveData,receiveData.length);
            String ack = "";
            String senderIP = "";
            int i = 0;
            int val = (int)1e6;
            serverSocket.setSoTimeout(2000);
            while(i < val)
            {
                serverSocket.receive(receivePacket);
                ack = new String( receivePacket.getData(), 0, receivePacket.getLength());
                senderIP = receivePacket.getAddress().toString();
                System.out.println("ACK RECEIVED: " + ack + " FROM " + senderIP);
                senderIP = senderIP.substring(1);
                if(ack.equals("exists"))
                {
                    System.out.println("Name already exists, try again with a different name");
                    break;
                }
                else {
                    String sender_name = ack;
                    System.out.println("check1");
                    if(mod == null) {
                        System.out.println("mod");
                    }
                    //mod.addElement(sender_name);
                    new NewJDialog().additem(mod, dia, sender_name, senderIP);
                    new NewJDialog().receivetext(mod,dia,sender_name,senderIP);
                }
                i++;
            }
            System.out.println("Out");
            serverSocket.close();
        }
        catch (IOException e)
        {
            System.out.println(e);
        }
      // should close serverSocket in finally block
    }

    public static void makeitrun(){
        try{
        int port = 4001;
        new GetAck().run(port);}
        catch(Exception e){}
    }
}
