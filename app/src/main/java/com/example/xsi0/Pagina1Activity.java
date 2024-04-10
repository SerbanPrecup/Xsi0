
package com.example.xsi0;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Pagina1Activity extends AppCompatActivity {

    private Button btnOf, btnOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina1);

        btnOf = findViewById(R.id.btnOf);
        btnOn = findViewById(R.id.btnOn);

        btnOf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Pagina1Activity.this, PlayersOfActivity.class);
                startActivity(intent);
            }
        });

        btnOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Pagina1Activity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}