package org.xlxw.xlxw.calcu;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{

    private EditText etInput;     //实例输入框
    private Button btOne;         //实例所有按钮
    private Button btTwo;
    private Button btThree;
    private Button btFour;
    private Button btFive;
    private Button btSix;
    private Button btSeven;
    private Button btEight;
    private Button btNine;
    private Button btZero;
    private Button btPoint;
    private Button btJia;
    private Button btJian;
    private Button btMul;
    private Button btDivide;
    private Button btEqu;
    private Button btClear;
    private Button btDel;
    boolean isEmpty;           //清空标识，当用户点击等号完成一次运算时进行清空

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        etInput = (EditText) findViewById(R.id.etInput);     //获取输入框的id
        btOne = (Button) findViewById(R.id.btOne);           //获取所有按钮的id
        btTwo = (Button) findViewById(R.id.btTwo);
        btThree = (Button) findViewById(R.id.btThree);
        btFour = (Button) findViewById(R.id.btFour);
        btFive = (Button) findViewById(R.id.btFive);
        btSix = (Button) findViewById(R.id.btSix);
        btSeven = (Button) findViewById(R.id.btSeven);
        btEight = (Button) findViewById(R.id.btEight);
        btNine = (Button) findViewById(R.id.btNine);
        btZero = (Button) findViewById(R.id.btZero);
        btPoint = (Button) findViewById(R.id.btPoint);
        btJia = (Button) findViewById(R.id.btJia);
        btJian = (Button) findViewById(R.id.btJian);
        btMul = (Button) findViewById(R.id.btMul);
        btDivide = (Button) findViewById(R.id.btDivide);
        btEqu = (Button) findViewById(R.id.btEqu);
        btClear = (Button) findViewById(R.id.btClear);
        btDel = (Button) findViewById(R.id.btDel);


        btOne.setOnClickListener(this);
        btTwo.setOnClickListener(this);
        btThree.setOnClickListener(this);
        btFour.setOnClickListener(this);
        btFive.setOnClickListener(this);
        btSix.setOnClickListener(this);
        btSeven.setOnClickListener(this);
        btEight.setOnClickListener(this);
        btNine.setOnClickListener(this);
        btZero.setOnClickListener(this);
        btPoint.setOnClickListener(this);
        btJia.setOnClickListener(this);
        btJian.setOnClickListener(this);
        btMul.setOnClickListener(this);
        btDivide.setOnClickListener(this);
        btEqu.setOnClickListener(this);
        btClear.setOnClickListener(this);
        btDel.setOnClickListener(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v)
    {
        // TODO Auto-generated method stub
        String etinput = etInput.getText().toString();    //获取输入框中的内容并转化为String类型
        switch (v.getId())
        {                              //判断点击按钮的id
            case R.id.btZero:
            case R.id.btOne:
            case R.id.btTwo:
            case R.id.btThree:
            case R.id.btFour:
            case R.id.btFive:
            case R.id.btSix:
            case R.id.btSeven:
            case R.id.btEight:
            case R.id.btNine:
            case R.id.btPoint:
                if (isEmpty)
                {
                    isEmpty = false;
                    etinput = "";
                    etInput.setText("");
                }
                etInput.setText(etinput + ((Button) v).getText());   //点击数字和小数点直接显示内容
                break;
            case R.id.btJia:
            case R.id.btJian:
            case R.id.btMul:
            case R.id.btDivide:
                if (isEmpty)
                {
                    isEmpty = false;
                    etinput = "";
                    etInput.setText("");    //当clear_flag为true时进入if语句，并可以清空，代表用户点击了等于号完成一次运算，并使clear_flag变成了true
                }
                etInput.setText(etinput + " " + ((Button) v).getText() + " ");   //点击运算符也是直接显示，但是为了后边运算要在运算符两侧加上空格
                break;
            case R.id.btDel:
                if (isEmpty)
                {
                    isEmpty = false;
                    etinput = "";
                    etInput.setText("");
                } else if (etinput != null && !etinput.equals(""))
                {
                    etInput.setText(etinput.substring(0, etinput.length() - 1));  //如果输入框内容不为空，则去掉最后一位
                }
                break;
            case R.id.btClear:
                isEmpty = false;
                etinput = "";
                etInput.setText("");    //直接设置输入框为空
                break;
            case R.id.btEqu:
                getResult();            //点击等号调用getResult()方法
                break;
        }
    }

    public void getResult()
    {
        String exp = etInput.getText().toString();  //获取输入框的内容
        if (exp == null || exp.equals(""))
        {
            return;
        }
        if (!exp.contains(" "))
        {                   //判断输入框是否包含空格，也就是没有点击运算符
            return;
        }
        if (isEmpty)
        {                           //点击等号clear_flag为true，当再点击别的数字或运
            // 算符时才会变为false，如果连续点击等号，则第二次点击无效，直接返回
            isEmpty = false;
            return;
        }
        isEmpty = true;
        double result = 0;
        String s1 = exp.substring(0, exp.indexOf(" "));                     //运算符前面的字符串
        String op = exp.substring(exp.indexOf(" ") + 1, exp.indexOf(" ") + 2);  //运算符，是根据运算符前边的空格计算的
        String s2 = exp.substring(exp.indexOf(" ") + 3);                     //运算符后边的字符串
        if (!s1.equals("") && !s2.equals(""))
        {
            double d1 = Double.parseDouble(s1);                            //将字符串转换为double类型
            double d2 = Double.parseDouble(s2);
            if (op.equals("+"))
            {
                result = d1 + d2;
            } else if (op.equals("-"))
            {
                result = d1 - d2;
            } else if (op.equals("*"))
            {
                result = d1 * d2;
            } else if (op.equals("/"))
            {
                if (d2 == 0)
                {          //判断除数为0的情况
                    result = 0;
                } else
                {
                    result = d1 / d2;
                }
            }
            if (!s1.contains(".") && !s2.contains(".") && !op.equals("/"))
            {   //如果两个数都是整形，那么结果就需要显示为整数
                int r = (int) result;                                       //将String型计算结果强制转换为整形
                etInput.setText(r + "");
            } else
            {
                etInput.setText(result + "");
            }
        } else if (!s1.equals("") && s2.equals(""))
        {         //如果用户输入一个数字就点击运算符，那么将不计算
            etInput.setText(exp);
        } else if (s1.equals("") && !s2.equals(""))
        {         //如果一上来就点击运算符并输入第二个数，那么第一个数默认为0
            double d2 = Double.parseDouble(s2);
            if (op.equals("+"))
            {
                result = 0 + d2;
            } else if (op.equals("-"))
            {
                result = 0 - d2;
            } else if (op.equals("*"))
            {
                result = 0;
            } else if (op.equals("/"))
            {
                result = 0;
            }
            if (!s1.contains(".") && !s2.contains(".") && !op.equals("/"))
            {
                int r = (int) result;
                etInput.setText(r + "");
            } else
            {
                etInput.setText(result + "");
            }
        } else
        {
            etInput.setText("");
        }
    }
}
