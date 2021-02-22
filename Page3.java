// ALARM CLOCK PAGE

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
import java.util.Arrays;
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
        Button res3 = findViewById(R.id.res3);
        Button res4 = findViewById(R.id.res4);
        String random_operation = random_operation(lvl);
        List<String> false_r = false_results(random_operation);
        String q1 = result(random_operation);
        String q2 = false_r.get(0);
        String q3 = false_r.get(1);
        String q4 = false_r.get(2);
        if (choice[0] <= 0.25){
            res1.setText(q1.substring(0,q1.length()-2));
            res2.setText(q2.substring(0,q2.length()-2));
            res3.setText(q3.substring(0,q3.length()-2));
            res4.setText(q4.substring(0,q4.length()-2));

        }else if (choice[0] <= 0.5 & choice[0] > 0.25){
            res1.setText(q2.substring(0,q2.length()-2));
            res2.setText(q3.substring(0,q3.length()-2));
            res3.setText(q4.substring(0,q4.length()-2));
            res4.setText(q1.substring(0,q1.length()-2));
        }else if(choice[0] > 0.75){
            res1.setText(q3.substring(0,q3.length()-2));
            res2.setText(q4.substring(0,q4.length()-2));
            res3.setText(q1.substring(0,q1.length()-2));
            res4.setText(q2.substring(0,q2.length()-2));

        }else{
            res1.setText(q4.substring(0,q4.length()-2));
            res2.setText(q1.substring(0,q1.length()-2));
            res3.setText(q2.substring(0,q2.length()-2));
            res4.setText(q3.substring(0,q3.length()-2));
        }

        TextView count_text = findViewById(R.id.count);
        count_text.setText("COUNT: 0");
        TextView answer = findViewById(R.id.answer);
        answer.setText("");
        TextView oper = findViewById(R.id.oper);
        oper.setText(random_operation.concat(" = ?"));

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

                if (choice[0] <= 0.25){
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

                }else{
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
                    choice[0]=change_operation4(res1, res2, res3, res4, oper, lvl);

                }

            }
        });


        res2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("ssssres2", "res2");
                secs[0]=20;

                if (choice[0] > 0.5 & choice[0] <= 0.75){
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

                }else{
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
                    choice[0] = change_operation4(res1, res2, res3, res4, oper, lvl);

                }
            }
        });

        res3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("ssssres3", "res3");
                secs[0]=20;

                if (choice[0] > 0.75){
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

                }else{
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
                    choice[0] = change_operation4(res1, res2, res3, res4, oper, lvl);

                }
            }
        });

        res4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("ssssres4", "res4");
                secs[0]=20;

                if(choice[0]<= 0.5 & choice[0] > 0.25){
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

                }else{
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
                    choice[0] = change_operation4(res1, res2, res3, res4, oper, lvl);

                }
            }
        });

    }

    public double change_operation4(Button res1, Button res2, Button res3, Button res4, TextView oper, String lvl){
        String random_operation = random_operation(lvl);
        oper.setText(random_operation.concat(" = ?"));
        Log.d("ssssRANDOM OPERATION",random_operation);
        Log.d("ssssRESULT",result(random_operation));
        Log.d("ssssFALSE_RESULT111",false_results(random_operation).get(0));
        Log.d("ssssFALSE_RESULT222",false_results(random_operation).get(1));
        Log.d("ssssFALSE_RESULT333",false_results(random_operation).get(2));
        double choice = new Random().nextDouble();
        Log.d("ssssCHANGE OPERATION", Double.toString(choice));
        List<String> false_r = false_results(random_operation);
        String q1 = result(random_operation);
        String q2 = false_r.get(0);
        String q3 = false_r.get(1);
        String q4 = false_r.get(2);
        if (choice <= 0.25){
            res1.setText(q1.substring(0,q1.length()-2));
            res2.setText(q2.substring(0,q2.length()-2));
            res3.setText(q3.substring(0,q3.length()-2));
            res4.setText(q4.substring(0,q4.length()-2));
        }else if (choice <= 0.5 & choice > 0.25){
            res1.setText(q2.substring(0,q2.length()-2));
            res2.setText(q3.substring(0,q3.length()-2));
            res3.setText(q4.substring(0,q4.length()-2));
            res4.setText(q1.substring(0,q1.length()-2));
        }else if(choice > 0.75){
            res1.setText(q3.substring(0,q3.length()-2));
            res2.setText(q4.substring(0,q4.length()-2));
            res3.setText(q1.substring(0,q1.length()-2));
            res4.setText(q2.substring(0,q2.length()-2));
        }else{
            res1.setText(q4.substring(0,q4.length()-2));
            res2.setText(q1.substring(0,q1.length()-2));
            res3.setText(q2.substring(0,q2.length()-2));
            res4.setText(q3.substring(0,q3.length()-2));
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
    public List<String> false_results(String operation){
        double choi = new Random().nextDouble();
        double error1;
        if (choi<0.3){
            double error_dec = randInt(-2,2);
            error1 = error_dec*10;
        }else{
            error1 = randInt(1,9);
        }
        if (error1==0){
            error1=randInt(1,10);
        }

        double error2;
        double choic = new Random().nextDouble();
        if (choic<0.5){
            double error_dec = randInt(-4,4);
            error2 = error_dec*10;
        }else{
            double error_dec2 = randInt(-4,4);
            error2 = error_dec2*10;
            error2 += randInt(1,9);
        }
        if (error2==0){
            error2=randInt(1,11);
        }

        double error_dec2 = randInt(-2,2);
        double error3 = error_dec2*10;
        if (error3==0){
            error3=randInt(1,100);
        }

        if (error1==error2 & error2==error3){
            error2 = error1 - 1;
            error3 = error1 + 1;
        }
        if(error1 == error2){
            error2 = error1 - 1;
        }
        if(error1 == error3){
            error3 = error1 +1;
        }
        if(error2 == error3){
            error2 = error3 - 1;
        }
        double ev = eval(operation);
        return Arrays.asList(String.valueOf(ev + error1), String.valueOf(ev + error2), String.valueOf(ev + error3));
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
