package Practice;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.security.spec.ECField;

class Send2 implements Runnable
{
    private DatagramSocket datagramSocket;

    public Send2(DatagramSocket datagramSocket)
    {
        this.datagramSocket = datagramSocket;
    }

    @Override
    public void run()
    {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String string = null;

        try
        {
            while ((string = bufferedReader.readLine()) != null)
            {
                byte b[] = new byte[1024];
                b = string.getBytes();
                DatagramPacket datagramPacket = new DatagramPacket(b, b.length, InetAddress.getByName("localhost"), 10002);
                datagramSocket.send(datagramPacket);
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}

class Receive2 implements Runnable
{
    private DatagramSocket datagramSocket;

    public Receive2(DatagramSocket datagramSocket)
    {
        this.datagramSocket = datagramSocket;
    }

    @Override
    public void run()
    {
        try
        {
            while (true)
            {
                String string = null;
                byte b[] = new byte[1024];
                DatagramPacket datagramPacket = new DatagramPacket(b, b.length);
                datagramSocket.receive(datagramPacket);
                string = new String(datagramPacket.getData(), 0, datagramPacket.getLength());
                System.out.println("kangYh:"+string);
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}
class ChatDemo
{
    public static void main(String[] args)throws Exception
    {
        new Thread(new Receive2(new DatagramSocket(10002))).start();
        new Thread(new Send2(new DatagramSocket())).start();
    }
}