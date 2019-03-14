package homework;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class IO
{
    private JFrame jFrame;
    private JPanel jPanel;
    private JLabel jLabel;
    private JButton chooseFile;
    private JFileChooser jFileChooser;
    private File file;
    private Toolkit toolkit;
    private StringBuilder codeOfText;
    private String codeOfUser;
    private JTextField jTextField;
    private JButton okButton;

    public IO()
    {
        toolkit = Toolkit.getDefaultToolkit();

        init();
    }


    public void init()
    {
        jFrame = new JFrame("请输入密码：");
        jFrame.setLayout(new BorderLayout());
        jFrame.setBounds((toolkit.getScreenSize().width - 400) / 2, (toolkit.getScreenSize().height - 400) / 2, 300, 200);
        jFrame.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }
        });
        jFrame.setResizable(false);

        jLabel = new JLabel("请输入密码");
        jLabel.setFont(new Font("微软雅黑", 1, 20));

        chooseFile = new JButton("打开密码文件 ");
        chooseFile.setSize(50, 25);
        chooseFile.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {

                chooseFile();
            }
        });

        jTextField = new JTextField(10);
        jTextField.setFont(new Font("微软雅黑", 1, 20));


        okButton = new JButton("确定");


        okButton.addMouseListener(new MouseAdapter()
        {

            @Override
            public void mouseClicked(MouseEvent e)
            {
                try
                {
                    codeOfUser = jTextField.getText();
                    if (codeOfText.toString().equals(codeOfUser))
                    {
                        mDialog(1);
                    }
                    else
                        mDialog(0);
                } catch (NullPointerException e1)
                {
                    mDialog(-1);
                }

            }
        });

        jPanel = new JPanel();
        jPanel.add(jLabel);
        jPanel.add(chooseFile);
        jPanel.add(jTextField);
        jPanel.add(okButton);
        jFrame.add(jPanel);
        jFrame.setVisible(true);
    }

    public void chooseFile()
    {
        jFileChooser = new JFileChooser(".");
        jFileChooser.showOpenDialog(jFrame);
        jFileChooser.setVisible(true);
        jFileChooser.setBounds((toolkit.getScreenSize().width - 400) / 2, (toolkit.getScreenSize().height - 400) / 2, 300, 200);
        jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        file = jFileChooser.getSelectedFile();
        BufferedInputStream bufferedInputStream = null;
        try
        {
            bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
            Byte str[] = new Byte[bufferedInputStream.available()];
            int b;
            codeOfText = new StringBuilder();
            while ((b = bufferedInputStream.read()) != -1)
            {
                codeOfText.append((char) b);
            }
        } catch (IOException e)
        {
            System.out.println("打开文件失败！");
        } finally
        {
            try
            {
                if (bufferedInputStream != null)
                {
                    bufferedInputStream.close();
                }
            } catch (IOException e)
            {
                System.out.println("关闭流失败！");
            }
        }
    }

    public void mDialog(int isRight)//Dialog提示窗口

    {

        JDialog jDialog = new JDialog(jFrame, "输入有误！", true);

        jDialog.setBounds((toolkit.getScreenSize().width - 200) / 2, (toolkit.getScreenSize().height - 200) / 2, 300, 100);

        //dialog提示信息

        JPanel jPanel = new JPanel();

        jPanel.setLayout(new BorderLayout());//创建jPanel容器，存储两个部件，并设置为BorderLayout布局

        JLabel jLabel = new JLabel();
        if (isRight == 1)
            jLabel.setText("密码正确！");
        else if (isRight == 0)
            jLabel.setText("密码错误！");
        else
            jLabel.setText("请先打开文件！");

        jLabel.setFont(new Font("微软雅黑", 1, 20));

        jLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JButton jButton = new JButton("确定");

        jPanel.add(jLabel, BorderLayout.CENTER);

        jPanel.add(jButton, BorderLayout.SOUTH);

        jDialog.add(jPanel);

        jDialog.validate();//同步数据，和上面的原理一样

        jButton.addActionListener(new ActionListener()

        {

            @Override

            public void actionPerformed(ActionEvent e)

            {

                jDialog.setVisible(false);//点击确定设置为不可见

            }

        });

        jDialog.setResizable(false);//不可调整大小

        jDialog.setVisible(true);


    }

    public static void main(String[] args)
    {
        new IO();
    }

}
