package com.example.xsi0;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PlayersOfActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players_of);

        final EditText playerOne = findViewById(R.id.playerOneName);
        final EditText playerTwo = findViewById(R.id.playerTwoName);
        final Button startGameBtn = findViewById(R.id.startGameButton);

        startGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String p1 = playerOne.getText().toString();
                final String p2 = playerTwo.getText().toString();

                if (p1.isEmpty() || p2.isEmpty()) {
                    Toast.makeText(PlayersOfActivity.this, "PLEASE ENTER PLAYER NAMES", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(PlayersOfActivity.this, Xsi0Activity.class);
                    intent.putExtra("playerOne", p1);
                    intent.putExtra("playerTwo", p2);
                    startActivity(intent);
                }
            }
        });
    }
}