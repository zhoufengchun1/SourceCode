package homework;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.security.Key;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;
import java.util.prefs.Preferences;

class ComplexCal

{

    private Toolkit toolkit = Toolkit.getDefaultToolkit();

    private JLabel labelForTitle;

    private JDialog dialogOfWrongInput;

    private JLabel wrongInfo;

    private JFrame f;

    private int count = 2;

    private Button createItem, deleteItem, equals;

    private JComplexList jComplexList;

    private JLabel result;


    public ComplexCal()

    {

        init();

    }


    private void init()

    {

        FrameInit();//主窗口初始化

        labelInit();//第一行提示信息初始化

        JComplexInit();//复数部件初始化

        ResultInit();//结果部件初始化

        f.validate();

        /* 添加完组件要用validate()方法刷新数据，

         否则就只能手动拖一下窗口才能正常显示窗口*/

    }


    private void JComplexInit()

    {

        jComplexList = new JComplexList();


    }


    public void FrameInit()

    {

        f = new JFrame("复数表达式计算");

        f.setLayout(new FlowLayout());

        f.setBounds((toolkit.getScreenSize().width - 400) / 2, (toolkit.getScreenSize().height - 400) / 2, 462, 389);

        //屏幕长度减去Frame的长或宽/2就是屏幕中心

        f.setVisible(true);

        f.addWindowListener(new WindowAdapter()

        {

            @Override

            public void windowClosing(WindowEvent e)

            {

                System.exit(0);

            }

        });

        //设置右上角关闭按钮的点击事件

        f.setResizable(false);

        //不可改变大小


    }


    public void ResultInit()

    {


        equals.addMouseListener(new MouseAdapter()

        {

            @Override

            public void mouseClicked(MouseEvent e)

            {

                String operator = "+";

                double sumOfComplex = 0;

                double sumOfReal = 0;

                Boolean flag = true;

                for (Object aJComplexList : jComplexList)//迭代器，迭代遍历ArrayList的元素

                {

                    JComplex jComplex = (JComplex) (aJComplexList);


                    try

                    {

                        double m = Integer.parseInt(jComplex.getTextOfComplex());

                        double n = Integer.parseInt(jComplex.getTextOfReal());

                        if ("+".equals(operator))

                        {

                            sumOfComplex += m;

                            sumOfReal += n;

                        }

                        else if ("-".equals(operator))

                        {

                            sumOfComplex -= m;

                            sumOfReal -= n;

                        }


                    } catch (NumberFormatException e1)

                    {

                        wrongDialog();

                        flag = false;

                        break;

                    }

                    operator = jComplex.getOperator();

                }

                if (flag)//有异常出现就不进行运算，也不弹出结果Dialog

                {

                    result = new JLabel();

                    result.setText(sumOfComplex + "" + "+" + sumOfReal + "" + "i");

                    result.setFont(new Font("微软雅黑", 1, 20));

                    resultDialog();

                }


            }

        });

    }


    public void labelInit()

    {

        JPanel jPanel = new JPanel();

        jPanel.setLayout(new FlowLayout());

        labelForTitle = new JLabel("请输入相关信息，得到相应结果！", SwingConstants.CENTER);

        labelForTitle.setFont(new Font("微软雅黑", 1, 20));

        f.add(labelForTitle);


        createItem = new Button("新建项");

        jPanel.add(createItem);

        deleteItem = new Button("删除项");

        jPanel.add(deleteItem);

        f.add(jPanel);

        createItem.addMouseListener(new MouseAdapter()

        {

            @Override

            public void mouseClicked(MouseEvent e)

            {

                jComplexList.addItem();

            }

        });

        deleteItem.addMouseListener(new MouseAdapter()

        {

            @Override

            public void mouseClicked(MouseEvent e)

            {

                jComplexList.deleteItem();

            }

        });

        equals = new Button("查看结果");

        jPanel.add(equals);

        f.add(jPanel);

    }


    public void wrongDialog()//Dialog提示窗口

