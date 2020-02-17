package com.example.ivanwl.specifit;

import android.os.Bundle;

import com.example.ivanwl.specifit.Interfaces.RestaurantCallback;
import com.example.ivanwl.specifit.Services.Location.GPS;
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
import static com.example.ivanwl.specifit.Utils.Utils.print;


public class RestaurantsActivity extends AppCompatActivity implements RestaurantCallback {
    GPS gps;
    NutritionixAPI nutritionix;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        gps = new GPS(this, this);
        nutritionix = new NutritionixAPI(this, this);
    }

    @Override
    public void getRestaurants(Double lattitude, Double longitude) {
        nutritionix.location(lattitude, longitude, 5000, 50);
    }

    @Override
    public void updateListView(ArrayList<String> restaurants) {
        ListView listView = findViewById(R.id.listview);
        ArrayAdapter adapter = new ArrayAdapter<>(this,
                R.layout.activity_listview, restaurants);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                print("Click");
            }
        });
    }

}
