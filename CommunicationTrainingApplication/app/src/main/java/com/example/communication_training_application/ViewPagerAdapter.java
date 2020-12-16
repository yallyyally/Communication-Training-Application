package com.example.communication_training_application;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.communication_training_application.model.UiseongUitaeData;

import java.util.ArrayList;

public class ViewPagerAdapter extends PagerAdapter {

    // LayoutInflater 서비스 사용을 위한 Context 참조 저장.
    private Context mContext = null ;
    private int cnt_data = 1;
    //private int cPosition = 0;

    private ArrayList<UiseongUitaeData> ulist = new ArrayList<>();

    public ViewPagerAdapter() {

    }

    // Context를 전달받아 mContext에 저장하는 생성자 추가.
    public ViewPagerAdapter(Context context, int cnt, ArrayList<UiseongUitaeData> list) {
        mContext = context ;
        cnt_data = cnt;
        //cPosition = position;
        ulist = list;

        Log.d("retrofit",ulist.get(0).getLink());
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = null ;
        //position = cPosition;


        if (mContext != null) {
            // LayoutInflater를 통해 "/res/layout/page.xml"을 뷰로 생성.
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.page, container, false);

            String str = ulist.get(position).getAnswer();
            TextView tvWord = (TextView) view.findViewById(R.id.tv_word) ;
            tvWord.setText(str);

            String str_type = ulist.get(position).getType()+"어"; //의성, 의태어 구분
            TextView tvType = (TextView) view.findViewById(R.id.tv_type) ;
            tvType.setText(str_type);

            String str1 = ulist.get(position).getDesc();
            TextView tvMeaning = (TextView) view.findViewById(R.id.tv_meaning);
            tvMeaning.setText(str1);

            String str2 = ulist.get(position).getExample();
            TextView tvEx = (TextView) view.findViewById(R.id.tv_ex_1);
            tvEx.setText(str2);

            String str3 = ulist.get(position).getLink();
            //Log.d("retrofit","링크"+ulist.get(position).getLink());
            ImageView ivSample = (ImageView) view.findViewById(R.id.iv_sample);

            if(ulist.get(position).getExtention().equals("jpg")){ //jpg 형식일 경우
                Glide.with(mContext)
                        //.asGif()
                        .load(str3)
                        //.override(200, 100)
                        .error(R.drawable.ic_outline_sentiment_dissatisfied_24)
                        .into(ivSample);
            }
            else{ //gif 형식일 경우
                Glide.with(mContext)
                        .asGif()
                        .load(str3)
                        //.override(200, 100)
                        .error(R.drawable.ic_outline_sentiment_dissatisfied_24)
                        .into(ivSample);
                
            }
            
        }

        // 뷰페이저에 추가.
        container.addView(view) ;

        return view ;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // 뷰페이저에서 삭제.
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        // 전체 페이지 수 리턴
        //
        return cnt_data;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view == (View)object);
    }
}
