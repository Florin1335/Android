package com.example.waterdrinkremainder.ui.history;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.waterdrinkremainder.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Executor;

public class HistoryFragment extends Fragment {
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private SimpleDateFormat dateFormatPref;
    private String date;
    private SharedPreferences pref_history;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_history, container, false);
        /*
        String s = "";
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd/mm");
        try {
            calendar.setTime(dateFormat.parse("08/04/2020"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String dt = dateFormat.format(calendar.getTime());
        s += dt + "\n";
        calendar.add(Calendar.DATE, -2);
        Date resultdate = new Date(calendar.getTimeInMillis());
        String dateInString = dateFormat.format(resultdate);
        s += dateInString;
        tv.setText(s);
        */
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd/MM");
        dateFormatPref = new SimpleDateFormat("dd/MM/YYYY");
        pref_history = getActivity().getSharedPreferences("pref_history", Context.MODE_PRIVATE);
        int media = 0, zile = 7;
        for (int i=1;i<=7;i++){
            TextView tv_data, tv_procent;
            ProgressBar pb;
            int id = root.getResources().getIdentifier("tv" + (i * 2 - 1), "id", getActivity().getPackageName());
            tv_data = root.findViewById(id);
            id = root.getResources().getIdentifier("pb" + i, "id", getActivity().getPackageName());
            pb = root.findViewById(id);
            id = root.getResources().getIdentifier("tv" + (i * 2), "id", getActivity().getPackageName());
            tv_procent = root.findViewById(id);
            tv_data.setText(dateFormat.format(calendar.getTime()));
            int goal = pref_history.getInt(dateFormatPref.format(calendar.getTime()) + "_goal", 0);
            int current = pref_history.getInt(dateFormatPref.format(calendar.getTime()) + "_current", 0);
            media += current;
            if (current == 0)
                zile--;
            pb.setMax(goal);
            pb.setProgress(current);
            if (goal != 0){
                int procent = (current * 100 )/ goal;
                if (procent > 100)
                    procent = 100;
                tv_procent.setText(String.valueOf(procent) + " %");
            }else
                tv_procent.setText("0 %");
            calendar.add(Calendar.DATE, -1);
        }
        if (zile == 0)
         media = 0;
        TextView tv_media = root.findViewById(R.id.history_media);
        tv_media.setText("Media de apă pe zi: " + media + " ML");

        return root;
    }
}