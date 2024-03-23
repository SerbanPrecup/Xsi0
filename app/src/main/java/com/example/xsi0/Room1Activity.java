package com.example.xsi0;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

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

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String roomName = inputRoom.getText().toString();
                String getPlayerName = getIntent().getStringExtra("user");

                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url = "http://192.168.0.53:5000/creare_room"; // Replace this with your actual API endpoint

                // Create the parameters for the POST request
                Map<String, String> params = new HashMap<>();
                params.put("room", roomName);
                params.put("user1", getPlayerName);

                // Create the request
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                        (Request.Method.POST, url, new JSONObject(params), new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    if(response.getString("RESPONSE").equals("TRUE")){
//                                        WaitingRoom waitingRoom = new WaitingRoom(Room1Activity.this, "Wait until all players are conected!", Room1Activity.this);
//                                        waitingRoom.setCancelable(true);
//                                        waitingRoom.show();
                                        Intent intent=new Intent(Room1Activity.this,Xsi0OnActivity.class);
                                        intent.putExtra("Room",roomName);
                                        intent.putExtra("USER1","TRUE");
                                        startActivity(intent);
                                    }
                                    else if(response.getString("RESPONSE").equals("FALSE")){
                                        System.out.println("Room existent");
                                    }
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // Handle errors
                                Log.e("Volley Error", "Error occurred: " + error.getMessage());
                            }
                        });

                // Add the request to the RequestQueue.
                queue.add(jsonObjectRequest);
            }
        });

        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String roomName = inputRoom.getText().toString();
                String getPlayerName = getIntent().getStringExtra("user");

                Map<String, String> params = new HashMap<>();
                params.put("room", roomName);
                params.put("user2", getPlayerName);

                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url = "http://192.168.0.53:5000/join_room"; // Replace this with your actual API endpoint


                // Create the request
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                        (Request.Method.POST, url, new JSONObject(params), new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    if(response.getString("RESPONSE").equals("TRUE")){
//                                        WaitingRoom waitingRoom = new WaitingRoom(Room1Activity.this, "Sa inceapa jocul!", Room1Activity.this);
//                                        waitingRoom.setCancelable(true);
//                                        waitingRoom.show();
                                        Intent intent=new Intent(Room1Activity.this,Xsi0OnActivity.class);
                                        intent.putExtra("Room",roomName);
                                        intent.putExtra("USER1","FALSE");
                                        startActivity(intent);
                                    }
                                    else if(response.getString("RESPONSE").equals("FALSE")){
                                        System.out.println("Room-ul nu exista");
                                    }
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // Handle errors
                                Log.e("Volley Error", "Error occurred: " + error.getMessage());
                            }

                        });
                        // Add the request to the RequestQueue.
                        queue.add(jsonObjectRequest);

            }
        });
    }
}

//
//
//        btnJoin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String roomName = inputRoom.getText().toString();
//                String getPlayerName = getIntent().getStringExtra("user");
//
//                // Trimite cererea către /join_room
//                new HttpRequestTask(new HttpRequestTask.OnTaskCompleted() {
//                    @Override
//                    public void onTaskCompleted(String result) {
//                        if (result != null) {
//                            try {
//                                JSONObject jsonResponse = new JSONObject(result);
//                                String response = jsonResponse.getString("RESPONSE");
//
//                                // Verificăm dacă răspunsul indică începerea jocului
//                                if (response.equals("TRUE")) {
//                                    // Inițiază începerea jocului sau afișează un mesaj că jocul începe
//                                    startGame();
//                                } else {
//                                    // Altfel, afișăm mesajul de răspuns primit de la server
//                                    Toast.makeText(Room1Activity.this, response, Toast.LENGTH_SHORT).show();
//                                }
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                                // În caz de eroare la parsare, afișăm un mesaj de eroare
//                                Toast.makeText(Room1Activity.this, "Eroare la procesarea răspunsului de la server", Toast.LENGTH_SHORT).show();
//                            }
//                        } else {
//                            // În caz de eroare la conectare, afișăm un mesaj de eroare
//                            Toast.makeText(Room1Activity.this, "Eroare la conectare la server", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                }).execute("/join_room", roomName, getPlayerName);
//            }
//        });
//
//        btnCreate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String roomName = inputRoom.getText().toString();
//                String getPlayerName = getIntent().getStringExtra("user");
//                HttpRequestTask httpRequestTask = new HttpRequestTask(new HttpRequestTask.OnTaskCompleted() {
//                    @Override
//                    public void onTaskCompleted(String result) { // Implementarea logicii după finalizarea task-ului aici...
//                        // Aici poți face orice ai nevoie după finalizarea task-ului
//                    }
//                });
//
//                // Apoi execute metoda pentru a efectua task-ul
//                httpRequestTask.execute("/creare_room", roomName, getPlayerName);
//
//                // Afișează dialogul de așteptare sau alte acțiuni

//            }
//        });
//    }
//
//    private class HttpRequestTask extends AsyncTask<String, Void, String> {
//
//        // Definirea interfeței OnTaskCompleted
//        interface OnTaskCompleted {
//            void onTaskCompleted(String result);
//        }
//
//        private OnTaskCompleted listener; // Referință către interfața OnTaskCompleted
//
//        // Constructor pentru HttpRequestTask care primește un listener OnTaskCompleted
//        public HttpRequestTask(OnTaskCompleted listener) {
//            this.listener = listener;
//        }
//
//        // Metoda care apelează listener-ul atunci când task-ul este complet
//        @Override
//        protected void onPostExecute(String result) {
//            super.onPostExecute(result);
//            if (listener != null) {
//                listener.onTaskCompleted(result);
//            }
//        }
//
//        @Override
//        protected String doInBackground(String... params) {
//            try {
//                String endpoint = params[0]; // Obține primul parametru care este endpoint-ul
//                String roomName = params[1];
//                String user1 = params[2];
//
//                JSONObject postData = new JSONObject();
//                postData.put("room", roomName);
//                postData.put("user1",user1);
//
//                URL url = new URL("http://127.0.0.1:5000" + endpoint);
//                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//                connection.setRequestMethod("POST");
//                connection.setRequestProperty("Content-Type", "application/json");
//                connection.setDoOutput(true);
//
//                try (OutputStream os = connection.getOutputStream()) {
//                    byte[] input = postData.toString().getBytes("utf-8");
//                    os.write(input, 0, input.length);
//                }
//
//                // Verificați codul de răspuns aici, citiți răspunsul și faceți alte operații necesare.
//                // Citim răspunsul de la server
//                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));//citim raspunsu de la sv
//                StringBuilder response = new StringBuilder();
//                String line;
//                while ((line = reader.readLine()) != null) {
//                    response.append(line);
//                }
//                reader.close();
//
//                // Afisăm răspunsul
//                System.out.println("Response: " + response.toString());
//                Log.w("TrafficStats", "A mers cumva");
//
//                // Închidem conexiunea
//                connection.disconnect();
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return null;
//        }
//
//    }
//    public void startGame()
//    {
//        Intent intent = new Intent(Room1Activity.this,Xsi0OnActivity.class);
//        startActivity(intent);
//    }
//}
