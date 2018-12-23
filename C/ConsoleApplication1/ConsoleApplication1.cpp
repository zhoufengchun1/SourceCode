
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#define Max 120
#define MAX 10000
typedef   struct
{
	int num;
	char  name[100];
	char  features[200];
} node;
typedef struct
{
	node  vexs[Max];
	int  edges[Max][Max];
	int n, e;
} MGraph;
void caidan(MGraph *a);
void caidan1();
void caidan2();
void shu(int denglu, MGraph *a);
void shu1(int you, MGraph *a);
void shu2(int guan, MGraph *a);
void pan(MGraph *a);
void pan1(MGraph *a);
void pan2(MGraph *a);
int main()
{
	MGraph a =
	{
		{
			{1,"体检中心","其中包括医务室等地"},
			{2,"操场","锻炼身体的场所"},
			{3,"校门北口","学校出入口"},
			{4,"银杏景观","一片银杏树林，风景优美"},
			{5,"邯郸音乐厅","艺术表演所在地"},
			{6,"图书馆","借书还书以及学习场所"},
			{7,"信息学部","信息学院所在地"},
			{8,"花园景观","风景优美，值得一看"},
			{9,"餐厅","学生用食场地"},
			{10,"校门东口","校园东口"},
			{11,"网计学院","网络空间安全与计算机学院地址"},
			{12,"校门南口","校园南口"}
		},
		{
			{MAX,350,MAX,MAX,200,MAX,MAX,MAX,MAX,MAX,MAX,MAX},
			{350,MAX,200,MAX,480,280,MAX,MAX,MAX,MAX,MAX,MAX},
			{MAX,200,MAX,100,MAX,MAX,MAX,MAX,100,MAX,MAX,MAX},
			{MAX,MAX,100,MAX,MAX,MAX,MAX,MAX,100,MAX,MAX,MAX},
			{200,480,MAX,MAX,MAX,400,500,MAX,MAX,MAX,MAX,MAX},
			{MAX,280,MAX,MAX,400,MAX,MAX,50,MAX,300,MAX,500},
			{MAX,MAX,MAX,MAX,500,MAX,MAX,100,MAX,MAX,MAX,400},
			{MAX,MAX,MAX,MAX,MAX,50,100,MAX,MAX,200,MAX,500},
			{MAX,MAX,100,100,MAX,MAX,MAX,MAX,MAX,100,MAX,MAX},
			{MAX,MAX,MAX,MAX,MAX,300,MAX,200,100,MAX,MAX,600},
			{MAX,MAX,MAX,MAX,500,MAX,MAX,MAX,MAX,MAX,MAX,400},
			{MAX,MAX,MAX,MAX,MAX,MAX,400,500,MAX,600,400,MAX}
		},
		12,20
	};
	caidan(&a);
	return 0;
}
void caidan(MGraph *a)
{
	printf("-----------校园导航系统-----------\n");
	printf("----------------------------------\n");
	printf("-------------登录用户-------------\n");
	printf("-------------1.管理人员-----------\n");
	printf("-------------2.游客---------------\n");
	printf("-------------3.退出---------------\n");
	printf("----------------------------------\n");
	int denglu;
	printf("\n提示：请输入菜单下方选项的序号来进行选择！！！\n");
	printf("\n输入：   ");
	scanf("%d", &denglu);
	shu(denglu, a);
}
void caidan1()
{
	printf("----------------菜单------------------------------\n");
	printf("----------1.查看景点信息--------------------------\n");
	printf("----------2.查找任意两景点之间的最短路径----------\n");
	printf("----------3.查找某一景点到其他景点的路径----------\n");
	printf("----------4.返回----------------------------------\n");
}
void caidan2()
{
	printf("----------------菜单------------------------------\n");
	printf("----------1.查看景点信息--------------------------\n");
	printf("----------2.修改景点信息--------------------------\n");
	printf("----------3.增加景点信息--------------------------\n");
	printf("----------4.删除景点信息--------------------------\n");
	printf("----------5.增加道路------------------------------\n");
	printf("----------6.删除道路------------------------------\n");
	printf("----------7.查找某一景点到其他景点的路径----------\n");
	printf("----------8.查找任意两景点之间的最短路径----------\n");
	printf("----------9.返回----------------------------------\n");
}
void shu(int denglu, MGraph *a)
{
	if (denglu == 2)
	{
		system("cls");
		caidan1();
		printf("\n请选择：   ");
		int you;
		scanf("%d", &you);
		shu1(you, a);
	}
	else if (denglu == 1)
	{
		system("cls");
		caidan2();
		printf("\n请选择：   ");
		int guan;
		scanf("%d", &guan);
		shu2(guan, a);
	}
	else if (denglu == 3)
		return;
	else
	{
		printf("\n输入错误！！！请重新输入!\n");
		int cong;
		printf("\n输入：   ");
		scanf("%d", &cong);
		shu(cong, a);
	}
}
void shu1(int you, MGraph *a)
{
	if (you == 1)
	{
		system("cls");
		caidan1();
		int i, xuan3;
		for (i = 0; i < a->n; i++)
		{
			printf("\n");
			printf("%d: ", a->vexs[i].num);
			printf("%s   ", a->vexs[i].name);
			printf("%s", a->vexs[i].features);
			printf("\n");
		}
		printf("\n请选择：   ");
		scanf("%d", &xuan3);
		shu1(xuan3, a);
	}
	else if (you == 2)
	{
		system("cls");
		caidan1();
		int xuan1, xuan2, i, j, xuan3;
		for (i = 0; i < a->n; i++)
		{
			printf("%d、", a->vexs[i].num);
			printf("%s   ", a->vexs[i].name);
			printf("\n");
		}
		printf("\n");
		printf("请选择要查询的起点和终点，提示：输入地点前的序号！！\n");
		scanf("%d %d", &xuan1, &xuan2);
		int min, D[Max], flag[Max] = { 0 }, k, tmp;
		for (i = 0; i < a->n; i++)
		{
			D[i] = a->edges[xuan1 - 1][i];
		}
		D[xuan1 - 1] = 0;
		flag[xuan1 - 1] = 1;
		for (i = 1; i < a->n; i++)
		{
			min = MAX;
			for (j = 0; j < a->n; j++)
			{
				if (flag[j] == 0 && min > D[j])
				{
					min = D[j];
					k = j;
				}
			}
			flag[k] = 1;
			for (j = 0; j < a->n; j++)
			{
				tmp = (a->edges[k][j] == MAX ? MAX : (min + a->edges[k][j]));
				if (flag[j] == 0 && tmp < D[j])
				{
					D[j] = tmp;
				}
			}

		}
		printf(" %s到%s的最短路径为：%d\n", a->vexs[xuan1 - 1].name, a->vexs[xuan2 - 1].name, D[xuan2 - 1]);
		printf("\n请选择：   ");
		scanf("%d", &xuan3);
		shu1(xuan3, a);
	}
	else if (you == 3)
	{
		system("cls");
		caidan1();
		int xuan1, i, j, xuan3;
		for (i = 0; i < a->n; i++)
		{
			printf("%d、", a->vexs[i].num);
			printf("%s   ", a->vexs[i].name);
			printf("\n");
		}
		printf("\n");
		printf("请选择要查询的起点，提示：输入地点前的序号！！\n");
		scanf("%d", &xuan1);
		int min, D[Max], flag[Max] = { 0 }, k, tmp;
		for (i = 0; i < a->n; i++)
		{
			D[i] = a->edges[xuan1 - 1][i];
		}
		D[xuan1 - 1] = 0;
		flag[xuan1 - 1] = 1;
		for (i = 1; i < a->n; i++)
		{
			min = MAX;
			for (j = 0; j < a->n; j++)
			{
				if (flag[j] == 0 && min > D[j])
				{
					min = D[j];
					k = j;
				}
			}
			flag[k] = 1;
			for (j = 0; j < a->n; j++)
			{
				tmp = (a->edges[k][j] == MAX ? MAX : (min + a->edges[k][j]));
				if (flag[j] == 0 && tmp < D[j])
				{
					D[j] = tmp;
				}
			}
			printf(" %s到%s的最短路径为：%d\n", a->vexs[xuan1 - 1].name, a->vexs[k].name, min);
			printf("\n");
		}
		printf("\n请选择：   ");
		scanf("%d", &xuan3);
		shu1(xuan3, a);
	}
	else if (you == 4)
	{
		system("cls");
		caidan(a);
	}
	else
	{
		printf("\n输入错误！！！请重新输入!\n");
		int cong;
		printf("\n输入：   ");
		scanf("%d", &cong);
		shu1(cong, a);
	}
}
void shu2(int guan, MGraph *a)
{
	if (guan == 1)
	{
		system("cls");
		caidan2();
		int i, xuan3;
		for (i = 0; i < a->n; i++)
		{
			printf("\n");
			printf("%d: ", a->vexs[i].num);
			printf("%s   ", a->vexs[i].name);
			printf("%s", a->vexs[i].features);
			printf("\n");
		}
		printf("\n请选择：   ");
		scanf("%d", &xuan3);
		shu2(xuan3, a);
	}
	else if (guan == 2)
	{
		system("cls");
		caidan2();
		int i, gai, xuan3;
		for (i = 0; i < a->n; i++)
		{
			printf("%d、", a->vexs[i].num);
			printf("%s   ", a->vexs[i].name);
			printf("\n");
		}
		printf("请输入要修改的景点的序号！！！\n");
		scanf("%d", &gai);
		printf("请输入修改后的信息\n");
		printf("景点名称：\n");
		scanf("%s", a->vexs[gai - 1].name);
		printf("景点简介：\n");
		scanf("%s", a->vexs[gai - 1].features);
		printf("修改成功！！！\n");
		printf("\n请选择：   ");
		scanf("%d", &xuan3);
		shu2(xuan3, a);
	}
	else if (guan == 3)
	{
		system("cls");
		caidan2();
		int sh, i;
		printf("请输入要增加的景点信息\n");
		printf("请输入要增加的景点的个数:\n");
		scanf("%d", &sh);
		for (i = 0; i < sh; i++)
		{
			printf("请输入景点的名称:\n");
			scanf("%s", a->vexs[a->n + i].name);
			printf("请输入景点的简介:\n");
			scanf("%s", a->vexs[a->n + i].features);
			a->vexs[a->n + i].num = a->n + i+1;
		}
		a->n = a->n + sh;
		printf("添加成功！！！\n");
		printf("是否要添加道路？\n");
		printf("如果是  请输 1，如果不是  请输 2\n");
		pan(a);
	}
	else if (guan == 4)
	{
		system("cls");
		caidan2();
		int sh, i,shan,j,xuan3,k;
		printf("请输入要删除的景点信息\n");
		printf("请输入要删除的景点的个数:\n");
		scanf("%d", &sh);
		for (i = 0; i < sh; i++)
		{
			for (j = 0; j < a->n; j++)
			{
				printf("%d、", a->vexs[j].num);
				printf("%s   ", a->vexs[j].name);
				printf("\n");
			}
			printf("请输入景点的序号:\n");
			scanf("%d",&shan);
			for (j = shan - 1; j < a->n-1; j++)
			{
				a->vexs[j] = a->vexs[j + 1];
				a->vexs[j].num--;
			}
			for (j = shan - 1; j < a->n-1; j++)
			{
				for (k = 0; k < a->n; k++)
				{
					a->edges[j][k] = a->edges[j + 1][k];
				}
			}
			for (j = shan - 1; j < a->n - 1; j++)
			{
				for (k = 0; k < a->n-1; k++)
					a->edges[k][j] = a->edges[k][j+1];
			}
			a->n--;
		}
		printf("\n请选择：   ");
		scanf("%d", &xuan3);
		shu2(xuan3, a);
	}
	else if (guan == 5)
	{
		system("cls");
		caidan2();
		int tiao,i,j,xuan3;
		printf("请输入要增加的道路的条数");
		scanf("%d", &tiao);
		for (i = 0; i < tiao; i++)
		{
			for (j = 0; j < a->n; j++)
			{
				printf("%d、", a->vexs[j].num);
				printf("%s   ", a->vexs[j].name);
				printf("\n");
			}
			pan1(a);
		}
		printf("添加成功\n");
		printf("\n请选择：   ");
		scanf("%d", &xuan3);
		shu2(xuan3, a);
	}
	else if (guan == 6)
	{
		system("cls");
		caidan2();
		int tiao, i, j, xuan3;
		printf("请输入要删除的道路的条数");
		scanf("%d", &tiao);
		for (i = 0; i < tiao; i++)
		{
			for (j = 0; j < a->n; j++)
			{
				printf("%d、", a->vexs[j].num);
				printf("%s   ", a->vexs[j].name);
				printf("\n");
			}
			pan2(a);
		}
		printf("删除成功\n");
		printf("\n请选择：   ");
		scanf("%d", &xuan3);
		shu2(xuan3, a);
	}
	else if (guan == 9)
	{
		system("cls");
		caidan(a);
	}
	else if (guan == 7)
	{
		system("cls");
		caidan2();
		int xuan1, i, j, xuan3;
		for (i = 0; i < a->n; i++)
		{
			printf("%d、", a->vexs[i].num);
			printf("%s   ", a->vexs[i].name);
			printf("\n");
		}
		printf("\n");
		printf("请选择要查询的起点，提示：输入地点前的序号！！\n");
		scanf("%d", &xuan1);
		int min, D[Max], flag[Max] = { 0 }, k, tmp;
		for (i = 0; i < a->n; i++)
		{
			D[i] = a->edges[xuan1 - 1][i];
		}
		D[xuan1 - 1] = 0;
		flag[xuan1 - 1] = 1;
		for (i = 1; i < a->n; i++)
		{
			min = MAX;
			for (j = 0; j < a->n; j++)
			{
				if (flag[j] == 0 && min > D[j])
				{
					min = D[j];
					k = j;
				}
			}
			flag[k] = 1;
			for (j = 0; j < a->n; j++)
			{
				tmp = (a->edges[k][j] == MAX ? MAX : (min + a->edges[k][j]));
				if (flag[j] == 0 && tmp < D[j])
				{
					D[j] = tmp;
				}
			}
			printf(" %s到%s的最短路径为：%d\n", a->vexs[xuan1 - 1].name, a->vexs[k].name, min);
			printf("\n");
		}
		printf("\n请选择：   ");
		scanf("%d", &xuan3);
		shu2(xuan3, a);
	}
	else if (guan == 8)
	{
		system("cls");
		caidan2();
		int xuan1, xuan2, i, j, xuan3;
		for (i = 0; i < a->n; i++)
		{
			printf("%d、", a->vexs[i].num);
			printf("%s   ", a->vexs[i].name);
			printf("\n");
		}
		printf("\n");
		printf("请选择要查询的起点和终点，提示：输入地点前的序号！！\n");
		scanf("%d %d", &xuan1, &xuan2);
		int min, D[Max], flag[Max] = { 0 }, k, tmp;
		for (i = 0; i < a->n; i++)
		{
			D[i] = a->edges[xuan1 - 1][i];
		}
		D[xuan1 - 1] = 0;
		flag[xuan1 - 1] = 1;
		for (i = 1; i < a->n; i++)
		{
			min = MAX;
			for (j = 0; j < a->n; j++)
			{
				if (flag[j] == 0 && min > D[j])
				{
					min = D[j];
					k = j;
				}
			}
			flag[k] = 1;
			for (j = 0; j < a->n; j++)
			{
				tmp = (a->edges[k][j] == MAX ? MAX : (min + a->edges[k][j]));
				if (flag[j] == 0 && tmp < D[j])
				{
					D[j] = tmp;
				}
			}

		}
		printf(" %s到%s的最短路径为：%d\n", a->vexs[xuan1 - 1].name, a->vexs[xuan2 - 1].name, D[xuan2 - 1]);
		printf("\n请选择：   ");
		scanf("%d", &xuan3);
		shu2(xuan3, a);
	}
	else
	{
		printf("\n输入错误！！！请重新输入!\n");
		int cong;
		printf("\n输入：   ");
		scanf("%d", &cong);
		shu2(cong, a);
	}
}
void pan(MGraph *a)
{
	int tian,xuan3;
		scanf("%d", &tian);
		if (tian == 1)
			shu2(5, a);
		else if (tian == 2)
		{
			printf("\n请选择：   ");
			scanf("%d", &xuan3);
			shu2(xuan3, a);
		}
		else
		{
			printf("输入错误\n");
			printf("请重新输入\n");
			pan(a);
		}
}
void pan1(MGraph *a)
{
	int  chang, qi, zhong;
	printf("\n请输入要增加的道路的起点，终点，长度\n");
	printf("PS:起点和终点请输名称前的序号\n");
	scanf("%d", &qi);
	scanf("%d", &zhong);
	scanf("%d", &chang);
	if ((qi > 0 && qi <= a->n) && (zhong > 0 && zhong <= a->n))
	{
		a->edges[qi - 1][zhong - 1] = chang;
		a->edges[zhong - 1][qi - 1] = chang;
	}
	else
	{
		printf("输入错误，请重新输入\n");
		pan1(a);
	}
}
void pan2(MGraph *a)
{
	int qi, zhong;
	printf("\n请输入要删除的道路的起点，终点!\n");
	printf("PS:起点和终点请输名称前的序号\n");
	scanf("%d", &qi);
	scanf("%d", &zhong);
	if ((qi > 0 && qi <= a->n) && (zhong > 0 && zhong <= a->n))
	{
		a->edges[qi - 1][zhong - 1] = MAX;
		a->edges[zhong - 1][qi - 1] = MAX;
	}
	else
	{
		printf("输入错误，请重新输入\n");
		pan2(a);
	}
}
