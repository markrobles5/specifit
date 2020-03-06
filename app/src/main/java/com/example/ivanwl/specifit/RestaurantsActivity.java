package com.example.ivanwl.specifit;

import android.content.Intent;
import android.os.Bundle;

import com.example.ivanwl.specifit.Adapters.RestaurantArrayAdapter;
import com.example.ivanwl.specifit.Interfaces.RestaurantCallback;
import com.example.ivanwl.specifit.Interfaces.RestaurantsCallback;
import com.example.ivanwl.specifit.Services.Firebase.Firebase;
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
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import static com.example.ivanwl.specifit.Utils.Utils.print;


public class RestaurantsActivity extends AppCompatActivity implements RestaurantsCallback {
    private GPS gps;
    private NutritionixAPI nutritionix;
    private Firebase firebase;
    private HashMap<String, Object> settings;
    private HashSet<String> favoriteRestaurants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        favoriteRestaurants = new HashSet<>();
        firebase = new Firebase(null, this, null);
        firebase.retrieveFavoriteRestaurants();
        Bundle extras = getIntent().getExtras();
        settings = (HashMap<String, Object>) extras.getSerializable("Settings");
    }

    @Override
    protected void onResume() {
        super.onResume();
        gps = new GPS(this, this);
        nutritionix = new NutritionixAPI(this, this, null, null);
    }

    @Override
    public void getRestaurants(Double latitude, Double longitude) {
        nutritionix.location(latitude, longitude, 5000, 50);
    }

    //  Restaurant Comparison for sorting restaurantList
    //  Sort by Favorites, then by distance
    private int compareRestaurants(Location r1, Location r2) {
        if (favoriteRestaurants.contains(r1.name) && favoriteRestaurants.contains(r2.name))
            return Double.compare(r1.distance_km, r2.distance_km);
        if (favoriteRestaurants.contains(r1.name))
            return -1;
        if (favoriteRestaurants.contains(r2.name))
            return 1;
        return Double.compare(r1.distance_km, r2.distance_km);
    }

    @Override
    public void updateListView(final ArrayList<Location> restaurants) {
        ListView listView = findViewById(R.id.listview);
        restaurants.sort((r1, r2) -> compareRestaurants(r1, r2));
        RestaurantArrayAdapter adapter = new RestaurantArrayAdapter(this, restaurants, favoriteRestaurants);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                goToRestaurantActivity(restaurants, i);
            }
        });
    }

    @Override
    public void updateFavoriteRestaurants(HashSet<String> favoriteRestaurants) {
        this.favoriteRestaurants = favoriteRestaurants;
    }

    private void goToRestaurantActivity(ArrayList<Location> restaurants, int index) {
        Intent intent = new Intent(this, RestaurantActivity.class);
        intent.putExtra("Restaurant_Name", restaurants.get(index).name);
        intent.putExtra("Restaurant_ID", restaurants.get(index).brand_id);
        intent.putExtra("Favorite_Restaurants", favoriteRestaurants);
        intent.putExtra("Settings", settings);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
