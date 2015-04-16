package com.example.rajithhasith.stock_app_android;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import im.delight.android.ddp.Meteor;


public class Stock_fill_menu extends ActionBarActivity {

    ArrayList<Product> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_fill_menu);

        final Meteor m =  MeteorDDP_Connection.mMeteor;

        productList = getIntent().getParcelableArrayListExtra("ProductList");

        Button fillCount_btn = (Button)findViewById(R.id.id_stock_fill_count);
        fillCount_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Stock_fill_menu.this, ProductList.class);
                i.putParcelableArrayListExtra("ProductList", productList);
                finish();
                startActivity(i);
            }
        });



        Button coutnReset_btn = (Button)findViewById(R.id.id_stock_fill_reset);
        coutnReset_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m.call("resetProductCounts");
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(Stock_fill_menu.this, MainActivity.class);
        //i.putParcelableArrayListExtra("ProductList", productList);
        finish();
        startActivity(i);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_stock_fill_menu, menu);
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
