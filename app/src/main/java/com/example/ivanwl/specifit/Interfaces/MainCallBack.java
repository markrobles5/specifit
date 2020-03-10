package com.example.ivanwl.specifit.Interfaces;


import com.example.ivanwl.specifit.Services.Firebase.Models.Dish;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public interface MainCallBack {
    void newSettings(HashMap<String, Object> settings);
    void retrieveMeals(HashMap<Date, ArrayList<Dish>> meals);
}
