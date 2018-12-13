package Practice;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

class Server2
{
    public static void main(String[] args) throws Exception
    {
        ServerSocket serverSocket = new ServerSocket(10002);
        Socket socket = serverSocket.accept();
        //获取Socket
        String ip = socket.getInetAddress().getHostAddress();
        System.out.println(ip);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        String string = null;
        while ((string = bufferedReader.readLine()) != null)
        {
            String Data = string.toUpperCase();
            bufferedWriter.write(Data);
            bufferedWriter.newLine();
            if ("over".equals(string))
            {
                break;
            }
            bufferedWriter.flush();
        }
        bufferedReader.close();
        bufferedWriter.close();
    }
}

class Client2
{
    public static void main(String[] args) throws Exception
    {
        Socket socket = new Socket("localhost", 10002);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        //键盘输入流
        BufferedWriter bufferedWriterOfSoc = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        //Socket输出流，将字节输出到服务端
        BufferedReader bufferedReaderOfSoc = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        //Socket输入流，从服务端接收数据
        String string = null;
        while ((string = bufferedReader.readLine()) != null)
        {
            if ("over".equals(string))
            {
                break;
            }
            bufferedWriterOfSoc.write(string);//读取键盘输入流并写入到Socket流
            bufferedWriterOfSoc.newLine();
            bufferedWriterOfSoc.flush();
            String Data=bufferedReaderOfSoc.readLine();//读取从服务端返回的数据
            System.out.println(Data);

        }
        bufferedReaderOfSoc.close();
        bufferedWriterOfSoc.close();
    }
}