import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.TreeMap;
import java.util.Iterator;
import java.util.Set;

public class DeletePoint
{
    private JComboBox jComboBox;
    private TreeMap treeMap;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private Set set;
    private File file;
    private Toolkit toolkit = Toolkit.getDefaultToolkit();

    public DeletePoint()
    {
        try
        {
            file = new File("D://info.obj");
            objectInputStream = new ObjectInputStream(new FileInputStream(file));
            treeMap = (TreeMap) objectInputStream.readObject();

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


        jComboBox = new JComboBox();
        jFrame.add(jComboBox);
        set = treeMap.keySet();
        Iterator iterator = set.iterator();
        while (iterator.hasNext())
        {
            jComboBox.addItem((String) iterator.next());
        }
        JButton cancelButton = new JButton("取消");
        JButton okayButton = new JButton("确认");
        jFrame.add(cancelButton);
        jFrame.add(okayButton);
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
                treeMap.remove((String) jComboBox.getSelectedItem());
                try
                {
                    objectOutputStream.writeObject(treeMap);
                    jFrame.setVisible(false);
                } catch (IOException e1)

                {

                }
            }
        });

    }
}
