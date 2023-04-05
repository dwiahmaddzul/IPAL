package info.androidhive.thingspeak;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private TextView status;
    private TextView datake;
    private TextView tanggal;
    private TextView note;
    private Button suhu;
    private Button humidity;
    private RequestQueue mQueue;
    public String Status;
    public String Datake;
    public String Tanggal;
    public String dataUP;
    String html = "<iframe width=\"300\" height=\"250\" style=\"border: 1px solid #cccccc;\" src=\"https://thingspeak.com/channels/1520221/charts/1?bgcolor=%23ffffff&color=%23d62020&dynamic=true&results=60&title=Temperature+Graph&type=line&height=250&width=50\"></iframe>\n";
    String html2 = "<iframe width=\"300\" height=\"250\" style=\"border: 1px solid #cccccc;\" src=\"https://thingspeak.com/channels/1520221/charts/1?bgcolor=%23ffffff&color=%23d62020&dynamic=true&results=60&title=Temperature+Graph&type=line&height=250&width=50\"></iframe>";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        status = findViewById(R. id. status);
        datake = findViewById(R. id. datake);
        tanggal = findViewById(R. id. tanggal);
        suhu = (Button) findViewById(R. id. button1);
        humidity = (Button) findViewById(R. id. button2);
        mQueue= Volley.newRequestQueue(this);



        suhu.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v){
                                          jsonParse();

                                          WebView webview;
                                          webview = (WebView) findViewById(R.id.webviewku);
                                          webview.getSettings().setJavaScriptEnabled(true);
                                          webview.loadData(html, "text/html", null);
                                      }

        });

        humidity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                jsonParse2();
                WebView webview;
                webview = (WebView) findViewById(R.id.webviewku);
                webview.getSettings().setJavaScriptEnabled(true);
                webview.loadData(html2, "text/html", null);
            }
        });


    }

    public void jsonParse() {
        String urlfeed = "https://api.thingspeak.com/channels/1520221/feeds.json?api_key=3CK09KV4IZ4T6B97&results=1"; //change this with you http request "READ A CHANNEL FEED"
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, urlfeed, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("feeds");
                            JSONObject feeds = jsonArray.getJSONObject(0);
                            Status = feeds.getString("field1");
                            status.setText(Status+" °C");
                            Datake = feeds.getString("entry_id");
                            datake.setText(Datake);
                            Tanggal = feeds.getString("created_at");
                            tanggal.setText(Tanggal);



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mQueue.add(request); }

    public void jsonParse2() {
        String urlfeed = "https://api.thingspeak.com/channels/1520221/feeds.json?api_key=3CK09KV4IZ4T6B97&results=1"; //change this with you http request "READ A CHANNEL FEED"
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, urlfeed, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("feeds");
                            JSONObject feeds = jsonArray.getJSONObject(0);
                            Status = feeds.getString("field2");
                            status.setText(Status+" °C");
                            Datake = feeds.getString("entry_id");
                            datake.setText(Datake);
                            Tanggal = feeds.getString("created_at");
                            tanggal.setText(Tanggal);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mQueue.add(request); }



    public void urlreq() {

        RequestQueue queue = Volley.newRequestQueue(this);
        final String base = "https://api.thingspeak.com/update?";
        final String api_key = "api_key";
        final String field_1 = "field1";

        // Build up the query URI
        Uri builtURI = Uri.parse(base).buildUpon()
                .appendQueryParameter(api_key, "WF6E1L4W0S2E2BXC") //change this with your write api key
                .appendQueryParameter(field_1, dataUP )
                .build();
        String url = builtURI.toString();

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display response string.

                        if (response.equals("0")) {
                            note.setText("Set failed" + "\n" + "try again !!"+ "\n" + "Response Id: "+ response);

                        } else { note.setText("Set success" +"\n" + "Response Id: "+ response);}
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                note.setText("no internet");

            }
        });
        queue.add(stringRequest);}
}