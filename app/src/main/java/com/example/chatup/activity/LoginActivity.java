package com.example.chatup.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.chatup.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    EditText mNomeutente;
    EditText mpassword;
    private static final String TAG = LoginActivity.class.getSimpleName();
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {
        //todo se utente è loggato
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String email = user.getEmail();
            Intent intent1 = new Intent(this, HomeActivity.class);
            intent1.putExtra("msg", user.getDisplayName());
            finish();
            startActivity(intent1);
        }
    }

    public void btclick(View view) {
        Log.d("LoginActivity", "LoginActivity button click");
        mNomeutente = (EditText) findViewById(R.id.memail);
        mpassword = (EditText) findViewById(R.id.epassword);

        String nomeutente = mNomeutente.getText().toString();
        String password = mpassword.getText().toString();
        Log.d("LoginActivity", "nomeutente");
        Log.d("LoginActivity", "password");

        if (!(mNomeutente.length() > 7 || !nomeutente.contains("@"))) {
            Toast.makeText(this, "email non valida", Toast.LENGTH_LONG).show();
            return;
        } else if (!(password.length() > 7)) {
            Toast.makeText(this, "password non valida", Toast.LENGTH_LONG).show();
            return;
        } else {
            loginuser(nomeutente, password);
        }

    }


    private void loginuser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.e(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    public void tvregister(View view) {
        Intent intent4 = new Intent(this, RegisterActivity.class);
        finish();
        startActivity(intent4);

    }
}