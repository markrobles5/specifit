package com.example.ivanwl.specifit;

import android.content.Intent;
import android.os.Bundle;

import com.example.ivanwl.specifit.Interfaces.MainCallBack;
import com.example.ivanwl.specifit.Services.Firebase.Firebase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.HashMap;
import java.util.Map;

import static com.example.ivanwl.specifit.Utils.Utils.print;


public class MainActivity extends AppCompatActivity implements MainCallBack {
    private Firebase firebase;
    //  This settings map will be passed down to child activities
    private HashMap<String, Object> settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Drop down menu to select diets. Can be copied and modified to be used for other needs.
        Spinner mySpinner = findViewById(R.id.spinner1);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<>(MainActivity.this,
                android.R.layout.simple_expandable_list_item_1,
                getResources().getStringArray(R.array.diets));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);
        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object item = parent.getItemAtPosition(position);
                //print(item.toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        final TextView textView = findViewById(R.id.text_id);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                Snackbar.make(view, "Location Updated", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
                 */
                goToRestaurants();
            }
        });

        setupServices();
    }

    private void goToRestaurants(){
        Intent intent = new Intent(this, RestaurantsActivity.class);
        intent.putExtra("Settings", settings);
        startActivity(intent);
    }

    private void setupServices() {
        firebase = new Firebase(this, null);
        firebase.retrieveSettings();
    }

    @Override
    public void newSettings(HashMap<String, Object> settings) {
        //  Values can be null
        //  Replace old settings map with new settings map
        this.settings = new HashMap<>(settings);

        //  TODO
        //  Settings Updated, do something in Main Activity
        //  This is called everytime on a setting update
        for (Map.Entry<String, Object> setting : settings.entrySet())
            print(setting.getKey() + ": " + setting.getValue());
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
            //  Build Intent and go to Settings Activity
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
