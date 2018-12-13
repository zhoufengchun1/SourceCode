package Practice;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.Thread.MAX_PRIORITY;

class Res       //共享资源
{
    int age = 0;
    String name;
    boolean isEmpty = true;//资源是否为空
    Lock lock = new ReentrantLock();
    private Condition conditionOfConusmer=lock.newCondition();
    private Condition conditionOfProducer=lock.newCondition();
    public void In(String name, int age)//生产方法
    {
        lock.lock();
        try
        {
            while (!isEmpty)//如果资源非空
            {
                conditionOfProducer.await();//等待
            }
            this.name = name;
            this.age = age;
            isEmpty = false;//生产完毕，资源非空
            conditionOfConusmer.signal();
        } catch (Exception e)
        {
            e.printStackTrace();
        }finally
        {
            lock.unlock();
        }
    }

    public synchronized void Out()//消费方法
    {
        lock.lock();
        try
        {
            while (isEmpty)//资源为空
            {
                conditionOfConusmer.await();//等待
            }
            System.out.println("姓名：" + name + "  年龄：" + age);
            isEmpty = true;
            conditionOfProducer.signal();
        } catch (Exception e)
        {
            e.printStackTrace();
        }finally
        {
            lock.unlock();
        }
    }
}


class Producer implements Runnable
{
    private Res res;
    private int i = 0;

    public Producer(Res res)
    {
        this.res = res;
    }

    @Override
    public void run()
    {
        while (true)
        {
            if (i % 2 == 0)
                res.In("小红", 10);
            else
                res.In("老王", 70);
            i++;
        }

    }
}

class Consumer implements Runnable
{
    private Res res;

    public Consumer(Res res)
    {
        this.res = res;

    }

    @Override
    public void run()
    {
        while (true)
        {
            res.Out();
        }


    }
}

class synchronizedDemo
{
    public static void main(String[] args)
    {
        Res res = new Res();//分别创建了两个生产者两个消费者，更能突出现象
        Thread thread1 = new Thread(new Consumer(res));
        thread1.setPriority(MAX_PRIORITY);
        Thread thread2 = new Thread(new Producer(res));
        Thread thread3 = new Thread(new Consumer(res));
        Thread thread4 = new Thread(new Producer(res));
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();



    }
}


