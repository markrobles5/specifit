package com.example.ivanwl.specifit.Services.Firebase;

import android.app.Activity;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.Map;
import static com.example.ivanwl.specifit.Utils.Utils.print;

public class Firebase implements Serializable {
    private Activity context;
    private FirebaseDatabase database;
    private DatabaseReference settingsRef;

    public Firebase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        settingsRef = database.getReference("Settings");
    }

    //  Save Settings key, value pairs in FB
    public void saveSettings(Map<String, Object> settings) {
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

    //  TODO
    //  Settings has changed, need to update Main Activity
    private void updateSettings(DataSnapshot dataSnapshot) {
        // Values can be null
        print("Entry: " + dataSnapshot.child("KEY").getValue(String.class));
        print("Entry: " + dataSnapshot.child("Hello").getValue(String.class));
    }
}
