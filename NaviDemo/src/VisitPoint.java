import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class VisitPoint
{
    private JFrame jFrame;
    private JComboBox jComboBox;
    private JLabel jLabel;
    private JButton okayButton;
    private Toolkit toolkit = Toolkit.getDefaultToolkit();

    private File file = new File("D://point.obj");
    private ObjectInputStream objectInputStream;

    private TreeMap treeMap;
    private Set set;

    public VisitPoint()
    {
        try
        {
            objectInputStream = new ObjectInputStream(new FileInputStream(file));
            jFrame = new JFrame();
        } catch (IOException e)
        {
            new mDialog("错误", "无景点信息文件！", jFrame);
        }
        frameInit();
    }

    public void frameInit()
    {
        try
        {
            jFrame.setLayout(new BorderLayout());
            jFrame.setBounds((toolkit.getScreenSize().width - 350) / 2, (toolkit.getScreenSize().height - 250) / 2, 350, 250);

        } catch (Exception e)
        {
            e.printStackTrace();
        }

        jComboBox = new JComboBox();
        jComboBox.setPreferredSize(new Dimension(270,30));
        try
        {
            treeMap = (TreeMap) objectInputStream.readObject();
            set = treeMap.keySet();
        } catch (IOException e)
        {

        } catch (ClassNotFoundException e)
        {

        }
        Iterator iterator = set.iterator();
        while (iterator.hasNext())
        {
            jComboBox.addItem((String) iterator.next());
        }

        jLabel = new JLabel();
        jLabel.setPreferredSize(new Dimension(270,20));
        jLabel.setFont(new Font("宋体", 1, 20));
        jLabel.setText((String) treeMap.get(jComboBox.getSelectedItem()));

        jComboBox.addItemListener(new ItemListener()
        {
            @Override
            public void itemStateChanged(ItemEvent e)
            {
                jLabel.setText((String) treeMap.get(jComboBox.getSelectedItem()));
            }
        });

        okayButton = new JButton("确定");
        okayButton.setFont(new Font("微软雅黑", 1, 20));
        okayButton.addMouseListener(new MouseAdapter()
        {

            @Override
            public void mouseClicked(MouseEvent e)
            {
                jFrame.setVisible(false);
            }
        });
        jFrame.add(jComboBox,BorderLayout.NORTH);
        jFrame.add(jLabel,BorderLayout.CENTER);
        jFrame.add(okayButton,BorderLayout.SOUTH);
        jFrame.setResizable(false);
        jFrame.setVisible(true);
    }

    public static void main(String[] args)
    {
        new VisitPoint();
    }
}
