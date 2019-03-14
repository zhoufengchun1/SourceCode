package homework;

import java.util.Scanner;

class Answer3
{
    public static void main(String[] args)//2-28
    {
        Answer cmp = new Answer();
        Scanner sc = new Scanner(System.in);
        String str = sc.next();
        boolean bl = huiWen(str);
        System.out.print(bl);
    }

    public static boolean huiWen(String str)
    {
        int len = str.length();
        int i;
        boolean flag = true;
        StringBuffer str_res = new StringBuffer(str);
        str_res = str_res.reverse();
        for (i = 0; i < len; i++)
        {
            if (str.charAt(i) != str_res.charAt(i))
            {
                flag = false;
                break;
            }
        }
        return flag;
    }
}