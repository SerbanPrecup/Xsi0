package com.example.xsi0;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.badge.BadgeUtils;
import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText inputEditText;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.btnUser);
        inputEditText = findViewById(R.id.inputUsername);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputEditText.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "COMPLETE YOUR USERNAME", Toast.LENGTH_SHORT).show();
                } else {
                    String userName = inputEditText.getText().toString();
                    Intent intent = new Intent(MainActivity.this, Room1Activity.class);
                    intent.putExtra("user", userName);
                    startActivity(intent);
                }
            }
        });
    }

}