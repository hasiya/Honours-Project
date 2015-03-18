package com.example.rajithhasith.stock_app_android;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.*;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import im.delight.android.ddp.Meteor;
import im.delight.android.ddp.MeteorCallback;


public class MainActivity extends ActionBarActivity implements MeteorCallback {

    private Meteor mMeteor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMeteor = new Meteor("ws://178.62.44.95:3000/websocket");
        mMeteor.setCallback(this);
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
        String subscriptionId = mMeteor.subscribe("products");

        // unsubscribe from data again (usually done later or not at all)
        //mMeteor.unsubscribe(subscriptionId);

        // insert data into a collection
        /*Map<String, Object> insertValues = new HashMap<String, Object>();
        //insertValues.put("_id", "bla");
        insertValues.put("name", "from android");
        insertValues.put("size", "nice");
        insertValues.put("price", "nice");
        mMeteor.insert("products", insertValues);*/

        /*JSONObject json = new JSONObject();
        try {
            json.put("name","boola");
            json.put("size","200");
            json.put("price","2.00");
        } catch (JSONException e) {
            e.printStackTrace();
        }*/



        //String name = "Boola";
        String [] Data;
        // Data = new String[]{"azsxdfgTEST", "600", "2.00"};

        // update data in a collection
       /* Map<String, Object> updateQuery = new HashMap<String, Object>();
        updateQuery.put("_id", "bla");
        Map<String, Object> updateValues = new HashMap<String, Object>();
        insertValues.put("_id", "my-key");
        insertValues.put("name", 5);
        mMeteor.update("products", updateQuery, updateValues);*/

        // remove data from a collection
        //mMeteor.remove("my-collection", "my-key");

        // call an arbitrary method
//        mMeteor.call("addProduct",Data);
    }

    @Override
    public void onDisconnect(int i, String s) {
        System.out.println("Disconnected");
    }

    @Override
    public void onDataAdded(String collectionName, String documentID, String fieldsJson) {
        System.out.println("Data added to <"+collectionName+"> in document <"+documentID+">");
        System.out.println("    Added: "+fieldsJson);

       /* View linearLayout =  findViewById(R.id.info);

        String name = null;
        String size;
        String price;

        JSONObject jObject;

        try {
            jObject  = new JSONObject(fieldsJson);
            name = jObject.getString("name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        TextView tv1 = new TextView(this);
        tv1.setText(name);
        setContentView(tv1);

        tv1.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));

        ((LinearLayout) linearLayout).addView(tv1);*/

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
