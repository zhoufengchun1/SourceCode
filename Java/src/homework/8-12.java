package homework;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

class ans
{
    public static void main(String[] args) throws IOException
    {
        PrintWriter printWriter = new PrintWriter(new File("D://123.txt"));
        Scanner scanner = new Scanner(System.in);
        System.out.print("起始：");
        int m = scanner.nextInt();
        System.out.print("结束：");
        int n = scanner.nextInt();
        for(int i=m;i<=n;i++)
        {
            if (su(i))
            {
                printWriter.print(i+" ");
            }
        }
        printWriter.close();
    }
    public static boolean su(int num)//判断是否为素数
    {
        int i = 2;
        for (; i <= Math.sqrt(num); i++)
        {
            if (num % i == 0)
            {
                return false;
            }
        }
        return true;
    }
}