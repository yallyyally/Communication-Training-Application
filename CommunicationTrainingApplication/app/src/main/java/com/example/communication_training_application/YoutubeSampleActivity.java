package com.example.communication_training_application;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

    private String VIDEO_CODE;
    private final String VIDEO_CODE_ORG = "-Uf2YC1YBuI";
    private final String API_KEY = "apikey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_sample);
        youTubePlayerSetup();
        mButton = findViewById(R.id.Button_Next);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //시작 시간 설정
                int starPoint_min = 1;
                int startPoint_sec = 1;
                int startPoint_mili = ((starPoint_min * 60) + startPoint_sec) * 1000;

                //동영상 설정
                VIDEO_CODE= "2jdNqxerUao";

                mYouTubePlayer.loadVideo(VIDEO_CODE, startPoint_mili); //1분 1초부터 시작


                //하단바 안보이게
                mYouTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);

            }
        });
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
