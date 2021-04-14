package com.example.waterdrinkremainder;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences pref_settings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_history, R.id.navigation_settings)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);

        pref_settings = getSharedPreferences("pref_settings", MODE_PRIVATE);
        boolean firstStart = pref_settings.getBoolean("firstStart", true);
        if (firstStart) {
            openDialog();
        }
    }

    private void openDialog(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        DialogFragment dialogFragment = new com.example.waterdrinkremainder.ui.dialog.DialogFragment();
        dialogFragment.show(fragmentManager, "Dialog");
        SharedPreferences.Editor editor = pref_settings.edit();
        editor.putBoolean("firstStart", false);
        editor.apply();
    }

}