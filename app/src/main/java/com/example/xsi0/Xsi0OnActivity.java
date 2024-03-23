package com.example.xsi0;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Xsi0OnActivity extends AppCompatActivity {

    TextView playerOneName, playerTwoName;
    private ImageView image1, image2, image3, image4, image5, image6, image7, image8, image9;

    private LinearLayout playerOneLayout, playerTwoLayout;

    private Button btnRefresh;

    String e11,e12,e13,e21,e22,e23,e31,e32,e33;

    String user1,roomName;

    boolean player1Turn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xsi0_on);

        playerOneName = findViewById(R.id.playerOneName);
        playerTwoName = findViewById(R.id.playerTwoName);

        image1 = findViewById(R.id.image1);
        image2 = findViewById(R.id.image2);
        image3 = findViewById(R.id.image3);
        image4 = findViewById(R.id.image4);
        image5 = findViewById(R.id.image5);
        image6 = findViewById(R.id.image6);
        image7 = findViewById(R.id.image7);
        image8 = findViewById(R.id.image8);
        image9 = findViewById(R.id.image9);

        playerOneLayout = findViewById(R.id.playerOneLayout);
        playerTwoLayout = findViewById(R.id.playerTwoLayout);

        btnRefresh = findViewById(R.id.btnRefresh);

        user1 = getIntent().getStringExtra("USER1");

        roomName = getIntent().getStringExtra("Room");

        getMatrice();



        image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    putX0(1,1);
                getMatrice();
            }
        });

        image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                putX0(1,2);
                getMatrice();
            }
        });

        image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                putX0(1,3);
                getMatrice();
            }
        });

        image4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                putX0(2,1);
                getMatrice();
            }
        });

        image5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                putX0(2,2);
                getMatrice();
            }
        });

        image6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                putX0(2,3);
                getMatrice();
            }
        });

        image7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                putX0(3,1);
                getMatrice();
            }
        });

        image8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                putX0(3,2);
                getMatrice();
            }
        });

        image9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                putX0(3,3);
                getMatrice();
            }
        });


        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMatrice();
            }
        });

    }

    private void getUserNames() {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        Map<String, String> params = new HashMap<>();
        params.put("room", roomName);
        JsonObjectRequest jsonObjectRequest;
        String url = "http://192.168.0.53:5000/get_users";
        jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, new JSONObject(params), new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            System.out.println(response.toString());
                            playerOneName.setText(response.getString("USER1"));
                            playerTwoName.setText(response.getString("USER2"));
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

    private void getMatrice(){
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        Map<String, String> params = new HashMap<>();
        params.put("room", roomName);



        JsonObjectRequest jsonObjectRequest;
            String url="http://192.168.0.53:5000/get_matrice";
            jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.POST, url, new JSONObject(params), new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            getUserNames();
                            try {
                                if(response.getString("RESPONSE").equals("X a castigat")) {
                                    jocTerminat("X");
                                }else if(response.getString("RESPONSE").equals("0 a castigat")){
                                    jocTerminat("0");
                                }else if(response.getString("RESPONSE").equals("EGALITATE")){
                                    jocTerminat("E");
                                }else{

                                e11 = response.getString("e11");
                                e12 = response.getString("e12");
                                e13 = response.getString("e13");
                                e21 = response.getString("e21");
                                e22 = response.getString("e22");
                                e23 = response.getString("e23");
                                e31 = response.getString("e31");
                                e32 = response.getString("e32");
                                e33 = response.getString("e33");
                                player1Turn = response.getBoolean("turnPlayer1");
                                if (Objects.equals(e11, "X")) {
                                    image1.setImageResource(R.drawable.cross_icon);
                                } else if (Objects.equals(e11, "0")) {
                                    image1.setImageResource(R.drawable.zero_icon);
                                }
                                if (Objects.equals(e12, "X")) {
                                    image2.setImageResource(R.drawable.cross_icon);
                                } else if (Objects.equals(e12, "0")) {
                                    image2.setImageResource(R.drawable.zero_icon);
                                }
                                if (Objects.equals(e13, "X")) {
                                    image3.setImageResource(R.drawable.cross_icon);
                                } else if (Objects.equals(e13, "0")) {
                                    image3.setImageResource(R.drawable.zero_icon);
                                }
                                if (Objects.equals(e21, "X")) {
                                    image4.setImageResource(R.drawable.cross_icon);
                                } else if (Objects.equals(e21, "0")) {
                                    image4.setImageResource(R.drawable.zero_icon);
                                }
                                if (Objects.equals(e22, "X")) {
                                    image5.setImageResource(R.drawable.cross_icon);
                                } else if (Objects.equals(e22, "0")) {
                                    image5.setImageResource(R.drawable.zero_icon);
                                }
                                if (Objects.equals(e23, "X")) {
                                    image6.setImageResource(R.drawable.cross_icon);
                                } else if (Objects.equals(e23, "0")) {
                                    image6.setImageResource(R.drawable.zero_icon);
                                }
                                if (Objects.equals(e31, "X")) {
                                    image7.setImageResource(R.drawable.cross_icon);
                                } else if (Objects.equals(e31, "0")) {
                                    image7.setImageResource(R.drawable.zero_icon);
                                }
                                if (Objects.equals(e32, "X")) {
                                    image8.setImageResource(R.drawable.cross_icon);
                                } else if (Objects.equals(e32, "0")) {
                                    image8.setImageResource(R.drawable.zero_icon);
                                }
                                if (Objects.equals(e33, "X")) {
                                    image9.setImageResource(R.drawable.cross_icon);
                                } else if (Objects.equals(e33, "0")) {
                                    image9.setImageResource(R.drawable.zero_icon);
                                }
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


    private void putX0(int i,int j){

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        Map<String, String> params = new HashMap<>();
        params.put("room", roomName);
        params.put("i", String.valueOf(i));
        params.put("j", String.valueOf(j));
        JsonObjectRequest jsonObjectRequest;
        String url="http://192.168.0.53:5000";
        if(Objects.equals(user1, "TRUE")){
            //System.out.println(url);
            url=url.concat("/put_x");
        }
        else{
//            System.out.println(url);
            url=url.concat("/put_0");
        }
        System.out.println(url);
        jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, new JSONObject(params), new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response.toString());
                        try {
                            if(response.getString("RESPONSE").equals("TRUE")) {
                                //getMatrice();
                                System.out.println("TRUE");
//                            } else if (response.getString("RESPONSE").equals("X a castigat")) {
//
//                                jocTerminat("X");
//                            } else if(response.getString("RESPONSE").equals("0 a castigat")) {
//                                jocTerminat("0");
//                            } else if(response.getString("RESPONSE").equals("EGALITATE"))
//                                jocTerminat("E");
                            }else if(response.getString("RESPONSE").equals("FALSE")){
                                System.out.println("FALSE");
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

    private void jocTerminat(String caracter) {
        if(caracter.equals("X") || caracter.equals("0")) {
            WinDialogOn winDialog = new WinDialogOn(Xsi0OnActivity.this, caracter + " has won the match", Xsi0OnActivity.this);
            winDialog.setCancelable(false);
            winDialog.show();
        } else if (caracter.equals("E")) {
            WinDialogOn winDialog = new WinDialogOn(Xsi0OnActivity.this,"It is a draw!",Xsi0OnActivity.this);
            winDialog.show();
        }
    }

    void exit() {


        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "http://192.168.0.53:5000/sterge_room";

        // Create the parameters for the POST request
        Map<String, String> params = new HashMap<>();
        params.put("room", roomName);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, new JSONObject(params), new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            System.out.println(response.toString());
                            if(response.getString("RESPONSE").equals("TRUE"))
                                System.out.println("Room sters");
                            else if(response.getString("RESPONSE").equals("FALSE")){
                                System.out.println("Room nesters");
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
        Intent intent= new Intent(Xsi0OnActivity.this,Pagina1Activity.class);
        startActivity(intent);
    }

}
