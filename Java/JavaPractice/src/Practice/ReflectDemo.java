package Practice;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class ReflectDemo
{

}

class Apple
{
    private int age;
    public String name;

    public void setName(String name)
    {
        this.name = name;
    }

    private Apple()
    {
        System.out.println("123");
    }

    public Apple(int age, String name)
    {
        this.name = name;
        this.age = age;
    }

    public int getAge()
    {
        return age;
    }

    public void setAge(int age)
    {
        this.age = age;
    }

    public static void main(String[] args)
    {
        try
        {
            Class cls = Class.forName("Practice.Apple");
            Constructor constructor = cls.getConstructor(int.class, String.class);
            Apple apple = (Apple) constructor.newInstance(13, "¶¾Æ»¹û");
            Method method = cls.getMethod("setAge", int.class);
            method.invoke(apple,10);
            method.setAccessible(true);
            System.out.println(apple.getAge());
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e)
        {
            e.printStackTrace();
        }

    }

}
