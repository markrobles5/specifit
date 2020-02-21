package com.example.ivanwl.specifit;

import android.content.Intent;
import android.os.Bundle;

import com.example.ivanwl.specifit.Interfaces.RestaurantCallback;
import com.example.ivanwl.specifit.Services.Nutritionix.NutritionixAPI;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.TextView;

public class RestaurantActivity extends AppCompatActivity implements RestaurantCallback {
    private String restaurantName;
    private String restaurantID;
    private NutritionixAPI nutritionix;

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
                goToFoodActivity();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        restaurantName = extras.getString("Restaurant_Name");
        restaurantID = extras.getString("Restaurant_ID");
        setTitle(restaurantName);
        TextView textView = findViewById(R.id.content);
        textView.setText("Restaurant ID: " + restaurantID);

        nutritionix = new NutritionixAPI(this, null, this);
        nutritionix.search(null, restaurantID);
    }

    private void goToFoodActivity() {
        Intent intent = new Intent(this, FoodActivity.class);
        intent.putExtra("Food_ID", "ddee3980ba2c92b252e6a68d");
        startActivity(intent);
    }


    //  TODO
    //  Update Menu Items passed from /search
    @Override
    public void updateListView() {

    }
}
