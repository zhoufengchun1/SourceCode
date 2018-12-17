import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;


public class Home
{
    private JFrame jFrame;
    private JPanel mapLabel;
    private JLabel title, map;
    private JButton admin, menu, close;
    private Font titleFont = new Font("微软雅黑", 1, 28);
    private Font charFont = new Font("微软雅黑", 1, 20);
    private Toolkit toolkit = Toolkit.getDefaultToolkit();

    private File pointFile = new File("D://point.obj");
    private File lengthFile = new File("D://length.obj");
    private File mapFile = new File("D://map.png");

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

        jFrame.setBounds((toolkit.getScreenSize().width - 700) / 2, (toolkit.getScreenSize().height - 450) / 2, 700, 450);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        admin = new JButton("管理员菜单");
        admin.setFont(charFont);
        admin.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                new adminMenu();
            }
        });

        menu = new JButton("功能菜单");
        menu.setFont(charFont);
        menu.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                new NormalMenu();
            }
        });
        buttonPanel.add(menu);
        if (isAdmin)
        {
            buttonPanel.add(admin);
            adminTips();
        }
        close = new JButton("关闭");
        close.setFont(charFont);
        close.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                System.exit(0);
            }
        });
        buttonPanel.add(close);

        jFrame.add(buttonPanel, BorderLayout.SOUTH);

        jFrame.setResizable(false);
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
        mapLabel = new JPanel();
        mapLabel.setSize(690, 400);
        mapLabel.add(map);
        jFrame.add(mapLabel, BorderLayout.CENTER);//地图显示
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

class NormalMenu
{
    private JFrame jFrame;
    private JButton visitButton, searchButton, okayButton;
    private Font font = new Font("微软雅黑", 1, 20);
    private Toolkit toolkit = Toolkit.getDefaultToolkit();

    public NormalMenu()
    {
        jFrame = new JFrame("功能菜单");
        jFrame.setBounds((toolkit.getScreenSize().width - 250) / 2, (toolkit.getScreenSize().height - 200) / 2, 250, 200);
        jFrame.setLayout(new FlowLayout());
        visitButton = new JButton("1.浏览景点信息");
        visitButton.setFont(font);
        searchButton = new JButton("2.查询最短路径");
        searchButton.setFont(font);
        okayButton = new JButton("关闭");
        okayButton.setFont(font);


        visitButton.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                new VisitPoint();
            }
        });

        searchButton.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                new SearchLength();
            }
        });

        okayButton.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                jFrame.setVisible(false);
            }
        });

        jFrame.add(visitButton);
        jFrame.add(searchButton);
        jFrame.add(okayButton);
        jFrame.setResizable(false);
        jFrame.setVisible(true);

    }

}


class adminMenu
{
    private JFrame jFrame;
    private JButton createPoint, editPoint, deletePoint, createLength, editLength;
    private JButton cancelButton;
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
        jFrame = new JFrame("管理员菜单");
        jFrame.setBounds((toolkit.getScreenSize().width - 250) / 2, (toolkit.getScreenSize().height - 310) / 2, 250, 310);
        jFrame.setLayout(new FlowLayout());

        childPanel = new JPanel();
        childPanel.setLayout(new FlowLayout());
        cancelButton = new JButton("关闭");
        childPanel.add(cancelButton);


        cancelButton.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                jFrame.setVisible(false);
            }
        });

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
                new CreateLength(jFrame);
            }
        });

        editLength = new JButton("5.修改道路信息");
        editLength.setFont(font);
        editLength.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                new CreateLength(jFrame);
            }
        });

        jFrame.add(createPoint);
        jFrame.add(editPoint);
        jFrame.add(deletePoint);
        jFrame.add(createLength);
        jFrame.add(editLength);
        jFrame.add(childPanel);
        jFrame.setVisible(true);
    }


}
