package com.example.communication_training_application;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class LipReadingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lip_reading);

        Button btnRand = (Button) findViewById(R.id.btn_word);
        btnRand.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view){
                final Dialog dialog = new Dialog(LipReadingActivity.this);

                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                dialog.setContentView(R.layout.dialog_lip_reading_word);
                dialog.show();
            }
        });
    }


}
