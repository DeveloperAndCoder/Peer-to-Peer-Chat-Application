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
public class MultiSend implements Runnable{
    public static void main(String args[]) throws IOException 
	{ 
		
	} 
    public static void sendData(String data, String groupid, int port) throws IOException{
        MulticastSocket socket=new MulticastSocket(port);
        byte[] buffer = data.getBytes(); 
        InetAddress group = InetAddress.getByName(groupid);
        DatagramPacket datagram = new
        DatagramPacket(buffer,buffer.length,group,port); 
        socket.send(datagram); 
    }
    @Override

     public void run()
    {
        
    }
    
}
