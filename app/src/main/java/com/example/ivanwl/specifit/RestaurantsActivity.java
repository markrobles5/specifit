package com.example.ivanwl.specifit;

import android.content.Intent;
import android.os.Bundle;

import com.example.ivanwl.specifit.Adapters.RestaurantArrayAdapter;
import com.example.ivanwl.specifit.Interfaces.RestaurantCallback;
import com.example.ivanwl.specifit.Interfaces.RestaurantsCallback;
import com.example.ivanwl.specifit.Services.Location.GPS;
import com.example.ivanwl.specifit.Services.Nutritionix.Models.Location.Location;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ivanwl.specifit.Services.Nutritionix.NutritionixAPI;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.example.ivanwl.specifit.Utils.Utils.print;


public class RestaurantsActivity extends AppCompatActivity implements RestaurantsCallback {
    private GPS gps;
    private NutritionixAPI nutritionix;
    private HashMap<String, Object> settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        gps = new GPS(this, this);
        nutritionix = new NutritionixAPI(this, this, null, null);
        Bundle extras = getIntent().getExtras();
        settings = (HashMap<String, Object>) extras.getSerializable("Settings");
    }

    @Override
    public void getRestaurants(Double lattitude, Double longitude) {
        nutritionix.location(lattitude, longitude, 5000, 50);
    }

    @Override
    public void updateListView(final ArrayList<Location> restaurants) {
        ListView listView = findViewById(R.id.listview);
        RestaurantArrayAdapter adapter = new RestaurantArrayAdapter(this, restaurants);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                goToRestaurantActivity(restaurants, i);
            }
        });
    }

    private void goToRestaurantActivity(ArrayList<Location> restaurants, int index) {
        Intent intent = new Intent(this, RestaurantActivity.class);
        intent.putExtra("Restaurant_Name", restaurants.get(index).name);
        intent.putExtra("Restaurant_ID", restaurants.get(index).brand_id);
        intent.putExtra("Settings", settings);
        startActivity(intent);
    }
}
