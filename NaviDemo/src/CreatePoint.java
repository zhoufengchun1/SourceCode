import javax.net.ssl.KeyStoreBuilderParameters;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.HashMap;
import java.util.HashSet;

public class CreatePoint
{
    private File file;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private HashMap hashMap;
    private Toolkit toolkit = Toolkit.getDefaultToolkit();

    public CreatePoint()
    {
        try
        {
            file = new File("D://info.obj");
            objectInputStream = new ObjectInputStream(new FileInputStream(file));
            hashMap = (HashMap) objectInputStream.readObject();
            frameInit();
        } catch (IOException e)
        {

        } catch (ClassNotFoundException e)
        {

        }

    }

    public void frameInit()
    {
        JSeparator jSeparator = new JSeparator(SwingConstants.HORIZONTAL);
        JTextArea jTextArea = new JTextArea(15, 10);
        JTextField jTextField = new JTextField(10);

        JFrame jFrame = new JFrame();
        jFrame.setBounds((toolkit.getScreenSize().width - 829) / 2, (toolkit.getScreenSize().height - 660) / 2, 350, 450);
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
                hashMap.put(jTextField.getText(), jTextArea.getText());
                try
                {
                    objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));
                    objectOutputStream.writeObject(hashMap);
                    jFrame.setVisible(false);
                } catch (IOException e1)
                {

                }
            }
        });
        jFrame.add(cancelButton);
        jFrame.add(okayButton);
        jFrame.setVisible(true);


    }
}
