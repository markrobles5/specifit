package com.example.ivanwl.specifit.Interfaces;

import com.example.ivanwl.specifit.Services.Nutritionix.Models.Location.Location;

import java.util.ArrayList;

public interface RestaurantsCallback {
    void getRestaurants(Double lattitude, Double longitude);
    void updateListView(ArrayList<Location> restaurants);
}
