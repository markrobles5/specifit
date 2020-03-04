package com.example.ivanwl.specifit.Interfaces;

import com.example.ivanwl.specifit.Services.Firebase.Models.Dish;
import com.example.ivanwl.specifit.Services.Nutritionix.Models.Food.Food;
import com.example.ivanwl.specifit.Services.Nutritionix.Models.Search.Hit;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

//  Interface Callback methods for services
//  to return data to Restaurant Activity
public interface RestaurantCallback {
    void updateListView(final ArrayList<Hit> restaurants);

    void storeMeal(ArrayList<Food> meal);

    void retrieveMeals(HashMap<Date, ArrayList<Dish>> meals);
}
