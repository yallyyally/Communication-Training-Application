package com.example.communication_training_application;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnLip = (Button) findViewById(R.id.btn_lip);
        btnLip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LipReadingActivity.class);
                startActivity(intent);
            }
        });

        Button btnDic = (Button) findViewById(R.id.btn_dic);
        btnDic.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),DictionaryActivity.class);
                startActivity(intent);
            }
        });


    }
}