package com.example.rajithhasith.stock_app_android;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

import im.delight.android.ddp.Meteor;
import im.delight.android.ddp.MeteorCallback;


public class Product_fill_count extends ActionBarActivity{

    Meteor mMeteor = MeteorDDP_Connection.mMeteor;
    Functions functions;

    ImageFromURL getImages;
    Product product;
    int TierNo;
    int TierPos;
    int TierSize;
    ArrayList<Product> tierProductList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_fill_count);

        functions = new Functions();

        product = getIntent().getParcelableExtra("Product");
        TierNo = getIntent().getExtras().getInt("tierNo");
        TierPos = getIntent().getExtras().getInt("tierPos");

        tierProductList = functions.getTierList(TierNo, MeteorDDP_Connection.productList);
        Collections.sort(tierProductList, Product.productLeftPos);

        ImageButton nxtProduct_btn = (ImageButton)findViewById(R.id.product_nxt);
        ImageButton prevProduct_btn =(ImageButton)findViewById(R.id.product_prev);

        if(TierNo == 1){
            if(TierPos == 0){
                prevProduct_btn.setEnabled(false);
            }
            else{
                prevProduct_btn.setEnabled(true);
            }
        }else{
            prevProduct_btn.setEnabled(true);
        }

        if(TierNo == 4){
            if(TierPos == (tierProductList.size()-1)){
                nxtProduct_btn.setEnabled(false);
            }
            else{
                nxtProduct_btn.setEnabled(true);
            }
        }
        else {
            nxtProduct_btn.setEnabled(true);
        }

        TextView imgLoadTxt = (TextView) findViewById(R.id.image_loading_txt);
        ImageView productImage = (ImageView) findViewById(R.id.product_fill_image);
        getImages = new ImageFromURL(productImage, imgLoadTxt);
        getImages.execute("http://178.62.29.188:3000/cfs/files/images/" + product.getImageID());


        TextView productName = (TextView) findViewById(R.id.product_fill_name);
        productName.setText("Name: " + product.getName());

        TextView productSize = (TextView) findViewById(R.id.product_fill_size);
        productSize.setText("Size: " + product.getSize());

        final TextView productPrice = (TextView) findViewById(R.id.product_fill_price);
        productPrice.setText("Price: " + product.getPrice());


        final EditText updateFillEdit = (EditText) findViewById(R.id.product_need_number);
        if (product.getNeedQuant() > 0) {
            updateFillEdit.setText("" + product.getNeedQuant());
            updateFillEdit.setSelection(updateFillEdit.length());
        } else {
            updateFillEdit.setText("");
        }

        updateFillEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String ns = "s";

                Object data[];
                int productNeedQuant = 0;
                //EditText updateFillEdit = (EditText)findViewById(R.id.product_fill_number);
                if (s.toString() != null && !s.toString().isEmpty()) {
                    try {
                        productNeedQuant = Integer.parseInt(s.toString());
                    } catch (Exception e) {
//                        productFillNumber = 0L;
                        String newVal = s.toString().substring(0, start);
                        productNeedQuant = Integer.parseInt(newVal);
                        updateFillEdit.setText(newVal);
                        updateFillEdit.setSelection(updateFillEdit.length());
                    }

                    /*Long.parseLong()
                    if(productFillNumber > 2147483647)*/

                }

                data = new Object[]{product.getId(), productNeedQuant};

                mMeteor.call("updateProductNeed", data);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });




        nxtProduct_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Product_fill_count.this, Product_fill_count.class);
                if(TierPos < tierProductList.size()-1){
                    i.putExtra("Product", tierProductList.get(TierPos+1));
                    i.putExtra("tierNo", TierNo);
                    i.putExtra("tierPos", TierPos+1);
                    finish();
                    startActivity(i);
                }else{
                    ArrayList<Product> nxtTierProductList = functions.getTierList(TierNo+1, MeteorDDP_Connection.productList);
                    Collections.sort(nxtTierProductList, Product.productLeftPos);
                    i.putExtra("Product", nxtTierProductList.get(0));
                    i.putExtra("tierNo", TierNo+1);
                    i.putExtra("tierPos", 0);
                    finish();
                    startActivity(i);
                }
            }
        });

        prevProduct_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Product_fill_count.this, Product_fill_count.class);
                if(TierPos == 0){
                    ArrayList<Product> prevTierProductList = functions.getTierList(TierNo-1, MeteorDDP_Connection.productList);
                    Collections.sort(prevTierProductList, Product.productLeftPos);
                    i.putExtra("Product", prevTierProductList.get(prevTierProductList.size()-1));
                    i.putExtra("tierNo", TierNo-1);
                    i.putExtra("tierPos", prevTierProductList.size()-1);
                    finish();
                    startActivity(i);
                }else{
                    i.putExtra("Product", tierProductList.get(TierPos-1));
                    i.putExtra("tierNo", TierNo);
                    i.putExtra("tierPos", TierPos-1);
                    finish();
                    startActivity(i);
                }
            }
        });


    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(Product_fill_count.this, ProductList.class);
        i.putExtra("tireNo", TierNo);
        finish();
        startActivity(i);
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


}