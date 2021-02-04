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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClientActivity extends AppCompatActivity {

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

    // Add mobile to shopping cart from API server
    public void addToShoppingCart(long mobile_id){
        // Define the HTTP request with parameters
        HttpParams parameters = new BasicHttpParams();
        parameters.setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
        HttpClient clientHttp = new DefaultHttpClient(parameters);
        HttpPost post = new HttpPost("http://10.0.2.2:8080/MoralesAguileraCarlosSanchezMunozCarlosSantiago/api/shoppingcart/add");

        // Get current client logged in system
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String client = preferences.getString("Email", "");

        // HTTP POST parameters
        Gson encoder = new Gson();
        String mobile = Long.toString(mobile_id);
        ShoppingCart shoppingCart = new ShoppingCart(client, mobile);

        try {
            // Define request header
            post.addHeader("Accept", "application/json");
            post.addHeader("Content-Type", "application/json");
            // Define request body
            post.setEntity(new StringEntity(encoder.toJson(shoppingCart)));

            // Execute the request
            HttpResponse response = clientHttp.execute(post);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        // Change SupportActionBar title
        getSupportActionBar().setTitle("Shop");

        // Layout elements
        Button logoutButton = this.findViewById(R.id.log_out_but);
        Button shoppingCartButton = this.findViewById(R.id.shopping_cart_but);
        listMobiles = this.findViewById(R.id.list_mobiles);

        mobiles = new ArrayList<Mobile>();
        // Get list of mobiles available in shop
        getMobiles();

        mobilesAdapter = new MobileListAdapter(this, mobiles, 1);
        listMobiles.setAdapter(mobilesAdapter);

        // Define item click listener for list of mobiles
        listMobiles.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AlertDialog alertDialog = new AlertDialog.Builder(ClientActivity.this).create();
                alertDialog.setIcon(R.drawable.cart);
                alertDialog.setTitle("Add to Shopping Cart.");
                alertDialog.setMessage("Do you want to add this mobile to the shopping cart?");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int j) {
                        alertDialog.dismiss();
                        // Add mobile to shopping cart in server
                        addToShoppingCart(mobiles.get(i).getId());
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

        // Define logout button behaviour
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(ClientActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });

        // Define shopping cart button behaviour
        shoppingCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ClientActivity.this, ShoppingCartActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });
    }
}