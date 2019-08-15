package com.kangyh.student.entity;

/**
 * @Author: KangYh
 * @Date: 2019/7/24 21:11
 */
public class Student
{
    private int Sno;
    private String sname;
    private int sage;
    private String saddress;

    public Student()
    {
    }

    public Student(int sno, String sname, int sage, String saddress)
    {
        Sno = sno;
        this.sname = sname;
        this.sage = sage;
        this.saddress = saddress;
    }

    public int getSno()
    {
        return Sno;
    }

    public String getSname()
    {
        return sname;
    }

    public int getSage()
    {
        return sage;
    }

    public String getSaddress()
    {
        return saddress;
    }

    public void setSno(int sno)
    {
        Sno = sno;
    }

    public void setSname(String sname)
    {
        this.sname = sname;
    }

    public void setSage(int sage)
    {
        this.sage = sage;
    }

    public void setSaddress(String saddress)
    {
        this.saddress = saddress;
    }

    @Override
    public String toString()
    {
        return "Student{" +
                "Sno=" + Sno +
                ", sname='" + sname + '\'' +
                ", sage=" + sage +
                ", saddress='" + saddress + '\'' +
                '}';
    }
}
