package com.example.waterdrinkremainder.ui.dialog;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.waterdrinkremainder.R;
import com.example.waterdrinkremainder.ui.home.HomeFragment;
import com.google.android.material.textfield.TextInputEditText;

public class DialogFragment extends androidx.fragment.app.DialogFragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =   inflater.inflate(R.layout.fragment_dialog, container, false);
        Button button = view.findViewById(R.id.dialog_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextInputEditText input_varsta = view.findViewById(R.id.input_varsta);
                TextInputEditText input_greutate = view.findViewById(R.id.input_greutate);
                String varsta = input_varsta.getText().toString();
                String greutate = input_greutate.getText().toString();
                if (varsta.length() == 0 || greutate.length() == 0) {
                    if (varsta.length() == 0)
                        input_varsta.setError("Introduceți vârsta.");
                    if (greutate.length() == 0)
                        input_greutate.setError("Introduceți greutatea.");
                }else{
                    SharedPreferences pref_settings = DialogFragment.this.getActivity().getSharedPreferences("pref_settings", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref_settings.edit();
                    editor.putString("settings_varsta", varsta);
                    editor.putString("settings_greutate", greutate);
                    editor.apply();
                    dismiss();
                }

            }
        });
        return view;
    }
}
