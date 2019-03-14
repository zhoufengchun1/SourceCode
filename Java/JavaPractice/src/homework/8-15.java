package homework;


import javax.swing.*;
import javax.tools.Tool;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.*;

class Info
{
    private JFrame jFrame;
    private JTextField name, passwd;
    private JButton login, register;
    private JPanel jPanel1, jpaneL2;
    private HashSet<Student> hashSet;
    private File file;
    private Toolkit toolkit = Toolkit.getDefaultToolkit();
    private Font font = new Font("微软雅黑", 1, 20);
    private JLabel jLabel1, jLabel2;

    public Info()
    {
        init();
    }

    public void init()
    {
        file = new File("D://info.obj");
        jFrame = new JFrame("登录");
        jFrame.setLayout(new FlowLayout());
        name = new JTextField(14);
        name.setVisible(true);
        name.setFont(font);
        passwd = new JTextField(14);
        passwd.setFont(font);
        register = new JButton("注册");
        login = new JButton("登录");

        jLabel1 = new JLabel("帐号");
        jLabel1.setFont(font);
        jLabel2 = new JLabel("密码");
        jLabel2.setFont(font);

        jpaneL2 = new JPanel();
        jpaneL2.setLayout(new FlowLayout());
        jpaneL2.add(login);
        jpaneL2.add(register);

        jFrame.add(jLabel1);
        jFrame.add(name);
        jFrame.add(jLabel2);
        jFrame.add(passwd);
        jFrame.add(jpaneL2);
        jFrame.setBounds((toolkit.getScreenSize().width - 400) / 2, (toolkit.getScreenSize().height - 400) / 2, 350, 170);

        jFrame.setVisible(true);
        login.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                String Name = name.getText();
                String PassWd = passwd.getText();
                Student student = new Student(Name, PassWd);
                readFile();
                if (isExists(student, false))
                {
                    mDialog("登录成功","欢迎！");
                }
                else
                {
                    mDialog("登录失败","请核对帐号密码是否匹配！");
                }
            }
        });

        register.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                String Name = name.getText();
                String PassWd = passwd.getText();
                Student student = new Student(Name, PassWd);
                readFile();
                if (!isExists(student, true))
                {
                    hashSet.add(student);//没找到就添加进Set
                    System.out.println(hashSet);
                    writeFile();
                    mDialog("注册成功",Name+" 注册成功！");
                }
                else
                {
                    mDialog("注册失败",Name+" 已存在！");
                }
            }
        });
    }

    public void readFile()
    {
        try
        {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file));
            hashSet = (HashSet) objectInputStream.readObject();
        } catch (IOException e)
        {

            if (hashSet == null)
            {
                hashSet = new HashSet<>();//程序第一次运行时添加进的hashMap是null，需要新实例化一个
                writeFile();//然后再写进去
            }
        } catch (ClassNotFoundException e)
        {
            mDialog("错误","密码文件内容读取失败!");
        }
    }

    public void writeFile()
    {
        try
        {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));
            objectOutputStream.writeObject(hashSet);
        } catch (IOException e)
        {
            e.printStackTrace();
            mDialog("错误","密码文件写入失败！");
        }
    }


    public boolean isExists(Student student, boolean isRegister)
    {
        String userName = student.getUserName();
        String passWd = student.getPasswd();
        Iterator iterator = hashSet.iterator();
        while (iterator.hasNext())
        {
            Student stu = (Student) iterator.next();
            if (stu.getUserName().equals(userName))//如果找到了相同用户名
            {
                if (isRegister)//注册的话
                {
                    return true;//已经找到了
                }
                return stu.getPasswd().equals(passWd);//登陆的话还要比较密码是否相同
            }
        }
        return false;//没找到就是假
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
        new Info();
    }
}

class Student implements Serializable
{
    private String userName;
    private String passwd;

    public Student(String userName, String passwd)
    {
        this.userName = userName;
        this.passwd = passwd;
    }

    public String getUserName()
    {
        return userName;
    }

    public String getPasswd()
    {
        return passwd;
    }

    @Override
    public int hashCode()
    {
        return userName.hashCode() + passwd.hashCode() * 3;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof Student))
        {
            return false;
        }
        Student student = (Student) obj;
        return userName.equals(student.userName);
    }
}
