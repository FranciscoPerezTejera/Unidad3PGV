package UDP;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


public class UdpClient {
    public static void main(String[] args)throws Exception {
        DatagramSocket clientSocket = new DatagramSocket();
        InetAddress IPAddess = InetAddress.getByName("localhost");
        byte [] sendData = new byte [1024];
        String sentence = "Saludos desde el cliente UDP";
        sendData = sentence.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length,IPAddess,9876);
        clientSocket.send(sendPacket);
        clientSocket.close();
    }
}
