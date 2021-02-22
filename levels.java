package com.example.alarm;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;

public class levels extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levels);

        final Globals global_level = (Globals) getApplicationContext();


        final Button easy = findViewById(R.id.easy);
        final Button medium = findViewById(R.id.medium);
        final Button hard = findViewById(R.id.hard);
        final Button back = findViewById(R.id.back);

        TextView your_level = findViewById(R.id.your_level);
        String[] t_lvl = {"Your level is set to: "};
        your_level.setText(t_lvl[0]+global_level.get_level());

        easy.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            public void onClick(View v) {
                //lvl[0] = "easy";
                global_level.set_level("EASY");
                your_level.setText(t_lvl[0] +"EASY");
            }
        });
        medium.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            public void onClick(View v) {
                global_level.set_level("MEDIUM");
                your_level.setText(t_lvl[0] +"MEDIUM");
            }
        });
        hard.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            public void onClick(View v) {
                global_level.set_level("HARD");
                your_level.setText(t_lvl[0] +"HARD");
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent activity2Intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(activity2Intent);
                finish();
            }
        });

    }

}