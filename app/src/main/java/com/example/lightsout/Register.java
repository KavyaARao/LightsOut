package com.example.lightsout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Button l = (Button) findViewById(R.id.login);
        Button r= (Button) findViewById(R.id.register);
        final SQLiteDatabase db;
        final EditText n=findViewById(R.id.name);
        final EditText e=findViewById(R.id.email);
        final EditText m=findViewById(R.id.phone);
        final EditText p=findViewById(R.id.pass);
        final EditText c=findViewById(R.id.cp);
        final String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";
        db=openOrCreateDatabase("LightsOut",MODE_PRIVATE,null);
        db.execSQL("CREATE TABLE IF NOT EXISTS Users(Name VARCHAR,Email VARCHAR,MobileNo BIGINT,Password VARCHAR);");
        l.setOnClickListener(new View.OnClickListener() {
                                 @Override
                                 public void onClick(View v) {
                                     Intent intent = new Intent(Register.this, Login.class);
                                     startActivity(intent);
                                 }
                             });
        r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if(n.getText().toString().equals(" ")||n.getText().toString().isEmpty())
            {
                Toast.makeText(Register.this, "Name can't be empty", Toast.LENGTH_SHORT).show();
            }
            else if(e.getText().toString().equals(" ")||e.getText().toString().isEmpty())
            {
                Toast.makeText(Register.this, "Email can't be empty", Toast.LENGTH_SHORT).show();
            }
            else if(!e.getText().toString().matches(regex))
            {
                Toast.makeText(Register.this, "Email has to be in example@example.com format", Toast.LENGTH_SHORT).show();
            }
            else if(m.getText().toString().equals(" ")||m.getText().toString().isEmpty())
            {
                Toast.makeText(Register.this, "Mobile Phone can't be empty", Toast.LENGTH_SHORT).show();
            }
            else if(m.getText().toString().length()!=10)
            {
                Toast.makeText(Register.this, "Phone Number has to have 10 digits", Toast.LENGTH_SHORT).show();
            }
            else if(p.getText().toString().equals(" ")||p.getText().toString().equals(null))
            {
                Toast.makeText(Register.this, "Password can't be empty",Toast.LENGTH_SHORT).show();
            }
            else if(c.getText().toString().equals(" ")||c.getText().toString().equals(null))
            {
                Toast.makeText(Register.this, "Please confirm your password",Toast.LENGTH_SHORT).show();
            }
            else if(!p.getText().toString().equals(c.getText().toString()))
            {
                Toast.makeText(Register.this, "yours passwords don't match please retry",Toast.LENGTH_SHORT).show();
            }
            else
            {
                //Toast.makeText(Register.this, "Welcome! registration is complete",Toast.LENGTH_SHORT).show();
                String em=e.getText().toString();
                //Toast.makeText(Register.this, "Welcome!" +em,Toast.LENGTH_SHORT).show();
                try {
                    Cursor c = db.rawQuery("SELECT * FROM Users where Email = '" +em+"';", null);
                    if(c.getCount()!=0)
                    {
                        Toast.makeText(getApplicationContext(), "This email address seems to already have an account try and login ", Toast.LENGTH_LONG).show();
                        c.close();
                    }
                    else
                    {
                        String na=n.getText().toString();
                        String pho=m.getText().toString();
                        long ph=Long.parseLong(pho);
                        String pass=p.getText().toString();
                        db.execSQL("INSERT INTO Users VALUES('"+na+"','"+em+"','"+ph+"','"+pass+"');");
                        Cursor resultSet = db.rawQuery("Select * from Users;",null);
                        resultSet.moveToFirst();

                        Toast.makeText(getApplicationContext(),"Welcome! To Light's Out Your account has been succesfully created",Toast.LENGTH_LONG).show();
                        resultSet.close();
                        Users u=new Users();
                        u.setEmail(em);
                        u.setName(na);
                        u.setPhoneNumber(ph);
                        u.setPassword(pass);
                        FirebaseDatabase db=FirebaseDatabase.getInstance("https://lightsout-2882-default-rtdb.asia-southeast1.firebasedatabase.app/");
                        DatabaseReference myRef=db.getReference("Users");
                        myRef.setValue(u);
                        Intent in=new Intent(Register.this,Login.class);
                        startActivity(in);
                    }
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
            }
        });

    }
}