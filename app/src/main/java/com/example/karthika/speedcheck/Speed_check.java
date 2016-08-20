package com.example.karthika.speedcheck;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class Speed_check extends AppCompatActivity {

    FileOutputStream fileOutputStream;
    int position1;
    int position2;
    long since1;
    long since2;
    float distance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_speed_check);
        GridView gridView = (GridView) findViewById(R.id.gridView);
        gridView.setAdapter(new ImageAdapter(this));
        String values[] = getIntent().getExtras().getStringArray("details");
        position1 = -1;
        position2 = -1;
        try {

            final File file = new File(values[4]);

            file.createNewFile();

            //final FileOutputStream
            fileOutputStream = new FileOutputStream(file);
            final byte nl[] = "\n".getBytes();
            byte loc[] = "Location : ".getBytes();
            fileOutputStream.write(loc);
            byte loc_name[] = values[0].getBytes();
            fileOutputStream.write(loc_name);
            final byte tab[] = "              ".getBytes();
            fileOutputStream.write(tab);
            fileOutputStream.write(nl);
            byte dat[] = "Date : ".getBytes();
            fileOutputStream.write(dat);
            byte dat_name[] = values[1].getBytes();
            fileOutputStream.write(dat_name);
            fileOutputStream.write(tab);
            fileOutputStream.write(nl);
            byte tim[] = "Time : ".getBytes();
            fileOutputStream.write(tim);
            byte tim_name[] = values[2].getBytes();
            fileOutputStream.write(tim_name);
            fileOutputStream.write(tab);
            fileOutputStream.write(nl);
            byte dist[] = "Distance : ".getBytes();
            fileOutputStream.write(dist);
            byte dist_name[] = values[3].getBytes();
            fileOutputStream.write(dist_name);
            distance = Long.valueOf(values[3]);

            fileOutputStream.write(nl);
            fileOutputStream.write(nl);
            byte sl[] = "Sl No:     ".getBytes();
            fileOutputStream.write(sl);
            byte v[] = "Vehicle".getBytes();
            fileOutputStream.write(v);
            fileOutputStream.write(tab);
            byte ti[] = "Speed (m/sec)".getBytes();
            fileOutputStream.write(ti);
            fileOutputStream.write(nl);
            fileOutputStream.write(nl);
             gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                int count = 0;

                String getname(int val) {
                    switch (val) {
                        case 0:
                            return "Two wheeler      ";
                        case 1:
                            return "Car                       ";
                        case 2:
                            return "Bus                      ";
                        case 3:
                            return "Cycle                   ";
                        case 4:
                            return "Mini Bus              ";
                        case 5:
                            return "Mini truck           ";
                        case 6:
                            return "Handcart                 ";
                        case 7:
                            return "Truck                   ";
                        case 8:
                            return "Auto rickshaw    ";
                    }
                    return "error";
                }


               @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(Speed_check.this, "You clicked " + getname(position) , Toast.LENGTH_SHORT).show();

                    String vehicle = getname(position);
                    if (position1 ==-1 && position2==-1) {
                        position1 = position;

                        Calendar rightnow = Calendar.getInstance();
                        long offset = rightnow.get(Calendar.ZONE_OFFSET) + rightnow.get(Calendar.DST_OFFSET);
                        long since = (rightnow.getTimeInMillis() + offset) % (24 * 60 * 60 * 1000);
                        since1 = since;
                    }
                    else if (position2==-1) {

                        position2 = position;
                        Calendar rightnow = Calendar.getInstance();
                        long offset = rightnow.get(Calendar.ZONE_OFFSET) + rightnow.get(Calendar.DST_OFFSET);
                        long since = (rightnow.getTimeInMillis() + offset) % (24 * 60 * 60 * 1000);
                        since2 = since;


                        if (position1 == position2) {
                            count=count+1;
                            long s = (since2 - since1) / 1000;
                            //Toast.makeText(Speed_check.this,"here"+position1,Toast.LENGTH_SHORT).show();

                            float speed = distance / s;
                            String veh_speed = String.format("%.2f",speed);
                            //String veh_speed_minimised = veh_speed.substring(0, 4);
                            position1 = -1;
                            position2 = -1;
                            try {
                                byte sl[] = Integer.toString(count).getBytes();
                                fileOutputStream.write(sl);
                                fileOutputStream.write(tab);
                                byte veh[] = vehicle.getBytes();
                                fileOutputStream.write(veh);
                                byte vehicle_speed_to_write[] = veh_speed.getBytes();
                                fileOutputStream.write(vehicle_speed_to_write);
                                fileOutputStream.write(nl);

                            } catch (IOException e) {
                                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                            }
                            Toast.makeText(Speed_check.this, getname(position) + " : " + veh_speed, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), getname(position1) + " ignored", Toast.LENGTH_SHORT).show();
                            position1 = position2;
                            position2 = -1;
                        }
                    }


                }
            });

        } catch (Exception e) {
            Toast.makeText(Speed_check.this, e.toString(), Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_volume_count, menu);
        createMenu(menu);
        return true;
    }

    private void createMenu(Menu menu) {
        MenuItem m = menu.add(0, 1, 1, "EXIT");
        {
            m.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        // if (id == R.id.action_settings) {
        //    return true;
        //}
        try {
            fileOutputStream.close();
            Intent j = new Intent(Intent.ACTION_MAIN);
            j.addCategory(Intent.CATEGORY_HOME);
            j.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(j);
            finish();
            System.exit(0);
        } catch (Exception e1) {
            Toast.makeText(getApplicationContext(), e1.toString(), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}

