package com.example.waterdrinkremainder.ui.home;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.waterdrinkremainder.Notificare;
import com.example.waterdrinkremainder.R;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class HomeFragment extends Fragment {
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;
    private SharedPreferences pref_settings;
    private SharedPreferences pref_history;
    private TextView home_goal;
    private ProgressBar progressBar;
    private Button button_adauga;
    private TextInputEditText input_adauga;
    private int id_notificare = 1;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        pref_settings = getActivity().getSharedPreferences("pref_settings", Context.MODE_PRIVATE);
        pref_history = getActivity().getSharedPreferences("pref_history", Context.MODE_PRIVATE);

        //Titlu
        TextView home_title = root.findViewById(R.id.home_title);
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd/MM/YYYY");
        date = dateFormat.format(calendar.getTime());
        home_title.setText("Apa consumată astăzi, " + date + ".");

        //goal apa
        home_goal = root.findViewById(R.id.home_goal);
        progressBar = root.findViewById(R.id.progressBar);
        calculeaza_goal();

        //apa curenta
        TextView home_current = root.findViewById(R.id.home_current);
        int apa_curenta = pref_history.getInt(date + "_current", 0);
        String home_current_string = "CURRENT WATER: " + Integer.toString(apa_curenta) + " ML";
        home_current.setText(home_current_string);
        progressBar.setProgress(apa_curenta);


        button_adauga = root.findViewById(R.id.button_adauga);
        input_adauga = root.findViewById(R.id.input_adauga);
        Button button_100ml = root.findViewById(R.id.button_100ml);
        Button button_250ml = root.findViewById(R.id.button_250ml);
        Button button_500ml = root.findViewById(R.id.button_500ml);
        Button button_1000ml = root.findViewById(R.id.button_1000ml);
        Button button_1500ml = root.findViewById(R.id.button_1500ml);
        Button button_2000ml = root.findViewById(R.id.button_2000ml);
        button_100ml.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String string = input_adauga.getText().toString();
                if (string.length() == 0) {
                    input_adauga.setText(adauga_apa(null, 100));
                }
                else
                    input_adauga.setText(adauga_apa(input_adauga.getText().toString(), 100));
            }
        });
        button_250ml.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String string = input_adauga.getText().toString();
                if (string.length() == 0) {
                    input_adauga.setText(adauga_apa(null, 250));
                }
                else
                    input_adauga.setText(adauga_apa(input_adauga.getText().toString(), 250));
            }
        });
        button_500ml.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String string = input_adauga.getText().toString();
                if (string.length() == 0) {
                    input_adauga.setText(adauga_apa(null, 500));
                }
                else
                    input_adauga.setText(adauga_apa(input_adauga.getText().toString(), 500));
            }
        });
        button_1000ml.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String string = input_adauga.getText().toString();
                if (string.length() == 0) {
                    input_adauga.setText(adauga_apa(null, 1000));
                }
                else
                    input_adauga.setText(adauga_apa(input_adauga.getText().toString(), 1000));
            }
        });
        button_1500ml.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String string = input_adauga.getText().toString();
                if (string.length() == 0) {
                    input_adauga.setText(adauga_apa(null, 1500));
                }
                else
                    input_adauga.setText(adauga_apa(input_adauga.getText().toString(), 1500));
            }
        });
        button_2000ml.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String string = input_adauga.getText().toString();
                if (string.length() == 0) {
                    input_adauga.setText(adauga_apa(null, 2000));
                }
                else
                    input_adauga.setText(adauga_apa(input_adauga.getText().toString(), 2000));
            }
        });
        button_adauga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String string = input_adauga.getText().toString();
                if (string.length() == 0){
                    input_adauga.requestFocus();
                    input_adauga.setError("Adăugați un număr.");
                }
                else{
                    int apa_curenta = pref_history.getInt(date + "_current", 0);
                    int ml = Integer.parseInt(string);
                    String home_current_string = "CURRENT WATER: " + Integer.toString(apa_curenta + ml) + " ML";
                    home_current.setText(home_current_string);
                    SharedPreferences.Editor editor = pref_history.edit();
                    editor.putInt(date + "_current", apa_curenta + ml);
                    editor.apply();
                    input_adauga.setText("");
                    progressBar.setProgress(apa_curenta + ml);
                    //notificare
                    Intent intent = new Intent(getActivity(), Notificare.class);
                    intent.putExtra("id_notificare", id_notificare);
                    intent.putExtra("mesaj", "Au trecut 3 ore de când ai băut apă ultima dată.");

                    PendingIntent alarmIntent = PendingIntent.getBroadcast(getActivity(), 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

                    AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
                    Calendar timp = Calendar.getInstance();
                    timp.add(Calendar.HOUR, 3);
                    alarmManager.set(AlarmManager.RTC_WAKEUP, timp.getTimeInMillis(), alarmIntent);
                }
            }
        });
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    public void calculeaza_goal () {
        String varsta = pref_settings.getString("settings_varsta", null);
        String greutate = pref_settings.getString("settings_greutate", null);
        if (varsta != null && greutate != null){
            float greutate_float = Float.parseFloat(greutate);
            int varsta_int = Integer.parseInt(varsta);
            int goalWater = (int)(((greutate_float * varsta_int) / 28.3) * 29.5735); // rezultat in ML
            SharedPreferences.Editor editor = pref_history.edit();
            editor.putInt(date + "_goal", goalWater);
            editor.apply();
            String home_goal_string = "WATER GOAL: " + Integer.toString(goalWater) + " ML";
            home_goal.setText(home_goal_string);
            progressBar.setMax(goalWater);
        }
    }

    private String adauga_apa (String string, int i){
        int ml;
        if (string != null)
            ml = Integer.parseInt(string) + i;
        else
            ml = i;
        return Integer.toString(ml);
    }

}