package com.example.karthika.speedcheck;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends ActionBarActivity {
    TextView textView;
    EditText editText;
    EditText editTextdist;
    TextView textView2;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        textView = (TextView) findViewById(R.id.textView);
        editText = (EditText) findViewById(R.id.editText);
        textView2 = (TextView) findViewById(R.id.textView2);
        editTextdist = (EditText) findViewById(R.id.editTextdist);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {


                    Date dt = new Date();
                    SimpleDateFormat ft1 = new SimpleDateFormat("dd-MM");
                    SimpleDateFormat ft2 = new SimpleDateFormat("HH:mm");
                    String date = ft1.format(dt);
                    String time = ft2.format(dt);
                    SimpleDateFormat ft3 = new SimpleDateFormat("dd.MM.yyyy");
                    SimpleDateFormat ft4 = new SimpleDateFormat("HH:mm:ss");

                    File f1 = Environment.getExternalStorageDirectory();
                    File f = new File(f1 ,"SpeedCheck");
                    if (!f.exists())
                        f.mkdir();
                    File f2 = new File(f1.getPath() + "/" + "SpeedCheck" + "/" + "SC" + "-" + date + "-" + time + ".txt");
                    f2.createNewFile();
                    String location = editText.getText().toString();
                    String distance = editTextdist.getText().toString();

                    if (location.length()!=0 && distance.length()!=0) {
                        String values[] = {location, ft3.format(dt), ft4.format(dt),distance, f2.getAbsolutePath()};
                        Intent i = new Intent(MainActivity.this, Speed_check.class);
                        i.putExtra("details", values);

                        startActivity(i);
                        //Toast.makeText(getApplicationContext(), "hello", Toast.LENGTH_SHORT).show();
                    }
                }

                catch(Exception e){
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                }

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
