package com.example.ivanwl.specifit.Interfaces;

import com.example.ivanwl.specifit.Services.Nutritionix.Models.Food.Food;
import com.example.ivanwl.specifit.Services.Nutritionix.Models.Search.Hit;

import java.util.ArrayList;

//  Interface Callback methods for services
//  to return data to Restaurant Activity
public interface FoodCallback {
    void updateTextViews(Food food);
}
