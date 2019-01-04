#include <bits/stdc++.h>
#include <algorithm>
int search(int num);
int a[100];
int n;
int main()
{
    int m;
    scanf("%d %d", &n, &m);
    int b[100];
    int i, j;
    for (i = 0; i < n; i++)
    {
        scanf("%d", &a[i]);
    }
    sort(a, a + n);
    for (i = 0; i < m; i++)
    {
        int temp;
        scanf("%d", &temp);
        b[i] = search(temp);
    }
    for (i = 0; i < m; i++)
    {
        printf("%d", b[i]);
        if (i != m - 1)
            printf(" ");
    }
}
int search(int num)
{
    int low, high;
    low = 0, high = n - 1;
    while (low < high)
    {
        int mid = (low + high) / 2;
        if (mid > num)
        {
            high = low - 1;
        }
        else if (mid < num)
        {
            low = mid + 1;
        }
        else return mid;
    }
    return -1;
}
