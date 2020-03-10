package com.example.ivanwl.specifit.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ivanwl.specifit.R;
import com.example.ivanwl.specifit.Services.Nutritionix.Models.Location.Location;
import com.example.ivanwl.specifit.Services.Nutritionix.Models.Search.Hit;

import java.util.ArrayList;
import java.util.HashSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DishArrayAdapter extends ArrayAdapter<Hit> {
    private Context context;
    private ArrayList<Hit> foodItems;
    private HashSet<Hit> selectedItems;

    public DishArrayAdapter(Context context, ArrayList<Hit> foodItems, HashSet<Hit> selectedItems) {
        super(context, R.layout.activity_listview, foodItems);
        this.context = context;
        this.foodItems = foodItems;
        this.selectedItems = selectedItems;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.activity_listview, parent, false);
        TextView restaurant = rowView.findViewById(R.id.name);
        ImageView x = rowView.findViewById(R.id.favorite);
        if (selectedItems.contains(foodItems.get(position)))
            x.setImageResource(android.R.drawable.checkbox_on_background);
        else
            x.setImageResource(android.R.drawable.checkbox_off_background);
        restaurant.setText(foodItems.get(position).fields.item_name);
        return rowView;
    }
}
