//import android.content.Context;
//import android.os.Handler;
//import android.os.Looper;
//import android.util.Log;
//
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.JsonObjectRequest;
//import com.android.volley.toolbox.Volley;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class HttpRequestThread extends Thread {
//    private Context context;
//    private RequestQueue requestQueue;
//    private Handler handler;
//    private boolean running;
//    private String url;
//    private long intervalMillis;
//
//    public HttpRequestThread(Context context, String url, long intervalMillis) {
//        this.context = context;
//        this.url = url;
//        this.intervalMillis = intervalMillis;
//        handler = new Handler(Looper.getMainLooper());
//    }
//
//    @Override
//    public void run() {
//        running = true;
//        requestQueue = Volley.newRequestQueue(context);
//
//        while (running) {
//            makeRequest();
//            try {
//                Thread.sleep(intervalMillis);
//            } catch (InterruptedException e) {
//                Log.e("HttpRequestThread", "Thread interrupted: " + e.getMessage());
//                running = false;
//            }
//        }
//    }
//
//    private void makeRequest() {
//        // Create the parameters for the POST request
//        Map<String, String> params = new HashMap<>();
//        params.put("room", roomName); // Assuming roomName is defined somewhere in your class
//
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
//                (Request.Method.POST, url, new JSONObject(params), new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try {
//                            Log.d("HttpRequestThread", "Response received: " + response.toString());
//                            // Handle the response here, update UI or perform necessary actions
//                        } catch (JSONException e) {
//                            Log.e("HttpRequestThread", "JSON parsing error: " + e.getMessage());
//                        }
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.e("HttpRequestThread", "Volley error occurred: " + error.getMessage());
//                        // Handle Volley error
//                    }
//                });
//
//        requestQueue.add(jsonObjectRequest);
//    }
//
//    public void stopThread() {
//        running = false;
//        interrupt();
//    }