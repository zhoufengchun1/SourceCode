#include <bits/stdc++.h>
using namespace std;
int du[1005];
int pre[1004];
void init(int num)
{
    for (int i = 1; i <= num; i++)
    {
        pre[i] = i;
    }
}
int find(int x)
{
    if (x == pre[x])
        return x;
    else
    {
        pre[x] = find(pre[x]);
        return pre[x];
    }
}
void join(int x, int y)
{
    int t1, t2;
    t1 = find(x);
    t2 = find(y);
    if (t1 != t2)
        pre[t2] = t1;
}
int judge(int num)
{
    int t = 0;
    for (int i = 1; i <= num; i++)
    {
        if (pre[i] == i)
            t++;
        if (du[i] % 2 != 0)
            return 0;
    }
    if (t != 1)
        return 0;
    return 1;
}
int main()
{
    int num, count;
    scanf("%d %d", &num, &count);
    init(num);
    int x, y;
    memset(du, 0, sizeof(du));

    for (int i = 1; i <= count; i++)
    {
        scanf("%d %d", &x, &y);
        du[x]++;
        du[y]++;
        join(x, y);

    }
    if (judge(num))
        printf("1");
    else
        printf("0");

}