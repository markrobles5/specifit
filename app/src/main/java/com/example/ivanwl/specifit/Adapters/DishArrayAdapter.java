package com.example.ivanwl.specifit.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ivanwl.specifit.R;
import com.example.ivanwl.specifit.Services.Nutritionix.Models.Location.Location;
import com.example.ivanwl.specifit.Services.Nutritionix.Models.Search.Hit;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DishArrayAdapter extends ArrayAdapter<Hit> {
    private Context context;
    private ArrayList<Hit> foodItems;

    public DishArrayAdapter(Context context, ArrayList<Hit> foodItems) {
        super(context, R.layout.activity_listview, foodItems);
        this.context = context;
        this.foodItems = foodItems;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.activity_listview, parent, false);
        TextView restaurant = rowView.findViewById(R.id.name);
        //TextView distance = rowView.findViewById(R.id.distance);
        restaurant.setText(foodItems.get(position).fields.item_name);
        return rowView;
    }
}
