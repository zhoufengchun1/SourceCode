package Practice;

import java.util.ArrayList;

public class ListDemo
{
    public static void main(String[] args)
    {
        ArrayList arrayList = new ArrayList();
        arrayList.add("123");
        arrayList.add("456");
        arrayList.add("789");
        arrayList.add(1,"012");//在指定位置添加元素
        System.out.println(arrayList.get(2));

        /*
        456
         */
    }
}
