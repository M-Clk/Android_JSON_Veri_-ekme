package com.example.jsonuc;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

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

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ProgressDialog progressDialog;
    CountDownTimer countDownTimer;

    private static final String JSON_URL = "http://service.aeyazilim.com/Genel/AllService.svc/Country";

    ListView lists;
    List<Data> dataListe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lists = findViewById(R.id.liste);
        dataListe = new ArrayList<>();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();
        verilerCek();

        lists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if ((dataListe.get(i) == null))
                    Toast.makeText(MainActivity.this, "Hata", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "ID : " + dataListe.get(i).get_CountryID(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void verilerCek() {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            JSONArray jsonArray = jsonObject.getJSONArray("CountryResult");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject obj = jsonArray.getJSONObject(i);

                                Data datam = new Data(obj.getString("_BinaryCode"), obj.getString("_CountryName"), obj.getInt("_CountryID"));

                                dataListe.add(datam);
                                //Log.e("Result",""+obj.getInt("_CountryID"));

                            }
                            CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), dataListe);

                            lists.setAdapter(customAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        progressDialog.dismiss();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(!internetKontrol()) {
                    Toast.makeText(getApplicationContext(), "İNTERNET YOK!", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }

                }
        });
        /**
         * RequestQueue : istek kuyruğu
         * */
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

        requestQueue.add(stringRequest);
    }
    /**
     * İnternet kontrolü yapılıyor!
     * */
    protected boolean internetKontrol() { //interneti kontrol eden method
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }
}