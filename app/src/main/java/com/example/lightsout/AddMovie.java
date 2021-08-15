package com.example.lightsout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddMovie extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movie);
        Button b=findViewById(R.id.back);
        Button a=findViewById(R.id.add);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inte = getIntent();
                String e = inte.getStringExtra("Email");
                String page = inte.getStringExtra("page");
                if (page.equalsIgnoreCase("platform")) {

                    Intent in = new Intent(AddMovie.this, platform.class);
                    in.putExtra("Email",e);
                    startActivity(in);
                }
                else if(page.equalsIgnoreCase("language")){
                    Intent intent=getIntent();
                    String l=intent.getStringExtra("language");
                    Intent in=new Intent(AddMovie.this,Language.class);
                    in.putExtra("Email",e);
                    in.putExtra("language",l);
                    startActivity(in);
                }
            }
        });
        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db;
                db=openOrCreateDatabase("LightsOut",MODE_PRIVATE,null);
                EditText moviename=findViewById(R.id.mname);
                EditText crew=findViewById(R.id.cast);
                EditText desc=findViewById(R.id.description);
                EditText gen=findViewById(R.id.genre);
                EditText lang=findViewById(R.id.language);
                EditText plat=findViewById(R.id.platform);
                if(moviename.getText().toString().isEmpty()||crew.getText().toString().isEmpty()||desc.getText().toString().isEmpty()||gen.getText().toString().isEmpty()||lang.getText().toString().isEmpty()||plat.getText().toString().isEmpty())
                {
                    Toast.makeText(AddMovie.this, "Please Enter all the details", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    String mn=moviename.getText().toString();
                    String c=crew.getText().toString();
                    String d=desc.getText().toString();
                    String g=gen.getText().toString();
                    String l=lang.getText().toString();
                    String p=plat.getText().toString();
                    db.execSQL("INSERT INTO Movies VALUES('"+mn+"','"+c+"','"+d+"','"+g+"','"+l+"','"+p+"');");
                    Cursor resultSet = db.rawQuery("Select * from Movies where Name='"+mn+"';",null);
                    resultSet.moveToFirst();
                    Toast.makeText(getApplicationContext(),"You have successfully added the movie "+resultSet.getString(0),Toast.LENGTH_LONG).show();
                    resultSet.close();
                }
            }
        });
    }
}