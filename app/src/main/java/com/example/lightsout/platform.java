package com.example.lightsout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class platform extends AppCompatActivity {
    public static String movies[];
    private RecyclerView moviesRV;
    public static String e;
    public static String movieName;
    private ArrayList<movieModel> movieModelArrayList;
    public static int c=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_platform);
        final SQLiteDatabase db;
        db=openOrCreateDatabase("LightsOut",MODE_PRIVATE,null);
        db.execSQL("CREATE TABLE IF NOT EXISTS Reviews(Name VARCHAR,Email VARCHAR,Review VARCHAR,Rating INT);");
        ImageView pl=findViewById(R.id.plat);
        ImageView pf=findViewById(R.id.platf);
        Intent in=getIntent();
        moviesRV = findViewById(R.id.allmovies);
        String plat=in.getStringExtra("platform");
        e=in.getStringExtra("Email");
        System.out.println(e);
        if (plat.equalsIgnoreCase("netflix"))
        {
            pl.setImageResource(R.drawable.netflix);
            pf.setImageResource(R.drawable.net);
        }
        else if (plat.equalsIgnoreCase("prime"))
        {
            pl.setImageResource(R.drawable.prime);
            pf.setImageResource(R.drawable.pr);
        }
        else if (plat.equalsIgnoreCase("hotstar"))
        {
            pl.setImageResource(R.drawable.hotstar);
            pf.setImageResource(R.drawable.hotstar1);
        }
        else
        {
            pl.setImageResource(R.drawable.all);
            pf.setImageResource(R.drawable.movies);
        }
        int i=0;
        movieModelArrayList = new ArrayList<>();
        if(plat.equalsIgnoreCase("all")) {
            Cursor c = db.rawQuery("SELECT * FROM Movies;", null);
            movies = new String[c.getCount()];
        }
        else
        {
            Cursor c = db.rawQuery("SELECT * FROM Movies where Platform ='"+plat+"';", null);
            movies = new String[c.getCount()];
        }
        System.out.println(plat);
        if(plat.equalsIgnoreCase("all"))
        {
            Cursor cur = db.rawQuery("SELECT * FROM Movies;", null);
            if (cur.getCount() != 0) {
                if (cur.moveToFirst()) {
                    do {
                        movieModelArrayList.add(new movieModel(cur.getString(0)));
                        movies[i] =cur.getString(0);
                        System.out.println(movies[i]);
                        i=i+1;
                    } while (cur.moveToNext());
                }

            } else {
                movies[i]="No Movies yet";
                movieModelArrayList.add(new movieModel("No Movies Yet!"));
                System.out.println(movies[i]);
            }

        }
        else {
            Cursor cur = db.rawQuery("SELECT * FROM Movies where Platform = '" + plat + "';", null);
            if (cur.getCount() != 0) {
                if (cur.moveToFirst()) {
                    do {
                        movieModelArrayList.add(new movieModel(cur.getString(0)));
                        movies[i] = cur.getString(0);
                        System.out.println(movies[i]);
                        i=i+1;
                    } while (cur.moveToNext());
                }

            } else {
                movies[i]="No Movies yet";
                movieModelArrayList.add(new movieModel("No Movies Yet!"));
                System.out.println(movies[i]);
            }
        }
        System.out.println(movies.length);
        movieAdapter moviesAdapter = new movieAdapter(this, movieModelArrayList);

        // below line is for setting a layout manager for our recycler view.
        // here we are creating vertical list so we will provide orientation as vertical
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        // in below two lines we are setting layoutmanager and adapter to our recycler view.
        moviesRV.setLayoutManager(linearLayoutManager);
        moviesRV.setAdapter(moviesAdapter);
        ImageButton h=findViewById(R.id.hindi);
        ImageButton eng=findViewById(R.id.english);
        ImageButton tz=findViewById(R.id.tamil);
        ImageButton te=findViewById(R.id.telegu);
        h.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(platform.this, Language.class);
                intent.putExtra("platform",plat);
                intent.putExtra("language","Hindi");
                intent.putExtra("Email",e);
                startActivity(intent);
            }
        });
        eng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(platform.this, Language.class);
                intent.putExtra("platform",plat);
                intent.putExtra("language","English");
                intent.putExtra("Email",e);
                startActivity(intent);
            }
        });
        tz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(platform.this, Language.class);
                intent.putExtra("platform",plat);
                intent.putExtra("language","Tamil");
                intent.putExtra("Email",e);
                startActivity(intent);
            }
        });
        te.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(platform.this, Language.class);
                intent.putExtra("platform",plat);
                intent.putExtra("language","Telegu");
                intent.putExtra("Email",e);
                startActivity(intent);
            }
        });
        ListView t=findViewById(R.id.top);
        Cursor cur = db.rawQuery("SELECT avg(rating) FROM Movies m, Reviews r GROUP BY r.Name HAVING m.Name=r.Name and m.Platform='"+plat+"';", null);
        System.out.println("SELECT Name,avg(rating) FROM Movies m, Reviews r GROUP BY r.Name HAVING m.Name=r.Name and m.Platform='"+plat+"';");
        if(cur.getCount()!=0)
        {
            if (cur.moveToFirst()) {
                do {
                    System.out.println("Average:"+cur.getFloat(0));
                } while (cur.moveToNext());
            }
        }
        final ViewGroup.LayoutParams[] layoutparams = new ViewGroup.LayoutParams[1];
        //final ArrayAdapter<String> ca = new ArrayAdapter<String>(this, R.layout.activity_listview, R.id.textView15, movies);
        final CustomAdapter ca=new CustomAdapter(this,movies);
        t.setAdapter(ca);
        // ListView on item selected listener.
        t.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                System.out.println(movies[position]);
                Intent in=new Intent(platform.this,Movie.class);
                in.putExtra("MovieName",movies[position]);
                in.putExtra("Email",e);
                startActivity(in);
            }
        });
        Button lo=findViewById(R.id.logout);
        lo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(platform.this,index.class);
                startActivity(i);
            }
        });
        FloatingActionButton b=findViewById(R.id.back);
        FloatingActionButton a=findViewById(R.id.add);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(platform.this,Dashboard.class);
                i.putExtra("Email",e);
                startActivity(i);
            }
        });
        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inte=new Intent(platform.this,AddMovie.class);
                inte.putExtra("Email",e);
                inte.putExtra("page","platform");
                startActivity(inte);
            }
        });
    }
    public String getMovie()
    {
        return movieName;
    }
}