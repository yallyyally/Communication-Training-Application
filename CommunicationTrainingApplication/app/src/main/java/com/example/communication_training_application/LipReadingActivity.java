
package com.example.communication_training_application;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Random;


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
    private String youtubeLink; //서버에서 받아오는 변수

    private String VIDEO_CODE; //동영상 링크: youtubeLink에서 파싱(v=다음 거)
    private final String API_KEY = "AIzaSyDInG8IWFJkwZKuJ_A1iMxUJ-N4Xm7xbRw";

    private String startPoint_str; //시작지점(문자열) - 서버에서 받아오는 변수
    private int startPoint_mili; //시작 지점(밀리초)


    private String endPoint_str; //종료지점(문자열) - 서버에서 받아오는 변수
    private int endPoint_mili; //종료 지점(밀리초)

    private int answerIndex; //답이 될 보기

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

    String answer; //정답받아오는 곳 - 서버에서 받아오는 변수

    //홈 이동 버튼
    Button Button_Home;

    Handler handler;

    ArrayList examples;
    ArrayList randomNumbers;
    int size;
    int exIndex1;
    int exIndex2;
    int exIndex3;
    int exIndex4;


    ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_lip_reading_sentence);

        answer = "울라불라불루짱"; //에러 방지
        //뷰 잇기
        LinearLayout_a = findViewById(R.id.Linearlayout_A);
        LinearLayout_b = findViewById(R.id.Linearlayout_B);
        LinearLayout_c = findViewById(R.id.Linearlayout_C);
        LinearLayout_d = findViewById(R.id.Linearlayout_D);

        TextView_a = findViewById(R.id.TextView_A);
        TextView_b = findViewById(R.id.TextView_B);
        TextView_c = findViewById(R.id.TextView_C);
        TextView_d = findViewById(R.id.TextView_D);


        Button_Home = findViewById(R.id.Button_Home);

        //예시 지문
        examples = new ArrayList<String>();
        examples.add("그렇게 할 거야");
        examples.add("제 자세와 이런 것들을");
        examples.add("요청을 할 계획입니다.");
        examples.add("장래희망이 아니에요");
        examples.add("밥은 먹었는지 안먹었는지");
        examples.add("조금만 더 버려졌었다면");
        examples.add("바로 끝까지 올라갈 수 있는지");
        examples.add("도대체 왜");
        examples.add("강사를 직접 마주하면서");
        examples.add("내가 할 수 있는 일인가");


        //Toast.makeText(getApplicationContext(),String.valueOf(examples.get(9)),Toast.LENGTH_SHORT).show();

        //세팅, 초기화
        youTubePlayerSetup();

        //음소거
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);

        //뷰 클릭 이벤트

        //홈으로 이동
        Button_Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });


    }

    //보기(지문) 클릭 핸들러
    public void clickHandler(View v) {
        AlertDialog.Builder incorrect = new AlertDialog.Builder(LipReadingActivity.this);
        incorrect.setTitle("오답입니다!");
        incorrect.setMessage("정답은 " + answer + " 입니다");
        // 버튼 추가 (Ok 버튼과 Cancle 버튼 )
        incorrect.setPositiveButton("다음 문제 풀기", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        });


        switch (v.getId()) { //정답 오답 비교 -> 오답일 경우 알림창
            case R.id.Linearlayout_A:
                if (answerIndex == 0) {
                    Toast.makeText(getApplicationContext(), "정답입니다!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "오답입니다!", Toast.LENGTH_SHORT).show();
                    incorrect.show();
                }
                break;
            case R.id.Linearlayout_B:
                if (answerIndex == 1) {
                    Toast.makeText(getApplicationContext(), "정답입니다!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "오답입니다!", Toast.LENGTH_SHORT).show();
                    incorrect.show();
                }
                break;
            case R.id.Linearlayout_C:
                if (answerIndex == 2) {
                    Toast.makeText(getApplicationContext(), "정답입니다!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "오답입니다!", Toast.LENGTH_SHORT).show();
                    incorrect.show();
                }
                break;
            case R.id.Linearlayout_D:
                if (answerIndex == 3) {
                    Toast.makeText(getApplicationContext(), "정답입니다!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "오답입니다!", Toast.LENGTH_SHORT).show();
                    incorrect.show();
                }
                break;
        }
        //지문 가져 오기 - 중복 X 네개
        Random random = new Random();
        randomNumbers = new ArrayList<Integer>();
        int num;

        size = 0;

        while (size < 4) {
            num = random.nextInt(9);
            if (size > 1) {
                if (randomNumbers.contains(num)) {
                    // Toast.makeText(getApplicationContext(),"있음",Toast.LENGTH_SHORT).show();
                } else {
                    randomNumbers.add(num);
                    size += 1;
                }
            } else {
                randomNumbers.add(num);
                size += 1;
            }

        }

        exIndex1 = (int) (randomNumbers.get(0));
        exIndex2 = (int) (randomNumbers.get(1));
        exIndex3 = (int) (randomNumbers.get(2));
        exIndex4 = (int) (randomNumbers.get(3));
        exampleA = String.valueOf(examples.get(exIndex1));
        exampleB = String.valueOf(examples.get(exIndex2));
        exampleC = String.valueOf(examples.get(exIndex3));
        exampleD = String.valueOf(examples.get(exIndex4));

//         Toast.makeText(getApplicationContext(),String.valueOf(exampleIndexes.get(0))+String.valueOf(exampleIndexes.get(1)),Toast.LENGTH_SHORT).show();

        //답 받아옴
        answer = "흐름을 방해하진 않는지";

        //답이 될 네개 중 한게
        answerIndex = random.nextInt(3);

        TextView_a.setText(exampleA);
        TextView_b.setText(exampleB);
        TextView_c.setText(exampleC);
        TextView_d.setText(exampleD);

        switch (answerIndex) {
            case 0:
                TextView_a.setText(answer);
                break;
            case 1:
                TextView_b.setText(answer);

                break;
            case 2:
                TextView_c.setText(answer);

                break;
            default:
                TextView_d.setText(answer);

                break;
        }
        //시작 시간 설정 - "00:03:00" 3분 부터 시작 가정
        startPoint_str = "00:02:45";
        startPoint_mili = strToMilli(startPoint_str);

        //종료시간 설정, 3분 9초 종료 가정 (재생시간 5초)
        endPoint_str = "00:02:50";
        endPoint_mili = strToMilli(endPoint_str);


        //동영상 설정
        youtubeLink = "https://www.youtube.com/watch?v=5LbFdY6vGsQ";
        VIDEO_CODE = linkToCode(youtubeLink);

        mYouTubePlayer.loadVideo(VIDEO_CODE, startPoint_mili); //1분 1초부터 시작


        //하단바 안보이게
        mYouTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);

        handler = new Handler();
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

    private void youTubePlayerSetup() {

        mYouTubePlayerView = (YouTubePlayerView) findViewById(R.id.YoutubeView_youtubeplayer);
        mOnInitializedListener = new YouTubePlayer.OnInitializedListener() {

            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                if (!b) {


                    mYouTubePlayer = youTubePlayer;

                    //시작 시간 설정 - 2분 0초부터 시작한다 가정
                    startPoint_str = "00:02:00";
                    startPoint_mili = strToMilli(startPoint_str);

                    //종료시간 설정, 2분 7초 종료 가정 (재생시간 5초)
                    endPoint_str = "00:02:07";
                    endPoint_mili = strToMilli(endPoint_str);


                    //동영상 링크 서버에서 받아옴, 파싱
                    youtubeLink = "https://www.youtube.com/watch?v=2jdNqxerUao";
                    VIDEO_CODE = linkToCode(youtubeLink);

                    mYouTubePlayer.loadVideo(VIDEO_CODE, startPoint_mili); //1분 30초부터 시작


                    //하단바 안보이게
                    mYouTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);

                    //일정시간만큼 재생
                    handler = new Handler();
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
                final int REQUEST_CODE = 1;

                if (youTubeInitializationResult.isUserRecoverableError()) {

                    AlertDialog myAlertDialog = (AlertDialog) youTubeInitializationResult.getErrorDialog(LipReadingActivity.this, REQUEST_CODE);
                    myAlertDialog.show();

                } else {
                    String errorMessage = String.format("There was an error initializing the YoutubePlayer (%1$s)", youTubeInitializationResult.toString());
                    Toast.makeText(getParent(), errorMessage, Toast.LENGTH_LONG).show();
                }

            }


        };


        //지문 가져 오기 - 중복 X 네개
        Random random = new Random();
        randomNumbers = new ArrayList<Integer>();
        int num;

        size = 0;

        while (size < 4) {
            num = random.nextInt(9);
            if (size > 1) {
                if (randomNumbers.contains(num)) {
                    // Toast.makeText(getApplicationContext(),"있음",Toast.LENGTH_SHORT).show();
                } else {
                    randomNumbers.add(num);
                    size += 1;
                }
            } else {
                randomNumbers.add(num);
                size += 1;
            }

        }

        exIndex1 = (int) (randomNumbers.get(0));
        exIndex2 = (int) (randomNumbers.get(1));
        exIndex3 = (int) (randomNumbers.get(2));
        exIndex4 = (int) (randomNumbers.get(3));
        exampleA = String.valueOf(examples.get(exIndex1));
        exampleB = String.valueOf(examples.get(exIndex2));
        exampleC = String.valueOf(examples.get(exIndex3));
        exampleD = String.valueOf(examples.get(exIndex4));

//         Toast.makeText(getApplicationContext(),String.valueOf(exampleIndexes.get(0))+String.valueOf(exampleIndexes.get(1)),Toast.LENGTH_SHORT).show();

        //답 받아옴
        answer = "살이 좀 빠졌습니다";

        //답이 될 네개 중 한게
        answerIndex = random.nextInt(3);

        TextView_a.setText(exampleA);
        TextView_b.setText(exampleB);
        TextView_c.setText(exampleC);
        TextView_d.setText(exampleD);

        switch (answerIndex) {
            case 0:
                TextView_a.setText(answer);
                break;
            case 1:
                TextView_b.setText(answer);

                break;
            case 2:
                TextView_c.setText(answer);

                break;
            default:
                TextView_d.setText(answer);

                break;
        }


        mYouTubePlayerView.initialize(API_KEY, mOnInitializedListener);

    }


    public int strToMilli(String time) {
        //time == "00:02:00"
        String hour = time.split(":")[0];
        String minute = time.split(":")[1];
        String second = time.split(":")[2];

        int hourInt = Integer.parseInt(hour);
        int minuteInt = Integer.parseInt(minute);
        int secondInt = Integer.parseInt(second);

        return (secondInt + minuteInt * 60 + hourInt * 3600) * 1000;

    }

    public String linkToCode(String videoLink) {
        int startIndex = videoLink.indexOf("watch?v=");
        startIndex += "watch?v=".length();

        String code = videoLink.substring(startIndex);

        System.out.println(code);

        return code;
    }


}
