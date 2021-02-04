package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TableLayout;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AdminActivity extends AppCompatActivity {

    ListView listMobiles;
    List<Mobile> mobiles;
    MobileListAdapter mobilesAdapter;

    // Get list of mobiles from API server
    public void getMobiles(){
        // Define the HTTP request
        HttpClient clientHttp = new DefaultHttpClient();
        HttpGet get = new HttpGet("http://10.0.2.2:8080/MoralesAguileraCarlosSanchezMunozCarlosSantiago/api/mobiles");

        try {
            // Execute the request
            HttpResponse response = clientHttp.execute(get);
            // Get response
            String responseText = EntityUtils.toString(response.getEntity());
            // Decode response into mobile objects
            Gson decoder = new Gson();
            mobiles = decoder.fromJson(responseText, new TypeToken<List<Mobile>>(){}.getType());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Delete mobile from list of mobiles from API server
    public void deleteMobile(long id){
        // Define the HTTP request
        HttpClient clientHttp = new DefaultHttpClient();
        HttpDelete delete = new HttpDelete("http://10.0.2.2:8080/MoralesAguileraCarlosSanchezMunozCarlosSantiago/api/mobiles/" + Long.toString(id));

        try {
            // Execute the request
            HttpResponse response = clientHttp.execute(delete);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        // Change SupportActionBar title
        getSupportActionBar().setTitle("Shop Panel");

        // Layout elements
        Button newMobileButton = this.findViewById(R.id.new_mob_but);
        Button logoutButton = this.findViewById(R.id.log_out_but);
        listMobiles = this.findViewById(R.id.list_mobiles);

        mobiles = new ArrayList<Mobile>();
        // Get list of mobiles available in shop
        getMobiles();

        mobilesAdapter = new MobileListAdapter(this, mobiles, 0);
        listMobiles.setAdapter(mobilesAdapter);

        // Define item click listener for list of mobiles
        listMobiles.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AlertDialog alertDialog = new AlertDialog.Builder(AdminActivity.this).create();
                alertDialog.setIcon(android.R.drawable.ic_delete);
                alertDialog.setTitle("Removal confirmation.");
                alertDialog.setMessage("Are you sure you want to remove this mobile?");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int j) {
                        alertDialog.dismiss();

                        // Delete mobile in server
                        deleteMobile(mobiles.get(i).getId());
                        mobiles.remove(i);
                        mobilesAdapter.notifyDataSetChanged();
                    }
                });
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int j) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        });

        // Define new mobile button behaviour
        newMobileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminActivity.this, CreateMobActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });

        // Define logout button behaviour
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(AdminActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });
    }
}