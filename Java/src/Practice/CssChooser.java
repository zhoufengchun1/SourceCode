package Practice;

import java.lang.reflect.Array;
import java.util.Scanner;

public class CssChooser
{
    public static void main(String[] args)
    {
        int line, num;
        Scanner scanner = new Scanner(System.in);
        line = scanner.nextInt();
        num = scanner.nextInt();
        int i, j;
        String string[] = new String[line];
        String condition[] = new String[num];
        StringBuilder stringBuilder = new StringBuilder();
        int count[] = new int[num];
        for (i = 0; i < line; i++)
        {
            string[i] = scanner.next();
        }
        for (i = 0; i < num; i++)
        {
            condition[i] = scanner.next();
        }
        for (i = 0; i < line; i++)
        {
            for (j = 0; j < num; j++)
            {
                if (string[i].contains(condition[j]))
                {
                    count[j]++;
                }
            }

        }
    }

}
