package com.seaco.seaconeuropsych;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.util.Xml;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;


public class prospective_end extends Activity {
    final Context context = this;               // for context purposes
    public static long duration;                // store duration taken from timer
    public static long end_start_time;          // Starting time of the current view for calculation of duration
    private static long visible_duration;       // store duration of the this screen visible
    private static int answer;                  // to store actual answer
    private static int id = 0;                  // to store what the user selects
    private static boolean proceed = false;     // to determine whether proceed to next activity
    private static int prev_id = 0;             // previous id stored
    private boolean correct = false;            // Is the user's answer correct? 0 - false, 1 - true
    private int attempts = 0;                   // number of attempts
    private boolean skipped = false;            // has the user skipped the test?

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_prospective_end);

        // calculate duration from initialised start time
        duration = System.currentTimeMillis() - prospective_initial.start_time;

        // setting the time that this View has started
        end_start_time = System.currentTimeMillis();

        //initialise user selection to 0
        id = 0;
        prev_id = 0;

        // switch to get decoy shape name
        String shapeName = null;
        switch (prospective_initial.fakeShape) {
            case 1:
                shapeName = getResources().getString(R.string.shape1);
                break;
            case 2:
                shapeName = getResources().getString(R.string.shape2);
                break;
            case 3:
                shapeName = getResources().getString(R.string.shape3);
                break;
            case 4:
                shapeName = getResources().getString(R.string.shape4);
                break;
        }

        //set up textview to display the Decoy shape prompt
        TextView textview = (TextView)findViewById(R.id.prospective_end_text);
        textview.setText(getString(R.string.prospective_end_text, shapeName));


        //Intent intent = getIntent();
        //final int answer = intent.getIntExtra("Answer", 0);

        // Extracting Answer from previous view
        answer = prospective_initial.shape;


        // Setting images
        final ImageView img1 = (ImageView) findViewById(R.id.imageView_1);
        img1.setImageResource(R.drawable.octagon_red);

        final ImageView img2= (ImageView) findViewById(R.id.imageView_2);
        img2.setImageResource(R.drawable.circle_orange);

        final ImageView img3= (ImageView) findViewById(R.id.imageView_3);
        img3.setImageResource(R.drawable.square_blue);

        final ImageView img4= (ImageView) findViewById(R.id.imageView_4);
        img4.setImageResource(R.drawable.triangle_green);

        // and setting up onClick events

        View.OnClickListener clickListener1 = new View.OnClickListener() {
            public void onClick(View v) {
                if (v.equals(img1)) {
                    // if this imageView is clicked, show a selection highlight and unset other highlights
                    // also set id to this selection
                    id = 1;
                    img1.setBackgroundColor(Color.rgb(150, 150, 150));
                    img2.setBackgroundColor(0x00000000);
                    img3.setBackgroundColor(0x00000000);
                    img4.setBackgroundColor(0x00000000);

                }
            }
        };
        img1.setOnClickListener(clickListener1);

        View.OnClickListener clickListener2 = new View.OnClickListener() {
            public void onClick(View v) {
                if (v.equals(img2)) {
                    id = 2;
                    img2.setBackgroundColor(Color.rgb(150,150,150));
                    img1.setBackgroundColor(0x00000000);
                    img3.setBackgroundColor(0x00000000);
                    img4.setBackgroundColor(0x00000000);

                }
            }
        };
        img2.setOnClickListener(clickListener2);


        View.OnClickListener clickListener3 = new View.OnClickListener() {
            public void onClick(View v) {
                if (v.equals(img3)) {
                    id = 3;
                    img3.setBackgroundColor(Color.rgb(150,150,150));
                    img2.setBackgroundColor(0x00000000);
                    img1.setBackgroundColor(0x00000000);
                    img4.setBackgroundColor(0x00000000);

                }
            }
        };
        img3.setOnClickListener(clickListener3);


        View.OnClickListener clickListener4 = new View.OnClickListener() {
            public void onClick(View v) {
                if (v.equals(img4)) {
                    id = 4;
                    img4.setBackgroundColor(Color.rgb(150,150,150));
                    img2.setBackgroundColor(0x00000000);
                    img3.setBackgroundColor(0x00000000);
                    img1.setBackgroundColor(0x00000000);

                }
            }
        };
        img4.setOnClickListener(clickListener4);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_prospective_end, menu);
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

    public void skipActivity(View view) {
        // when SKIP button is clicked, confirm and skip
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(getResources().getString(R.string.end_message_skip_confirm));

        // setting what to do when clicking OK button
        builder.setPositiveButton(R.string.confirm_button, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                proceed = true;
                dialogAnswer(-1, answer);
            }
        });

        // what to do when user cancels skipping
        builder.setNegativeButton(R.string.cancel_button, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                proceed = false;

            }
        });

        // show dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void endActivity(View view) {
        // when NEXT button is clicked, check answer
        proceed = false;
        if (prev_id == 0) {
            // if it's the first attempt, save first attempt
            prev_id = id;
        }
        attempts += 1;
        dialogAnswer(id, answer);
    }

    private void dialogAnswer(int id, int answer) {
        final int actual_answer = answer;
        skipped = false;

        // build a dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        if (id == -1) {
            // if the user skipped
            proceed = true;
            skipped = true;
            visible_duration = System.currentTimeMillis() - end_start_time;
            builder.setMessage(getResources().getString(R.string.end_message_skip));
        }
        else {
            // check if answer == user's selection
            if (id == answer) {
                // if answer is correct
                // getResources().getString(R.string.time_taken) + duration / 1000 +
                // getResources().getString(R.string.time_unit_seconds) +
                builder.setMessage(getResources().getString(R.string.end_message_correct));
                proceed = true;
                correct = true;
                visible_duration = System.currentTimeMillis() - end_start_time;
            } else if (id == 0) {
                // if no shape is selected
                builder.setMessage(getResources().getString(R.string.end_message_empty));
            } else {
                // if wrong
                builder.setMessage(getResources().getString(R.string.end_message_wrong));

                if (id != prev_id) {
                    // if the user chooses another wrong answer
                    builder.setMessage(getResources().getString(R.string.end_message_wrong_accept));
                    proceed = true;
                    correct = false;
                    visible_duration = System.currentTimeMillis() - end_start_time;
                }

            }
        }

        builder.setPositiveButton(R.string.confirm_button, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                // if answer is correct or skipped, proceed to next activity
                if (proceed) {
                    // passing data for reports

                    final String xmlFile = "userData";
                    String userName = "username";
                    String password = "password";
                    try {
                        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "X" + "/", "userData.xml");
                        FileOutputStream fileos = new FileOutputStream(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "X" + "/" + "userData.xml");
                        System.out.println("S");
                        //FileOutputStream fileos= getApplicationContext().openFileOutput(xmlFile, Context.MODE_PRIVATE);
                        XmlSerializer xmlSerializer = Xml.newSerializer();
                        StringWriter writer = new StringWriter();
                        xmlSerializer.setOutput(writer);
                        xmlSerializer.startDocument("UTF-8", true);
                        xmlSerializer.startTag(null, "userData");
                        xmlSerializer.startTag(null, "userName");
                        xmlSerializer.text("x");
                        xmlSerializer.endTag(null, "userName");
                        xmlSerializer.startTag(null,"password");
                        xmlSerializer.text("s");
                        xmlSerializer.endTag(null, "password");
                        xmlSerializer.endTag(null, "userData");
                        xmlSerializer.endDocument();
                        xmlSerializer.flush();
                        String dataWrite = writer.toString();
                        fileos.write(dataWrite.getBytes());
                        fileos.close();

                    }
                    catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    catch (IllegalArgumentException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    catch (IllegalStateException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }


                    Intent intent = new Intent(context, prospective_result.class);
                    intent.putExtra("1_Prospective_Actual_Answer", actual_answer);
                    intent.putExtra("1_Prospective_Final_Answer", prospective_end.id);
                    intent.putExtra("1_Prospective_Correct", correct);
                    intent.putExtra("1_Prospective_Number_Attempted", attempts);
                    intent.putExtra("1_Prospective_Skipped", skipped);
                    intent.putExtra("1_Prospective_Initial_Until_Final_Duration", duration);
                    intent.putExtra("1_Prospective_End_Screen_Visible_Duration", visible_duration);
                    startActivity(intent);
                    finish();
                }

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onBackPressed() { // Disable hardware back button
    }
}
