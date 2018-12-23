package homework;

import javax.swing.*;
import javax.swing.text.MutableAttributeSet;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Array;
import java.net.Socket;
import java.util.*;

import static java.lang.Math.*;

class judge
{
    private JTextField[] jTextFields;
    private JFrame jFrame;
    private JButton jButton1;
    private JButton jButton2;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JTextArea jTextArea;
    private Toolkit toolkit;
    private double aver = 0;

    public judge(int num)
    {
        toolkit = Toolkit.getDefaultToolkit();
        init(num);
    }

    public void init(int num)
    {
        jFrame = new JFrame("模拟裁判评分");
        jFrame.setLayout(new FlowLayout());
        jFrame.setBounds((toolkit.getScreenSize().width - 400) / 2, (toolkit.getScreenSize().height - 400) / 2, 462, 150);
        jPanel1 = new JPanel(new GridLayout((int) ceil(num / 5), 5));

        jTextFields = new JTextField[num];
        for (int i=0;i<num;i++)
        {
            jTextFields[i] = new JTextField(5);
            jPanel1.add(jTextFields[i]);
        }
        jTextArea = new JTextArea("0.00", 1, 4);

        jButton1 = new JButton("平均分1");
        jButton1.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                jTextArea.setText(new aver()
                {
                    @Override
                    public double calcuAver()
                    {
                        double sum = 0;
                        try
                        {
                            for (int i = 0; i < jTextFields.length; i++)
                            {
                                double temp = Double.parseDouble(jTextFields[i].getText().toString());
                                sum += temp;
                            }
                        } catch (NumberFormatException e)
                        {
                            mDialog();
                        }
                        return sum / jTextFields.length;
                    }
                }.calcuAver() + "");
            }
        });
        jButton2 = new JButton("平均分2");
        jButton2.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                jTextArea.setText(new aver()
                {
                    @Override
                    public double calcuAver()
                    {
                        double sum = 0;
                        double[] score = new double[jTextFields.length];
                        try
                        {
                            Arrays.sort(score);
                            for (int i = 0; i < score.length; i++)
                            {
                                score[i] = Double.parseDouble(jTextFields[i].getText().toString());
                                System.out.println(score[i]);
                            }
                            score[0] = 0;
                            score[score.length - 1] = 0;
                            for (double temp : score)
                            {
                                sum += temp;
                            }
                        } catch (NumberFormatException e)
                        {
                            mDialog();
                        }
                        return sum / (score.length - 2);
                    }
                }.calcuAver() + " ");
            }
        });
        jPanel2 = new JPanel();
        jPanel2.add(jButton1);
        jPanel2.add(jButton2);
        jPanel2.add(jTextArea);

        jFrame.add(jPanel1);
        jFrame.add(jPanel2);
        jFrame.setVisible(true);

    }

    public void mDialog()
    {
        JDialog jDialog = new JDialog(jFrame, "输入有误！", true);

        jDialog.setBounds((toolkit.getScreenSize().width - 200) / 2, (toolkit.getScreenSize().height - 200) / 2, 300, 100);
        //dialog提示信息
        JPanel jPanel = new JPanel();

        jPanel.setLayout(new BorderLayout());//创建jPanel容器，存储两个部件，并设置为BorderLayout布局

        JLabel jLabel = new JLabel("输入的信息有误，请检查！");

        jLabel.setFont(new Font("微软雅黑", 1, 20));

        jLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JButton jButton = new JButton("确定");

        jPanel.add(jLabel, BorderLayout.CENTER);

        jPanel.add(jButton, BorderLayout.SOUTH);

        jDialog.add(jPanel);

        jDialog.validate();

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
        new judge(10);
    }
}


interface aver
{
    abstract double calcuAver();
}