    {

        dialogOfWrongInput = new JDialog(f, "输入有误！", true);

        dialogOfWrongInput.setBounds((toolkit.getScreenSize().width - 200) / 2, (toolkit.getScreenSize().height - 200) / 2, 300, 100);

        //dialog提示信息

        JPanel jPanel = new JPanel();

        jPanel.setLayout(new BorderLayout());//创建jPanel容器，存储两个部件，并设置为BorderLayout布局

        JLabel jLabel = new JLabel("输入的信息有误，请检查！");

        jLabel.setFont(new Font("微软雅黑", 1, 20));

        jLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JButton jButton = new JButton("确定");

        jPanel.add(jLabel, BorderLayout.CENTER);

        jPanel.add(jButton, BorderLayout.SOUTH);

        dialogOfWrongInput.add(jPanel);

        dialogOfWrongInput.validate();//同步数据，和上面的原理一样

        jButton.addActionListener(new ActionListener()

        {

            @Override

            public void actionPerformed(ActionEvent e)

            {

                dialogOfWrongInput.setVisible(false);//点击确定设置为不可见

            }

        });

        dialogOfWrongInput.setResizable(false);//不可调整大小

        dialogOfWrongInput.setVisible(true);


    }


    public void resultDialog()

    {

        JDialog jDialog = new JDialog(f, "结果", true);

        jDialog.setBounds((toolkit.getScreenSize().width - 200) / 2, (toolkit.getScreenSize().height - 200) / 2, 300, 100);


        JPanel jPanel = new JPanel();

        JButton jButton = new JButton("确定");

        jPanel.setLayout(new BorderLayout());

        jPanel.add(result);

        result.setHorizontalAlignment(SwingConstants.CENTER);

        jPanel.add(jButton, BorderLayout.SOUTH);

        jDialog.add(jPanel);

        jDialog.setResizable(false);

        jButton.addActionListener(new ActionListener()

        {

            @Override

            public void actionPerformed(ActionEvent e)

            {

                jDialog.setVisible(false);

            }

        });

        jDialog.validate();

        jDialog.setVisible(true);


    }


    public void deleteDialog()

    {

        dialogOfWrongInput = new JDialog(f, "删除错误", true);

        dialogOfWrongInput.setBounds((toolkit.getScreenSize().width - 200) / 2, (toolkit.getScreenSize().height - 200) / 2, 300, 100);

        //dialog提示信息

        JPanel jPanel = new JPanel();

        jPanel.setLayout(new BorderLayout());//创建jPanel容器，存储两个部件，并设置为BorderLayout布局

        JLabel jLabel = new JLabel("最少要保留两个运算项");

        jLabel.setFont(new Font("微软雅黑", 1, 20));

        jLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JButton jButton = new JButton("确定");

        jPanel.add(jLabel, BorderLayout.CENTER);

        jPanel.add(jButton, BorderLayout.SOUTH);

        dialogOfWrongInput.add(jPanel);

        dialogOfWrongInput.validate();//同步数据，和上面的原理一样

        jButton.addActionListener(new ActionListener()

        {

            @Override

            public void actionPerformed(ActionEvent e)

            {

                dialogOfWrongInput.setVisible(false);//点击确定设置为不可见

            }

        });

        dialogOfWrongInput.setResizable(false);//不可调整大小

        dialogOfWrongInput.setVisible(true);

    }


    class JComplexList extends ArrayList//List动态数组，内部类的形式可以方便共享数据

    {

        public int sum = 2;


        public JComplexList()//初始化一定有两个运算表达式

        {

            JComplex J1 = new JComplex(f);

            J1.setJComboBoxVisible(true);

            JComplex J2 = new JComplex(f);

            J2.setJComboBoxVisible(false);

            super.add(J1);

            super.add(J2);

        }


        public void addItem()

        {

            sum = super.size();

            JComplex temp = (JComplex) (super.get(sum - 1));

            temp.setJComboBoxVisible(true);

            JComplex jComplex = new JComplex(f);

            super.add(jComplex);

            jComplex.setJComboBoxVisible(false);//最后一个式子不显示运算符

        }


