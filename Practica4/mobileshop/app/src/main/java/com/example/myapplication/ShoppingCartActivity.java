package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCartActivity extends AppCompatActivity {

    ListView listMobiles;
    List<Mobile> mobiles;
    MobileListAdapter mobilesAdapter;

    // Get list of mobiles in shopping cart from API server
    public void getShoppingCart(){
        // Define the HTTP request
        HttpParams parameters = new BasicHttpParams();
        parameters.setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
        HttpClient clientHttp = new DefaultHttpClient();
        HttpPost post = new HttpPost("http://10.0.2.2:8080/MoralesAguileraCarlosSanchezMunozCarlosSantiago/api/shoppingcart/list");

        // Get current client logged in system
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String client = preferences.getString("Email", "");

        try {
            // Define request header
            post.addHeader("Accept", "application/json");
            post.addHeader("Content-Type", "application/json");

            // Define request body
            post.setEntity(new StringEntity(client));

            // Execute the request
            HttpResponse response = clientHttp.execute(post);
            // Get response
            String responseText = EntityUtils.toString(response.getEntity());

            int responseCode = response.getStatusLine().getStatusCode();
            System.out.println(client);
            System.out.println(responseCode);
            System.out.println(responseText);

            // Decode response into mobile objects
            Gson decoder = new Gson();
            mobiles = decoder.fromJson(responseText, new TypeToken<List<Mobile>>(){}.getType());

            System.out.println(mobiles.toString());
            for(Mobile m:mobiles){
                System.out.println(m.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Delete mobile from list of shopping cart from API server
    public void deleteItemFromShoppingCart(long id){
        // Define the HTTP request
        HttpClient clientHttp = new DefaultHttpClient();
        HttpDelete delete = new HttpDelete("http://10.0.2.2:8080/MoralesAguileraCarlosSanchezMunozCarlosSantiago/api/shoppingcart/" + Long.toString(id));

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
        setContentView(R.layout.activity_shopping_cart);

        // Change SupportActionBar title
        getSupportActionBar().setTitle("Shopping Cart");

        // Layout elements
        Button backButton = this.findViewById(R.id.back_button);
        listMobiles = this.findViewById(R.id.list_mobiles);

        mobiles = new ArrayList<Mobile>();
        System.out.println("Antes");
        getShoppingCart();

        mobilesAdapter = new MobileListAdapter(this, mobiles, 1);
        listMobiles.setAdapter(mobilesAdapter);

        // Define item click listener for list of mobiles in shopping cart
        listMobiles.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AlertDialog alertDialog = new AlertDialog.Builder(ShoppingCartActivity.this).create();
                alertDialog.setIcon(android.R.drawable.ic_delete);
                alertDialog.setTitle("Discard confirmation.");
                alertDialog.setMessage("Are you sure you want to discard this mobile from shopping cart?");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int j) {
                        alertDialog.dismiss();

                        // Delete mobile from shopping cart in server
                        deleteItemFromShoppingCart(mobiles.get(i).getId());
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

        // Define back button behaviour
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(ShoppingCartActivity.this, ClientActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });
    }
}