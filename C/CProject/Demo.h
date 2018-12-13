#include <bits/stdc++.h>

using namespace std;

int main(int argc, char *argv[])
{
    int i, j, k;
    for (int n = 100; n <= 999; n++)
    {
        i = n / 100;
        j = (n - i * 100) / 10;
        k = n % 10;
        if (i * i + j * j + k * k == n)
            printf("%d", n);
    }
}
