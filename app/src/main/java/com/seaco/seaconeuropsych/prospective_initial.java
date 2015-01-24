package com.seaco.seaconeuropsych;
//trial
//soomadee's 
//here again
//lol
//haobin'sfsdjhfkhkfhfakhfkahfkljalhflahdkfldjkfjkdj
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;


public class prospective_initial extends ActionBarActivity {
    public static int shape;        //store shape for this session
    public static int fakeShape;    //the decoy shape for this session
    public static long start_time;  //start timer

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Hide title bar
        //supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.prospective_initial);

        //initialise start timer
        start_time = System.currentTimeMillis();

        // Generate random shape from 1-4
        Random r = new Random();
        shape = r.nextInt(3) + 1;
        String shapeName = null;

        // get shape name from string.xml, and apply bold+underline
        // also generates a decoy shape
        switch (shape) {
            case 1:
                shapeName = "<b><u>" + getResources().getString(R.string.shape1) + "</u></b>";
                fakeShape = 2;
                break;
            case 2:
                shapeName = "<b><u>" + getResources().getString(R.string.shape2) + "</u></b>";
                fakeShape = 3;
                break;
            case 3:
                shapeName = "<b><u>" + getResources().getString(R.string.shape3) + "</u></b>";
                fakeShape = 4;
                break;
            case 4:
                shapeName = "<b><u>" + getResources().getString(R.string.shape4) + "</u></b>";
                fakeShape = 1;
                break;
        }


        // Initialise textview
        TextView txtview = (TextView)findViewById(R.id.prospective_initial_text);
        txtview.setText(Html.fromHtml(getString(R.string.prospective_initial_text, "<br>", shapeName)));

        // setup Images
        ImageView img1= (ImageView) findViewById(R.id.imageView_1);
        img1.setImageResource(R.drawable.octagon_red);

        ImageView img2= (ImageView) findViewById(R.id.imageView_2);
        img2.setImageResource(R.drawable.circle_orange);

        ImageView img3= (ImageView) findViewById(R.id.imageView_3);
        img3.setImageResource(R.drawable.square_blue);

        ImageView img4= (ImageView) findViewById(R.id.imageView_4);
        img4.setImageResource(R.drawable.triangle_green);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_prospective_initial, menu);
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

    public void nextActivity(View view) {
        // when Next is clicked, start next activity
        Intent intent = new Intent(this, pair_level1.class);

        intent.putExtra("Answer", shape);
        startActivity(intent);
    }
}