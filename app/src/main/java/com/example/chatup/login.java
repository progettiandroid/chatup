package com.example.chatup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
    public void btclick(View view){
        Intent intent1=new Intent(this,home.class);
        //todo 1 passare dati a mainActivity con putextra
        intent1.putExtra("msg","davide");
        startActivity(intent1);
    }
    public void tvregister(View view){
        Intent intent4=new Intent(this,register.class);
        startActivity(intent4);
    }
}