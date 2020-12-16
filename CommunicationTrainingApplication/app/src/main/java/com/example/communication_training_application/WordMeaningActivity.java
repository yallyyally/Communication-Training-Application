package com.example.communication_training_application;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.communication_training_application.model.UiseongUitaeData;

import java.util.ArrayList;

public class WordMeaningActivity extends AppCompatActivity {
    private ViewPager viewPager ;
    private ViewPagerAdapter pagerAdapter ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_meaning);

        int cnt = getIntent().getIntExtra("data-count",1);
        int position = getIntent().getIntExtra("data-position", 0);
        //long id = getIntent().getLongExtra("data-position", 0);
        //int position = (int) id;
        //Log.d("position:","position is      "+position);

        ArrayList<UiseongUitaeData> ulist = (ArrayList<UiseongUitaeData>) getIntent().getSerializableExtra("data");


        viewPager = (ViewPager) findViewById(R.id.viewPager) ;
        pagerAdapter = new ViewPagerAdapter(this, cnt, ulist) ;
        viewPager.setAdapter(pagerAdapter) ;
        viewPager.setCurrentItem(position);

        Toast toast = Toast.makeText(this.getApplicationContext(),"좌우로 스와이프하여 단어를 학습해 보세요.", Toast.LENGTH_LONG);
        toast.show();


    }
}
