package com.example.lightsout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Dashboard extends AppCompatActivity {
    public static String movies[]= new String[10];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        final SQLiteDatabase db;
        db=openOrCreateDatabase("LightsOut",MODE_PRIVATE,null);
        db.execSQL("CREATE TABLE IF NOT EXISTS Movies(Name VARCHAR,Crew VARCHAR,Description VARCHAR,Genre VARCHAR,Language VARCHAR,Platform VARCHAR);");
        Intent in=getIntent();
        String e=in.getStringExtra("Email");
        Button lo=findViewById(R.id.logout);
        ImageButton n=findViewById(R.id.netflix);
        ImageButton p=findViewById(R.id.prime);
        ImageButton h=findViewById(R.id.hotstar);
        ImageButton a=findViewById(R.id.all);
        n.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Dashboard.this, platform.class);
                intent.putExtra("platform","Netflix");
                intent.putExtra("Email",e);
                startActivity(intent);
            }
        });
        p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Dashboard.this, platform.class);
                intent.putExtra("platform","Prime");
                intent.putExtra("Email",e);
                startActivity(intent);
            }
        });
        h.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Dashboard.this, platform.class);
                intent.putExtra("platform","Hotstar");
                intent.putExtra("Email",e);
                startActivity(intent);
            }
        });
        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Dashboard.this, platform.class);
                intent.putExtra("platform","all");
                intent.putExtra("Email",e);
                startActivity(intent);
            }
        });

        lo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Dashboard.this, "You have successfully Logged out!", Toast.LENGTH_SHORT).show();
                Intent in=new Intent(Dashboard.this,MainActivity.class);
                startActivity(in);
            }
        });
        int i=0;
        ListView l = findViewById(R.id.rec);
        TextView t=findViewById(R.id.textView15);
        final ViewGroup.LayoutParams[] layoutparams = new ViewGroup.LayoutParams[1];
        Cursor cur = db.rawQuery("SELECT * FROM Count ORDER BY Count DESC Limit 10;", null);
        if (cur.moveToFirst()) {
            do {
                movies[i]=cur.getString(0);
                i=i+1;
            } while (cur.moveToNext());
        }
        final CustomAdapter ca=new CustomAdapter(this,movies);
        l.setAdapter(ca);
        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                System.out.println(movies[position]);
                Intent in=new Intent(Dashboard.this,Movie.class);
                in.putExtra("MovieName",movies[position]);
                       in.putExtra("Email",e);
                startActivity(in);

            }
        });
    }
}