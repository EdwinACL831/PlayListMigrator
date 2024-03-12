package com.example.playlistmigrator.auth;

import static com.example.playlistmigrator.tracksselection.TrackSelectionActivity.SELECTED_TRACKS_KEY;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.ComponentActivity;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.playlistmigrator.R;
import com.example.playlistmigrator.common.ConfigPropertiesManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.Objects;

public class SelectDestinationActivity extends ComponentActivity {
    private static final String TAG = SelectDestinationActivity.class.getSimpleName();
    private static final int RC_SIGN_IN = 2;
    private final Context context = SelectDestinationActivity.this;
    private SignInButton googleBtn;
    private GoogleSignInClient googleSignInClient;
    private Button singOutGoogle;

    /**
     * In this method we do the OAuth2.0 for google APIs, specially for Youtube Data API.
     * this authentication is necessary in order to use APIs that modify/create/update any user's account.
     *
     * In that authentication request, there are some Scopes also necessary to perform the intended actions
     * described before.
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_destination_activity);
        singOutGoogle = findViewById(R.id.sign_out_from_google_btn);
        googleBtn = findViewById(R.id.google_sign_in);
        googleBtn.setSize(SignInButton.SIZE_STANDARD);
        googleBtn.setVisibility(View.INVISIBLE);
        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestId()
                .requestIdToken(ConfigPropertiesManager.getInstance(context).getGoogleClientId())
                .requestScopes(new Scope("https://www.googleapis.com/auth/youtube.force-ssl"))
                .requestScopes(new Scope("https://www.googleapis.com/auth/youtubepartner"))
                .requestScopes(new Scope("https://www.googleapis.com/auth/youtube")) // Request YouTube scopes
                .build();
        googleSignInClient = GoogleSignIn.getClient(context, signInOptions);

        /* If this button ins clicked, it will start the singIn() method
         * That basically is for sending a signal to the activity to start
         * the ForResult lifecycle method (is deprecated a really big thing)
         */
        googleBtn.setOnClickListener(v -> signIn());
        singOutGoogle.setOnClickListener(v -> signOut());
    }

    private void signOut() {
        googleSignInClient.signOut().addOnCompleteListener((Activity) context, task -> {
            if(task.isComplete() && task.isSuccessful()) {
                Log.d(TAG, "user signed out from their Google's account");
                Toast.makeText(context, "User signed out!", Toast.LENGTH_SHORT).show();
                updateUI(null);
            }
        });
    }

    private void signIn() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    /**
     * Checks if there is already an active account. If so, then we don't display the button to
     * request a new authentication. n the other hand, if there isn't then we display the google button
     * to authenticate it
     */
    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(context);
        updateUI(account);
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

    /***
     * Here we handle the results of the authentication process
     */
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            if(completedTask.isComplete() && completedTask.isSuccessful()) {
                // Signed in successfully, show authenticated UI.
                updateUI(account);
            }
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }

    private void updateUI(GoogleSignInAccount account) {
        if (null == account) {
            googleBtn.setVisibility(View.VISIBLE);
            singOutGoogle.setEnabled(false);
        } else {
            googleBtn.setVisibility(View.INVISIBLE);
            singOutGoogle.setEnabled(true);
            Log.d(TAG,"user already logged-in");
            Toast.makeText(context, "User logged in", Toast.LENGTH_SHORT).show();
            String tracksSelectedJSON = getIntent().getStringExtra(SELECTED_TRACKS_KEY);
            Log.d(TAG, Objects.requireNonNull(tracksSelectedJSON));
        }
    }
}
