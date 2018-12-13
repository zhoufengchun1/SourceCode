package Practice;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

class Send
{
    public static void main(String[] args) throws Exception
    {
        DatagramSocket datagramSocket = new DatagramSocket();
        while (true)
        {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            String str = null;
            while ((str = bufferedReader.readLine()) != null)
            {
                if (str.equals("over"))
                {
                    break;
                }
                byte b[] = new byte[1024];
                b = str.getBytes();
                DatagramPacket datagramPacket = new DatagramPacket(b, b.length, InetAddress.getByName("localhost"), 10000);
                datagramSocket.send(datagramPacket);
            }
            datagramSocket.close();
        }

    }
}

class Receive
{
    public static void main(String[] args) throws Exception
    {

        DatagramSocket datagramSocket = new DatagramSocket(10000);
        while (true)
        {

            byte b[] = new byte[1024];
            DatagramPacket datagramPacket = new DatagramPacket(b, b.length);
            datagramSocket.receive(datagramPacket);
            System.out.println(new String(datagramPacket.getData(), 0, datagramPacket.getLength()));
        }
    }
}
