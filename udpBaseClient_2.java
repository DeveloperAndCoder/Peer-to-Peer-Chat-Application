 
// Java program to illustrate Client side 
// Implementation using DatagramSocket 
import java.io.IOException; 
import java.net.DatagramPacket; 
import java.net.DatagramSocket; 
import java.net.InetAddress; 
import java.util.Scanner; 

public class udpBaseClient_2 implements Runnable
{ 
	public static void main(String args[]) throws IOException 
	{ 
		
	} 
        
        public static void sendData(String data, String ipAddr) throws IOException{
            //Scathrow new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.nner sc = new Scanner(System.in); 

		// Step 1:Create the socket object for 
		// carrying the data. 
		DatagramSocket ds = new DatagramSocket(); 
                System.out.println("Sending to " + ipAddr);
		InetAddress ip = InetAddress.getByName(ipAddr); 
		byte buf[] = null; 

		// loop while user not enters "bye" 
		//while (true) 
		{ 
			String inp = data;//sc.nextLine(); 

			// convert the String input into the byte array. 
			buf = inp.getBytes(); 

			// Step 2 : Create the datagramPacket for sending 
			// the data. 
			DatagramPacket DpSend = 
				new DatagramPacket(buf, buf.length, ip, 1234); 

			// Step 3 : invoke the send call to actually send 
			// the data. 
			ds.send(DpSend); 
		} 
        }

    @Override
    public void run() {
        
    }
} 
