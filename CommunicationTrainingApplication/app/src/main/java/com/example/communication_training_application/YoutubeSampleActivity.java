package com.example.communication_training_application;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class YoutubeSampleActivity extends YouTubeBaseActivity {

    private YouTubePlayerView mYouTubePlayerView;
    private YouTubePlayer.OnInitializedListener mOnInitializedListener;
    private YouTubePlayer mYouTubePlayer;
    private Button mButton;
    private TextView text;
    private String VIDEO_CODE;
    private final String VIDEO_CODE_ORG = "-Uf2YC1YBuI";
    private final String API_KEY = "apikey";

    public int endTime ;
    private int startPoint_mili;
    private int startPoint_min;
    private int startPoint_sec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_sample);
        youTubePlayerSetup();

        mButton = findViewById(R.id.Button_Next);
        text = findViewById(R.id.TextView_Time);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //시작 시간 설정
                startPoint_min = 1;
                startPoint_sec = 1;
                startPoint_mili = ((startPoint_min * 60) + startPoint_sec) * 1000;
                endTime = startPoint_mili + 6500;

                //동영상 설정
                VIDEO_CODE = "2jdNqxerUao";

                mYouTubePlayer.loadVideo(VIDEO_CODE, startPoint_mili); //1분 1초부터 시작


                //하단바 안보이게
                mYouTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);

            }
        });


        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //For every 1 second, check the current time and endTime
                if (mYouTubePlayer.getCurrentTimeMillis() <= endTime) {
                    text.setText("Video Playing at " + mYouTubePlayer.getCurrentTimeMillis());
                    handler.postDelayed(this, 1000);
                } else {
                    handler.removeCallbacks(this); //no longer required
                    text.setText(" Reached " + endTime);
                    mYouTubePlayer.pause(); //and Pause the video
                    //and restart
                    //mYouTubePlayer.loadVideo(VIDEO_CODE, startPoint_mili); //1분 1초부터 시작


                }
            }
        }, 1000);
    }

    //초기 설정
    private void youTubePlayerSetup() {

        mYouTubePlayerView = (YouTubePlayerView) findViewById(R.id.YoutubeView_youtubeplayer);
        mOnInitializedListener = new YouTubePlayer.OnInitializedListener() {

            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                if (!b) {


                    mYouTubePlayer = youTubePlayer;

                    //시작 시간 설정
                    int starPoint_min_org = 1;
                    int startPoint_sec_org = 30;
                    int startPoint_mili_org = ((starPoint_min_org * 60) + startPoint_sec_org) * 1000;
                    endTime = startPoint_mili_org + 6500; //종료시간 설정, 6.5초 재생 가정..
                    mYouTubePlayer.loadVideo(VIDEO_CODE_ORG, startPoint_mili_org); //1분 1초부터 시작

                    //하단바 안보이게
                    mYouTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };

        mYouTubePlayerView.initialize(API_KEY, mOnInitializedListener);
    }
}
