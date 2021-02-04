package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

import java.io.IOException;

public class LogClientActivity extends AppCompatActivity {

    // Client login into API server
    public void loginClient(String email, String password){
        // Define the HTTP request with parameters
        HttpParams parameters = new BasicHttpParams();
        parameters.setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
        HttpClient clientHttp = new DefaultHttpClient(parameters);
        HttpPost post = new HttpPost("http://10.0.2.2:8080/MoralesAguileraCarlosSanchezMunozCarlosSantiago/api/clients/login");

        // HTTP POST parameters
        Gson encoder = new Gson();
        EmailPass email_pass = new EmailPass(email, password);

        try {
            // Define request header
            post.addHeader("Accept", "application/json");
            post.addHeader("Content-Type", "application/json");
            // Define request body
            post.setEntity(new StringEntity(encoder.toJson(email_pass)));

            // Execute the request
            HttpResponse response = clientHttp.execute(post);
            // Get status code
            int responseCode = response.getStatusLine().getStatusCode();

            // Check success of the request
            if(responseCode == 201){
                Intent intent = new Intent(LogClientActivity.this, ClientActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);

                // Save client
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("Email",email);
                editor.apply();
            }else{
                AlertDialog alertDialog = new AlertDialog.Builder(LogClientActivity.this).create();
                alertDialog.setIcon(R.drawable.warning);
                alertDialog.setTitle("Login error");
                alertDialog.setMessage("Email and password doesn't match any client.");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }

            Log.i("Post", "Se ha ejecutado.");


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_client);

        // Change SupportActionBar title
        getSupportActionBar().setTitle("Login");

        // Layout elements
        Button logClientButton = this.findViewById(R.id.log_cli_conf_but);
        TextInputEditText emailText = this.findViewById(R.id.email_input);
        TextInputEditText passwordText = this.findViewById(R.id.password_input);

        // Define login button behaviour
        logClientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginClient(emailText.getText().toString(), passwordText.getText().toString());
            }
        });
    }
}