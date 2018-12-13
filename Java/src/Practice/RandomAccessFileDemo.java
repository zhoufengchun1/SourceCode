package Practice;

import java.io.*;

public class RandomAccessFileDemo
{
    public static void main(String[] args) throws IOException
    {
        DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream("D:/123.txt"));
        DataInputStream dataInputStream = new DataInputStream(new FileInputStream("D:/123.txt"));
        dataOutputStream.writeInt(789);
        dataOutputStream.writeBoolean(true);
        dataOutputStream.writeDouble(3.14159);
        System.out.println(dataInputStream.readDouble());
        System.out.println(dataInputStream.readInt());
        System.out.println(dataInputStream.readBoolean());
    }
}
