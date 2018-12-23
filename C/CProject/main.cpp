#include <bits/stdc++.h>

using namespace std;

int main()
{
    int n, p;
    scanf("%d%d", &n, &p);
    int a[1001];
    int *b = (int *) malloc(sizeof(int) * p);
    for (int i = 0; i < p; i++)b[i] = 0;
    for (int i = 0; i < n; i++)
    {
        scanf("%d", &a[i]);
        int flag = a[i] % p;
        while (b[flag % p] && b[flag % p] != a[i])flag++;
        b[flag % p] = a[i];
        if (i)printf(" ");
        printf("%d", flag % p);
    }
    return 0;
}
