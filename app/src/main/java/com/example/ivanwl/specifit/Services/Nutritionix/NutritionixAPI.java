package com.example.ivanwl.specifit.Services.Nutritionix;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ivanwl.specifit.Interfaces.FoodCallback;
import com.example.ivanwl.specifit.Interfaces.RestaurantCallback;
import com.example.ivanwl.specifit.Interfaces.RestaurantsCallback;
import com.example.ivanwl.specifit.R;
import com.example.ivanwl.specifit.Services.Nutritionix.Models.Food.Food;
import com.example.ivanwl.specifit.Services.Nutritionix.Models.Food.Foods;
import com.example.ivanwl.specifit.Services.Nutritionix.Models.Location.Location;
import com.example.ivanwl.specifit.Services.Nutritionix.Models.Location.Locations;
import com.example.ivanwl.specifit.Services.Nutritionix.Models.Search.Field;
import com.example.ivanwl.specifit.Services.Nutritionix.Models.Search.Hit;
import com.example.ivanwl.specifit.Services.Nutritionix.Models.Search.Search;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import static com.example.ivanwl.specifit.Utils.Utils.print;



public class NutritionixAPI {
//    final private String APP_KEY = "c357b6ce5147c83e6044ecc59ac56027";
//    final private String APP_ID = "548c69f5";
    final private String APP_KEY = "cd40afcf4306dcf1582ccf87b566b9ae";
    final private String APP_ID = "ae956aa2";
    private Activity context;
    private RestaurantsCallback restaurantsCallback;
    private RestaurantCallback restaurantCallback;
    private FoodCallback foodCallback;

    public NutritionixAPI(Activity context, RestaurantsCallback restaurantsCallback, RestaurantCallback restaurantCallback, FoodCallback foodCallback) {
        this.context = context;
        this.restaurantsCallback = restaurantsCallback;
        this.restaurantCallback = restaurantCallback;
        this.foodCallback = foodCallback;
    }

    public void search(String query, String restaurantID) {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            String postURL = "https://api.nutritionix.com/v1_1/search";
            JSONObject jsonBody = new JSONObject();
            JSONObject restaurant = new JSONObject();
            restaurant.put("brand_id", restaurantID);
            JSONObject calories = new JSONObject();
            calories.put("from", "200");
            calories.put("to", "2000");
            restaurant.put("nf_calories", calories);
            jsonBody.put("appId", APP_ID);
            jsonBody.put("appKey", APP_KEY);
            jsonBody.put("offset", "0");
            jsonBody.put("limit", "50");
            if (query != null)
                jsonBody.put("query", query);
            if (restaurantID != null)
                jsonBody.put("filters", restaurant);

            JsonObjectRequest jsonRequest = new JsonObjectRequest(postURL, jsonBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Search model = new Search();
                    Gson gson = new Gson();
                    model = gson.fromJson(response.toString(),Search.class);
                    search(model);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError e) {
                    e.printStackTrace();
                }
            });

            requestQueue.add(jsonRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //  do something with menu items from restaurant
    private void search(Search model) {
        ArrayList<Hit> foodItems = new ArrayList<>();
        for (Hit x : model.hits) {
            foodItems.add(x);
        }
        
        //  pass what you need in this function, then complete
        //  this function in Restaurant activity
        restaurantCallback.updateListView(foodItems);
    }

    public void location(double latitude, double longitude, int distance, int limit) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("trackapi.nutritionix.com")
                .appendPath("v2")
                .appendPath("locations")
                .appendQueryParameter("ll", Double.toString(latitude) + "," + Double.toString(longitude))
                .appendQueryParameter("distance", Integer.toString(distance) + "m")
                .appendQueryParameter("limit", Integer.toString(limit));
        String getURL = builder.build().toString();

        JsonObjectRequest jsonRequest = new JsonObjectRequest
                (Request.Method.GET, getURL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Locations model = new Locations();
                Gson gson = new Gson();
                model = gson.fromJson(response.toString(),Locations.class);
                location(model);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                e.printStackTrace();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("x-app-id", APP_ID);
                headers.put("x-app-key", APP_KEY);
                return headers;
            }
        };
        requestQueue.add(jsonRequest);
    }

    private void location(Locations model) {
        ArrayList<Location> restaurants = new ArrayList<>();
        HashSet<String> restaurantSet = new HashSet<>();
        for (Location x : model.locations) {
            if (!restaurantSet.contains(x.name)) {
                restaurants.add(x);
                restaurantSet.add(x.name);
            }
        }

        //  Callback goes back to Restaurant Activity
        //  to update ListView
        restaurantsCallback.updateListView(restaurants);
    }

    public void food(String foodID) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("trackapi.nutritionix.com")
                .appendPath("v2")
                .appendPath("search")
                .appendPath("item")
                .appendQueryParameter("nix_item_id", foodID);
        String getURL = builder.build().toString();

        JsonObjectRequest jsonRequest = new JsonObjectRequest
                (Request.Method.GET, getURL, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Foods model = new Foods();
                        Gson gson = new Gson();
                        model = gson.fromJson(response.toString(),Foods.class);
                        food(model);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError e) {
                        e.printStackTrace();
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("x-app-id", APP_ID);
                headers.put("x-app-key", APP_KEY);
                return headers;
            }
        };
        requestQueue.add(jsonRequest);
    }

    //  do something with nutrients for specific food
    private void food(Foods model) {
        //print(Integer.toString(model.foods[0].nf_calories));
        foodCallback.updateTextViews(model.foods[0]);
    }

    public void foods(ArrayList<String> foodIDs) {
        ArrayList<Food> foodsList = new ArrayList<>();
        for (String foodID : foodIDs) {
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            Uri.Builder builder = new Uri.Builder();
            builder.scheme("https")
                    .authority("trackapi.nutritionix.com")
                    .appendPath("v2")
                    .appendPath("search")
                    .appendPath("item")
                    .appendQueryParameter("nix_item_id", foodID);
            String getURL = builder.build().toString();

            JsonObjectRequest jsonRequest = new JsonObjectRequest
                    (Request.Method.GET, getURL, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Foods model = new Foods();
                            Gson gson = new Gson();
                            model = gson.fromJson(response.toString(), Foods.class);
                            foodsList.add(model.foods[0]);
                            if (foodsList.size() == foodIDs.size())
                                meal(foodsList);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError e) {
                            e.printStackTrace();
                        }
                    }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("x-app-id", APP_ID);
                    headers.put("x-app-key", APP_KEY);
                    return headers;
                }
            };
            requestQueue.add(jsonRequest);
        }
    }

    private void meal(ArrayList<Food> foodsList) {
        restaurantCallback.storeMeal(foodsList);
    }
}
