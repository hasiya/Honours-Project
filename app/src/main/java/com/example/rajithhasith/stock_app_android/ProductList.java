package com.example.rajithhasith.stock_app_android;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.*;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import im.delight.android.ddp.Meteor;
import im.delight.android.ddp.MeteorCallback;


public class ProductList extends ActionBarActivity implements MeteorCallback {

    private Meteor mMeteor;
    Product product;
    Intent i = this.getIntent();

    ArrayList<Product> productList;
    ArrayList<Product> tierProductList;

    private ListView ProductListView;
    Context context;

    int TierNo = 1;

    public static ArrayList<String> ProductListNames;

    public ArrayList<String> getPorductList(ArrayList<Product> product_list){
        ArrayList<String> list = new ArrayList<String>();

        for(int i=0; i < product_list.size(); i++){
            Product tmpProduct = product_list.get(i);

            String name = tmpProduct.name + "("+ tmpProduct.size +")";
            list.add(name);
        }
        return list;
    }

    public ArrayList<Product> getTierList(int tierNo, ArrayList<Product> fullProductList){
        ArrayList<Product> tierList = new ArrayList<>();

        for(int i=0; i < fullProductList.size(); i++){
            Product tempProduct = fullProductList.get(i);

            if(tempProduct.isOnShelf()){
                if(tempProduct.getTierNo() == tierNo){
                    tierList.add(tempProduct);
                }
            }

        }
        return tierList;


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        ImageButton tierListNxt_btn = (ImageButton)findViewById(R.id.tier_list_nxt);
        ImageButton tierListPrev_btn = (ImageButton)findViewById(R.id.tier_list_prev);

        productList = getIntent().getParcelableArrayListExtra("ProductList");

        if(getIntent().getExtras().containsKey("tireNo")){
            TierNo = getIntent().getExtras().getInt("tireNo");
        }

        if(TierNo == 1){
            tierListPrev_btn.setEnabled(false);
        }else{
            tierListPrev_btn.setEnabled(true);
        }

        if(TierNo == 4){
            tierListNxt_btn.setEnabled(false);
        }else{
            tierListNxt_btn.setEnabled(true);
        }

        if(TierNo < 1){
            TierNo = 1;
        }

        TextView tierText = (TextView) findViewById(R.id.tier_no_txt);
        tierText.setText("Tier No. " + TierNo);


        tierProductList = getTierList(TierNo, productList);

        context = this;

        ProductListView = (ListView) findViewById(R.id.id_product_list);
        ProductListView.setAdapter(new CustomAdapter(this, tierProductList, TierNo));

        /*mMeteor = new Meteor("ws://178.62.44.95:3000/websocket");
        mMeteor.setCallback(this);*/


        tierListNxt_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProductList.this, ProductList.class);
                i.putParcelableArrayListExtra("ProductList", productList);
                i.putExtra("tireNo", TierNo+1);
                startActivity(i);
            }
        });


        tierListPrev_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProductList.this, ProductList.class);
                i.putParcelableArrayListExtra("ProductList", productList);
                i.putExtra("tireNo", TierNo-1);
                startActivity(i);
            }
        });


    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMeteor.disconnect();
        Log.d("Meteor", "disconnected");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_product_list, menu);
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
        System.out.println("Test");

        //final ListView listview = (ListView) findViewById(R.id.listview);

       /* JSONObject ProductJson = null;
        try {
            ProductJson = new JSONObject(fieldsJson);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String name = null;
        String price = null;
        try {
            name = ProductJson.getString("name");
            price = ProductJson.getString("price");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        product.setId(documentID);
        product.setName(name);
        product.setPrice(price);

        productList.add(product);

        RelativeLayout R_layout = (RelativeLayout)findViewById(R.id.Rlaout);

        *//*LinearLayout layout = new LinearLayout(this);
       // setContentView(layout);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setId(id);
        if(id != 1){
            layout
        }*//*

        ///RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        //p.addRule(RelativeLayout.BELOW, R.id.below_id);
        TextView tv = new TextView(this);
        tv.setText(product.getName());
        //tv.setId(id);
        tv.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT));
        tv.setPadding(0,padding,0,0);
        tv.setTextSize(20);
       // layout.addView(tv);

        R_layout.addView(tv);
        padding = padding+30;

*/
        //lis





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
