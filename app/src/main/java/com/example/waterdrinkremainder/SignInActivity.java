package com.example.waterdrinkremainder;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.se.omapi.Session;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import static android.content.ContentValues.TAG;

public class SignInActivity extends AppCompatActivity{

    private GoogleSignInClient mGoogleSignInClient;
    private int RC_SIGN_IN = 0;
    private SharedPreferences pref_settings;
    Intent intent;
    private ProgressDialog mProgress;
    private SignInButton sign_in_button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        sign_in_button = findViewById(R.id.sign_in_button);
        pref_settings = getSharedPreferences("pref_settings", MODE_PRIVATE);
        intent = new Intent(SignInActivity.this, MainActivity.class);
        mProgress = new ProgressDialog(this);
        mProgress.setTitle("Procesare...");
        mProgress.setMessage("Așteptați...");
        mProgress.setCancelable(false);
        mProgress.setIndeterminate(true);
        if (pref_settings.getBoolean("isAuth", false))
        startActivity(intent);
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build();
            mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
            sign_in_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.sign_in_button:
                            signIn();
                            mProgress.show();
                            break;
                        // ...
                    }
                }
            });
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            SharedPreferences.Editor editor = pref_settings.edit();
            editor.putBoolean("isAuth", true);
            editor.apply();
            startActivity(intent);
            mProgress.dismiss();
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "Eroare: " + e.getStatusCode());
        }
    }
}
