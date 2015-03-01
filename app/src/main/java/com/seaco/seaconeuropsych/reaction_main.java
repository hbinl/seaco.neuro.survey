package com.seaco.seaconeuropsych;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Random;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class reaction_main extends ActionBarActivity {
    int count=0;
    long timestart=-1;
    long timefinish;
    double []times=new double[13];
    Button match;
    Button start;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reaction_main);

        start=(Button) findViewById(R.id.button1);
        final TextView reaction=(TextView) findViewById(R.id.textView2);
        reaction.setVisibility(View.INVISIBLE);
        final EditText text=(EditText) findViewById(R.id.editText2);
        text.setVisibility(View.INVISIBLE);
        ImageView imageview2=(ImageView) findViewById(R.id.imageView2);
        ImageView imageview1=(ImageView) findViewById(R.id.imageView1);
        //set an image to the image views
        imageview1.setImageResource(R.drawable.l2);
        imageview2.setImageResource(R.drawable.l1);
        match=(Button) findViewById(R.id.button2);
        match.setVisibility(View.INVISIBLE);
        match.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                timefinish = System.currentTimeMillis();

                count++;
                times[count] = (timefinish - timestart)/1000;
                BigDecimal bde = new BigDecimal(times[count]);
                bde = bde.round(new MathContext(3));
                double time = bde.doubleValue();
                if (timestart == -1) {
                    reaction.setVisibility(View.INVISIBLE);
                    text.setVisibility(View.INVISIBLE);
                } else {
                    text.setVisibility(View.VISIBLE);
                    reaction.setVisibility(View.VISIBLE);
                    text.setText(String.valueOf(time) + " seconds!");
                    timestart = -1;
                    if (count == 12) {
                        double sum = 0;
                        for (int j = 3; j <= 12; j++) {
                            sum = sum + times[j];
                        }
                        double mean = sum / 10;
                        BigDecimal bd = new BigDecimal(mean);
                        bd = bd.round(new MathContext(3));
                        double rounded = bd.doubleValue();
                        text.setText("Average reaction time is : " + String.valueOf(rounded));
                        match.setVisibility(View.INVISIBLE);

                    } else {
                        displayPictures();
                    }

                }
            }
        });

        start.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                //start the game
                match.setVisibility(View.VISIBLE);
                start.setVisibility(View.INVISIBLE);
                displayPictures();


                //match.setVisibility(View.INVISIBLE);


            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_reaction_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @TargetApi(Build.VERSION_CODES.KITKAT)
    @SuppressLint("NewApi")
    public void displayPictures(){


        Random r=new Random();

        int chosen=r.nextInt(12-1)+1;
        final ImageView img1=(ImageView) findViewById(R.id.imageView1);
        final ImageView img2=(ImageView) findViewById(R.id.imageView2);
        switch (chosen){
            case 1 :
            {
                img1.setImageResource(R.drawable.l1);
                img2.setImageResource(R.drawable.l2);
                break;
            }

            case 2:
            {
                img1.setImageResource(R.drawable.l3);
                img2.setImageResource(R.drawable.l2);
                break;
            }

            case 3:
            {
                img1.setImageResource(R.drawable.l3);
                img2.setImageResource(R.drawable.l4);
                break;
            }
            case 4:
            {
                img1.setImageResource(R.drawable.l5);
                img2.setImageResource(R.drawable.l4);
                break;
            }
            case 5:
            {
                img1.setImageResource(R.drawable.l5);
                img2.setImageResource(R.drawable.l6);
                break;
            }
            case 6:
            {
                img1.setImageResource(R.drawable.l6);
                img2.setImageResource(R.drawable.l11);
                break;
            }
            case 7:
            {
                img1.setImageResource(R.drawable.l12);
                img2.setImageResource(R.drawable.l7);
                break;
            }
            case 8:
            {
                img1.setImageResource(R.drawable.l10);
                img2.setImageResource(R.drawable.l8);
                break;
            }
            case 9:
            {
                img1.setImageResource(R.drawable.l9);
                img2.setImageResource(R.drawable.l1);
                break;
            }
            case 10:
            {
                img1.setImageResource(R.drawable.l1);
                img2.setImageResource(R.drawable.l10);
                break;
            }
            case 11:
            {
                img1.setImageResource(R.drawable.l1);
                img2.setImageResource(R.drawable.l2);
                break;
            }
            case 12:
            {
                img1.setImageResource(R.drawable.l12);
                img2.setImageResource(R.drawable.l3);
                break;
            }
        }


        randomise();



    }
    public void randomise(){
        //randomises the amount of time before chaging the picture
        Random r=new Random();
        //get a random number between 10000 and 5000, 5000 included
        int end=r.nextInt(10000-5000)+5000;
        CountDownTimer timer= new CountDownTimer(end,1000) {

            public void onFinish() {
                //display a random pairs of cards
                randompic();
                //start counter for the number of seconds
                timestart=System.currentTimeMillis();


            }


            String text="";
            public void onTick(long millisUntilFinished) {
                //count number of milliseconds
                text=text+ millisUntilFinished;
            }
        }.start();
        timer.start();



    }


    public void randompic(){
        Random r=new Random();
        //obtain a random number between 1 and 12,  1 included
        int chosen=r.nextInt(12-1)+1;
        final ImageView img1=(ImageView) findViewById(R.id.imageView1);
        final ImageView img2=(ImageView) findViewById(R.id.imageView2);
        //display the images depending on the random number
        switch (chosen){
            case 1 :
            {
                img1.setImageResource(R.drawable.l1);
                img2.setImageResource(R.drawable.l1);
                break;
            }

            case 2:
            {
                img1.setImageResource(R.drawable.l2);
                img2.setImageResource(R.drawable.l2);
                break;
            }

            case 3:
            {
                img1.setImageResource(R.drawable.l3);
                img2.setImageResource(R.drawable.l3);
                break;
            }
            case 4:
            {
                img1.setImageResource(R.drawable.l4);
                img2.setImageResource(R.drawable.l4);
                break;
            }
            case 5:
            {
                img1.setImageResource(R.drawable.l5);
                img2.setImageResource(R.drawable.l5);
                break;
            }
            case 6:
            {
                img1.setImageResource(R.drawable.l6);
                img2.setImageResource(R.drawable.l6);
                break;
            }
            case 7:
            {
                img1.setImageResource(R.drawable.l7);
                img2.setImageResource(R.drawable.l7);
                break;
            }
            case 8:
            {
                img1.setImageResource(R.drawable.l8);
                img2.setImageResource(R.drawable.l8);
                break;
            }
            case 9:
            {
                img1.setImageResource(R.drawable.l9);
                img2.setImageResource(R.drawable.l9);
                break;
            }
            case 10:
            {
                img1.setImageResource(R.drawable.l10);
                img2.setImageResource(R.drawable.l10);
                break;
            }
            case 11:
            {
                img1.setImageResource(R.drawable.l1);
                img2.setImageResource(R.drawable.l1);
                break;
            }
            case 12:
            {
                img1.setImageResource(R.drawable.l12);
                img2.setImageResource(R.drawable.l12);
                break;
            }
        }

    }

    public void endActivity(View view) {
        // when OK button is clicked, proceed
        Intent intent = new Intent(reaction_main.this, prospective_end.class);
        int shape = 1;
        intent.putExtra("Answer", shape);
        startActivity(intent);

    }

    @Override
    public void onBackPressed() { // Disable hardware back button
    }
}