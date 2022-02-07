package com.example.chatup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //todo 2 ricevere i dati da intent login con il metodo getextras()
        Bundle b = getIntent().getExtras();
        String extra = b.getString("msg");

        //todo 3 presentare i dati all utente con un toast
        Toast.makeText(this,"Utente :"+ extra,Toast.LENGTH_LONG).show();

    }
}