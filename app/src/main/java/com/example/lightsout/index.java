package com.example.lightsout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class index extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        SQLiteDatabase db;
        db = openOrCreateDatabase("LightsOut", MODE_PRIVATE, null);
        Button r = (Button) findViewById(R.id.register);
        Button l=(Button) findViewById(R.id.login);
        r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(index.this, Register.class);
                startActivity(in);
            }
        });
        l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(index.this, Login.class);
                startActivity(intent);
            }
        });
    }
}