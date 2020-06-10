package com.example.caps;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    com.example.caps.Game game;
    ToneGenerator tg;
    private String log = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        game = new com.example.caps.Game();
        tg = new ToneGenerator(AudioManager.STREAM_ALARM,100);
        game.score = 0;
        game.questionNumber = 1;


        TextView tv_question = findViewById(R.id.question);
        TextView tv_qNumber = findViewById(R.id.qNum);
        TextView tv_score = findViewById(R.id.score);
        Button bt_done = findViewById(R.id.done);

        bt_done.setClickable(true);
        tv_question.setText(game.qa());

        String Qnumber = "Q# "+ game.questionNumber;
        tv_qNumber.setText(Qnumber);

        String scoreonscreen = "Score = "+game.score;
        tv_score.setText(scoreonscreen);

    }

    public void onClickDone(View view){
        EditText input_ans = findViewById(R.id.answer);
        Button bt_done = findViewById(R.id.done);
        TextView tv_qNumber = findViewById(R.id.qNum);
        TextView tv_score = findViewById(R.id.score);
        TextView tv_log = findViewById(R.id.log);
        TextView tv_question = findViewById(R.id.question);



        String playerans = input_ans.getText().toString();
        game.TorF(playerans);

        if (game.makesound==1){
            tg.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD,200);
        }else if (game.makesound == 0){
            tg.startTone(ToneGenerator.TONE_CDMA_ABBR_ALERT,200);
        }

        String qn = "Q# "+game.questionNumber;
        tv_qNumber.setText(qn);

        String sc = "Score = "+ game.score;

        int questioncount = game.questionNumber-1;
        if (game.anstype.equals("city")){
            log= "Q# "+ questioncount+": "+game.nowquestion + "\n"+ "Your answer: "+playerans+"\n"+"Correct answer: "+ game.city+"\n"+"\n"+log;
        }else {
            log= "Q# "+ questioncount+": "+game.nowquestion + "\n"+ "Your answer: "+playerans+"\n"+"Correct answer: "+ game.country+"\n"+"\n"+log;
        }

        tv_log.setText(log);


        tv_score.setText(sc);



        if (game.questionNumber ==10){
            game.score = 0;
            game.questionNumber =1 ;

            bt_done.setClickable(false);
            tv_qNumber.setText("Game Over");
        }else {
            game.qa();
            tv_question.setText(game.nowquestion);
        }


    }

}





