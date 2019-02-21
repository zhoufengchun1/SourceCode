#include <bits/stdc++.h>

int main()
{
    char a[52];
    memset(a, -1, sizeof(a));
    int i = 0;
    char temp = '?';
    float sum = 1;
    int count = 0;
    int m = 0;
    temp = a[0];
    while (temp != -1)
    {
        if (temp == '-')
        {
            temp = a[++i];
            sum += 0.5;
        }
        else
        {
            temp = a[i++];
            count++;
        }
        if (temp == '2')
        {
            m++;
        }
    }
    if ((a[count--] - '0') % 2 == 0)
        sum++;
    printf("%f", (m / count) * sum);
}
