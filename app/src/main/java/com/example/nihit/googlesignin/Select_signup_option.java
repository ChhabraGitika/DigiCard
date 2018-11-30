package com.example.nihit.googlesignin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Select_signup_option extends AppCompatActivity {
ImageButton personal;
ImageButton Business_use;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_signup_option);
        personal=(ImageButton) findViewById(R.id.user1);
        Business_use=(ImageButton) findViewById(R.id.business1);
        personal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iuser=new Intent(Select_signup_option.this,New_user.class);
                startActivity(iuser);
            }

        });
        Business_use.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iuser=new Intent(Select_signup_option.this,New_user.class);
                startActivity(iuser);
            }

        });
    }
}
