package com.example.lightsout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.content.ContentValues.TAG;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button r=(Button)findViewById(R.id.login);
        Button l=(Button)findViewById(R.id.register);
        final SQLiteDatabase db;
        db=openOrCreateDatabase("LightsOut",MODE_PRIVATE,null);
        r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });
        l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               final EditText em=(EditText) findViewById(R.id.email);
               final EditText pa=(EditText) findViewById(R.id.pass);
               if(em.getText().toString().equals(" ")||em.getText().toString().isEmpty())
               {
                   Toast.makeText(Login.this, "Email Can't be Empty!", Toast.LENGTH_LONG).show();
               }
                else if(pa.getText().toString().equals(" ")||pa.getText().toString().isEmpty())
                {
                    Toast.makeText(Login.this, "Please Enter your Password!", Toast.LENGTH_LONG).show();
                }
                else
               {
                   String e=em.getText().toString();
                   String p=pa.getText().toString();
                   Cursor c = db.rawQuery("SELECT * FROM Users where Email = '" +e+"';", null);
                   if(c.getCount()!=0)
                   {
                        c.moveToFirst();
                        String pass=c.getString(3);
                        if(!p.equals(pass))
                        {
                            Toast.makeText(Login.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(Login.this, "Welcome! you have successfully Logged in", Toast.LENGTH_SHORT).show();
                            Intent in=new Intent(Login.this,Dashboard.class);
                            in.putExtra("Email", e);
                            startActivity(in);
                        }
                   }
                   else
                   {
                       Toast.makeText(getApplicationContext(), "You dont seem to have an account kindly register! ", Toast.LENGTH_LONG).show();
                   }

               }
            }
        });
    }
}