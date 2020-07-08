package com.example.boredapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private RequestQueue requestQueue;
    String[] types = {"Any","education", "recreational", "social", "diy", "charity", "cooking", "relaxation", "music", "busywork"};
    String[] price={"0.0","0.1","0.2","0.3","0.4","0.5","0.6","0.7","0.8"};
    String[] participants={"Any","1","2","3","4","5","6","7","8"};
    ActivityObject act= new ActivityObject("1","1","2","3","4");
    String URL="https://www.boredapi.com/api/activity";

    private void theJsonParser(String url){
        //String url="https://cat-fact.herokuapp.com/facts/random";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{

                            String key= response.getString("key");
                            String activity = response.getString("activity");
                            String type = response.getString("type");
                            String price = response.getString("price");
                            String participants= response.getString("participants");

                            act= new ActivityObject(key,activity,type, price,participants);


                            showActivity();
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "Could not find activity with given parameters", Toast.LENGTH_LONG).show();

                        }
                    }
                },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(MainActivity.this, "Server Error", Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(request);
    }

    void showActivity(){
        TextView text= (TextView) findViewById(R.id.textView);
        text.setText(act.activity);
    }
    void DropDownAdapters(){
        Spinner typeSpinner = (Spinner) findViewById(R.id.type);
        //Spinner priceSpinner = (Spinner) findViewById(R.id.price);
        Spinner participantsSpinner = (Spinner) findViewById(R.id.participants);
        //String[] types = {"education", "recreational", "social", "diy", "charity", "cooking", "relaxation", "music", "busywork"};
        ArrayAdapter<CharSequence> EntriesAdapter = new ArrayAdapter<CharSequence>(MainActivity.this, R.layout.spinner_text, types );
        EntriesAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        typeSpinner.setAdapter(EntriesAdapter);
        //EntriesAdapter = new ArrayAdapter<CharSequence>(MainActivity.this, R.layout.spinner_text, price );
        //priceSpinner.setAdapter(EntriesAdapter);
        EntriesAdapter = new ArrayAdapter<CharSequence>(MainActivity.this, R.layout.spinner_text, participants );
        participantsSpinner.setAdapter(EntriesAdapter);

    }
    void searchYaWalad(View v){
        URL="https://www.boredapi.com/api/activity";
        Spinner typeSpinner = (Spinner) findViewById(R.id.type);
        //Spinner priceSpinner = (Spinner) findViewById(R.id.price);
        Spinner participantsSpinner = (Spinner) findViewById(R.id.participants);

        String type= typeSpinner.getSelectedItem().toString();
        //String price= priceSpinner.getSelectedItem().toString();
        String participants= participantsSpinner.getSelectedItem().toString();
        if (type.equals("Any")==false){
            URL+="?type="+type+"&";
        }
        else{URL+="?";
        }
        if (participants.equals("Any")==false){
            URL+="participants="+participants;
        }

        theJsonParser(URL);

    }
    void searchRandom(View v){
        URL="https://www.boredapi.com/api/activity";
        theJsonParser(URL);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.getSupportActionBar().hide();
        requestQueue=Volley.newRequestQueue(MainActivity.this);
        DropDownAdapters();
        final Button searchButton = (Button) findViewById(R.id.button1);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                searchYaWalad(v);

            }
        });

        final Button randomButton = (Button) findViewById(R.id.button2);
        randomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                searchRandom(v);

            }
        });
    }
}
