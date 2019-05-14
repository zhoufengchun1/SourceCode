import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

class Demo
{
    public static void main(String[] args) throws IOException
    {
        Socket socket = new Socket("localhost", 8080);
        PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        printWriter.println("GET / HTTP/1.1");
        printWriter.println("Host: localhost");
        printWriter.println("Content-Type: text/html");
        printWriter.println();
        printWriter.flush();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String string = "";
        while ((string = bufferedReader.readLine() )!= null)
        {
            System.out.println(string);
        }
        bufferedReader.close();
        printWriter.close();
        socket.close();
    }
}
