package com.example.ivanwl.specifit;

import android.os.Bundle;

import com.example.ivanwl.specifit.Adapters.DishArrayAdapter;
import com.example.ivanwl.specifit.Interfaces.FoodCallback;
import com.example.ivanwl.specifit.Services.Nutritionix.Models.Food.Food;
import com.example.ivanwl.specifit.Services.Nutritionix.Models.Search.Hit;
import com.example.ivanwl.specifit.Services.Nutritionix.NutritionixAPI;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class FoodActivity extends AppCompatActivity implements FoodCallback {
    NutritionixAPI nutritionix;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        String foodID = extras.getString("Food_ID");

        nutritionix = new NutritionixAPI(this, null, null, this);
        nutritionix.food(foodID);
    }

    @Override
    public void updateTextViews(Food food) {
        TextView foodName = findViewById(R.id.foodName);
        foodName.setText(food.food_name);
        TextView servingSize = findViewById(R.id.servingSize);
        servingSize.setText("Serving size: " + food.serving_qty + food.serving_units + " (" + food.serving_weight_grams + " grams)");
        TextView calories = findViewById(R.id.calories);
        calories.setText("Calories: " + food.nf_calories);
        TextView fat = findViewById(R.id.fat);
        fat.setText("Fat: " + Double.toString(food.nf_total_fat));
        TextView cholesterol = findViewById(R.id.cholesterol);
        cholesterol.setText("Cholesterol: " + Double.toString(food.nf_cholesterol));
        TextView sodium = findViewById(R.id.sodium);
        sodium.setText("Sodium: " + food.nf_sodium);
        TextView carbs = findViewById(R.id.carbs);
        carbs.setText("Total Carbohydrates: " + food.nf_total_carbohydrates);
        TextView fiber = findViewById(R.id.fiber);
        fiber.setText("Dietary Fiber: " + food.nf_dietary_fiber);
        TextView sugars = findViewById(R.id.sugars);
        sugars.setText("Sugars: " + food.nf_sugars);
        TextView proteins = findViewById(R.id.proteins);
        proteins.setText("Protein: " + food.nf_proteins);


    }

}
