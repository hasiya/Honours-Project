package com.example.rajithhasith.stock_app_android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.security.PublicKey;
import java.util.ArrayList;

/**
 * Created by Rajith Hasith on 03/04/2015.
 */
public class CustomAdapter extends BaseAdapter {

    Context context;
    ArrayList<Product> ProductList;
    private static LayoutInflater inflater=null;

    public CustomAdapter(Activity activity, ArrayList<Product> list) {

        ProductList = list;
        context = activity;

        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return ProductList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class Holder {
        TextView tv;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        Holder holder = new Holder();

        View rowView;

        rowView = inflater.inflate(R.layout.list_item,null);
        holder.tv = (TextView)rowView.findViewById(R.id.id_list_text);

        holder.tv.setText(ProductList.get(position).name);

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(context, Product_fill.class);
                i.putExtra("Product", ProductList.get(position));
                context.startActivity(i);
               // Toast.makeText(context, "You Clicked " + ProductList.get(position).name + "("+ProductList.get(position).id+")", Toast.LENGTH_SHORT).show();
            }
        });

        return rowView;
    }
}
