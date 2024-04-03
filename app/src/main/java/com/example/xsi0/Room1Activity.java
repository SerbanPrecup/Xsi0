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
                String url = getString(R.string.url) + "/creare_room";

                Map<String, String> params = new HashMap<>();
                params.put("room", roomName);
                params.put("user1", getPlayerName);

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                        (Request.Method.POST, url, new JSONObject(params), new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    if (response.getString("RESPONSE").equals("TRUE")) {
                                        Intent intent = new Intent(Room1Activity.this, Xsi0OnActivity.class);
                                        intent.putExtra("Room", roomName);
                                        intent.putExtra("USER1", "TRUE");
                                        startActivity(intent);
                                    } else if (response.getString("RESPONSE").equals("FALSE")) {
                                        Toast.makeText(Room1Activity.this, "The room is already created.", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("Volley Error", "Error occurred: " + error.getMessage());
                            }
                        });

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
                String url = getString(R.string.url) + "/join_room";


                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                        (Request.Method.POST, url, new JSONObject(params), new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    if (response.getString("RESPONSE").equals("TRUE")) {
                                        Intent intent = new Intent(Room1Activity.this, Xsi0OnActivity.class);
                                        intent.putExtra("Room", roomName);
                                        intent.putExtra("USER1", "FALSE");
                                        startActivity(intent);
                                    } else if (response.getString("RESPONSE").equals("FALSE")) {
                                        Toast.makeText(Room1Activity.this, "The room doesn't exist.", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("Volley Error", "Error occurred: " + error.getMessage());
                            }

                        });
                queue.add(jsonObjectRequest);

            }
        });
    }
}
