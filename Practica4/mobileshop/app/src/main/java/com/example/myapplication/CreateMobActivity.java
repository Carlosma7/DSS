package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class CreateMobActivity extends AppCompatActivity {

    // Create mobile into list of mobiles from API server
    public void createMobile(String brand, String model, String os, String color, String year, String price){
        // Define the HTTP request with parameters
        HttpParams parameters = new BasicHttpParams();
        parameters.setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
        HttpClient clientHttp = new DefaultHttpClient(parameters);
        HttpPost post = new HttpPost("http://10.0.2.2:8080/MoralesAguileraCarlosSanchezMunozCarlosSantiago/api/mobiles");

        // HTTP POST parameters
        Gson encoder = new Gson();
        Mobile mobile = new Mobile(brand, model, os, color, Integer.parseInt(year), Integer.parseInt(price));

        try{
            // Define request header
            post.addHeader("Accept", "application/json");
            post.addHeader("Content-Type", "application/json");
            // Define request body
            post.setEntity(new StringEntity(encoder.toJson(mobile)));

            // Execute the request
            HttpResponse response = clientHttp.execute(post);

            // AlertDialog to notify successful request
            AlertDialog alertDialog = new AlertDialog.Builder(CreateMobActivity.this).create();
            alertDialog.setIcon(R.drawable.verify);
            alertDialog.setTitle("Mobile registered");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();
                            Intent intent = new Intent(CreateMobActivity.this, AdminActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                            startActivity(intent);
                        }
                    });
            alertDialog.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_mob);

        // Change SupportActionBar title
        getSupportActionBar().setTitle("Register new mobile");

        // Layout elements
        Button saveButton = this.findViewById(R.id.save_but);
        TextInputEditText brandText = this.findViewById(R.id.brand_input);
        TextInputEditText modelText = this.findViewById(R.id.model_input);
        TextInputEditText osText = this.findViewById(R.id.os_input);
        TextInputEditText colorText = this.findViewById(R.id.color_input);
        TextInputEditText yearText = this.findViewById(R.id.year_input);
        TextInputEditText priceText = this.findViewById(R.id.price_input);

        // Define save button behaviour
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createMobile(brandText.getText().toString(), modelText.getText().toString(),
                        osText.getText().toString(), colorText.getText().toString(),
                        yearText.getText().toString(), priceText.getText().toString());
            }
        });
    }
}