package com.example.ivanwl.specifit.Interfaces;

import com.example.ivanwl.specifit.Services.Nutritionix.Models.Food.Food;
import com.example.ivanwl.specifit.Services.Nutritionix.Models.Search.Hit;

import java.util.ArrayList;

//  Interface Callback methods for services
//  to return data to Restaurant Activity
public interface RestaurantCallback {
    void updateListView(final ArrayList<Hit> restaurants);

    void storeMeal(ArrayList<Food> meal);
}