        public void deleteItem()

        {

            if (jComplexList.size() >= 3)

            {

                sum = super.size();

                JComplex temp = (JComplex) (super.get(sum - 1));

                temp.setVisible(false);//现将最后一个元素隐藏

                jComplexList.remove(temp);//删除

                sum = super.size();//重新获取大小

                temp = (JComplex) (super.get(sum - 1));

                temp.setJComboBoxVisible(false);//设置最后一个元素的运算符不可见

            }

            else

            {

                deleteDialog();//弹个错误框

            }

        }

    }


    public static void main(String[] args)

    {

        new ComplexCal();

    }

}

class JComplex extends JComponent //复数组件，继承JComponent

{

    private TextField textFieldOfReal;//实部输入框

    private TextField textFieldOfComplex;//虚部部输入框

    private JComboBox<String> jComboBox;

    private String textOfReal;//实部数据

    private String textOfComplex;//虚部数据

    private String operate = "+";//默认为"+"

    private JPanel jPanel;

    private JLabel jLabelPlus, jLabeli;//加号和"i"字符的JLabel

    private String[] operator = new String[]{"+", "-"};


    public TextField getTextFieldOfReal()

    {

        return textFieldOfReal;

    }


    public TextField getTextFieldOfComplex()

    {

        return textFieldOfComplex;

    }


    public JComplex(Frame f)

    {

        createModule();

        addModule(f);

    }


    public void createModule()//创建组件

    {


        jComboBox = new JComboBox<String>(operator);

        jComboBox.setSelectedIndex(0);

        jComboBox.addItemListener(e ->
        {

            if (e.getStateChange() == ItemEvent.SELECTED)

            {

                JComplex.this.operate = jComboBox.getSelectedItem().toString();

            }

        });


        textFieldOfComplex = new TextField(5);

        textFieldOfComplex.setFont(new Font("微软雅黑", 1, 20));

        inputLimit(textFieldOfComplex);


        textFieldOfReal = new TextField(5);

        textFieldOfReal.setFont(new Font("微软雅黑", 1, 20));

        inputLimit(textFieldOfReal);


        jLabelPlus = new JLabel("+");

        jLabelPlus.setFont(new Font("微软雅黑", 1, 20));


        jLabeli = new JLabel("i   ");

        jLabeli.setFont(new Font("微软雅黑", 1, 20));


        //

    }


    private void inputLimit(TextField textFieldOfReal)

    {

        textFieldOfReal.addKeyListener(new KeyAdapter()//键盘监听

        {

            @Override

            public void keyPressed(KeyEvent e)

            {

                int code = e.getKeyCode();

                String s = textFieldOfReal.getText();

                if (s.length() >= 6)//长度限制

                {

                    e.consume();

                }

                if (!(code >= KeyEvent.VK_0 && code <= KeyEvent.VK_9) && !(code == KeyEvent.VK_PERIOD) && !(code == KeyEvent.VK_DELETE) && !(code == KeyEvent.VK_BACK_SPACE))

                    e.consume();//只能输入数字或者小数点

            }

        });

    }


    public void addModule(Frame f)//添加组件

    {

        jPanel = new JPanel(new GridLayout(1, 0, 5, 5));


        jPanel.add(textFieldOfComplex);

        jPanel.add(jLabelPlus);

        jPanel.add(textFieldOfReal);

        jPanel.add(jLabeli);

        jPanel.add(jComboBox);

        f.add(jPanel);

    }


    public void setJComboBoxVisible(boolean isLast)//设置运算符可见性

    {

        jComboBox.setVisible(isLast);

    }


    public String getTextOfReal()

    {

        return textFieldOfReal.getText();

    }//获取实部字符串


    public String getTextOfComplex()

    {

        return textFieldOfComplex.getText();

    }//获取虚部字符串


    public String getOperator()

    {

        return operate;

    }//获取JCombobox的运算符


    @Override

    public void setVisible(boolean aFlag)

    {

        jPanel.setVisible(aFlag);

    }

}
