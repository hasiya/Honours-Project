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
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import im.delight.android.ddp.Meteor;
import im.delight.android.ddp.MeteorCallback;


public class ProductList extends ActionBarActivity {

    Functions functions;

    Product product;
    Intent i = this.getIntent();

   // ArrayList<Product> productList;
    ArrayList<Product> tierProductList;

    private ListView ProductListView;
    Context context;

    int TierNo = 1;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        functions = new Functions();

        ImageButton tierListNxt_btn = (ImageButton)findViewById(R.id.tier_list_nxt);
        ImageButton tierListPrev_btn = (ImageButton)findViewById(R.id.tier_list_prev);

        //productList = MeteorDDP_Connection.productList;

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


        tierProductList = functions.getTierList(TierNo, MeteorDDP_Connection.productList);
        Collections.sort(tierProductList, Product.productLeftPos);

        context = this;

        ProductListView = (ListView) findViewById(R.id.id_product_list);
        ProductListView.setAdapter(new ProductListAdapter(this, tierProductList, TierNo));



        tierListNxt_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProductList.this, ProductList.class);
                i.putParcelableArrayListExtra("ProductList", MeteorDDP_Connection.productList);
                i.putExtra("tireNo", TierNo+1);
                finish();
                startActivity(i);
            }
        });


        tierListPrev_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProductList.this, ProductList.class);
                i.putParcelableArrayListExtra("ProductList", MeteorDDP_Connection.productList);
                i.putExtra("tireNo", TierNo-1);
                finish();
                startActivity(i);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(ProductList.this, Stock_fill_menu.class);
        i.putParcelableArrayListExtra("ProductList", MeteorDDP_Connection.productList);
        finish();
        startActivity(i);
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






}
