package Practice;

import java.io.*;

public class IODemo
{
    public static void main(String[] args)throws Exception
    {
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("D:/123.obj"));
        Student student = (Student) objectInputStream.readObject();
        System.out.println(student);
        objectInputStream.close();
    }
}

class Student implements Serializable
{
    transient private int age;
    private String name;

    public Student(int age, String name)
    {
        this.age = age;
        this.name = name;
    }
}


