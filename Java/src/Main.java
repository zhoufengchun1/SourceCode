import java.util.Arrays;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        int m = scanner.nextInt();
        int[][] count = new int[m][2];
        int[] max = new int[m+1¡¢];

        for(int i=0;i<m;i++)
        {
            count[i][0] = scanner.nextInt();
            count[i][1] = 0;
            max[i] = 0;
        }
        for (int i = 0; i < m; i++)
        {
            for (int j = 1; j <= m; j++)
            {
                if (max[j] + count[i][0] <= 100)
                {
                    max[j] += count[i][0];
                    count[i][1] = j;
                    break;
                }
            }
        }
        int temp=1;
        for(int i=0;i<m;i++)
        {
            if (count[i][1] > temp)
            {
                temp = count[i][1];
            }
        }
        for(int i=0;i<m;i++)
        {
            System.out.println(count[i][0] + " " + count[i][1]);
        }
        System.out.println(temp);
    }
}