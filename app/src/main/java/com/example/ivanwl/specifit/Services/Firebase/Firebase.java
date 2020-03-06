package com.example.ivanwl.specifit.Services.Firebase;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.example.ivanwl.specifit.Interfaces.MainCallBack;
import com.example.ivanwl.specifit.Interfaces.RestaurantCallback;
import com.example.ivanwl.specifit.Interfaces.RestaurantsCallback;
import com.example.ivanwl.specifit.Services.Firebase.Models.Dish;
import com.example.ivanwl.specifit.Services.Nutritionix.Models.Food.Food;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;

public class Firebase implements Serializable {
    private Activity context;
    private FirebaseDatabase database;
    private DatabaseReference settingsRef;
    private MainCallBack mainCallBack;
    private RestaurantsCallback restaurantsCallback;
    private DatabaseReference favoriteRestaurantsRef;
    private String username;
    private DatabaseReference mealsRef;
    private HashMap<String, Object> meals;
    private RestaurantCallback restaurantCallback;

    public Firebase(MainCallBack mainCallBack, RestaurantsCallback restaurantsCallback, RestaurantCallback restaurantCallback) {
        this.username = "user1";
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        this.mainCallBack = mainCallBack;
        this.restaurantsCallback = restaurantsCallback;
        this.restaurantCallback = restaurantCallback;
        settingsRef = database.getReference(username).child("Settings");
        favoriteRestaurantsRef = database.getReference(username).child("favoriteRestaurants");
        mealsRef = database.getReference(username).child("meals");
    }

    //  Save Settings key, value pairs in FB
    public void storeSettings(Map<String, Object> settings) {
        for (Map.Entry<String, Object> setting : settings.entrySet()) {
            settingsRef.child(setting.getKey()).setValue(setting.getValue());
        }
    }

    public void retrieveSettings() {
        settingsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                updateSettings(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError e) {
                e.toException().printStackTrace();
            }
        });
    }

    private void updateSettings(DataSnapshot dataSnapshot) {
        HashMap<String, Object> settings = new HashMap<>();
        for (DataSnapshot key : dataSnapshot.getChildren())
            settings.put(key.getKey(), key.getValue());

        mainCallBack.newSettings(settings);
    }

    public void storeFavoriteRestaurants(HashSet<String> restaurantsSet) {
        ArrayList<String> restaurantsList = new ArrayList<>(restaurantsSet);
        favoriteRestaurantsRef.setValue(restaurantsList);
    }

    public void retrieveFavoriteRestaurants() {
        favoriteRestaurantsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                updateFavoriteRestaurants(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError e) {
                e.toException().printStackTrace();
            }
        });
    }

    private void updateFavoriteRestaurants(DataSnapshot dataSnapshot) {
        HashSet<String> favoriteRestaurants = new HashSet<>();
        for (DataSnapshot key : dataSnapshot.getChildren())
            favoriteRestaurants.add(key.getValue(String.class));

        restaurantsCallback.updateFavoriteRestaurants(favoriteRestaurants);
    }

    public void storeMeal(ArrayList<Food> meal, Date time) {
        DatabaseReference currentMealRef = mealsRef.child(time.toString());
        ArrayList<HashMap<String, Object>> newMeal = new ArrayList<>();
        for (Food food : meal) {
            HashMap<String, Object> newFood = new HashMap<>();
            newFood.put("name", food.food_name);
            newFood.put("calories", food.nf_calories);
            newMeal.add(newFood);
        }
        currentMealRef.setValue(newMeal);
    }

    public void retrieveMeals() {
        mealsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                HashMap<Date, ArrayList<Dish>> meals = new HashMap<>();
                GenericTypeIndicator<ArrayList<Dish>> t = new GenericTypeIndicator<ArrayList<Dish>>() {};
                for (DataSnapshot meal : dataSnapshot.getChildren()) {
                    Calendar date = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
                    try {
                        date.setTime(sdf.parse(meal.getKey()));// all done
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    meals.put(date.getTime(), meal.getValue(t));
                }
                restaurantCallback.retrieveMeals(meals);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}