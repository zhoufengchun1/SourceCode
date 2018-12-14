import javax.naming.Name;
import javax.print.DocFlavor;
import javax.sound.midi.spi.MidiFileReader;
import javax.swing.*;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;


public class Home
{
    private JFrame jFrame;
    private JPanel jPanel;
    private JLabel jLabelOfDestion, jLabelOfOrigin, title, map;
    private JComboBox jComboBoxOfDestin, jComboBoxOfOrigin;
    private Font titleFont = new Font("微软雅黑", 1, 28);
    private Font charFont = new Font("微软雅黑", 1, 20);
    private Toolkit toolkit = Toolkit.getDefaultToolkit();

    private File pointFile = new File("D://point.txt");
    private File lengthFile = new File("D://length.txt");
    private File mapFile = new File("D://map.png");
    private File infoFile = new File("D://info.txt");

    private HashSet<String> Name;
    private String[] point;
    private int[][] a;

    private boolean isAdmin = false;

    public Home(boolean isAdmin)
    {
        this.isAdmin = isAdmin;//确定用户身份
        init();
    }

    public void init()
    {
        jFrame = new JFrame();
        jFrame.setLayout(new BorderLayout());

        titleInit();//初始化标题栏
        mapInit();//初始化地图
        comboboxInit();//初始化底部选择栏

        point = new String[Name.size()];//将获取到的数据转换为数组，
        point = Name.toArray(point);//数组大小为ArrayList的长度

        jFrame.setBounds((toolkit.getScreenSize().width - 829) / 2, (toolkit.getScreenSize().height - 660) / 2, 700, 450);
        jFrame.setVisible(true);
    }

    public void titleInit()
    {
        title = new JLabel("校园导航系统", SwingConstants.CENTER);
        title.setFont(titleFont);
        jFrame.add(title, BorderLayout.NORTH);//标题文字
    }

    public void mapInit()
    {
        ImageIcon imageIcon = new ImageIcon(mapFile.getPath());
        imageIcon.setImage(imageIcon.getImage().getScaledInstance(imageIcon.getIconWidth(),
                imageIcon.getIconHeight(), Image.SCALE_DEFAULT));
        map = new JLabel();
        map.setBounds(0, 0, 690, 400);
        map.setHorizontalAlignment(0);
        map.setIcon(imageIcon);
        jPanel = new JPanel();
        jPanel.setSize(690, 400);
        jPanel.add(map);
        jFrame.add(jPanel, BorderLayout.CENTER);//地图显示
    }


    public void comboboxInit()
    {
        jComboBoxOfDestin = new JComboBox();
        jComboBoxOfOrigin = new JComboBox();
        for (String string : Name)
        {
            jComboBoxOfDestin.addItem(string);
            jComboBoxOfOrigin.addItem(string);
        }
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new FlowLayout());

        jLabelOfOrigin = new JLabel("始发地：");
        jLabelOfOrigin.setFont(charFont);
        jPanel.add(jLabelOfOrigin);
        jPanel.add(jComboBoxOfOrigin);

        jLabelOfDestion = new JLabel("目的地：");
        jLabelOfDestion.setFont(charFont);
        jPanel.add(jLabelOfDestion);
        jPanel.add(jComboBoxOfDestin);

        JButton jbutton = new JButton("确定");
        jbutton.setFont(charFont);
        jbutton.setSize(20, 30);
        jPanel.add(jbutton);
        if (isAdmin)
        {
            JButton admin = new JButton("管理员菜单");
            admin.setFont(charFont);
            jPanel.add(admin);
            admin.addMouseListener(new MouseAdapter()
            {
                @Override
                public void mouseClicked(MouseEvent e)
                {

                }
            });
        }

        jFrame.add(jPanel, BorderLayout.SOUTH);

    }


    public void adminTips()
    {
        String errorTitle = "数据错误！";
        try
        {
            checkFile(mapFile, "地图");
        } catch (IOException e)
        {
            e.printStackTrace();
            new mDialog(errorTitle, "请管理员先录入地图数据！", jFrame);
            //writeMap
        }
        try
        {
            checkFile(pointFile, "景点");

        } catch (IOException e)
        {
            e.printStackTrace();
            new mDialog(errorTitle, "请管理员先录入景点数据！", jFrame);
            //writePoint
        }
        try
        {
            checkFile(lengthFile, "距离");

        } catch (IOException e)
        {
            e.printStackTrace();
            new mDialog(errorTitle, "请管理员先录入距离数据！", jFrame);
            //writeLength
        }
    }

    public void checkFile(File file, String string) throws IOException
    {
        if (!file.exists() || file.length() == 0)
        {
            throw new IOException(string + "文件打开失败！");
        }
    }


    public static void main(String[] args)
    {
        Home home = new Home(true);
    }
}

class adminMenu
{
    private JFrame jFrame;
    private JButton createPoint, editPoint, deletePoint, createLength, editLength;
    private JButton okayButton, cancelButton;
    private Toolkit toolkit = Toolkit.getDefaultToolkit();
    private Font font = new Font("微软雅黑", 1, 20);
    private File pointFile = new File("D://point.txt");
    private File lengthFile = new File("D://length.txt");
    private JFrame childFrame;
    private JPanel childPanel;

    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;


    public adminMenu()
    {
        jFrame = new JFrame();
        jFrame.setBounds((toolkit.getScreenSize().width - 829) / 2, (toolkit.getScreenSize().height - 660) / 2, 350, 450);
        jFrame.setLayout(new FlowLayout());

        childPanel = new JPanel();
        childPanel.setLayout(new FlowLayout());
        cancelButton = new JButton("取消");
        okayButton = new JButton("确认");
        childPanel.add(cancelButton);
        childPanel.add(okayButton);

        createPoint = new JButton("1.创建景点信息");
        createPoint.setFont(font);
        createPoint.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                new CreatePoint();
            }
        });

        editPoint = new JButton("2.修改景点信息");
        editPoint.setFont(font);
        editPoint.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                new EditPoint();
            }
        });

        deletePoint = new JButton("3.删除景点信息");
        deletePoint.setFont(font);
        deletePoint.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                new DeletePoint();
            }
        });

        createLength = new JButton("4.创建道路信息");
        createLength.setFont(font);
        createLength.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                super.mouseClicked(e);
            }
        });

        editLength = new JButton("5.修改道路信息");
        editLength.setFont(font);
        editLength.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                super.mouseClicked(e);
            }
        });


    }


}
