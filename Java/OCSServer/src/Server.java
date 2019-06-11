import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private  DatagramSocket datagramSocket;


    public Server() {
        try {
            datagramSocket = new DatagramSocket(2000);
        } catch (IOException e) {
            e.printStackTrace();
        }
        new Thread(new Runnable() {
            public void run() {
                while (true) {
                    try {
                        new Thread(new ExecuteTask(datagramSocket));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    class ExecuteTask implements Runnable
    {

        private DatagramSocket ds;
        public ExecuteTask(DatagramSocket ds)
        {
            this.ds = ds;
        }
        public void run()
        {
            try
            {
                while(true)
                {
                    byte[] buf = new byte[1024];
                    DatagramPacket dp = new DatagramPacket(buf,buf.length);
                    datagramSocket.receive(dp);
                    String ip = dp.getAddress().getHostAddress();

                    String data = new String(dp.getData(),0,dp.getLength());

                    if("886".equals(data))
                    {
                        datagramSocket.send(ip+"离开聊天室");
                        break;
                    }


                    System.out.println(ip+":"+data);
                }
            }
            catch (Exception e)
            {
                throw new RuntimeException("接收端失败");
            }
        }
    }
    public static void main(String[] args) {
        new Server();
    }
}