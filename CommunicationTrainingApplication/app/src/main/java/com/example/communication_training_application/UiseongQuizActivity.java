package com.example.communication_training_application;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class UiseongQuizActivity extends FragmentActivity {
    /**
     * The number of pages (wizard steps) to show in this demo.
     */
    private static final int NUM_PAGES = 5;

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager2 viewPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private FragmentStateAdapter pagerAdapter;

    Button Button_Complete;
    ImageView ImageView_Next;
    ImageView ImageView_Back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uiseong_quiz);

        // Instantiate a ViewPager2 and a PagerAdapter.
        viewPager = findViewById(R.id.pager);
        pagerAdapter = new ScreenSlidePagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setPageTransformer(new ZoomOutPageTransformer());

        Button_Complete = findViewById(R.id.Button_Complete);
        ImageView_Back = findViewById(R.id.ImageView_Back);
        ImageView_Next = findViewById(R.id.ImageView_Next);

        Button_Complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == Button_Complete) {
                    if (viewPager.getCurrentItem() != NUM_PAGES-1)
                        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                    else
                        Toast.makeText(getApplicationContext(), "마지막 문제 입니다", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
    }

    /*제스처 스와이핑 비활성화 */
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        return false;
    }
    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStateAdapter {
        public ScreenSlidePagerAdapter(FragmentActivity fa) {
            super(fa);
        }

        @Override
        public Fragment createFragment(int position) {
            return new ScreenSlidePageFragment();
        }

        @Override
        public int getItemCount() {
            return NUM_PAGES;
        }
    }
}