package com.seaco.seaconeuropsych;

import java.util.Random;

import android.content.Intent;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class pair_level1 extends Activity {
    int first =0;
    public int activecounter=0;							//counts the number of active car
    public Button b[]= new Button[7];
    public int values[] = new int[7];   			//Stores the random numbers
    int display=-1;							//button currently activeious button
    int prev=-1;								// keep tract of prev
    int clicked=-1;
    int solved=0;							//keep tract of the total number of pairs obtained so far
    public int but_val [] = new int[7];           //List of values for each button
    int odd=0;
    Button next;

    protected void onSaveInstanceState(Bundle outState)
    {
        //Save all the values required for the game
        //Save the button states and any information displayed
        super.onSaveInstanceState(outState);


        outState.putInt("previous", prev);
        outState.putInt("clicked1", activecounter);
        outState.putInt("solv", solved);
        outState.putInt("count_val", activecounter);
        outState.putInt("disp", display);
        outState.putIntArray("array", but_val);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pair_level1);
        b[1]=(Button) findViewById(R.id.button1);
        b[2]=(Button) findViewById(R.id.button2);
        b[3]=(Button) findViewById(R.id.button3);
        b[4]=(Button) findViewById(R.id.button4);
        b[5]=(Button) findViewById(R.id.button5);
        b[6]=(Button) findViewById(R.id.button6);
        next=(Button) findViewById(R.id.next);

        for(int c=1;c<=6;c++)
        {
            b[c].setBackgroundResource(R.drawable.button);
            b[c].setText("?");
            b[c].setTextColor(Color.parseColor("#ffffff"));
        }
        randomization();
        //array store images
        //2nd array, array of integer, index of the other array
        //Assign listeners to each buttons

        b[1].setOnClickListener(new cln(1));
        b[2].setOnClickListener(new cln(2));
        b[3].setOnClickListener(new cln(3));
        b[4].setOnClickListener(new cln(4));
        b[5].setOnClickListener(new cln(5));
        b[6].setOnClickListener(new cln(6));
        next.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(pair_level1.this, pair_level2.class);
                startActivity(intent);
                finish();


            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pair_level1, menu);
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
    public void randomization()
    {
        //Store 3 distinct integers in an array
        //Use the above 3 values and insert them twice in a 6 slotted array
        //The array with 6 numbers are each assigned to a number using their index
        //Shuffle the 6 slot array so as each button is assigned randomly to an int


        Random random= new Random();
        for(int i=1; i<=3; i++)
        {
            int val=random.nextInt(100);
            while(exists(val)) { val=random.nextInt(100) ;}
            values[i]= val;
        }
        int valindex = 1;
        for(int x=1;x<=6;x++)
        {
            if (valindex >3)
            {
                valindex=1;
            }
            but_val[x]= values[valindex];
            String temp="";
            temp="Button "+x+" has val = "+values[valindex];
            System.out.println(temp);
            valindex++;

        }
        shuffleArray(but_val);
    }
    public boolean exists(int v)
    {
        for(int i=1;i<7;i++)
        {
            if (values[i]==v) return true;
        }
        return false;
    }
    public class cln implements View.OnClickListener
    {
        int n;
        int prev1;
        public cln(int button_index)
        {
            n = button_index;
        }

        @Override
        public void onClick(View v)
        {

            b[n].setText(""+but_val[n]);

            if (odd==0){
                odd=1;
            }
            else if (odd==1){
                odd=0;
            }

            if(clicked==-1)
            {
                b[n].setText(""+but_val[n]);
                clicked=n;
                display=n;
                activecounter++;

                prev=n;
                first=1;
                //Log.d(tag, "Button Pressed ="+n + "value of button = "+ but_val[n]);
            }
            else if (n!=clicked)
            {
                activecounter++;
                b[n].setText(""+but_val[n]);

                prev = clicked;
                clicked = n;

                if(but_val[prev] == but_val[n])
                {


                    b[prev].setEnabled(false);
                    b[n].setEnabled(false);

                    but_val[prev] = -1;
                    but_val[n] = -1;

                    solved++;
                    if(solved==3) {
                        ;}

                    clicked=-1;
                    prev= -1;

                }
                else
                {
                    display=n;

                    if (odd==0){
                        b[n].setText(""+but_val[n]);

                        CountDownTimer timer= new CountDownTimer(3000, 1000) {

                            public void onFinish() {
                                b[prev].setText("?");
                                b[n].setText("?");

                            }


                            String text="";
                            public void onTick(long millisUntilFinished) {
                                // TODO Auto-generated method stub
                                text=text+ millisUntilFinished;

                            }
                        }.start();
                        timer.start();

                    }
                    else{
                        b[n].setText(""+but_val[n]);
                        b[prev].setText("?");
                    }

                }

            }
        }
    }
    //Shuffle the Values of any array of type int passed
    //Swap integers function
    public void shuffleArray(int[] a)
    {
        int n = a.length;
        Random random = new Random();
        random.nextInt();
        for (int i = 1; i < n; i++) {
            int change = i + random.nextInt(n - i);
            swap(a, i, change);
        }
    }

    public void pause() {
        // TODO Auto-generated method stub






    }
    private void swap(int[] a, int i, int change) {
        int helper = a[i];
        a[i] = a[change];
        a[change] = helper;
    }

    @Override
    public void onBackPressed() { // Disable hardware back button
    }

}
