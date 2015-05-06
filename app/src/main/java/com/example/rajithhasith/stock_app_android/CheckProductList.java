package com.example.rajithhasith.stock_app_android;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;


public class CheckProductList extends ActionBarActivity {

    Functions functions;
    ArrayList<Product> checkProductsList;

    private ListView CheckoutListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_product_list);

        functions = new Functions();

        checkProductsList = functions.getFillProductsList();

        CheckoutListView = (ListView)findViewById(R.id.id_product_check_list);
        CheckoutListView.setAdapter(new ProductCheckListAdapter(this,checkProductsList));





    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(CheckProductList.this, Stock_fill_menu.class);
        finish();
        startActivity(i);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_check_product_list, menu);
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
