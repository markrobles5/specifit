package com.example.ivanwl.specifit.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.ivanwl.specifit.R;
import com.example.ivanwl.specifit.Services.Nutritionix.Models.Location.Location;

import java.util.ArrayList;
import java.util.HashSet;

import static com.example.ivanwl.specifit.Utils.Utils.print;

public class RestaurantArrayAdapter extends ArrayAdapter<Location> {
    private Context context;
    private ArrayList<Location> restaurants;
    private HashSet<String> favoriteRestaurants;
    private HashSet<String> visitedRestaurants;

    public RestaurantArrayAdapter(Context context, ArrayList<Location> restaurants, HashSet<String> favoriteRestaurants, HashSet<String> visitedRestaurants) {
        super(context, R.layout.activity_listview, restaurants);
        this.context = context;
        this.restaurants = restaurants;
        this.favoriteRestaurants = favoriteRestaurants;
        this.visitedRestaurants = visitedRestaurants;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.activity_listview, parent, false);
        ImageView favorite = rowView.findViewById(R.id.favorite);
        if (visitedRestaurants.contains(restaurants.get(position).name))
            favorite.setImageResource(android.R.drawable.star_big_off);
        if (favoriteRestaurants.contains(restaurants.get(position).name))
            favorite.setImageResource(android.R.drawable.btn_star_big_on);
        TextView restaurant = rowView.findViewById(R.id.name);
        TextView distance = rowView.findViewById(R.id.distance);
        restaurant.setText(restaurants.get(position).name);
        distance.setText(Math.round(restaurants.get(position).distance_km * 0.621371 * 100.0) / 100.0 + "mi");
        return rowView;
    }
}
