package com.example.ivanwl.specifit;

import android.content.Intent;
import android.os.Bundle;

import com.example.ivanwl.specifit.Adapters.DishArrayAdapter;
import com.example.ivanwl.specifit.Adapters.RestaurantArrayAdapter;
import com.example.ivanwl.specifit.Interfaces.RestaurantCallback;
import com.example.ivanwl.specifit.Services.Nutritionix.Models.Search.Hit;
import com.example.ivanwl.specifit.Services.Nutritionix.NutritionixAPI;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import static com.example.ivanwl.specifit.Utils.Utils.print;

public class RestaurantActivity extends AppCompatActivity implements RestaurantCallback {
    private String restaurantName;
    private String restaurantID;
    private NutritionixAPI nutritionix;
    private HashMap<String, Object> settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                //goToFoodActivity();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        restaurantName = extras.getString("Restaurant_Name");
        restaurantID = extras.getString("Restaurant_ID");
        settings = (HashMap<String, Object>) extras.getSerializable("Settings");
        setTitle(restaurantName);
        TextView textView = findViewById(R.id.content);
        //textView.setText("Restaurant ID: " + restaurantID);

        nutritionix = new NutritionixAPI(this, null, this, null);
        nutritionix.search(null, restaurantID);
    }

    private void goToFoodActivity(ArrayList<Hit> foodItems, int index) {
        Intent intent = new Intent(this, FoodActivity.class);
        intent.putExtra("Food_ID", foodItems.get(index)._id);
        intent.putExtra("Settings", settings);
        startActivity(intent);
    }


    //  Update Menu Items passed from /search
    @Override
    public void updateListView(final ArrayList<Hit> foodItems) {
        ListView listView = findViewById(R.id.dishListview);
        DishArrayAdapter adapter = new DishArrayAdapter(this, foodItems);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                goToFoodActivity(foodItems, i);
            }
        });
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
