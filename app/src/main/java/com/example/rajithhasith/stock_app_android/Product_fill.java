package com.example.rajithhasith.stock_app_android;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import im.delight.android.ddp.Meteor;
import im.delight.android.ddp.MeteorCallback;


public class Product_fill extends ActionBarActivity implements MeteorCallback {

    Meteor mMeteor;

    Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_fill);

        mMeteor = new Meteor("ws://178.62.44.95:3000/websocket");
        mMeteor.setCallback(this);

        product = getIntent().getParcelableExtra("Product");

        TextView productName = (TextView) findViewById(R.id.product_fill_name);
        productName.setText(product.getName()+" ("+product.getSize()+")");

        final EditText updateFillEdit = (EditText)findViewById(R.id.product_fill_number);

        final Button update_btn = (Button) findViewById(R.id.product_fill_update_btn);
        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Object data[];

                //EditText updateFillEdit = (EditText)findViewById(R.id.product_fill_number);
                int productFillNumber = Integer.parseInt(updateFillEdit.getText().toString());

                data = new Object[]{product.getId(), productFillNumber};

                mMeteor.call("updateProductFill",data);*/
            }
        });

        updateFillEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String ns = "s";

                Object data[];
                int productFillNumber = 0;
                //EditText updateFillEdit = (EditText)findViewById(R.id.product_fill_number);
                if(s.toString() != null && !s.toString().isEmpty()) {
                    try{
                        productFillNumber = Integer.parseInt(s.toString());
                    }catch (Exception e){
//                        productFillNumber = 0L;
                        String newVal = s.toString().substring(0,start);
                        productFillNumber = Integer.parseInt(newVal);
                        updateFillEdit.setText(newVal);
                        updateFillEdit.setSelection(updateFillEdit.length());
                    }

                    /*Long.parseLong()
                    if(productFillNumber > 2147483647)*/

                }

                data = new Object[]{product.getId(), productFillNumber};

                mMeteor.call("updateProductFill",data);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_product_fill, menu);
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

    }

    @Override
    public void onDisconnect(int i, String s) {

    }

    @Override
    public void onDataAdded(String s, String s2, String s3) {

    }

    @Override
    public void onDataChanged(String s, String s2, String s3, String s4) {

    }

    @Override
    public void onDataRemoved(String s, String s2) {

    }

    @Override
    public void onException(Exception e) {

    }
}
