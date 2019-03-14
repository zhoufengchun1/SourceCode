package Others;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;

class Complex
{
    private double imaginary, real;

    public Complex()
    {
    }

    public Complex(double real, double imaginary)
    {
        this.real = real;
        this.imaginary = imaginary;
    }

    public Complex(Complex a)
    {
        this.real = a.real;
        this.imaginary = a.imaginary;
    }

    public Complex(String i, String m)
    {
        if (i.equals("")) this.real = 0;
        if (m.equals("")) this.imaginary = 0;
        else
        {
            this.real = Double.valueOf(i);
            this.imaginary = Double.valueOf(m);
        }

    }

    public double returnReal()
    {
        return this.real;
    }

    public double returnImaginary()
    {
        return this.imaginary;
    }

    public void addition(Complex f, Complex s, Complex t)
    {
        this.real = f.real + s.real + t.real;
        this.imaginary = f.imaginary + s.imaginary + t.imaginary;
    }

    public void substraction(Complex f, Complex s, Complex t)
    {
        this.real = f.real - s.real - t.real;
        this.imaginary = f.imaginary - s.imaginary - t.imaginary;
    }

    public void additSubstract(Complex f, Complex s, Complex t)
    {
        this.real = f.real + s.real - t.real;
        this.imaginary = f.imaginary + s.imaginary - t.imaginary;
    }

    public void substractAddit(Complex f, Complex s, Complex t)
    {
        this.real = f.real - s.real + t.real;
        this.imaginary = f.imaginary - s.imaginary + t.imaginary;
    }


}

public class JComplex extends JFrame implements CaretListener, ActionListener
{
    public TextPanel text_panel[];
    public OperatorPanel operator_panel[];
    public ButtonPanel button_panel;

    public JComplex()
    {
        super("复数计算器");
        text_panel = new TextPanel[4];
        operator_panel = new OperatorPanel[2];
        button_panel = new ButtonPanel();
        this.setBounds(600, 600, 600, 500);
        this.setBackground(Color.gray);
        this.setLocationRelativeTo(null);  //窗口置于屏幕中央
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);//单击窗口关闭按钮时结束程序运行
        this.setLayout(new GridLayout(4, 8, 20, 20));
        for (int i = 0; i < 4; i++)
        {
            text_panel[i] = new TextPanel();
            text_panel[i].text_real.addCaretListener(this);
            text_panel[i].text_imaginary.addCaretListener(this);
            text_panel[i].setVisible(true);
        }
        text_panel[3].text_real.setEditable(false);
        text_panel[3].text_imaginary.setEditable(false);
        for (int i = 0; i < 2; i++)
        {
            operator_panel[i] = new OperatorPanel();
            operator_panel[i].box_operator.addActionListener(this);
            operator_panel[i].setVisible(true);
        }
        button_panel.setVisible(true);
        button_panel.button_show.addActionListener(this);

        this.validate();

        this.add(new Label(""));
        this.getContentPane().add(text_panel[0]);
        this.getContentPane().add(operator_panel[0]);
        this.getContentPane().add(text_panel[1]);
        this.getContentPane().add(operator_panel[1]);
        this.getContentPane().add(text_panel[2]);
        this.getContentPane().add(button_panel);
        this.getContentPane().add(text_panel[3]);

//        actionPerformed(null);
        this.setVisible(true);      //窗口可见
    }

    class TextPanel extends Panel
    {
        public JTextField text_real, text_imaginary;
        private JLabel label_operator1, label_operator2;

        public TextPanel()
        {

            this.setLayout(new GridLayout(1, 4, 20, 20));
            label_operator1 = new JLabel("+");
            label_operator2 = new JLabel("i");
            text_real = new JTextField(50);
            text_imaginary = new JTextField(50);
            text_real.setVisible(true);
            text_real.setVisible(true);
            this.add(text_real);
            this.add(label_operator1);
            this.add(text_imaginary);
            this.add(label_operator2);
        }

    }

    class OperatorPanel extends Panel
    {
        public JComboBox box_operator;
        private String stringOperator[] = {"+", "-"};

        public OperatorPanel()
        {
            this.setLayout(new GridLayout(2, 1, 10, 10));
            box_operator = new JComboBox(stringOperator);
            box_operator.setEditable(false);
            box_operator.setLightWeightPopupEnabled(true);
            this.add(box_operator);
        }

    }

    class ButtonPanel extends Panel
    {
        public JButton button_show;

        public ButtonPanel()
        {
            button_show = new JButton("=");
            this.add(button_show);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        // TODO Auto-generated method stub
        Complex p[] = new Complex[4];
        for (int i = 0; i < 4; i++)
        {
            p[i] = new Complex(text_panel[i].text_real.getText(), text_panel[i].text_imaginary.getText());
        }
        if (operator_panel[0].box_operator.getSelectedItem().equals("+"))
        {
            if (operator_panel[1].box_operator.getSelectedItem().equals("+")) p[3].addition(p[0], p[1], p[2]);
            else p[3].additSubstract(p[0], p[1], p[2]);
        }
        else
        {
            if (operator_panel[1].box_operator.getSelectedItem().equals("+")) p[3].substractAddit(p[0], p[1], p[2]);
            else p[3].substraction(p[0], p[1], p[2]);
        }
        if (e.getSource() == button_panel.button_show)
        {
            text_panel[3].text_real.setText(p[3].returnReal() + "");
            text_panel[3].text_imaginary.setText(p[3].returnImaginary() + "");
        }

    }

    @Override
    public void caretUpdate(CaretEvent e)
    {
        // TODO Auto-generated method stub


    }

    public static void main(String arg[])
    {
        new JComplex();
    }


}
