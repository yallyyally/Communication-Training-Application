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

public class YoutubeSampleActivity extends YouTubeBaseActivity{

    YouTubePlayerView youTubePlayerView;
    Button btn;
    YouTubePlayer.OnInitializedListener listener;

    String videoPath;

    int starPoint_min;
    int startPoint_sec;

    int startPoint_mili;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_sample);

        btn = findViewById(R.id.youtubeBtn);
        youTubePlayerView = findViewById(R.id.youtubeView);

        starPoint_min = 1;
        startPoint_sec = 1;
        startPoint_mili = ((starPoint_min * 60) + startPoint_sec) * 1000;
        videoPath="2jdNqxerUao";

        listener = new YouTubePlayer.OnInitializedListener() {

            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {


                youTubePlayer.loadVideo(videoPath, startPoint_mili); //1분 1초부터 시작
                youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);
                //https://www.youtube.com/watch?v=NmkYHmiNArc 유투브에서 v="" 이부분이 키에 해당


            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Toast.makeText(getApplicationContext(), "동영상 호출 실패", Toast.LENGTH_SHORT).show();
            }



        };
        youTubePlayerView.initialize("아무키", listener);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"다음 문제",Toast.LENGTH_SHORT).show();

                //버튼 누르면 동영상 변경
                videoPath = "NmkYHmiNArc";

                 starPoint_min = 1;
                 startPoint_sec = 30;

                 startPoint_mili = ((starPoint_min * 60) + startPoint_sec) * 1000;

                youTubePlayerView.initialize("아무키", listener);

            }
        });
    }
}