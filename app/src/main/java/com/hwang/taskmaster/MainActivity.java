package com.hwang.taskmaster;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.TextView;


import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import java.util.Arrays;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {


  /**
   * TODO refactor this into a file to reference and incremement in the future if necessary
   */
  private static final int RC_SIGN_IN = 3742;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

  }


  public void onSignInButtonPressed(View v){
    List<AuthUI.IdpConfig> providers = Arrays.asList(
            new AuthUI.IdpConfig.EmailBuilder().build(),
            new AuthUI.IdpConfig.GoogleBuilder().build());

// Create and launch sign-in intent
    startActivityForResult(
            AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(providers)
                    .build(),
            RC_SIGN_IN);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (requestCode == RC_SIGN_IN) {
      IdpResponse response = IdpResponse.fromResultIntent(data);

      if (resultCode == RESULT_OK) {
        // Successfully signed in
        Intent intent = new Intent(this, ProjectActivity.class);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String displayName = user.getDisplayName();
        TextView welcomeView = findViewById(R.id.welcomView);
        intent.putExtra("DISPLAY_NAME" , displayName);
        welcomeView.setText(displayName);
        startActivity(intent);
        // ...
      } else {
        // Sign in failed. If response is null the user canceled the
        // sign-in flow using the back button. Otherwise check
        // response.getError().getErrorCode() and handle the error.
        // ...
      }
    }
  }


}
