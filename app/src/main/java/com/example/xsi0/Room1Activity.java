package com.example.xsi0;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Room1Activity extends AppCompatActivity {

    Button btnJoin, btnCreate;
    TextInputEditText inputRoom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room1);
        btnJoin = findViewById(R.id.btnJoin);
        btnCreate = findViewById(R.id.btnCreate);
        inputRoom = findViewById(R.id.inputRoom);
        final String getPlayerName = getIntent().getStringExtra("user");


        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String roomName = inputRoom.getText().toString();
                new HttpRequestTask().execute("/join_room", roomName, getPlayerName); // Trimite cererea către /join_room
            }
        });

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String roomName = inputRoom.getText().toString();
                new HttpRequestTask().execute("/creare_room", roomName, getPlayerName); // Trimite cererea către /create_room

                WaitingRoom waitingRoom = new WaitingRoom(Room1Activity.this, "Wait until all players are conected!", Room1Activity.this);
                waitingRoom.setCancelable(true);
                waitingRoom.show();
            }
        });

    }

    private class HttpRequestTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            try {
                String endpoint = params[0]; // Obține primul parametru care este endpoint-ul
                String roomName = params[1];
                String user1 = params[2];

                JSONObject postData = new JSONObject();
                postData.put("room", roomName);
                postData.put("user1",user1);

                URL url = new URL("http://127.0.0.1:5000" + endpoint);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setDoOutput(true);

                try (OutputStream os = connection.getOutputStream()) {
                    byte[] input = postData.toString().getBytes("utf-8");
                    os.write(input, 0, input.length);
                }

                // Verificați codul de răspuns aici, citiți răspunsul și faceți alte operații necesare.
                // Citim răspunsul de la server
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));//citim raspunsu de la sv
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                // Afisăm răspunsul
                System.out.println("Response: " + response.toString());
                Log.w("TrafficStats", "A mers cumva");

                // Închidem conexiunea
                connection.disconnect();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
