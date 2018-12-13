package Practice;

import java.util.ArrayList;
import java.util.Iterator;

public class CollectionDemo
{
    public static void main(String[] args)
    {
        ArrayList arrayList = new ArrayList();
        arrayList.add("123");
        arrayList.add("456");
        arrayList.add("789 ");//先添加三个元素
        Iterator iterator = arrayList.iterator();//获取迭代器
        while (iterator.hasNext())//当可以迭代的话
        {
            System.out.println(iterator.next());//打印迭代的元素
        }
    }
    /*
    123
    456
    789
     */
}
