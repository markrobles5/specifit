package com.example.ivanwl.specifit;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import com.example.ivanwl.specifit.Services.Firebase.Firebase;

import java.util.HashMap;
import java.util.Map;

import static com.example.ivanwl.specifit.Utils.Utils.print;


public class SettingsActivity extends AppCompatActivity {
    private Firebase firebase;
    private HashMap<String, Object> settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        Bundle bundle = getIntent().getExtras();
        settings = (HashMap<String, Object>) bundle.getSerializable("Settings");
        //get a handle on preferences that require validation
        firebase = new Firebase(null, null);
        
        SharedPreferences prefs =
                PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("Height", settings.get("Height").toString());
        editor.putString("Weight", settings.get("Weight").toString());
        editor.putString("sex", settings.get("sex").toString());
        editor.putString("BirthDay", settings.get("BirthDay").toString());
        editor.putString("Activity", settings.get("Activity").toString());
        editor.putString("Goal", settings.get("Goal").toString());
        editor.apply();
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        Preference.OnPreferenceChangeListener numberCheckListener = new Preference.OnPreferenceChangeListener() {

            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                //Check that the string is an integer.
                return numberCheck(preference, newValue);
            }
        };

        private boolean numberCheck(Object key, Object newValue) {
            String text = newValue.toString().replace("\n","");
            try {
                Double.parseDouble(text);
            } catch (NumberFormatException e) {
                Toast toast = Toast.makeText(getContext(), "Invalid Input", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER|Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();
                return false;
            }
            return true;
        }

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
            //get a handle on preferences that require validation
            getPreferenceScreen().findPreference("Height").setOnPreferenceChangeListener(numberCheckListener);
            //getPreferenceScreen().findPreference("Height").setDefaultValue();
            getPreferenceScreen().findPreference("Weight").setOnPreferenceChangeListener(numberCheckListener);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                super.onBackPressed();
                // updates settings to map
                SharedPreferences prefs =
                        PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("Height", prefs.getString("Height", "DEFAULT")
                        .replace("\n",""));
                editor.putString("Weight", prefs.getString("Weight", "DEFAULT")
                        .replace("\n",""));
                editor.apply();
                firebase.storeSettings(mapSettings(prefs.getAll()));
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private Map<String, Object> mapSettings(Map settings) {
        Map<String, Object> map = new HashMap<>(settings);
        return map;
    }
}