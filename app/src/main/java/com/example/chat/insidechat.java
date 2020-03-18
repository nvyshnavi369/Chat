package com.example.chat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class insidechat extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insidechat);
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(insidechat.this,home.class);
        super.onBackPressed();
    }
}
