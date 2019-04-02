package homework;

import javax.management.modelmbean.InvalidTargetObjectTypeException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.TreeMap;

import static java.lang.Math.*;

class Calculator implements ActionListener
{
    private JFrame jFrame;
    private TreeMap treeMap;
    private JButton percentButton, ceButton, cButton, backButton, divButton,//百分号，CE，C，删除除号
            sqrtButton, mutiButton,             //开根号，乘号
            squareButton, subButton,            //平方，减号
            cubeButton, plusButton,             //立方，加号
            inverseButton, signButton, pointButton, equalsButton;//倒数，正负号，小数点，等于
    private JButton[] numButton;
    private JPanel jpanel;
    private JLabel jLabel;
    private Toolkit toolkit = Toolkit.getDefaultToolkit();
    private String string;
    private ArrayList<JButton> arrayList;
    private int[] id;
    private double num, temp1 = 0;

    private String operate;

    public Calculator()
    {
        init();
    }

    public void init()
    {
        frameInit();
        panelInit();
        textInit();
        buttonInit();
        jFrame.setVisible(true);

    }

    public void frameInit()
    {
        jFrame = new JFrame("简单计算器");
        jFrame.setLayout(new FlowLayout());
        jFrame.setBounds((toolkit.getScreenSize().width - 400) / 2, (toolkit.getScreenSize().height - 400) / 2, 410, 360);
        jFrame.setResizable(false);
    }

    public void buttonInit()
    {
        arrayList = new ArrayList<>();
        for (int i = 0; i < 25; i++)
        {
            arrayList.add(new JButton());
        }
        percentButton = new JButton("%");
        arrayList.set(0, percentButton);

        ceButton = new JButton("CE");
        arrayList.set(1, ceButton);

        cButton = new JButton("C");
        arrayList.set(2, cButton);

        backButton = new JButton("<-");
        arrayList.set(3, backButton);

        divButton = new JButton("÷");
        arrayList.set(4, divButton);

        sqrtButton = new JButton("√");
        arrayList.set(5, sqrtButton);

        numButton = new JButton[10];
        int k = 0;
        for (int i = 1; i <= 3; i++)
        {
            for (int j = 1; j <= 3; j++)
            {
                int num = 7 + (-3) * (i - 1) + (j - 1);
                numButton[k] = new JButton(num + "");
                arrayList.set(6 + 5 * (i - 1) + (j - 1), numButton[k++]);
            }
        }

        mutiButton = new JButton("×");
        arrayList.set(9, mutiButton);

        sqrtButton = new JButton("x^2");
        arrayList.set(10, sqrtButton);

        subButton = new JButton("-");
        arrayList.set(14, subButton);

        cubeButton = new JButton("x^3");
        arrayList.set(15, cubeButton);

        plusButton = new JButton("+");
        arrayList.set(19, plusButton);

        inverseButton = new JButton("1/x");
        arrayList.set(20, inverseButton);

        signButton = new JButton("±");
        arrayList.set(21, signButton);

        numButton[9] = new JButton("0");
        arrayList.set(22, numButton[9]);

        pointButton = new JButton(".");
        arrayList.set(23, pointButton);

        equalsButton = new JButton("=");
        arrayList.set(24, equalsButton);

        ListIterator iterator = arrayList.listIterator();
        id = new int[25];
        while (iterator.hasNext())
        {
            JButton jButton = (JButton) iterator.next();
            jButton.setFont(new Font("微软雅黑", 1, 20));
            if (!jButton.getText().equals(""))
            {
                jpanel.add(jButton);
                jButton.addActionListener(this);
            }
        }
        jFrame.add(jpanel);
    }

    public void textInit()
    {
        jLabel = new JLabel("0", SwingConstants.RIGHT);
        jLabel.setPreferredSize(new Dimension(300, 80));
        jLabel.setFont(new Font("仿宋", 1, 50));
        jFrame.validate();
        JSeparator jSeparator = new JSeparator();
        jSeparator.setPreferredSize(new Dimension(384, 5));
        JPanel jpanel = new JPanel();
        jpanel.setPreferredSize(new Dimension(300, 10));
        jFrame.add(jpanel);
        jFrame.add(jLabel);
        jFrame.add(jSeparator);
    }

