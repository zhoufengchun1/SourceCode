import javax.net.ssl.KeyStoreBuilderParameters;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.TreeMap;
import java.util.HashSet;

public class CreatePoint
{
    private File file;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private TreeMap treeMap;
    private Toolkit toolkit = Toolkit.getDefaultToolkit();

    public CreatePoint()
    {
        try
        {
            file = new File("D://info.obj");
            objectInputStream = new ObjectInputStream(new FileInputStream(file));
            treeMap = (TreeMap) objectInputStream.readObject();
            frameInit();
        } catch (IOException e)
        {
            treeMap = new TreeMap();
            frameInit();

        } catch (ClassNotFoundException e)
        {
            frameInit();
        }

    }

    public void frameInit()
    {
        JSeparator jSeparator = new JSeparator(SwingConstants.HORIZONTAL);
        JTextArea jTextArea = new JTextArea(15, 30);
        JTextField jTextField = new JTextField(20);

        JFrame jFrame = new JFrame();
        jFrame.setBounds((toolkit.getScreenSize().width - 350) / 2, (toolkit.getScreenSize().height - 450) / 2, 350, 450);
        jFrame.setLayout(new FlowLayout());

        jFrame.add(jTextField);
        jFrame.add(jSeparator);
        jFrame.add(jTextArea);

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
                treeMap.put(jTextField.getText(), jTextArea.getText());
                try
                {
                    objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));
                    objectOutputStream.writeObject(treeMap);
                    new mDialog("成功", "数据正常保存", jFrame);
                    jFrame.setVisible(false);
                } catch (IOException e1)
                {
                    new mDialog("失败", "数据异常！", jFrame);
                }
            }
        });
        jFrame.add(cancelButton);
        jFrame.add(okayButton);
        jFrame.setVisible(true);


    }


}
