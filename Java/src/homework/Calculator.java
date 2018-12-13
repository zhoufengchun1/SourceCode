package homework;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

class Calculator
{
    private JFrame jFrame;
    private TreeMap treeMap;
    public Calculator()
    {

    }

    public void init()
    {

    }

    public void frameInit()
    {
        jFrame = new JFrame("¼òµ¥¼ÆËãÆ÷");
        jFrame.setLayout(new GridLayout(5,5));
    }

    public void mapInit()
    {
        treeMap = new TreeMap();

    }

    public void panelInit()
    {

    }
}
interface operate
{
    abstract String getOperate();
}
interface number
{
    abstract int getNumber();
}
interface calcuNum
{
    abstract double getResult();
}
