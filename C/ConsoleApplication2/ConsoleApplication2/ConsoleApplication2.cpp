#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#define Max 1000
typedef struct//景点
{
	int num;
	char name[100];
	char features[200];
}VertexType;
typedef int EdgeType;
typedef struct//图的存储结构
{
	VertexType vexs[100];
	EdgeType edges[100][100];
	int n, e;
}MGraph;
void DengLu()
{
	printf("---------------校园导航--------------------\n");
	printf("               1,游客                      \n");
	printf("               2,管理员                    \n");
	printf("               0,退出系统                  \n");
	printf("请输入所需序号:");
}
void CaiDanYK()
{
	printf("--------------校园导航游客界面---------------\n");
	printf("              1,景点介绍                     \n");
	printf("              2,某一景点到其他景点的最短路径 \n");
	printf("              3,任意两景点之间的最短距离     \n");
	printf("              0,退出游客界面                 \n");
	printf("请输入所需序号:");
}
void CaiDanGLY()
{
	printf("---------------校园导航管理员界面-------------\n");
	printf("               1,创建地图                     \n");
	printf("               2,景点介绍                     \n");
	printf("               3,修改景点信息                 \n");
	printf("               4,增加景点信息                 \n");
	printf("               5,删除景点信息                 \n");
	printf("               6,增加道路                     \n");
	printf("               7,删除道路                     \n");
	printf("               8,某一景点到其他景点的最短路径 \n");
	printf("               9,任意两景点之间的最短距离     \n");
	printf("               0,退出管理员界面               \n");
	printf("请输入所需序号:");
}
void create(MGraph *G)//建立图的邻接矩阵
{
	int j, k;
	G->n = 12;
	G->e = 20;
	for (j = 1; j <= G->n; j++)//输入顶点信息，建立顶点表
	{
		G->vexs[j].num = j;
		strcpy(G->vexs[j].features, " ");
	}
	strcpy(G->vexs[1].name, "校门南口");
	strcpy(G->vexs[1].features, "学校正门");
	strcpy(G->vexs[2].name, "网计学院");
	strcpy(G->vexs[3].name, "邯郸音乐厅");
	strcpy(G->vexs[3].features, "节日才艺表演的地方");
	strcpy(G->vexs[4].name, "体检中心");
	strcpy(G->vexs[5].name, "信息学部");
	strcpy(G->vexs[6].name, "花园景观");
	strcpy(G->vexs[6].features, "有美丽的风景，还有湖水");
	strcpy(G->vexs[7].name, "图书馆");
	strcpy(G->vexs[7].features, "图书馆的书应有尽有");
	strcpy(G->vexs[8].name, "操场");
	strcpy(G->vexs[8].features, "学生运动跑步的地方");
	strcpy(G->vexs[9].name, "校门东口");
	strcpy(G->vexs[10].name, "餐厅");
	strcpy(G->vexs[11].name, "校园北口");
	strcpy(G->vexs[12].name, "银杏景观");
	strcpy(G->vexs[12].features, "美丽的银杏树，每年都有很多游客去拍照");
	for (j = 1; j <= G->n; j++)
	{
		for (k = 1; k <= G->n; k++)
		{
			G->edges[j][k] = Max;
		}
	}
	G->edges[1][2] = G->edges[2][1] = 400;
	G->edges[1][5] = G->edges[5][1] = 400;
	G->edges[1][6] = G->edges[6][1] = 500;
	G->edges[1][9] = G->edges[9][1] = 600;
	G->edges[2][3] = G->edges[3][2] = 500;
	G->edges[3][4] = G->edges[4][3] = 200;
	G->edges[3][8] = G->edges[8][3] = 480;
	G->edges[3][7] = G->edges[7][3] = 400;
	G->edges[3][5] = G->edges[5][3] = 500;
	G->edges[4][8] = G->edges[8][4] = 350;
	G->edges[5][6] = G->edges[6][5] = 100;
	G->edges[6][7] = G->edges[7][6] = 100;
	G->edges[6][9] = G->edges[9][6] = 200;
	G->edges[7][8] = G->edges[8][7] = 280;
	G->edges[7][9] = G->edges[9][7] = 300;
	G->edges[8][11] = G->edges[11][8] = 200;
	G->edges[9][10] = G->edges[10][9] = 100;
	G->edges[10][11] = G->edges[11][10] = 100;
	G->edges[10][12] = G->edges[12][10] = 100;
	G->edges[11][12] = G->edges[12][11] = 100;
}
void  GetVex(MGraph *G)//景点介绍
{
	int i, j, k;
	for (i = 1; i <= G->n; i++)//景点的编号和姓名
	{
		printf("%4d %4s\n", G->vexs[i].num, G->vexs[i].name);
	}
	printf("请输入要查询的景点编号");
	while (1)
	{
		scanf("%d", &k);
		if (k > 0 && k <= G->n)
		{
			printf("%4d %4s  %4s\n", G->vexs[k].num, G->vexs[k].name, G->vexs[k].features);
		}
		else
		{
			printf("编号错误！\n");
			break;
		}
	}
}
void putVertex(MGraph *G, int v)
{
	int i, j, c, d;
	char a[123];
	printf("请输入需要修改的信息(1,编号2,名称3,介绍):");
	scanf("%d", &c);
	if (c == 2)
	{
		printf("请输入修改后的信息项的信息:");
		scanf("%s", a);
		strcpy(G->vexs[v].name, a);
	}
	if (c == 3)
	{
		printf("请输入修改后的信息项的信息:");
		scanf("%s", a);
		if (a == G->vexs[v].features)
		{
			strcpy(G->vexs[v].features, a);
		}
	}
	if (c == 1)
	{
		printf("请输入修改后的信息项的信息:");
		scanf("%d", &d);
		G->vexs[v].num = d;
	}
}
void InSertVertex(MGraph *G, VertexType v)
{
	G->n++;
	G->vexs[G->n] = v;
}
void DeleteVertex(MGraph *G, int v)
{
	int j;
	for (j = v; j <= G->n; j++)
	{
		G->vexs[j] = G->vexs[j + 1];
	}
	G->n--;
}
void InsertArc(MGraph *G, int v, int w)//v,w两景点，e为vw的边
{
	int e;
	printf("请输入道路长度:");
	scanf("%d", &e);
	G->edges[v][w] = G->edges[w][v] = e;
}
void DeleteArc(MGraph *G, int v, int w)
{
	G->edges[v][w] = Max;
	G->edges[w][v] = Max;
}
void ShortestPath(MGraph *G)
{
	int v, j, b, c, i, m, h, f = 0;
	int final[100];
	int P[100], D[100];
	printf("请输入景点的编号:");
	scanf("%d", &v);
	for (j = 1; j <= G->n; j++)
	{
		if (v == G->vexs[j].num)
		{
			b = j;
			f = 1;
		}
	}
	if (f == 0)
	{
		printf("没有找到！");
	}
	for (j = 1; j <= G->n; j++)
	{
		P[j] = -1;
		D[j] = G->edges[b][j];
		final[j] = 0;
	}
	D[b] = 0;
	final[b] = 1;
	for (j = 2; j <= G->n; j++)
	{
		m = Max + 1;
		for (i = 1; i <= G->n; i++)
		{
			if (final[i] == 0 && D[i] < m)
			{
				c = i;
				m = D[i];
			}
		}
		final[c] = 1;
		for (i = 1; i <= G->n; i++)
		{
			if (final[i] == 0 && D[c] + G->edges[c][i] < D[i])
			{
				D[i] = D[c] + G->edges[c][i];
				P[i] = c;
			}
		}
	}
	printf("%4s到其他景点的路径:\n", G->vexs[b].name);
	for (i = 2; i <= G->n; i++)
	{
		h = P[i];
		printf("%d米 %s", D[i], G->vexs[b].name);
		while (h > 1)
		{
			printf("<-%s", G->vexs[h].name);
			h = P[h];
		}
		printf("\n");
	}
}
void TopeStination(MGraph *G)
{
	int D[1233];
	int P[1233];
	int final[1233];
	int v, w;
	int m, h, b, c, i, j, k;
	printf("请输入起点编号，终点编号:");
	while (1)
	{
		scanf("%d %d", &v, &w);
		if (v == w)
		{
			printf("输入错误！");
			break;
		}

		for (i = 1; i <= G->n; i++)
		{
			if (v == G->vexs[i].num)
			{
				b = i;
			}
			if (w == G->vexs[i].num)
			{
				c = i;
			}
		}
		for (j = 1; j <= G->n; j++)
		{
			D[j] = G->edges[b][j];
			P[j] = -1;
			final[j] = 0;
		}
		D[b] = 0;
		final[b] = 1;
		for (i = 2; i <= G->n; i++)
		{
			m = Max + 1;
			for (j = 1; j <= G->n; j++)
			{
				if (final[j] == 0 && D[j] < m)
				{
					m = D[j];
					h = j;
				}
			}
		}
		final[h] = 1;
		for (i = 1; i <= G->n; i++)
		{
			if (final[i] == 0 && D[h] + G->edges[h][i] < D[i])
			{
				P[i] = h;
				D[i] = D[h] + G->edges[h][i];
			}
		}
		for (j = 1; j <= G->n; j++)
		{
			if (j == c)
			{
				printf("%d米 %s", D[j], G->vexs[j].name);
				h = P[i];
				while (h >= 1)
				{
					printf("<-%s", G->vexs[h].name);
					h = P[h];
				}
				printf("<-%s\n", G->vexs[b].name);
			}
		}
		printf("请输入起点编号，终点编号:");

	}
}
int main()
{
	MGraph G;
	VertexType v1;
	int a, b;
	int v, w, v2, w2, i, j, k;
	while (1)
	{
		DengLu();
		scanf("%d", &a);
		
			if (a == 1)
			{
				create(&G);
				CaiDanYK();
				scanf("%d", &b);
				if (b == 1)
				{
					GetVex(&G);
				}
				if (b == 2)
				{
					ShortestPath(&G);
				}
				if (b == 3)
				{
					TopeStination(&G);
				}
				if (b == 0)
				{
					break;
				}
			}
		
		
			if (a == 2)
			{
				CaiDanGLY();
				scanf("%d", &b);
				create(&G);
				if (b == 1)
				{
					create(&G);
					printf("创建完毕!\n");
				}
				if (b == 2)
				{
					GetVex(&G);
				}
				if (b == 3)
				{
					create(&G);
					printf("请输入要修改的景点编号:");
					scanf("%d", &v);
					putVertex(&G, v);
				}
				if (b == 4)
				{
					printf("请输入需要增加的景点信息:(编号 名称 详细信息)");
					scanf("%d  %s  %s", &v1.num, v1.name, v1.features);
					InSertVertex(&G, v1);
				}
				if (b == 5)
				{
					printf("请输入需要删除的景点编号:");
					scanf("%d", &v);
					DeleteVertex(&G, v);
				}
				if (b == 6)
				{
					printf("请输入需要增加道路的两景点编号：");
					scanf("%d  %d", &i, &j);
					for (k = 1; k <= G.n; k++)
					{
						if (i == G.vexs[k].num)
							v = i;
						if (j == G.vexs[k].num)
							w = j;
					}
					InsertArc(&G, v, w);
				}
				if (b == 7)
				{
					printf("请输入需要删除道路的两景点编号:");
					scanf("%d %d", &i, &j);
					for (k = 1; k < G.n; k++)
					{
						if (i == G.vexs[k].num)
						{
							v2 = i;
						}
						if (j == G.vexs[k].num)
						{
							w2 = j;
						}
					}
					DeleteArc(&G, v2, w2);
				}
				if (b == 8)
				{
					ShortestPath(&G);
				}
				if (b == 9)
				{
					TopeStination(&G);
				}
				if (b == 0)
				{
					break;
				}
			}
		
		if (a == 0)
		{
			exit(0);
		}
	}
}
