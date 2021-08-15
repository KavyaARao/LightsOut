package com.example.lightsout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddReview extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);
        Intent inte=getIntent();
        String moviename=inte.getStringExtra("MovieName");
        String e=inte.getStringExtra("Email");
        TextView mn=findViewById(R.id.mname);
        TextView em=findViewById(R.id.author);
        mn.setText(moviename);
        em.setText(e);
        EditText review=findViewById(R.id.rev);
        EditText rating=findViewById(R.id.rate);
        Button b=findViewById(R.id.back);
        Button a=findViewById(R.id.add);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent in = new Intent(AddReview.this, Movie.class);
                    in.putExtra("Email",e);
                    in.putExtra("MovieName",moviename);
                    startActivity(in);

            }
        });
        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db;
                db=openOrCreateDatabase("LightsOut",MODE_PRIVATE,null);
                if(review.getText().toString().isEmpty()||rating.getText().toString().isEmpty())
                {
                    Toast.makeText(AddReview.this, "Please Enter all the details", Toast.LENGTH_SHORT).show();
                }
                else
                {

                    String r=review.getText().toString();
                    int rate=Integer.parseInt(rating.getText().toString());
                    db.execSQL("INSERT INTO Reviews VALUES('"+moviename+"','"+e+"','"+r+"','"+rate+"');");
                    Cursor resultSet = db.rawQuery("Select * from Reviews where Name='"+moviename+"';",null);
                    resultSet.moveToFirst();
                    Toast.makeText(getApplicationContext(),"You have successfully added the review "+resultSet.getString(0),Toast.LENGTH_LONG).show();
                    resultSet.close();
                }
            }
        });
    }
}