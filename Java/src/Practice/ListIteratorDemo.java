package Practice;

import java.util.ArrayList;
import java.util.ListIterator;

public class ListIteratorDemo
{
    public static void main(String[] args)
    {
        ArrayList arrayList = new ArrayList();
        arrayList.add("1");
        arrayList.add("2");
        arrayList.add("3");
        arrayList.add("4");//添加元素

        ListIterator listIterator = arrayList.listIterator();
        while (listIterator.hasNext())
        {
            listIterator.next();//先正向遍历，将指针移动到列表尾
        }
        System.out.println(arrayList);

        while (listIterator.hasPrevious())//再逆向遍历
        {
            Object object = listIterator.previous();
            if (object.equals("1"))//若元素等于“1”
            {
                listIterator.set("9");//替换为“9”
            }
        }
        System.out.println(arrayList);
    }
}
