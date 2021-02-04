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
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class RegAdminActivity extends AppCompatActivity {

    // Admin register into API server
    public void registerAdmin(String email, String password){
        // Define the HTTP request with parameters
        HttpParams parameters = new BasicHttpParams();
        parameters.setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
        HttpClient clientHttp = new DefaultHttpClient(parameters);
        HttpPost post = new HttpPost("http://10.0.2.2:8080/MoralesAguileraCarlosSanchezMunozCarlosSantiago/api/admins/register");

        // HTTP POST parameters
        Gson encoder = new Gson();
        EmailPass emailPass = new EmailPass(email, password);

        try {
            // Define request header
            post.addHeader("Accept", "application/json");
            post.addHeader("Content-Type", "application/json");
            // Define request body
            post.setEntity(new StringEntity(encoder.toJson(emailPass)));

            // Execute the request
            HttpResponse response = clientHttp.execute(post);
            // Get status code
            int responseCode = response.getStatusLine().getStatusCode();

            // Check success of the request
            if(responseCode == 201){
                finish();
                Intent intent = new Intent(RegAdminActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }else{
                AlertDialog alertDialog = new AlertDialog.Builder(RegAdminActivity.this).create();
                alertDialog.setIcon(R.drawable.warning);
                alertDialog.setTitle("Register error");
                alertDialog.setMessage("Email is already registered.");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_admin);

        // Change SupportActionBar title
        getSupportActionBar().setTitle("Register");

        // Layout elements
        Button regAdminButton = this.findViewById(R.id.reg_adm_conf_but);
        TextInputEditText emailText = this.findViewById(R.id.email_input);
        TextInputEditText passwordText = this.findViewById(R.id.password_input);

        // Define register button behaviour
        regAdminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerAdmin(emailText.getText().toString(), passwordText.getText().toString());
            }
        });
    }
}