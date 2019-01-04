#include <bits/stdc++.h>
using namespace std;
int main()
{
	int N, K;
	scanf("%d %d", &N, &K);
	int i, j;
	int a[102];
	for (i = 0; i < N; i++)
	{
		scanf("%d", &a[i]);
	}
	int temp;
	for (i = 0; i < K; i++)
	{
		for (j = 0; j < N-i-1; j++)
		{
			if (a[j] > a[j + 1])
			{
				temp = a[j];
				a[j] = a[j + 1];
				a[j + 1] = temp;
			}
		}
	}
	for (i = 0; i < N; i++)
	{
		printf("%d", a[i]);
		if (i != N - 1)
			printf(" ");
	}
}