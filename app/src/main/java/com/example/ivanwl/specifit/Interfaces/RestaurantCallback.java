package com.example.ivanwl.specifit.Interfaces;

import com.example.ivanwl.specifit.Services.Nutritionix.Models.Location.Location;

import java.util.ArrayList;


//  Interface Callback methods for services
//  to return data to Restaurant Activity
public interface RestaurantCallback {
    void getRestaurants(Double lattitude, Double longitude);
    void updateListView(ArrayList<Location> restaurants);
}
