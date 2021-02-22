// EXERCISES PAGE

package com.example.alarm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Page3 extends AppCompatActivity {
    Timer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page3);

        final Globals glob = (Globals) getApplicationContext();
        String lvl  = glob.get_level();

        glob.set_ring(true);
        glob.set_started(false);
        Intent intent = new Intent(getApplicationContext(), RingtonePlayingService.class);

        Integer[] count = {0};
        final Double[] choice = {new Random().nextDouble()};
        Log.d("ssssRANDOM_CHOICE", choice[0].toString());
        Boolean[] ok = {true};

        Button res1 = findViewById(R.id.res1);
        Button res2 = findViewById(R.id.res2);
        String rand_op = random_operation(lvl);
        String u1 = result(rand_op);
        String u2 = false_result(rand_op);
        if (choice[0] < 0.5){
            res1.setText(u1.substring(0,u1.length()-2));
            res2.setText(u2.substring(0,u2.length()-2));
        }else{
            res2.setText(u1.substring(0,u1.length()-2));
            res1.setText(u2.substring(0,u2.length()-2));
        }
        TextView count_text = findViewById(R.id.count);
        count_text.setText("COUNT: 0");
        TextView answer = findViewById(R.id.answer);
        answer.setText("");
        TextView oper = findViewById(R.id.oper);
        oper.setText(rand_op.concat(" = ?"));

        timer = new Timer();

        int[] secs = {20};
        final Handler sec = new Handler();
        sec.postDelayed(new Runnable() {
            @Override
            public void run() {
                secs[0] = secs[0] -1;
                if(secs[0] <= 0){
                    glob.set_ring(true);
                }else{
                    glob.set_ring(false);
                }
                if (ok[0]){
                    sec.postDelayed(this, 1000);
                    Log.d("ssssec","seeeeeec");
                }

            }
        }, 0);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (glob.get_ring()){
                    Log.d("ssssRING","RING");
                    if(glob.get_started()){
                        Log.d("ssssSTARTED","STARTED");
                    }
                    else {
                        Log.d("ssssSTART_SERVICE","START_SERVICE");
                        startService(intent);
                        glob.set_started(true);
                    }
                }
                else{
                    if(ok[0]){
                        Log.d("ssssSTOOOP_SERVICE","STOOOP_SERVICE");
                        stopService(intent);
                    }
                    glob.set_started(false);
                    glob.set_ring(false);
                }
                handler.postDelayed(this, 1000);
            }
        }, 20000);


        res1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("ssssres1", "res1");
                secs[0]=20;

                if (choice[0] <0.5){
                    ++count[0];
                    count_text.setText("COUNT: "+String.valueOf(count[0]));
                    answer.setTextColor(Color.GREEN);
                    answer.setText("CORRECT!");
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            answer.setText("");
                        }
                    },1000);

                }
                if(choice[0] >=0.5){
                    count[0] = 0;
                    count_text.setText("COUNT: 0");
                    answer.setTextColor(Color.RED);
                    answer.setText("WRONG!");
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            answer.setText("");
                        }
                    },1000);

                }
                Log.d("ssssCOUNT",count[0].toString());
                if(count[0]>2){
                    Log.d("ssssIF_COUNT>2",count[0].toString());
                    Intent activity2Intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(activity2Intent);
                    glob.set_ring(false);
                    ok[0]= false;
                    glob.set_sveglia("no");
                    finish();
                }else{
                    Log.d("ssssELSE_COUNT",count[0].toString());
                    choice[0]=change_operation(res1, res2, oper, lvl);

                }

            }
        });


        res2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("ssssres2", "res2");
                secs[0]=20;

                if (choice[0] > 0.5){
                    ++count[0];
                    count_text.setText("COUNT: "+String.valueOf(count[0]));
                    answer.setTextColor(Color.GREEN);
                    answer.setText("CORRECT!");
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            answer.setText("");
                        }
                    },1000);

                }
                if(choice[0] <= 0.5){
                    count[0] = 0;
                    count_text.setText("COUNT: 0");
                    answer.setTextColor(Color.RED);
                    answer.setText("WRONG!");
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            answer.setText("");
                        }
                    },1000);
                }
                Log.d("ssssCOUNT",count[0].toString());
                if(count[0]>2){
                    Log.d("ssssIF_COUNT>2",count[0].toString());
                    Intent activity2Intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(activity2Intent);
                    glob.set_ring(false);
                    ok[0]= false;
                    glob.set_sveglia("no");
                    finish();
                }else{
                    Log.d("ssssSTART_CHANGE_OPER","START_CHANGE_OPER");
                    choice[0] = change_operation(res1, res2, oper, lvl);

                }
            }
        });

    }

    public double change_operation(Button res1, Button res2, TextView oper, String lvl){
        String random_operation = random_operation(lvl);
        oper.setText(random_operation.concat(" = ?"));
        Log.d("ssssRANDOM OPERATION",random_operation);
        Log.d("ssssRESULT",result(random_operation));
        Log.d("ssssFALSE_RESULT",false_result(random_operation));
        double choice = new Random().nextDouble();
        Log.d("ssssCHANGE OPERATION", Double.toString(choice));
        if (choice<0.5){
            String q1 = result(random_operation);
            String q2 = false_result(random_operation);
            res1.setText(q1.substring(0,q1.length()-2));
            res2.setText(q2.substring(0,q2.length()-2));
        }else{
            String q3 = result(random_operation);
            String q4 = false_result(random_operation);
            res2.setText(q3.substring(0,q3.length()-2));
            res1.setText(q4.substring(0,q4.length()-2));
        }
        return choice;
    }

    public String rand_sign(){
        double x = new Random().nextDouble();
        String res;
        if (x<0.33333333333){
            res = "+";
        }else if(x>0.6666666666){
            res = "-";
        }else{
            res = "*";
        }
        return res;
    }
    public int get_rand_elem(List<Integer> list)
    {
        Random rand = new Random();
        return list.get(rand.nextInt(list.size()));
    }
    public static int randInt(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }
    public String random_operation(String level){
        int l;
        if (level.equals("HARD")){
            l = 1000;
        }
        else if(level.equals("MEDIUM")){
            l = 100;
        }
        else{
            l = 10;
        }
        List<Integer> lint = new ArrayList<>();
        for (int i=0;i<7;i++){
            lint.add(randInt(0, l));  // DIFFICULTYYYYYY
        }
        return get_rand_elem(lint)+rand_sign()+get_rand_elem(lint)+rand_sign()+get_rand_elem(lint);
    }
    public String result(String operation){
        return String.valueOf(eval(operation));
    }
    public String false_result(String operation){
        double error = randInt(0,10);
        error -= 6;
        if (error==0){
            error=randInt(0,10);
        }
        return String.valueOf(eval(operation)+error);
    }

    public static double eval(final String str) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < str.length()) ? str.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char)ch);
                return x;
            }

            // Grammar:
            // expression = term | expression `+` term | expression `-` term
            // term = factor | term `*` factor | term `/` factor
            // factor = `+` factor | `-` factor | `(` expression `)`
            //        | number | functionName factor | factor `^` factor

            double parseExpression() {
                double x = parseTerm();
                for (;;) {
                    if      (eat('+')) x += parseTerm(); // addition
                    else if (eat('-')) x -= parseTerm(); // subtraction
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (;;) {
                    if      (eat('*')) x *= parseFactor(); // multiplication
                    else if (eat('/')) x /= parseFactor(); // division
                    else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return parseFactor(); // unary plus
                if (eat('-')) return -parseFactor(); // unary minus

                double x;
                int startPos = this.pos;
                if (eat('(')) { // parentheses
                    x = parseExpression();
                    eat(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(str.substring(startPos, this.pos));
                } else if (ch >= 'a' && ch <= 'z') { // functions
                    while (ch >= 'a' && ch <= 'z') nextChar();
                    String func = str.substring(startPos, this.pos);
                    x = parseFactor();
                    if (func.equals("sqrt")) x = Math.sqrt(x);
                    else if (func.equals("sin")) x = Math.sin(Math.toRadians(x));
                    else if (func.equals("cos")) x = Math.cos(Math.toRadians(x));
                    else if (func.equals("tan")) x = Math.tan(Math.toRadians(x));
                    else throw new RuntimeException("Unknown function: " + func);
                } else {
                    throw new RuntimeException("Unexpected: " + (char)ch);
                }

                if (eat('^')) x = Math.pow(x, parseFactor()); // exponentiation

                return x;
            }
        }.parse();
    }



}
