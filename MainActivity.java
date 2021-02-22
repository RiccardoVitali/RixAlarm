package com.example.alarm;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.Calendar;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button level = findViewById(R.id.level);
        final TextView textOra = findViewById(R.id.textOra);
        final Button button = findViewById(R.id.Start_button);
        final Globals glob = (Globals) getApplicationContext();

        Button quiz = findViewById(R.id.quiz);
        quiz.setVisibility(View.INVISIBLE);

        Intent in = new Intent(getApplicationContext(), RingtonePlayingService.class);

        Log.d("ssssSTART_MAIN","START MAIN");

        Date currentTime = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        String[] strDate = {dateFormat.format(currentTime)};
        Log.d("ssssTIMETIMETIME",strDate[0]);

        String[] sveglia = {glob.get_sveglia()};
        if (sveglia[0].equals("no")){
            textOra.setText("");
        }else{
            textOra.setText("Alarm set for:  "+sveglia[0]);
        }


        int[] diff = {87989};
        if(!sveglia[0].equals("no")){
            diff[0] = compute_sec(strDate[0], glob.get_sveglia());
            Log.d("ssssACTUAL DIFF",String.valueOf(diff[0]));


            if(glob.get_main_ring()){

                final Handler second = new Handler();
                second.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("ssssASPETTO ", String.valueOf(diff[0]));
                        startService(in);
                        glob.set_sveglia("no");
                        glob.set_main_ring(false);
                        button.setVisibility(View.INVISIBLE);
                        level.setVisibility(View.INVISIBLE);
                        quiz.setVisibility(View.VISIBLE);
                        Animation anim = new AlphaAnimation(0.5f, 1.0f);
                        anim.setDuration(50); //You can manage the blinking time with this parameter
                        anim.setStartOffset(200);
                        anim.setRepeatMode(Animation.REVERSE);
                        anim.setRepeatCount(Animation.INFINITE);
                        quiz.startAnimation(anim);

                    }
                }, 1000*diff[0]);
            }
        }


        quiz.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("ssssquiz", "quiz");
                stopService(in);
                Intent activity2Intent = new Intent(getApplicationContext(), Page3.class);
                startActivity(activity2Intent);
                button.setVisibility(View.VISIBLE);
                level.setVisibility(View.VISIBLE);
                finish();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent activity2Intent = new Intent(getApplicationContext(), Page2.class);
                startActivity(activity2Intent);

            }
        });

        level.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent activity2Intent = new Intent(getApplicationContext(), levels.class);
                startActivity(activity2Intent);

            }
        });

    }
    public int compute_sec(String cur, String sve){
        int sec;
        int sve_h = Integer.parseInt(sve.substring(0,2));
        int cur_h = Integer.parseInt(cur.substring(0,2));
        int sve_m = Integer.parseInt(sve.substring(3,5));
        int cur_m = Integer.parseInt(cur.substring(3,5));
        Log.d("ssssCOMPUTE",String.valueOf(cur_h));
        Log.d("ssssCOMPUTE",String.valueOf(sve_h));
        int sve_sec = (sve_h*3600)+(sve_m*60);
        int cur_sec = (cur_h*3600)+(cur_m*60);
        if (sve_sec==cur_sec){
            sec = 1;
        }
        else if (sve_sec < cur_sec){
            sec = 24 * 3600 - cur_sec + sve_sec;
        }else{
            sec=sve_sec-cur_sec;
        }
        return sec;
    }
}

