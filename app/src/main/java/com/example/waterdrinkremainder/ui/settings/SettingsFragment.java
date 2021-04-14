package com.example.waterdrinkremainder.ui.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.example.waterdrinkremainder.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class SettingsFragment extends PreferenceFragmentCompat {
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        getPreferenceManager().setSharedPreferencesName("pref_settings");
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
        Preference button = findPreference(getString(R.string.button_sign_out));
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);
        button.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                signOut();
                return false;
            }
        });
    }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        getActivity().finish();
                        SharedPreferences pref_settings = getActivity().getSharedPreferences("pref_settings", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref_settings.edit();
                        editor.putBoolean("isAuth", false);
                        editor.apply();
                    }
                });
    }
}