package Practice;

import javax.print.DocFlavor;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

class Client3
{
    public static void main(String[] args) throws Exception
    {
        Socket socket = new Socket("localhost", 10001);
//        BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream("D://123.txt"));.
        FileInputStream fileInputStream = new FileInputStream("D://123.txt");
        OutputStream outputStream = socket.getOutputStream();
        //字节输入流，从文件中读取
//        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(socket.getOutputStream());
        //socket输出流
        int num;
        byte b[] = new byte[1024];
        while ((num = fileInputStream.read(b)) != -1)
        {
            outputStream.write(b, 0, b.length);
        }
        socket.shutdownOutput();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        System.out.println(bufferedReader.readLine());
        bufferedReader.close();
        fileInputStream.close();
    }
}

class Server3
{
    public static void main(String[] args) throws Exception
    {
        ServerSocket serverSocket = new ServerSocket(10001);
        Socket socket = serverSocket.accept();
        System.out.println("ip:" + socket.getInetAddress().getHostAddress() + " Connected");
//        BufferedInputStream bufferedInputStream = new BufferedInputStream(socket.getInputStream());
        //Socket输入流
        InputStream inputStream = socket.getInputStream();

//        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream("D://copy.txt"));
        //读取流
        FileOutputStream fileOutputStream = new FileOutputStream("D://copy.txt");

        int num;
        byte b[] = new byte[1024];
        while ((num = inputStream.read(b)) != -1)
        {
            fileOutputStream.write(b, 0, b.length);
        }
        BufferedWriter bufferedWriter1 = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
        printWriter.println("Upload Success!");
        inputStream.close();
        fileOutputStream.close();
    }
}
