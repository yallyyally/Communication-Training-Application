package com.example.communication_training_application;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.ToxicBakery.viewpager.transforms.RotateDownTransformer;
import com.example.communication_training_application.model.UiseongUitaeData;

import java.util.ArrayList;

public class WordMeaningActivity extends AppCompatActivity {
    private ViewPager viewPager ;
    private ViewPagerAdapter pagerAdapter ;
    public static Context context;
    public LinearLayout ll;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_meaning);

        context = this;

        ll = (LinearLayout)findViewById(R.id.ll_word);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE|View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        ll.setPadding(0, statusBarHeight(this), 0, 0);

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
        viewPager.setPageTransformer(true, new RotateDownTransformer());

        Toast toast = Toast.makeText(this.getApplicationContext(),"좌우로 스와이프하여 단어를 학습해 보세요.", Toast.LENGTH_LONG);
        toast.show();


    }
    public int statusBarHeight(Context context){
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height","dimen","android");
        if (resourceId > 0) { result = getResources().getDimensionPixelSize(resourceId); } return result;
    }
}
