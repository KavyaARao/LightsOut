package com.example.lightsout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Language extends AppCompatActivity {
    public static String movies[];
    private RecyclerView moviesRV;
    public String e;
    public static String movieName;
    private ArrayList<movieModel> movieModelArrayList;
    public static int c=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);
        final SQLiteDatabase db;
        db=openOrCreateDatabase("LightsOut",MODE_PRIVATE,null);
        ImageView pl=findViewById(R.id.langplat);
        ImageView pf=findViewById(R.id.langplatf);
        moviesRV=findViewById(R.id.alllangmovies);
        Intent in=getIntent();
        String plat=in.getStringExtra("platform");
        String l=in.getStringExtra("language");
        e=in.getStringExtra("Email");
        System.out.println(e);
        if (l.equalsIgnoreCase("Hindi"))
        {
            pl.setImageResource(R.drawable.hindi);
            pf.setImageResource(R.drawable.hindi2);
        }
        else if (l.equalsIgnoreCase("English"))
        {
            pl.setImageResource(R.drawable.moviee);
            pf.setImageResource(R.drawable.movies);
        }
        else if (l.equalsIgnoreCase("Tamil"))
        {
            pl.setImageResource(R.drawable.tamil1);
            pf.setImageResource(R.drawable.tamil2);
        }
        else
        {
            pl.setImageResource(R.drawable.telegu1);
            pf.setImageResource(R.drawable.telegu2);
        }
        int i=0;
        Cursor c = db.rawQuery("SELECT * FROM Movies;", null);
        movies=new String[c.getCount()+1];
        System.out.println(plat);
        if(plat.equalsIgnoreCase("all"))
        {
            Cursor cur = db.rawQuery("SELECT * FROM Movies where Language='"+l+"';", null);
            if (cur.getCount() != 0) {
                if (cur.moveToFirst()) {
                    do {
                        movies[i] =cur.getString(0);
                        System.out.println(movies[i]);
                        i=i+1;
                    } while (cur.moveToNext());
                }

            } else {
                movies[i]="No Movies yet";
                System.out.println(movies[i]);
            }

        }
        else {
            Cursor cur = db.rawQuery("SELECT * FROM Movies where Language = '"+l+"' and Platform = '" + plat + "';", null);
            if (cur.getCount() != 0) {
                if (cur.moveToFirst()) {
                    do {
                        movies[i] = cur.getString(0);
                        System.out.println(movies[i]);
                        i=i+1;
                    } while (cur.moveToNext());
                }

            } else {
                movies[i]="No Movies yet";
                System.out.println(movies[i]);
            }
        }
        System.out.println(movies.length);
        movieModelArrayList = new ArrayList<>();
        if(movies.length-1>1) {
            for (int j = 0; j < movies.length; j++) {
                movieModelArrayList.add(new movieModel(movies[j]));
            }
        }
        else
        {
            movieModelArrayList.add(new movieModel(movies[0]));
        }
        movieAdapter moviesAdapter = new movieAdapter(this, movieModelArrayList);

        // below line is for setting a layout manager for our recycler view.
        // here we are creating vertical list so we will provide orientation as vertical
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        // in below two lines we are setting layoutmanager and adapter to our recycler view.
        moviesRV.setLayoutManager(linearLayoutManager);
        moviesRV.setAdapter(moviesAdapter);

        ListView t=findViewById(R.id.langtop);
        final ViewGroup.LayoutParams[] layoutparams = new ViewGroup.LayoutParams[1];
        //final ArrayAdapter<String> ca = new ArrayAdapter<String>(this, R.layout.activity_listview, R.id.textView15, movies);
        final CustomAdapter ca=new CustomAdapter(this,movies);
        t.setAdapter(ca);
        // ListView on item selected listener.
        t.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                System.out.println(movies[position]);
                Intent in=new Intent(Language.this,Movie.class);
                in.putExtra("MovieName",movies[position]);
                in.putExtra("Email",e);
                startActivity(in);
            }
        });
        Button lo=findViewById(R.id.logout);
        lo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Language.this,index.class);
                startActivity(i);
            }
        });
        FloatingActionButton b=findViewById(R.id.back);
        FloatingActionButton a=findViewById(R.id.add);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Language.this,platform.class);
                i.putExtra("Email",e);
                i.putExtra("platform",plat);
                startActivity(i);
            }
        });
        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inte=new Intent(Language.this,AddMovie.class);
                inte.putExtra("Email",e);
                inte.putExtra("page","language");
                inte.putExtra("language",l);
                startActivity(inte);
            }
        });
    }
}