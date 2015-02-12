package com.seaco.seaconeuropsych;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class NumericEndReport extends ActionBarActivity {
    public static long duration;
    private long number;
    private int round_no;
    private int numberOfDigits;
    private int num_correct_so_far;
    private int num_errors;
    private boolean skipped;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numeric_end_report);

        // Extracting intent data from previous activity
        Intent intent = getIntent();
        number = intent.getLongExtra("numberGeneratedForCurrentRound", 0);
        round_no = intent.getIntExtra("roundNo",1);
        numberOfDigits = intent.getIntExtra("numberOfDigits", 0);
        num_correct_so_far = intent.getIntExtra("numCorrectSoFar",0);
        num_errors = intent.getIntExtra("numErrors",0);
        skipped = intent.getBooleanExtra("skipped",false);

        // getting total time taken
        duration = System.currentTimeMillis() - NumericStart.start_time;

        // Uppercasing the first char of the boolean value
        String skipped_str = Boolean.toString(skipped);
        String skipped_str_caps = Character.toUpperCase(skipped_str.charAt(0)) + skipped_str.substring(1);

        // Displaying the data
        TextView textview = (TextView)findViewById(R.id.report_textview);

        textview.setText(
                getString(R.string.numeric_last_number) + number + "\n" +
                        getString(R.string.numeric_rounds_answered) + round_no + "\n" +
                        getString(R.string.numeric_num_of_digits) + numberOfDigits + "\n" +
                        getString(R.string.numeric_num_correct) + num_correct_so_far + "\n" +
                        getString(R.string.numeric_num_wrong) + num_errors + "\n" +
                        getString(R.string.numeric_skipped) + skipped_str_caps + "\n" +
                        getString(R.string.numeric_duration) + duration/1000 + " " + getString(R.string.duration_unit)

        );
    }

    public void proceed(View view) {
        // placeholder for linking to another test game
        Intent intent = new Intent(this, EndBranch.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_numeric_end_report, menu);
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

    @Override
    public void onBackPressed() { // Disable hardware back button
    }
}
