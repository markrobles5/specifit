package com.example.ivanwl.specifit;

import android.content.Intent;
import android.os.Bundle;

import com.example.ivanwl.specifit.Adapters.DishArrayAdapter;
import com.example.ivanwl.specifit.Interfaces.RestaurantCallback;
import com.example.ivanwl.specifit.Services.Firebase.Firebase;
import com.example.ivanwl.specifit.Services.Firebase.Models.Dish;
import com.example.ivanwl.specifit.Services.Nutritionix.Models.Food.Food;
import com.example.ivanwl.specifit.Services.Nutritionix.Models.Search.Hit;
import com.example.ivanwl.specifit.Services.Nutritionix.NutritionixAPI;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import static com.example.ivanwl.specifit.Utils.Utils.print;

public class RestaurantActivity extends AppCompatActivity implements RestaurantCallback {
    private String restaurantName;
    private String restaurantID;
    private NutritionixAPI nutritionix;
    private Firebase firebase;
    private HashMap<String, Object> settings;
    private HashSet<String> favoriteRestaurants;
    private HashSet<Hit> selectedItems = new HashSet<>();
    private Date dateTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String addText = "Added to Favorites";
                String removeText = "Removed from Favorites";
                if (favoriteRestaurants.contains(restaurantName)) {
                    favoriteRestaurants.remove(restaurantName);
                    fab.setImageResource(android.R.drawable.ic_input_add);
                    Snackbar.make(view, removeText, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    favoriteRestaurants.add(restaurantName);
                    fab.setImageResource(android.R.drawable.ic_delete);
                    Snackbar.make(view, addText, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                firebase.storeFavoriteRestaurants(favoriteRestaurants);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        restaurantName = extras.getString("Restaurant_Name");
        restaurantID = extras.getString("Restaurant_ID");
        favoriteRestaurants = (HashSet<String>) extras.getSerializable("Favorite_Restaurants");
        settings = (HashMap<String, Object>) extras.getSerializable("Settings");
        setTitle(restaurantName);

        TextView textView = findViewById(R.id.content);
        if (favoriteRestaurants.contains(restaurantName))
            fab.setImageResource(android.R.drawable.ic_delete);

        nutritionix = new NutritionixAPI(this, null, this, null);
        nutritionix.search(null, restaurantID);
        firebase = new Firebase(null, null, this);
    }

    private void goToFoodActivity(ArrayList<Hit> foodItems, int index) {
        Intent intent = new Intent(this, FoodActivity.class);
        intent.putExtra("Food_ID", foodItems.get(index)._id);
        intent.putExtra("Settings", settings);
        startActivity(intent);
    }


    //  Update Menu Items passed from /search
    @Override
    public void updateListView(final ArrayList<Hit> foodItems) {
        ListView listView = findViewById(R.id.dishListview);
        DishArrayAdapter adapter = new DishArrayAdapter(this, foodItems, selectedItems);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                goToFoodActivity(foodItems, i);
            }
        });

        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            HashSet<Hit> selectedFoodItems;
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position,
                                                  long id, boolean checked) {
                // Here you can do something when items are selected/de-selected,
                // such as update the title in the CAB
                if (checked) {
                    selectedFoodItems.add(foodItems.get(position));
                    selectedItems.add(foodItems.get(position));
                    adapter.notifyDataSetChanged();
                }
                else {
                    selectedFoodItems.remove(foodItems.get(position));
                    selectedItems.remove(foodItems.get(position));
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                // Respond to clicks on the actions in the CAB
                switch (item.getItemId()) {
                    case R.id.action_add:
                        for (Hit dish: selectedFoodItems){
                            print(dish.fields.item_name);
                        }
                        selectedItems.clear();
                        mode.finish(); // Action picked, so close the CAB
                        return true;
                    default:
                        return false;
                }
            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                // Inflate the menu for the CAB
                MenuInflater inflater = mode.getMenuInflater();
                inflater.inflate(R.menu.context_menu, menu);
                selectedFoodItems = new HashSet<>();
                return true;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                // Here you can make any necessary updates to the activity when
                // the CAB is removed. By default, selected items are deselected/unchecked.
                ArrayList<String> foodIDs = new ArrayList<>();
                for (Hit dish: selectedFoodItems)
                    foodIDs.add(dish.fields.item_id);
                nutritionix.foods(foodIDs);
                selectedFoodItems.clear();
                selectedItems.clear();
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                // Here you can perform updates to the CAB due to
                // an <code><a href="/reference/android/view/ActionMode.html#invalidate()">invalidate()</a></code> request
                return false;
            }
        });
    }

    @Override
    public void storeMeal(ArrayList<Food> meal) {
        this.dateTime = Calendar.getInstance().getTime();
        firebase.storeMeal(meal, this.dateTime);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