    public void panelInit()
    {
        jpanel = new JPanel();
        jpanel.setLayout(new GridLayout(5, 5, 5, 3));
        jpanel.setSize(300, 400);

    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        String text = jLabel.getText();
        StringBuilder stringBuilder = new StringBuilder(text);


        if (e.getSource() instanceof JButton)
        {
            string = ((JButton) e.getSource()).getText();
            switch (string)
            {
                case "1":
                case "2":
                case "3":
                case "4":
                case "5":
                case "6":
                case "7":
                case "8":
                case "9":
                    if (operate != null)
                    {
                        jLabel.setText("");
                        jLabel.setText(string);
                    }
                    else if (text.equals("0"))
                    {
                        jLabel.setText("");
                        jLabel.setText(string);
                    }
                    else
                    {
                        jLabel.setText(text + string);

                    }
                    break;
                case "0":
                    if (!text.equals("0"))
                    {
                        jLabel.setText(text + "0");
                    }
                    break;
                case ".":
                    if (!text.contains("."))
                    {
                        jLabel.setText(text + ".");
                    }
            }
            try
            {
                num = Double.parseDouble(text);
                String string = ((JButton) e.getSource()).getText();
                switch (string)
                {
                    case "%":
                        jLabel.setText(num / 100 + "");
                        break;
                    case "CE":
                        jLabel.setText("0");
                        break;
                    case "C":
                        jLabel.setText("0");
                        temp1 = 0;
                        num = 0;
                        operate = null;
                        break;
                    case "<-":
                        jLabel.setText(stringBuilder.replace(stringBuilder.length() - 1, stringBuilder.length(), "").toString());
                        break;
                    case "√":
                        if (num < 0)
                        {
                            throw new Exception("Error");
                        }
                        else
                            jLabel.setText(sqrt(num) + "");
                        break;
                    case "÷":
                    case "×":
                    case "+":
                    case "-":
                        if (operate != null)
                        {
                            basicCalcu(string);
                        }
                        temp1 = num;
                        operate = string;
                        break;
                    case "x^2":
                        jLabel.setText(pow(num, 2) + "");
                        num = pow(num, 2);
                        break;
                    case "x^3":
                        jLabel.setText(pow(num, 3) + "");
                        num = pow(num, 3);
                        break;
                    case "1/x":
                        jLabel.setText(1 / num + "");
                        num = num / 1;
                        break;
                    case "±":
                        if (num > 0)
                        {
                            jLabel.setText("-" + num);
                        }
                        else if (num < 0)
                            jLabel.setText(stringBuilder.replace(0, 1, "").toString());
                        num = (-1) * num;
                        break;
                    case "=":
                        basicCalcu(operate);
                        jLabel.setText(num + "");
                        temp1 = 0;
                        break;
                }
            } catch (Exception e1)
            {

            }
            num = Double.parseDouble(text);
            String string = ((JButton) e.getSource()).getText();

        }
    }

    private void basicCalcu(String string) throws Exception
    {
        switch (string)
        {
            case "+":
                jLabel.setText(temp1 + num + "");
                num += temp1;
                System.out.println(num);
                break;
            case "-":
                jLabel.setText(temp1 - num + "");
                num = temp1 - num;
                break;
            case "×":
                jLabel.setText(temp1 * num + "");
                num *= temp1;
                break;
            case "÷":
                if (num == 0)
                {
                    jLabel.setText("Error");
                    throw new Exception("除数不能为0！");
                }
                else
                {
                    jLabel.setText(temp1 / num + "");
                    num = temp1 / num;

                }
                break;
        }
    }

    public static void main(String[] args)
    {
        new Calculator();
    }
}

