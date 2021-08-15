package com.example.lightsout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Movie extends AppCompatActivity {
    String e;
    String movieName;
    public static String reviews[];
    public static int ratings[];
    public static String author[];
    private RecyclerView reviewRV;
    private ArrayList<reviewModel> reviewModelArrayList;
    public static int c=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        Intent in=getIntent();
        e=in.getStringExtra("Email");
        movieName=in.getStringExtra("MovieName");
        System.out.println("Email"+e);
        TextView mn=findViewById(R.id.movname);
        mn.setText(movieName);
        System.out.println(movieName);
        reviewRV = findViewById(R.id.reviews);
        SQLiteDatabase db;
        db=openOrCreateDatabase("LightsOut",MODE_PRIVATE,null);
        db.execSQL("CREATE TABLE IF NOT EXISTS Count(Name VARCHAR,Count INT);");
        try {
            Cursor c = db.rawQuery("SELECT * FROM Count where Name = '" +movieName+"';", null);
            if(c.getCount()!=0)
            {
                db.execSQL("UPDATE Count set count=count+1 where Name='"+movieName+"';");
                Cursor resultSet = db.rawQuery("Select * from Count;",null);
                resultSet.moveToFirst();
                c.close();
            }
            else
            {
                db.execSQL("INSERT INTO Count VALUES('"+movieName+"',1);");
                Cursor resultSet = db.rawQuery("Select * from Count;",null);
                resultSet.moveToFirst();
                resultSet.close();
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        Cursor cur = db.rawQuery("SELECT * FROM Movies where Name = '" + movieName + "';", null);
        if (cur.moveToFirst()) {
            do {
                String d=cur.getString(2);
                String p=cur.getString(5);
                String l=cur.getString(4);
                String c=cur.getString(1);
                String genre= cur.getString(3);
                TextView desc=findViewById(R.id.about);
                TextView plat=findViewById(R.id.platform);
                TextView lang=findViewById(R.id.language);
                TextView gen=findViewById(R.id.movgenre);
                TextView ca=findViewById(R.id.cast);
                desc.setText("Description : \n"+d+"\n");
                plat.setText("Platform : "+p+"\n");
                lang.setText("Language : "+l+"\n");
                gen.setText("Genre : "+genre+"\n");
                ca.setText("Cast and Crew: \n"+c+"\n");

            } while (cur.moveToNext());
        }
        int i=0;
        Cursor c = db.rawQuery("SELECT * FROM Reviews where Name = '" + movieName +"';", null);
        reviews=new String[c.getCount()];
        ratings=new int[c.getCount()];
        author=new String[c.getCount()];
        System.out.println("SELECT * FROM Reviews where Name = '" + movieName + "';");
        reviewModelArrayList = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM Reviews where Name = '" + movieName + "';", null);
            if (cursor.getCount() != 0) {
                if (cursor.moveToFirst()) {
                    do {
                        reviewModelArrayList.add(new reviewModel(cursor.getString(2),cursor.getInt(3),"By:  \n"+cursor.getString(1)));
                        reviews[i] = cursor.getString(2);
                        ratings[i]=cursor.getInt(3);
                        author[i]=cursor.getString(1);
                        System.out.println(author[i]);
                        i=i+1;
                    } while (cursor.moveToNext());
                }

            } else {
                reviewModelArrayList.add(new reviewModel("No Reviews Yet!",0,"By:  \n None"));
            }
        System.out.println(reviews.length);
        reviewAdapter reviewAdapter = new reviewAdapter(this, reviewModelArrayList);

        // below line is for setting a layout manager for our recycler view.
        // here we are creating vertical list so we will provide orientation as vertical
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        // in below two lines we are setting layoutmanager and adapter to our recycler view.
        reviewRV.setLayoutManager(linearLayoutManager);
        reviewRV.setAdapter(reviewAdapter);
        FloatingActionButton b=findViewById(R.id.back);
        FloatingActionButton a=findViewById(R.id.add);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Movie.this,Dashboard.class);
                i.putExtra("Email",e);
                startActivity(i);
            }
        });
        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inte=new Intent(Movie.this,AddReview.class);
                inte.putExtra("Email",e);
                inte.putExtra("MovieName",movieName);
                startActivity(inte);
            }
        });
        Button lo=findViewById(R.id.logout);
        lo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Movie.this,index.class);
                startActivity(i);
            }
        });
    }
}