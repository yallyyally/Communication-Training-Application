package com.example.communication_training_application;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DictionaryActivity extends AppCompatActivity {
    ArrayList<Uiseong> ulist = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);

        ImageView ivHome = (ImageView) findViewById(R.id.iv_home);
        ivHome.setOnClickListener(new ImageView.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        ArrayList<String> url = new ArrayList<>();

        url.add("https://media.giphy.com/media/l3V0BY1HsT9DJskVi/giphy.gif");
        url.add("https://media.giphy.com/media/l3V0lsGtTMSB5YNgc/giphy.gif");
        url.add("https://media.giphy.com/media/3o6ozFNGTtax2pSGmA/giphy.gif");
        url.add("https://media.giphy.com/media/3o6ozxRrKIQgKyKdoc/giphy.gif");
        url.add("https://media.giphy.com/media/TObbUke0z8Mo/giphy.gif");
        url.add("https://media.giphy.com/media/TObbUke0z8Mo/giphy.gif");
        url.add("https://media.giphy.com/media/TObbUke0z8Mo/giphy.gif");
        url.add("https://media.giphy.com/media/TObbUke0z8Mo/giphy.gif");
        url.add("https://media.giphy.com/media/TObbUke0z8Mo/giphy.gif");
        url.add("https://media.giphy.com/media/TObbUke0z8Mo/giphy.gif");

        //사전 데이터 받아와서 리스트에 추가
        for(int i = 0; i < 10; i++){
            ulist.add(new Uiseong("word "+i, "meaning "+i, "example "+i, url.get(i)));
        }
        /*
        // 리사이클러뷰에 표시할 데이터 리스트 생성.
        ArrayList<String> list = new ArrayList<>();
        for (int i=0; i<100; i++) {
            list.add(String.format("TEXT %d", i)) ;
        }
         */

        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        RecyclerView recyclerView = findViewById(R.id.rv) ;
        recyclerView.setLayoutManager(new LinearLayoutManager(this)) ;

        // 리사이클러뷰에 RecyclerviewAdapter 객체 지정.
        RecyclerviewAdapter adapter = new RecyclerviewAdapter(ulist) ;
        recyclerView.setAdapter(adapter) ;
    }
}
