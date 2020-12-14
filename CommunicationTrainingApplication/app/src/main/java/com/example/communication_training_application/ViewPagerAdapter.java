package com.example.communication_training_application;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class ViewPagerAdapter extends PagerAdapter {

    // LayoutInflater 서비스 사용을 위한 Context 참조 저장.
    private Context mContext = null ;
    private int cnt_data = 1;
    //private int cPosition = 0;

    public ViewPagerAdapter() {

    }

    // Context를 전달받아 mContext에 저장하는 생성자 추가.
    public ViewPagerAdapter(Context context, int position, int cnt) {
        mContext = context ;
        cnt_data = cnt;
        //cPosition = position;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = null ;
        //position = cPosition;


        if (mContext != null) {
            // LayoutInflater를 통해 "/res/layout/page.xml"을 뷰로 생성.
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.page, container, false);

            TextView textView = (TextView) view.findViewById(R.id.word) ;
            textView.setText("TEXT0" + position) ;
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
        // 현재 페이지 수는 10개로 고정.
        return cnt_data;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view == (View)object);
    }
}
