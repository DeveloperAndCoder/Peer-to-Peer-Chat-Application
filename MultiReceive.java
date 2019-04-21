/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author rahul
 */

import java.net.InetAddress;
import java.net.*; 
import java.io.*; 
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
public class MultiReceive implements Runnable{
        private MulticastSocket socket; 
	private InetAddress group; 
        private String gpstr;
	private int port; 
	private static final int MAX_LEN = 1000; 
        static NewJDialog dia;
	MultiReceive(MulticastSocket socket,String group,int port) 
	{ 
		this.socket = socket; 
            try { 
                this.group = InetAddress.getByName(group);
                gpstr=group;
            } catch (UnknownHostException ex) {
                Logger.getLogger(MultiReceive.class.getName()).log(Level.SEVERE, null, ex);
            }
		this.port = port; 
	} 
//        MultiReceive()
//        {
//            
//        }
        
        public static void init(NewJDialog D) 
        {
            dia = D;
        }
        public static StringBuilder data(byte[] a) 
	{ 
		if (a == null) 
			return null; 
		StringBuilder ret = new StringBuilder(); 
		int i = 0; 
		while (a[i] != 0) 
		{ 
			ret.append((char) a[i]); 
			i++; 
		} 
		return ret; 
	} 
	
        @Override
	public void run() 
	{ 
            System.out.println("groupchat finished=" + GroupChat.finished);

            while(!GroupChat.finished) 
            { 
                try
                {
                    byte[] message = new byte[65535];
                //byte[] buffer = new byte[MultiReceive.MAX_LEN]; 
                //MulticastSocket socket= new MulticastSocket(1666);
                DatagramPacket datagram = new
                DatagramPacket(message,message.length,group,port); 
                //String message=""; 
                
                System.out.println("receiving multicast datagram" );
                socket.receive(datagram); 
                
                System.out.println("received multicast datagram" );
                message =datagram.getData();
                String dd = data(message).toString();
                System.out.println("mesaage = " + dd); 
                

                String addr = gpstr;//socket.getInterface().getAddress().toString();
                //addr=addr.trim();
                System.out.println("c1="+dd);
                String sender = datagram.getAddress().toString();
                sender = sender.substring(1);
                System.out.println("c2="+dd);
                System.out.println(sender + "HH" + NewJDialog.ips.get(sender));
                System.out.println("c2.5="+dd);
                if(NewJDialog.ips.get(sender) != null)
                {
                    System.out.println("c3="+dd);
                    NewJDialog.receive(dd, addr, dia);
                }
                
                } 
                catch(IOException e) 
                { 
                    System.out.println("Socket closed!"); 
                } 
            }
        }
}
