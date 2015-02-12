package com.seaco.seaconeuropsych;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class EndBranch extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_branch);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_end_branch, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void yesContinue3(View view) {
        // when OK button is clicked, proceed
        Intent intent = new Intent(EndBranch.this, NumericStart.class);

        startActivity(intent);

    }
    public void yesContinue6(View view) {
        // when OK button is clicked, proceed
        Intent intent = new Intent(EndBranch.this, Lights_main.class);

        startActivity(intent);

    }

    public void endActivity(View view) {
        // when OK button is clicked, proceed
        Intent intent = new Intent(EndBranch.this, EndActivity.class);

        startActivity(intent);

    }

    @Override
    public void onBackPressed() { // Disable hardware back button
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(new CalligraphyContextWrapper(newBase));
    }
}
