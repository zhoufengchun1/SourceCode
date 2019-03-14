package Practice;


import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

class Read implements Runnable
{
    private PipedInputStream pipedInputStream;

    public Read(PipedInputStream pipedInputStream)
    {
        this.pipedInputStream = pipedInputStream;
    }

    @Override
    public void run()
    {
        try
        {
            System.out.println("开始读取");
            byte b[] = new byte[1024];
            int len = pipedInputStream.read(b);
            String str = new String(b, 0, len);
            System.out.println(str);
        } catch (IOException e)
        {
        }
    }
}

class Write implements Runnable
{
    public Write(PipedOutputStream pipedOutputStream)
    {
        this.pipedOutputStream = pipedOutputStream;
    }

    private PipedOutputStream pipedOutputStream;

    @Override
    public void run()
    {
        try
        {
            System.out.println("开始写入");
            pipedOutputStream.write("1234".getBytes());
            pipedOutputStream.close();
        } catch (IOException e)
        {
        }
    }
}

public class PipedStreamDemo
{
    public static void main(String[] args) throws IOException
    {
        PipedOutputStream pipedOutputStream = new PipedOutputStream();
        PipedInputStream pipedInputStream = new PipedInputStream();
        pipedInputStream.connect(pipedOutputStream);
        new Thread(new Read(pipedInputStream)).start();
        new Thread(new Write(pipedOutputStream)).start();
    }

}
