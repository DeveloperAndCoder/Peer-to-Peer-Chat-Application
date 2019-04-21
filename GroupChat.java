
import java.net.InetAddress;
import java.net.*; 
import java.io.*; 
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author rahul
 */
public class GroupChat implements Runnable{
    static NewJDialog dia;
    
    public static InetAddress group;
    public static int port;
    static volatile boolean finished = false;
    public static MulticastSocket socket;
    //public static Map<String,String> multicast=new HashMap<String,String>();
    public static DefaultListModel multimod;//=new DefaultListModel();
    public static void init( NewJDialog D) {
        //dia=D;
        try {
            socket=new MulticastSocket(1666);
        } catch (IOException ex) {
            Logger.getLogger(GroupChat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void initsocket(MulticastSocket skt,InetAddress grp,int prt)
    {
        socket=skt;
        group=grp;
        port=prt;
    }
    public static void main(String[] args) throws IOException
	{ 
            /*InetAddress group = InetAddress.getByName("239.0.0.0");
            int port=6000;
            MulticastSocket socket = new MulticastSocket(port);
            
            // Since we are deploying 
            socket.setTimeToLive(0); 
            //this on localhost only (For a subnet set it as 1
            socket.joinGroup(group);    
            Thread t = new Thread(new MultiReceive(socket,group,port)); 
			
            // Spawn a thread for reading messages 
            t.start();  */
        }
    public void join(/*InetAddress gp*/String gp,int prt,String name) throws IOException
    {
        gp = gp.trim();
        name=name.trim();
        System.out.println("name " + name + " NewJDialog.getMyName() " + NewJDialog.getMyName());
        if(NewJDialog.names.get(name) != null) return;
        this.group=InetAddress.getByName(gp);
        this.port=1666;
        //MulticastSocket socket = new MulticastSocket(port);
        // Since we are deploying 
        socket.setTimeToLive(1); 
        //this on localhost only (For a subnet set it as 1
        socket.joinGroup(group);
        Thread t4 = new Thread(new MultiReceive(socket,gp,port));
        t4.start();
        
        //new NewJDialog().names.put(name,gp.toString());
        new NewJDialog().additem(new Reciever().mod,dia,name,gp.toString());
    }
    /*public void sendData(String data, String ipAddr) throws IOException{
        byte[] buffer = data.getBytes(); 
        DatagramPacket datagram = new
        DatagramPacket(buffer,buffer.length,group,port); 
        socket.send(datagram); 
    }*/
    public static void sendData(String data, String groupid, int port) throws IOException{
        port=1666;
        groupid = groupid;
        System.out.println("Send " + data + " " + groupid + " " + port);
        //MulticastSocket socket=new MulticastSocket(port);
        byte[] buffer = data.getBytes(); 
        InetAddress group = InetAddress.getByName(groupid);
        
        DatagramPacket datagram = new DatagramPacket(buffer,buffer.length,group,port); 
        System.out.println("sending multidata");
        socket.send(datagram); 
        System.out.println("multidata sent");
    }
    
    void ListenForRequest() throws SocketException, IOException {
        DatagramSocket dp = new DatagramSocket(7001);
        while(true) {
            byte[] receiveData = new byte[1000];
            DatagramPacket receivePacket = new DatagramPacket(receiveData,receiveData.length);
            System.out.println("receiving datapacket ");
            dp.receive(receivePacket);
            System.out.println("datapacket received " );
            String data=new String( receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println("Groupchatdata "+ data );
            String[] gpstr=data.split("\\+");
            System.out.println("gpstr= "+gpstr[0]+"   namestr= "+gpstr[1]);
            InetAddress gp = InetAddress.getByName(gpstr[0]);
            int prt=7001;
            System.out.println("joining group "+gpstr[1]+" address "+gpstr);
            join(gpstr[0],prt,gpstr[1]);
            System.out.println("group joined ");
        }
    }
    
    @Override
    public void run()
    {
        try {
            System.out.println("Calling listen for request function ");
            ListenForRequest();
        } catch (Exception ex) {
            Logger.getLogger(GroupChat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

