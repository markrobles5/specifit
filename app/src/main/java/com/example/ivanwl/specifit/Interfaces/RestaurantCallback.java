package com.example.ivanwl.specifit.Interfaces;

import java.util.ArrayList;


//  Interface Callback methods for services
//  to return data to Restaurant Activity
public interface RestaurantCallback {
    void getRestaurants(Double lattitude, Double longitude);
    void updateListView(ArrayList<String> restaurants);
}
