package com.example.rajithhasith.stock_app_android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.*;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import im.delight.android.ddp.Meteor;
import im.delight.android.ddp.MeteorCallback;


public class MainActivity extends ActionBarActivity implements MeteorCallback {

    Meteor mMeteor;

    Product product;
    ArrayList productList = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMeteor = new Meteor("ws://178.62.44.95:3000/websocket");
        mMeteor.setCallback(this);


        Button btn_StockFilling = (Button)findViewById(R.id.id_stock_filling);
        btn_StockFilling.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Stock_fill_menu.class);
                i.putParcelableArrayListExtra("ProductList", productList);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMeteor.disconnect();
        Log.d("Meteor","disconnected");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConnect() {
        Log.d("Meteor","connected");
        //System.out.println("Connected");

        // subscribe to data from the server
        mMeteor.subscribe("products");
        mMeteor.subscribe("barcodes");

    }

    @Override
    public void onDisconnect(int i, String s) {
        System.out.println("Disconnected");
    }

    @Override
    public void onDataAdded(String collectionName, String documentID, String fieldsJson) {
        System.out.println("Data added to <"+collectionName+"> in document <"+documentID+">");
        System.out.println("    Added: "+fieldsJson);

        if(collectionName.equals("products")) {

            JSONObject ProductJson = null;
            try {
                ProductJson = new JSONObject(fieldsJson);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String id = null;
            String name = null;
            String size = null;
            String price = null;
            String imageID = null;
            boolean onShelf = false;
            int tierNo = 0;
            int leftPosition = 0;
            int noOfColumns = 0;
            int defaultOrderQuant = 0;
            int tmpOrderQuant = 0;

            try {
                id = documentID;
                name = ProductJson.getString("name");
                price = ProductJson.getString("price");
                size = ProductJson.getString("size");
                imageID = ProductJson.getString("picture");
                defaultOrderQuant = ProductJson.getInt("defaultOrderQuant");
                JSONObject position = ProductJson.getJSONObject("position");
                onShelf = position.getBoolean("onShelf");
                tierNo = position.getInt("tierNo");
                leftPosition = position.getInt("leftPosition");
                noOfColumns = position.getInt("noOfCols");
            } catch (JSONException e) {
                e.printStackTrace();
            }


            product = new Product(id, name, size, price, defaultOrderQuant, tmpOrderQuant, onShelf, tierNo, leftPosition, noOfColumns, imageID);

            productList.add(product);
        }

    }

    @Override
    public void onDataChanged(String collectionName, String documentID, String updatedValuesJson, String removedValuesJson) {
        System.out.println("Data changed in <"+collectionName+"> in document <"+documentID+">");
        System.out.println("    Updated: "+updatedValuesJson);
        System.out.println("    Removed: "+removedValuesJson);
    }

    @Override
    public void onDataRemoved(String collectionName, String documentID) {
        System.out.println("Data removed from <"+collectionName+"> in document <"+documentID+">");
    }

    @Override
    public void onException(Exception e) {
        System.out.println("Exception");
        if (e != null) {
            e.printStackTrace();
        }
    }
}
