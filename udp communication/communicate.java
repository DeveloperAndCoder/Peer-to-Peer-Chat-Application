import java.sql.*;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class communicate {
    private DatagramSocket dataSocket;
    public static void main(String args[]) throws Exception {
        communicate ob = new communicate();
        String ip = "192.168.43.118";
        ob.text(/*ip*/"127.0.0.1");
    }
    public void text(String friend_ip) throws Exception{
        //System.out.println("Press 'Exit' to exit the chat");
        int send_port = 40002;
        int receive_port = 40002;
        //InetAddress inetaddr = InetAddress.getByName(friend_ip);
        //byte[] ipAddr = new byte[] { (byte)192, (byte)168, (byte)43, (byte)118 };
        byte[] ipAddr = new byte[] { (byte)127, (byte)0, (byte)0, (byte)1 };
        System.out.println("Length = " + ipAddr.length);
        InetAddress inetaddr = InetAddress.getByAddress(ipAddr);"
        System.out.println(inetaddr.toString());
        /*DatagramSocket */dataSocket = new DatagramSocket(receive_port, inetaddr);
        System.out.println("YO");
        Runnable send = new SendMsg(send_port, friend_ip, dataSocket);
        Runnable receive = new GetMsg(friend_ip, receive_port, dataSocket);
        Thread t1 = new Thread(send);
        Thread t2 = new Thread(receive);
        t1.start();
        t2.start();
    }
    
    class SendMsg implements Runnable {
        //private DatagramSocket senderSocket;
        private int send_port;
        private String sendto_ip;
        SendMsg(int port, String friend_ip, DatagramSocket ds)throws Exception {
            send_port = port;
            sendto_ip = friend_ip;
        }
        public void run(){
            try {
                while(true) {
                String sendString;
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                sendString = br.readLine();
                byte[] sendData = sendString.getBytes();
                //byte[] ipAddr = new byte[] { (byte)192, (byte)168, (byte)43, (byte)118 };
                byte[] ipAddr = new byte[] { (byte)127, (byte)0, (byte)0, (byte)1 };
                InetAddress inetAddress = InetAddress.getByAddress(ipAddr);
                DatagramPacket sendPacket = new DatagramPacket (sendData, sendData.length, inetAddress, send_port);
                //senderSocket.send(sendPacket);
                serverSocket.send(sendPacket);
                System.out.println("Message Sent");
                }
            }
            catch(Exception e) {
                System.out.println("Exc at Sender\n" + e);
            }
            finally {
                if(serverSocket != null) serverSocket.close();
            }
        }

    }

    class GetMsg implements Runnable{
        //private DatagramSocket serverSocket;
        private String friend_ip;
        private int receive_port;
        
        GetMsg(String ip, int port, DatagramSocket ds) throws Exception{
            friend_ip = ip;
            receive_port = port;
        }
        public void run(){
            try {
                while(true) {
                byte[] receiveData = new byte[10000];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket);
                String message = new String (receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println(friend_ip + ": " + message);
                }
            }
            catch(Exception e) {
                System.out.println("Exc at Rec\n" + e);
            }
            finally {
                if(serverSocket != null) serverSocket.close();
            }
        }
    }
}
