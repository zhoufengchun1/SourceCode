package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Button button_2 = (Button) findViewById(R.id.Button_2);
        button_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecondActivity.this,FirstActivity.class);
                intent.putExtra("data_return", "123456");
                setResult(RESULT_OK,intent);
                finish();
            }

        });

    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent();
        intent.putExtra("data_return", "0987");
        setResult(RESULT_OK,intent);
        finish();
    }
}

