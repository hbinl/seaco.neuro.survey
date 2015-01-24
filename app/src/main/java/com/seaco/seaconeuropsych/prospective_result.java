package com.seaco.seaconeuropsych;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;


public class prospective_result extends ActionBarActivity {
    final Context context = this;               // for context purposes
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Hide title bar
        //supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_prospective_result);

        // extractin data from intents
        Intent intent = getIntent();
        int actual_answer = intent.getIntExtra("1_Prospective_Actual_Answer",0);
        int user_answer = intent.getIntExtra("1_Prospective_Final_Answer",0);
        int attempts = intent.getIntExtra("1_Prospective_Number_Attempted",0);
        Long initial_duration = intent.getLongExtra("1_Prospective_Initial_Until_Final_Duration",0);
        Long visible_duration = intent.getLongExtra("1_Prospective_End_Screen_Visible_Duration",0);

        // getting boolean datas, and capitalising first character
        String skipped = Boolean.toString(intent.getBooleanExtra("1_Prospective_Skipped",false));
        skipped = Character.toUpperCase(skipped.charAt(0)) + skipped.substring(1);

        String correct = Boolean.toString(intent.getBooleanExtra("1_Prospective_Correct", false));
        correct = Character.toUpperCase(correct.charAt(0)) + correct.substring(1);

        // displaying report
        TextView textview = (TextView)findViewById(R.id.prospective_result_report);
        textview.setText(
                getString(R.string.prospective_results_initial_answer) + actual_answer + "\n" +
                        getString(R.string.prospective_results_final_answer) + user_answer + "\n" +
                        getString(R.string.prospective_results_correct) + correct + "\n" +
                        getString(R.string.prospective_results_number_attempted) + attempts + "\n" +
                        getString(R.string.prospective_results_skipped) + skipped + "\n" +
                        getString(R.string.prospective_results_time_initial_final) + initial_duration/1000 + "s\n" +
                        getString(R.string.prospective_results_time_end_visible) + visible_duration/1000 + "s\n"
        );



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_prospective_result, menu);
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

    public void endActivity(View view) {
        // when OK button is clicked, proceed
        Intent intent = new Intent(context, EndBranch.class);

        startActivity(intent);

    }
}
