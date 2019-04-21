import java.io.IOException;
import java.net.*;
import javax.swing.DefaultListModel;

public class Reciever {
    
    public static NewJDialog dia;
    
    public static void init(NewJDialog D) {
        dia = D;
    }
    
    public static void main(String[] args) throws IOException{
        //int port = args.length == 0 ? 4000 : Integer.parseInt(args[0]);
        //new Reciever().run(port);
    }

    public static DefaultListModel mod;
    
    public static void init(DefaultListModel m, NewJDialog D) {
        mod=m;
        dia=D;
    }
    
    public void run(int port) throws IOException {    // port=4000
      try {
         //GetAck.init(mod,dia);
        InetAddress addr = InetAddress.getByName("255.255.255.255");
        DatagramSocket serverSocket = new DatagramSocket(port, addr);
        DatagramSocket senderSocket = new DatagramSocket(5000);
        byte[] receiveData = new byte[1000];
        String sendString = /*dia.*/NewJDialog.getMyName();
        byte[] sendData = sendString.getBytes();

        System.out.printf("Listening on udp:%s:%d%n",
                InetAddress.getLocalHost().getHostAddress(), port);   
        //NewJDialog.receivetext();
        DatagramPacket receivePacket = new DatagramPacket(receiveData,
                           receiveData.length);

        while(true)
        {
              serverSocket.receive(receivePacket);
              String sender_name = new String( receivePacket.getData(), 0,
                                 receivePacket.getLength() );
              String sender_ip = receivePacket.getAddress().toString();
              System.out.println("RECEIVED: " + sender_name + " FROM " + sender_ip);
              sender_ip = sender_ip.substring(1);
              //mod.addElement(sender_name);
              new NewJDialog().additem(mod, dia, sender_name, sender_ip);
              new NewJDialog().receivetext(mod,dia,sender_name,sender_ip);
              //new NewJFrame().receive();
              // now send acknowledgement packet back to sender     
              InetAddress IPAddress = receivePacket.getAddress();
              DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length,
                   IPAddress, /*receivePacket.getPort()*/4001);
              senderSocket.send(sendPacket);
        }
      } catch (IOException e) {
              System.out.println(e);
      }
      // should close serverSocket in finally block
    }
}

