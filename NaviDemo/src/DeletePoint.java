import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class DeletePoint
{
    private JComboBox jComboBox;
    private HashMap hashMap;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private Set set;
    private File file;

    public DeletePoint()
    {
        try
        {
            file = new File("D://info.obj");
            objectInputStream = new ObjectInputStream(new FileInputStream(file));
            hashMap = (HashMap) objectInputStream.readObject();

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

        jComboBox = new JComboBox();
        jFrame.add(jComboBox);
        set = hashMap.keySet();
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
                hashMap.remove((String) jComboBox.getSelectedItem());
                try
                {
                    objectOutputStream.writeObject(hashMap);
                    jFrame.setVisible(false);
                } catch (IOException e1)

                {

                }
            }
        });

    }
}
