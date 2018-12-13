import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.net.Socket;

public class cilent
{
    private JFrame jFrame;
    private JLabel accountLabel, passwdLabel, inviteLabel;
    private JTextField accountText, passwdText, inviteText;
    private Toolkit toolkit = Toolkit.getDefaultToolkit();
    private JPanel accountJPanel, passwdJPanel, buttonjPanel, invitejPanel;
    private JButton loginButton, registButton;
    private Font font = new Font("微软雅黑", 1, 18);

    private Socket socket;
    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;

    private String account, passwd;
    private String tips;

    public cilent()
    {
        clientInit();
        init();
    }

    public void init()
    {
        jFrame = new JFrame("用户登录");
        jFrame.setLayout(new FlowLayout());
        jFrame.setBounds((toolkit.getScreenSize().width - 400) / 2, (toolkit.getScreenSize().height - 400) / 2, 270, 200);

        componentInit(accountJPanel = new JPanel(), accountLabel = new JLabel(), accountText = new JTextField(), "   帐号");
        componentInit(passwdJPanel = new JPanel(), passwdLabel = new JLabel(), passwdText = new JTextField(), "   密码");
        componentInit(invitejPanel = new JPanel(), inviteLabel = new JLabel(), inviteText = new JTextField(), "邀请码");

        loginButtonInit();
        registButtonInit();

        jFrame.setVisible(true);
        jFrame.setResizable(false);

    }

    public void componentInit(JPanel jPanel, JLabel jLabel, JTextField jTextField, String str)
    {
        jPanel.setLayout(new FlowLayout());

        jLabel.setText(str);
        jLabel.setFont(font);

        jTextField.setText("");
        jTextField.setColumns(14);

        jPanel.add(jLabel);
        jPanel.add(jTextField);

        jFrame.add(jPanel);
    }

    public void loginButtonInit()
    {
        loginButton = new JButton("登录");

        loginButton.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                sendInfo(0);
            }
        });
        jFrame.add(loginButton);
    }

    public void sendInfo(int code)
    {
        account = accountText.getText();
        passwd = passwdText.getText();
        String string;
        if (code == 0)
        {
            string = "登录";
        }
        else
            string = "注册";
        try
        {
            bufferedWriter.write(code + "\r\n");
            bufferedWriter.flush();//输出标示，告诉服务端是登录还是注册，登录为0，注册为1

            bufferedWriter.write(account + "\r\n");//必须要有结束标示，否则服务端不会停止读取
            bufferedWriter.flush();                    //刷新流
            bufferedWriter.write(passwd + "\r\n");
            bufferedWriter.flush();
            if (code == 1)          //注册的话有一个邀请码，需要多传输一次
            {
                bufferedWriter.write(inviteText.getText() + "\r\n");
                bufferedWriter.flush();
            }
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            tips = bufferedReader.readLine();
            System.out.println(tips);
        } catch (IOException e1)
        {
            mDialog(string + "结果", "交换数据失败！");

        } catch (NullPointerException e1)
        {
            mDialog(string + "结果", "服务端关闭！请先打开服务端！");
        } finally
        {
            try
            {
                bufferedReader.close();
                bufferedWriter.close();
            } catch (IOException e1)
            {
                tips = "流关闭失败！";
                mDialog(string + "结果", tips);
            }
            mDialog(string + "结果", tips);
        }
    }

    public void registButtonInit()
    {
        registButton = new JButton("注册");
        registButton.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                sendInfo(1);
            }
        });
        jFrame.add(registButton);
    }

    public void clientInit() throws NullPointerException
    {
        try
        {
            socket = new Socket("localhost", 10001);
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            if (bufferedWriter == null)
            {
                throw new NullPointerException();
            }
        } catch (IOException e)
        {

        }


    }

    public void mDialog(String title, String tips)
    {
        JDialog jDialog = new JDialog(jFrame, title, true);
        jDialog.setBounds((toolkit.getScreenSize().width - 200) / 2, (toolkit.getScreenSize().height - 200) / 2, 300, 100);
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BorderLayout());
        JLabel jLabel = new JLabel(tips);
        jLabel.setFont(font);

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
        new cilent();
    }

}
