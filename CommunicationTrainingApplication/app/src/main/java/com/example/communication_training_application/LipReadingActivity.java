package com.example.communication_training_application;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;


import androidx.appcompat.app.AppCompatActivity;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class LipReadingActivity extends YouTubeBaseActivity {

    private YouTubePlayerView mYouTubePlayerView;
    private YouTubePlayer.OnInitializedListener mOnInitializedListener;
    private YouTubePlayer mYouTubePlayer;

    //https://www.youtube.com/watch?v=2jdNqxerUao
    private String youtubeLink;
    private String VIDEO_CODE; //동영상 링크: v=다음 거로 파싱해야 함.
    private String VIDEO_CODE_ORG;
    private final String API_KEY = "apikey";

    private int startPoint_mili; //시작 지점(밀리초)
    private int startPoint_min;  //시작 지점(분)
    private int startPoint_sec; //시작지점(초)

    private int endPoint_mili; //종료 지점(밀리초)
    private int endPoint_min;  //종료 지점(분)
    private int endPoint_sec; //종료지점(초)

    private int runningTime; // 지속 시간(영상 길이, 밀리초)

    //보기 4개(버튼)
    LinearLayout LinearLayout_a;
    LinearLayout LinearLayout_b;
    LinearLayout LinearLayout_c;
    LinearLayout LinearLayout_d;

    //지문 4개
    TextView TextView_a;
    String exampleA;
    TextView TextView_b;
    String exampleB;
    TextView TextView_c;
    String exampleC;
    TextView TextView_d;
    String exampleD;

    String answer;//정답

    //정답 보기 버튼
    Button Button_watchAnswer;

    //홈 이동 버튼
    Button Button_Home;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_lip_reading_sentence);

        answer = "울라불라불루짱";
        //뷰 잇기
        LinearLayout_a = findViewById(R.id.Linearlayout_A);
        LinearLayout_b = findViewById(R.id.Linearlayout_B);
        LinearLayout_c = findViewById(R.id.Linearlayout_C);
        LinearLayout_d = findViewById(R.id.Linearlayout_D);

        TextView_a = findViewById(R.id.TextView_A);
        TextView_b = findViewById(R.id.TextView_B);
        TextView_c = findViewById(R.id.TextView_C);
        TextView_d = findViewById(R.id.TextView_D);


        Button_watchAnswer = findViewById(R.id.Button_WatchAnswer);
        Button_Home = findViewById(R.id.Button_Home);

        //세팅, 초기화
        youTubePlayerSetup();


        //뷰 클릭 이벤트
        //정답 보기
        Button_watchAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //정답 보기 버튼
                AlertDialog.Builder myAlertBuilder = new AlertDialog.Builder(LipReadingActivity.this);

                // alert의 title과 Messege 세팅
                myAlertBuilder.setTitle("정답 보기");
                myAlertBuilder.setMessage(answer);

                // 버튼 추가 (Ok 버튼과 Cancle 버튼 )
                myAlertBuilder.setPositiveButton("문제로 이동", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // OK 버튼을 눌렸을 경우

                    }
                });

                // Alert를 생성해주고 보여주는 메소드(show를 선언해야 Alert가 생성됨)
                myAlertBuilder.show();

            }
        });
        //홈으로 이동
        Button_Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });


    }


    public void clickHandler(View v)
    {
        AlertDialog.Builder incorrect = new AlertDialog.Builder(LipReadingActivity.this);
        incorrect.setTitle("오답입니다!");
        incorrect.setMessage("정답은 "+answer+" 입니다");
        // 버튼 추가 (Ok 버튼과 Cancle 버튼 )
        incorrect.setPositiveButton("다음 문제 풀기", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        });



        switch(v.getId())
        { //정답 오답 비교 -> 오답일 경우 알림창
            case R.id.Linearlayout_A:
                if (answer.equals(exampleA))
                {
                    Toast.makeText(getApplicationContext(), "정답입니다!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "오답입니다!", Toast.LENGTH_SHORT).show();
                    incorrect.show();
                }
                break;
            case R.id.Linearlayout_B:
                if (answer.equals(exampleB))
                {
                    Toast.makeText(getApplicationContext(), "정답입니다!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "오답입니다!", Toast.LENGTH_SHORT).show();
                    incorrect.show();
                }
                break;
            case R.id.Linearlayout_C:
                if (answer.equals(exampleC))
                {
                    Toast.makeText(getApplicationContext(), "정답입니다!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "오답입니다!", Toast.LENGTH_SHORT).show();
                    incorrect.show();
                }
                break;
            case R.id.Linearlayout_D:
                if (answer.equals(exampleD))
                {
                    Toast.makeText(getApplicationContext(), "정답입니다!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "오답입니다!", Toast.LENGTH_SHORT).show();
                    incorrect.show();
                }
                break;
        }
        //예문 변경 - 서버에서 받아오기
        exampleA = "제발";
        exampleB = "바뀌었으면";
        exampleC = "좋겠다";
        exampleD = "하..졸려";
        answer="하..졸려";

        TextView_a.setText(exampleA);
        TextView_b.setText(exampleB);
        TextView_c.setText(exampleC);
        TextView_d.setText(exampleD);


        //시작 시간 설정
        startPoint_min = 1;
        startPoint_sec = 30;
        startPoint_mili = ((startPoint_min * 60) + startPoint_sec) * 1000;

        //종료시간 설정, 1분 40초 종료 가정 (재생시간 5초)
        endPoint_min = 1;
        endPoint_sec = 40;
        endPoint_mili = ((endPoint_min * 60) + endPoint_sec) * 1000;


        //동영상 설정
        youtubeLink = "https://www.youtube.com/watch?v=5LbFdY6vGsQ";
        VIDEO_CODE = linkToCode(youtubeLink);

        mYouTubePlayer.loadVideo(VIDEO_CODE, startPoint_mili); //1분 1초부터 시작


        //하단바 안보이게
        mYouTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);
    }
    private void youTubePlayerSetup() {

        mYouTubePlayerView = (YouTubePlayerView) findViewById(R.id.YoutubeView_youtubeplayer);
        mOnInitializedListener = new YouTubePlayer.OnInitializedListener() {

            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                if (!b) {


                    mYouTubePlayer = youTubePlayer;

                    //시작 시간 설정 - 1분 30초부터 시작한다 가정

                    startPoint_min = 1;
                    startPoint_sec = 30;
                    startPoint_mili = ((startPoint_min * 60) + startPoint_sec) * 1000;

                    //종료시간 설정, 1분 35초 종료 가정 (재생시간 5초)
                    endPoint_min = 1;
                    endPoint_sec = 35;
                    endPoint_mili = ((endPoint_min * 60) + endPoint_sec) * 1000;

                    //5초 지속 시간 가정
                    runningTime = endPoint_mili - startPoint_mili;

                    //동영상 링크 서버에서 받아옴, 파싱
                    youtubeLink = "https://www.youtube.com/watch?v=2jdNqxerUao";
                    VIDEO_CODE = linkToCode(youtubeLink);

                    mYouTubePlayer.loadVideo(VIDEO_CODE, startPoint_mili); //1분 30초부터 시작

                    //하단바 안보이게
                    mYouTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);

                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //For every 1 second, check the current time and endTime
                            if (mYouTubePlayer.getCurrentTimeMillis() <= endPoint_mili) {
                                handler.postDelayed(this, 1000);
                            } else {
                                handler.removeCallbacks(this); //no longer required
                                mYouTubePlayer.pause(); //and Pause the video
                                //and restart
                                //mYouTubePlayer.loadVideo(VIDEO_CODE, startPoint_mili); //1분 1초부터 시작


                            }
                        }
                    }, 1000);
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };


        //서버로부터 지문 받아옴
        exampleA = "아니요 뚱인데요";
        exampleB = "아니요 스폰지밥";
        exampleC = "아니요 징징이";
        exampleD = "아니요 집게사장";

        //답 받아옴
        answer = "아니요 뚱인데요";
        TextView_a.setText(exampleA);
        TextView_b.setText(exampleB);
        TextView_c.setText(exampleC);
        TextView_d.setText(exampleD);


        mYouTubePlayerView.initialize(API_KEY, mOnInitializedListener);

    }

    public String linkToCode(String videoLink) {
        int startIndex = videoLink.indexOf("watch?v=");
        startIndex += "watch?v=".length();

        String code = videoLink.substring(startIndex);

        System.out.println(code);

        return code;
    }


}
