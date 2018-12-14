import org.w3c.dom.css.CSSStyleRule;

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

public class EditPoint
{
    private JComboBox jComboBox;
    private String key;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private HashMap hashMap;
    private Set<String> set;
    private File file;

    public EditPoint()
    {
        try
        {
            file = new File("D://info.obj");
            objectInputStream = new ObjectInputStream(new FileInputStream(file));
            hashMap = (HashMap) objectInputStream.readObject();
            set = hashMap.keySet();
            frameInit();
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
        Iterator iterator = set.iterator();
        while (iterator.hasNext())
        {
            jComboBox.addItem((String)iterator.next());
        }
        JTextArea jTextArea = new JTextArea(15, 10);

        jComboBox.addItemListener(new ItemListener()
        {
            @Override
            public void itemStateChanged(ItemEvent e)
            {
                jTextArea.setText((String) hashMap.get((String) jComboBox.getSelectedItem()));
            }
        });
        JButton okayButton = new JButton("确定");
        JButton cancelButton = new JButton("取消");
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
                String string = jTextArea.getText();
                hashMap.put((String) jComboBox.getSelectedItem(), string);
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
