package homework;

import java.util.Scanner;

import static java.lang.Math.*;

abstract class Figure
{
    public Point point;

    protected Figure(Point point)
    {
        this.point = point;
    }

    protected Figure()
    {
        this(new Point());
    }
}

class Triangle extends Figure
{
    private boolean flag = true;
    private Point p1, p2, p3;
    private double r1, r2, r3;

    public Triangle(Point p1, Point p2, Point p3) throws IllegalArgumentException
    {
        if (p1.getX() == p2.getX() && p2.getX() == p3.getX() || p1.getY() == p2.getY() && p2.getY() == p3.getY())
        {
            flag = false;
            throw new IllegalArgumentException();
        }
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
    }

    public double CalRound()
    {
        r1 = sqrt(pow(p1.getX() - p2.getX(), 2) + pow(p1.getY() - p2.getY(), 2));
        r2 = sqrt(pow(p1.getX() - p3.getX(), 2) + pow(p1.getY() - p3.getY(), 2));
        r3 = sqrt(pow(p3.getX() - p2.getX(), 2) + pow(p3.getY() - p2.getY(), 2));
        return r1 + r2 + r3;
    }

    public double CalArea()
    {
        CalRound();
        double s = (r1 + r2 + r3) / 2.0;
        return sqrt(s * (s - r1) * (s - r2) * (s - r3));
    }

    public void setP1(Point p1)
    {
        this.p1 = p1;
    }

    public void setP2(Point p2)
    {
        this.p2 = p2;
    }

    public void setP3(Point p3)
    {
        this.p3 = p3;
    }

    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        Point p[] = new Point[3];
        double temp;
        System.out.println("输入三点坐标：");
        for (int i = 0; i < 3; i++)
        {
            double x = scanner.nextDouble();
            double y = scanner.nextDouble();
            p[i] = new Point(x, y);
        }
        try
        {
            Triangle triangle = new Triangle(p[0], p[1], p[2]);
            System.out.println("可以构建三角形！");
            System.out.println("算面积还是周长？\n1.面积\n2.周长");
            int m = scanner.nextInt();
            if (m == 1)
            {
                System.out.println("面积是：" + triangle.CalArea());
            }
            else if (m == 2)
            {
                System.out.println("周长是：" + triangle.CalRound());
            }
            else
                System.out.println("错误输入");
        } catch (IllegalArgumentException e)
        {
            System.out.println("三点共线，不能组成三角形！！");
        } finally
        {
            System.exit(0);
        }
    }

}

class Polygon extends Figure
{
    private Point point[];

    public Polygon(final Point point[]) throws IllegalArgumentException
    {
        int lenth = point.length;
        if (lenth <= 2)
        {
            throw new IllegalArgumentException();
        }
        int sumOfX = 0;
        int sumOfY = 0;
        for (int i = 0; i < lenth; i++)
        {
            if (point[i].getX() == point[0].getX())
            {
                sumOfX++;
            }
            if (point[i].getY() == point[0].getY())
            {
                sumOfY++;
            }
        }
        if (sumOfX == lenth || sumOfY == lenth)
            throw new IllegalArgumentException();
        this.point = point;

    }

    public double CalRound()
    {
        double sum = 0;
        for (int i = 0; i < point.length; i++)
        {
            sum += sqrt(pow(point[i].getX() - point[(i + 1) % point.length].getX(), 2) + pow(point[i].getY() - point[(i + 1) % point.length].getY(), 2));

        }
        return sum;
    }

    public double CalArea()
    {
        double sum = 0;
        Triangle triangle=null;
        for (int i = 1; i < point.length - 1; i++)
        {
            if (triangle == null)
            {
                triangle = new Triangle(point[0], point[1], point[2]);
            }else
            {
                triangle.setP2(point[i]);
                triangle.setP3(point[i+1]);
            }

            sum +=triangle.CalArea();
        }
        return sum;
    }

    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        int i = 0;
        System.out.println("点的个数：");
        int num = scanner.nextInt();
        double x, y;
        Point point[] = new Point[num];
        for (i = 0; i < num; i++)
        {
            x = scanner.nextDouble();
            y = scanner.nextDouble();
            point[i] = new Point(x, y);
        }
        try
        {
            Polygon polygon = new Polygon(point);
            System.out.println("可以构建多边形。");

            System.out.println("算面积还是周长？\n1.面积\n2.周长");
            int m = scanner.nextInt();
            if (m == 1)
            {
                System.out.println("面积是：" + polygon.CalArea());
            }
            else if (m == 2)
            {
                System.out.println("周长是：" + polygon.CalRound());
            }
            else
                System.out.println("错误输入");
        } catch (IllegalArgumentException e)
        {
            System.out.println("不可以构建多边形！");
        } finally
        {
            System.exit(0);

        }
    }
}

class Point
{
    private double x;
    private double y;

    public Point(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    public Point()
    {
    }

    public double getX()
    {
        return x;
    }

    public double getY()
    {
        return y;
    }

    public void setX(double x)
    {
        this.x = x;
    }

    public void setY(double y)
    {
        this.y = y;
    }
}
