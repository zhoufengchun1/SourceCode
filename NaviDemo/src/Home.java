import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
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
    private JDialog mDialog = Client.getjDialog();

    private File pointFile = new File("D://String.txt");
    private File lengthFile = new File("D://length.txt");
    private File mapFile = new File("D://map.png");

    private ArrayList<String> Name;
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


    public void addString()
    {
        Name = new ArrayList<>();
        try
        {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(pointFile));
            int num = 0;
            String str;
            while ((str = bufferedReader.readLine()) != null)
            {
                Name.add(str);
            }
        } catch (IOException e)
        {
            //wrongDialog
        }

    }

    public void writeLength()//根据已有的景点，创建图的结构
    {

        System.out.println("开始录入：");
        int num = point.length;
        a = new int[num][num];
        int i, j;
        Scanner scanner = new Scanner(System.in);
        for (i = 0; i < num; i++)//初始化
        {
            for (j = 0; j < num; j++)
            {
                a[i][j] = -1;
            }
        }
        for (i = 0; i < num; i++)//录入信息
        {
            for (j = 0; j <= i; j++)
            {
                String string1 = point[i];
                String string2 = point[j];

                if (i == j)//对角线全为0
                {
                    a[i][j] = 0;
                }
                else
                {
                    System.out.println("请输入" + string1 + "到" + string2 + "的距离：");
                    int length = scanner.nextInt();
                    a[i][j] = length;
                }


                a[j][i] = a[i][j];//对称矩阵

            }

        }
        System.out.println("录入完毕。");
        try                     //写入文件
        {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(lengthFile));
            int count = 0;
            for (i = 0; i < num; i++)
            {
                for (j = 0; j < num; j++)
                {
                    bufferedWriter.write(a[i][j] + "");
                    if (j != num - 1)
                    {
                        bufferedWriter.write(" ");
                    }
                }
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
        } catch (IOException e)
        {

        }


    }

    public void readLength()//
    {
        int i, j;
//        BufferedReader bufferedReader = new BufferedReader(new FileReader("D://length.txt"));
        int num;
        for (i = 0; i < a.length; i++)
        {
            for (j = 0; j < a.length; j++)
            {

            }
        }
    }

    public void comboboxInit()
    {
        addString();
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
