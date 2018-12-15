import javax.print.DocFlavor;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;

public class CreateLength
{
    private JComboBox jComboBox1, jComboBox2;
    private JTextField jTextField;
    private ObjectInputStream objectInputStream1, objectInputStream2;
    private ObjectOutputStream objectOutputStream;
    private File lengthFile = new File("D://length.obj");
    private File pointFile = new File("D://point.obj");
    private int length[][];
    private Toolkit toolkit = Toolkit.getDefaultToolkit();

    private TreeMap treeMap;
    private Set set;

    public CreateLength(JFrame jFrame)
    {
        try
        {
            objectInputStream1 = new ObjectInputStream(new FileInputStream(lengthFile));
            objectOutputStream = new ObjectOutputStream(new FileOutputStream(lengthFile));
            objectInputStream2 = new ObjectInputStream(new FileInputStream(pointFile));

            length = (int[][]) objectInputStream1.readObject();
            treeMap = (TreeMap) objectInputStream2.readObject();
            if (treeMap.isEmpty())
            {
                new mDialog("错误", "景点数据为空！", jFrame);
            }
            else
            {
                set = treeMap.entrySet();
                if (set.isEmpty())
                    new mDialog("错误", "景点数据为空！", jFrame);
                else
                {
                    frameInit();
                }
            }
        } catch (IOException e)
        {

        } catch (ClassNotFoundException e)
        {

        }
    }

    public void frameInit()
    {
        JFrame jFrame = new JFrame();
        jFrame.setLayout(new FlowLayout());
        jFrame.setBounds((toolkit.getScreenSize().width - 829) / 2, (toolkit.getScreenSize().height - 660) / 2, 350, 450);

        jTextField = new JTextField(5);
        jComboBox1 = new JComboBox();
        jComboBox2 = new JComboBox();

        Iterator iterator = set.iterator();
        while (iterator.hasNext())
        {
            String string = (String) iterator.next();
            jComboBox1.addItem(string);
            jComboBox2.addItem(string);
        }

        JButton cancelButton = new JButton("取消");
        JButton okayButton = new JButton("确认");
        cancelButton.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                jFrame.setVisible(false);
            }
        });
        okayButton.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                if (length == null)
                {
                }
            }
        });

        jFrame.add(jComboBox1);
        jFrame.add(jComboBox2);
        jFrame.add(jTextField);
        jFrame.add(cancelButton);
        jFrame.add(okayButton);
    }

}
