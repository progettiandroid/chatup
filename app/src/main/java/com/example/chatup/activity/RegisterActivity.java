package com.example.chatup.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.chatup.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class RegisterActivity extends AppCompatActivity {
    static final String CHAT_PREFS = "ChatPrefs";
    static final String NOME_KEY = "USERNAME";
    EditText mconfermapassword;
    EditText kemail;
    EditText kpassword;
    EditText mnome;

    private FirebaseAuth mAuth;

    private static final String TAG = RegisterActivity.class.getSimpleName();

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initUI();


        mAuth = FirebaseAuth.getInstance();
    }

    private void initUI() {
        kemail = (EditText) findViewById(R.id.memail);
        kpassword = (EditText) findViewById(R.id.epassword);
        mconfermapassword = (EditText) findViewById(R.id.confpassword);
        mnome = (EditText) findViewById(R.id.enome);
    }

    private void createFirebaseUser(String email, String password, final String nome) {


        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.i(TAG, "createUserWithEmail:success");
                            Toast.makeText(RegisterActivity.this, "Authentication success.",
                                    Toast.LENGTH_SHORT).show();
                            setNome(nome);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.e(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }


    public void btrclick(View view) {
        String nome = mnome.getText().toString();
        String email = kemail.getText().toString();
        String password = kpassword.getText().toString();
        if (!nomevalido(nome))
            Toast.makeText(getApplicationContext(), "nome non valido", Toast.LENGTH_SHORT).show();
        else if (!emailvalida(email)) {
            Toast.makeText(getApplicationContext(), "email non valida", Toast.LENGTH_SHORT).show();
        } else if (!passwordvalida(password))
            Toast.makeText(getApplicationContext(), "password non valida", Toast.LENGTH_SHORT).show();


        createFirebaseUser(email, password, nome);
    }

    public void tvclick(View view) {
        Intent intent3 = new Intent(this, LoginActivity.class);
        finish();
        startActivity(intent3);
    }

    private boolean nomevalido(String nome) {
        if (nome.length() > 3)
            return true;
        else
            return false;
    }

    private boolean emailvalida(String email) {
        return email.contains("@");
    }

    private boolean passwordvalida(String password) {
        String confermapassword = mconfermapassword.getText().toString();
        return confermapassword.equals(password) && password.length() > 7;
    }

    private void setNome(String nome) {
        FirebaseUser user = mAuth.getCurrentUser();
        UserProfileChangeRequest changeRequest = new UserProfileChangeRequest.Builder()
                .setDisplayName(nome)
                .build();
        user.updateProfile(changeRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.i(TAG, "nome caricato con successo");
                } else {
                    Log.e(TAG, "errore", task.getException());
                }

                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                finish();
                startActivity(intent);
            }
        })

        ;
    }

}







