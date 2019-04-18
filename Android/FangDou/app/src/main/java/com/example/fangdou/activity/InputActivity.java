package com.example.fangdou.activity;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.fangdou.R;
import com.example.fangdou.Tts;

public class InputActivity extends AppCompatActivity implements View.OnClickListener
{

    private EditText editText;
    private Button buildButton, retypeButton;
    private Spinner spinner;
    private String input;
    private String[] languageList;
    private String[] pinyinList;
    private TextInputLayout textInputLayout;
    private int position = 0;
    private Tts tts;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("DIY语音合成");
        toolbar.setBackgroundColor(getResources().getColor(R.color.color_default));
        ThemeActivity.setStatusBarColor(InputActivity.this, getResources().getColor(R.color.color_default));
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        initView();


    }

    public void initView()
    {
        editText = findViewById(R.id.diyEdit);
        textInputLayout = findViewById(R.id.textInputLayout);
        textInputLayout.setHint("你想听到些什么呢？");
        textInputLayout.setHintTextAppearance(R.style.TextInputAppTheme);
        buildButton = findViewById(R.id.build);
        retypeButton = findViewById(R.id.retype);
        spinner = findViewById(R.id.spinner);
        languageList = getResources().getStringArray(R.array.voicer_cloud_entries);
        pinyinList = getResources().getStringArray(R.array.voicer_cloud_values);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.item_selected, languageList);
        arrayAdapter.setDropDownViewResource(R.layout.item_drop);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                Toast.makeText(view.getContext(), "现在的发言人是：" + languageList[position], Toast.LENGTH_SHORT).show();
                InputActivity.this.position = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }

        });

        retypeButton.setOnClickListener(this);
        buildButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.retype:
                editText.setText("");
                textInputLayout.setErrorEnabled(false);
                break;
            case R.id.build:
                input = editText.getText().toString();
                if (input.matches(".*[a-zA-Z]+.*"))
                {
                    textInputLayout.setError("输入不能包含字母，请修改");
                } else
                {
                    textInputLayout.setErrorEnabled(false);
                    tts = new Tts(v, input, pinyinList[position]);
                    tts = null;
                }
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}
