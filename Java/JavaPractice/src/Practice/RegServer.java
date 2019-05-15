package Practice;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

class RegServer
{
    public static void main(String[] args) throws IOException
    {
        ServerSocket serverSocket = new ServerSocket(8080);
        Socket socket = serverSocket.accept();

        InputStream inputStream = socket.getInputStream();
        byte[] bytes = new byte[1024];
        int len = inputStream.read(bytes);
        System.out.println(new StringBuffer(new String(bytes,0,len)));
        OutputStream outputStream = socket.getOutputStream();
        PrintWriter printWriter = new PrintWriter(outputStream, true);
        printWriter.println("<font size=7 color=green> ×¢²á³É¹¦ </font>");
        socket.close();
        serverSocket.close();
    }
}
