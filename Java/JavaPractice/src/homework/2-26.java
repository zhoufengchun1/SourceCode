package homework;

import java.util.Scanner;

class Answer4
{
    public static void main(String[] args)//2-26
    {
        int a[] = new int[5];
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int i;
        for (i = 0; i < n; i++)
            a[i] = sc.nextInt();
    }

    private static int Gongyue(int a[])//计算两个数的最大公约数
    {
        int i;
        int m = a[0];
        for (i = 1; i < a.length - 1; i++)
        {
            m = Gongyue_2(m, a[i]);
        }
        return m;
    }

    private static int Gongyue_2(int a, int b)
    {
        int i;
        int temp = 1;
        int min = a < b ? a : b;
        if (a == 1 || b == 1)
            return 1;
        else
        {
            for (i = 2; i <= min; i++)
            {
                if (a % i == 0 && b % i == 0)
                    temp = i;
            }
            return temp;
        }
    }

}