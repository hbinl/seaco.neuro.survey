package com.seaco.seaconeuropsych;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;


public class NumericMainInput extends ActionBarActivity {
    private Context context = this;
    private EditText textInput;
    private long number;
    private int round_no;
    private int numberOfDigits;
    private int num_correct_so_far;
    private int num_errors;
    private boolean skipped;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numeric_main_input);

        // extracting data from previous Activity
        Intent intent = getIntent();
        number = intent.getLongExtra("numberGeneratedForCurrentRound", 0);
        round_no = intent.getIntExtra("roundNo",1);
        numberOfDigits = intent.getIntExtra("numberOfDigits", 0);
        num_correct_so_far = intent.getIntExtra("numCorrectSoFar",0);
        num_errors = intent.getIntExtra("numErrors",0);
        skipped = false;


        textInput = (EditText) findViewById(R.id.numInput);

        // Listen to soft keyboard Done/Return button presses
        textInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    checkAnswer(findViewById(R.id.submit_button));
                }
                return false;
            }
        });

        // Auto focus on the input field
        getWindow().setSoftInputMode (WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        textInput.setFocusableInTouchMode(true);
        textInput.setFocusable(true);
        textInput.requestFocus();
    }

    public void checkAnswer(View view) {
        // Checks the answer once the input is submitted
        String input = textInput.getText().toString();
        long user_input;

        // Check if empty input
        if (input.trim().equals("")) {
            // If empty, set default value
            user_input = -1;
        }
        else {
            // If not, parse the input into Long for comparison
            user_input = (long) Long.parseLong(input);
        }

        // Starts comparing
        if (user_input == number) {
            // If correct answer, increment correct count, and proceed

            num_correct_so_far++;

            if (round_no < 11) {
                nextRound();
            }
            else {
                endRounds();
            }
        }
        else {
            // else, increment wrong count
            num_errors++;

            if (num_errors < 2) {
                nextRound();
            }
            else {
                endRounds();
            }
        }
    }

    public void nextRound() {
        // Proceeding to the next Round
        Intent intent = new Intent(this, NumericMainActivity.class);
        intent.putExtra("roundNo",round_no+1);
        intent.putExtra("numCorrectSoFar",num_correct_so_far);
        intent.putExtra("numErrors",num_errors);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
        overridePendingTransition(0, 0);
    }

    public void endRounds() {

        // >2 wrongs, or reached round 11, hence ending the game and show report
        Intent intent = new Intent(this, NumericEndReport.class);
        intent.putExtra("numberGeneratedForCurrentRound", number);
        intent.putExtra("roundNo",round_no);
        intent.putExtra("numCorrectSoFar",num_correct_so_far);
        intent.putExtra("numErrors",num_errors);
        intent.putExtra("skipped", skipped);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
        overridePendingTransition(0, 0);
    }

    public void numericSkip(View view) {
        // when SKIP button is clicked, confirm and skip
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(getResources().getString(R.string.message_skip_confirm));

        // setting what to do when clicking OK button
        builder.setPositiveButton(R.string.button_confirm, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                skipped = true;
                endRounds();
            }
        });

        // what to do when user cancels skipping
        builder.setNegativeButton(R.string.button_cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // just doing nothing
                if (true) {}
            }
        });

        // show dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_numeric_main_input, menu);
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
