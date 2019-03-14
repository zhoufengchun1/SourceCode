package Practice;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

class Server
{
    public static void main(String[] args) throws Exception
    {
        ServerSocket serverSocket = new ServerSocket(10001);
        while (true)
        {
            Socket socket = serverSocket.accept();
            InputStream inputStream = socket.getInputStream();
            byte b[] = new byte[1024];
            int len = inputStream.read(b);
            System.out.println(socket.getInetAddress().getHostAddress()+ "Connected");
            System.out.println(new String(b, 0, b.length));

            OutputStream outputStream = socket.getOutputStream();
            outputStream.write("12345 67890".getBytes());
        }
    }
}

class Client
{
    public static void main(String[] args) throws Exception
    {
        Socket socket = new Socket("localhost", 10001);
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write("This is String data".getBytes());

        InputStream inputStream = socket.getInputStream();
        byte[] b = new byte[1024];
        int len = inputStream.read(b);
        String string = new String(b, 0, len);
        System.out.println(string);
        outputStream.close();
    }
}