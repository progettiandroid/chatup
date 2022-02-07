package com.example.chatup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }
        public void btrclick(View view){
            Intent intent2=new Intent(this,login.class);
            startActivity(intent2);
        }
        public void tvclick(View view){
            Intent intent3=new Intent(this,login.class);
            startActivity(intent3);
        }
    }

