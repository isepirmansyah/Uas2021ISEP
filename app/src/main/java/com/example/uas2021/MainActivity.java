package com.example.uas2021;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    TextView txtJSON;
    EditText nama;
    Button btnJSON;
    RequestQueue mQueue;
    StringRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mQueue = Volley.newRequestQueue(this);
        txtJSON = findViewById(R.id.txtJson);
        btnJSON = findViewById(R.id.btn_get);
        nama = findViewById(R.id.txt_nama);

        btnJSON.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request = new StringRequest(Request.Method.GET, "http://192.168.5.204/uas/show.php",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonobject = new JSONObject(response);
                                    JSONArray jsonarray = jsonobject.getJSONArray("kegemaran");
                                    JSONObject data = jsonarray.getJSONObject(0);

                                    String firstName = data.getString("hobby");

                                    txtJSON.setText(firstName);


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Imputan tidak ada",Toast.LENGTH_LONG).show();
                        error.printStackTrace();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> parameters = new HashMap<String, String>();
                        parameters.put("nama", nama.getText().toString());
                        return parameters;
                    }
                };
                mQueue.add(request);
            }
        });
    }

}