// ALARM CLOCK PAGE

package com.example.alarm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;


public class Page2 extends AppCompatActivity {
    TimePicker alarmTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page2);

        final Globals global_sveglia = (Globals) getApplicationContext();

        alarmTime = findViewById(R.id.simpleTimePicker);
        final Button set_it = findViewById(R.id.Set_button);

        set_it.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                global_sveglia.set_sveglia(AlarmTime2());
                global_sveglia.set_main_ring(true);

                Intent activity2Intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(activity2Intent);
                finish();

            }
        });

    }
    public String AlarmTime2(){
        int alarmHours = alarmTime.getCurrentHour();
        Log.d("ssssalarm_hours",String.valueOf(alarmHours));
        int alarmMinutes = alarmTime.getCurrentMinute();
        Log.d("ssssalarm_minutes",String.valueOf(alarmMinutes));
        String correctHours;
        String correctMinutes;
        if (alarmMinutes<10){
            correctMinutes = "0".concat(String.valueOf(alarmMinutes));
        }else{
            correctMinutes = String.valueOf(alarmMinutes);
        }
        if (alarmHours<10){
            correctHours = "0".concat(String.valueOf(alarmHours));
        }else{
            correctHours = String.valueOf(alarmHours);
        }
        Log.d("ssssALARMTIME2",correctHours+":"+correctMinutes);
        return correctHours+":"+correctMinutes;
    }
    public String AlarmTime(){
        Integer alarmHours = alarmTime.getCurrentHour();
        Log.d("ssssalarmhours",alarmHours.toString());
        Integer alarmMinutes = alarmTime.getCurrentMinute();
        String stringAlarmTime;
        String correctMinutes;
        if (alarmMinutes<10){
            correctMinutes = "0";
            correctMinutes = correctMinutes.concat(alarmMinutes.toString());
        }else{
            correctMinutes = alarmMinutes.toString();
        }
        if (alarmHours==0){
            Log.d("ssssalarmhours","alarmHours==0");

            stringAlarmTime = "12".concat(":").concat(correctMinutes).concat(" AM");

        }
        else if (alarmHours<10){
            Log.d("ssssalarm<10",alarmHours.toString());
            stringAlarmTime = "0"+alarmHours.toString().concat(":").concat(correctMinutes).concat(" AM");
        }
        else if (alarmHours<12){
            stringAlarmTime = alarmHours.toString().concat(":").concat(correctMinutes).concat(" AM");
        }
        else if(alarmHours==12){
            stringAlarmTime = alarmHours.toString().concat(":").concat(correctMinutes).concat(" PM");
        }
        else{
            Log.d("ssssalarmhours","alarmHours>12");

            alarmHours = alarmHours - 12;
            if (alarmHours < 10){
                stringAlarmTime = "0"+alarmHours.toString().concat(":").concat(correctMinutes).concat(" PM");

            }else{
                stringAlarmTime = alarmHours.toString().concat(":").concat(correctMinutes).concat(" PM");
            }

        }
        return stringAlarmTime;
    }


}
