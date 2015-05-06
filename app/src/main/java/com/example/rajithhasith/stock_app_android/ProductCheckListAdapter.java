package com.example.rajithhasith.stock_app_android;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import im.delight.android.ddp.Meteor;

/**
 * Created by Rajith Hasith on 05/05/2015.
 */
public class ProductCheckListAdapter extends BaseAdapter {

    private static LayoutInflater inflater=null;
    Context context;
    ArrayList<Product> CheckList;
    int TirtNo;

    Meteor mMeteor = MeteorDDP_Connection.mMeteor;

    public ProductCheckListAdapter(Activity activity, ArrayList<Product> list) {
        CheckList = list;
        context = activity;

        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return CheckList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        final Holder holder = new Holder();

        View itemView = inflater.inflate(R.layout.check_list_item,null);

        holder.iv_image = (ImageView)itemView.findViewById(R.id.product_check_image);
        holder.tv_name = (TextView)itemView.findViewById(R.id.check_list_name_txt);
        holder.tv_needCount = (TextView)itemView.findViewById(R.id.need_quantity_txt);
        holder.checkBox = (CheckBox)itemView.findViewById(R.id.fill_check);
        holder.et_fillCount = (EditText)itemView.findViewById(R.id.product_fill_number);
        holder.order_check = (CheckBox)itemView.findViewById(R.id.Order_check);

        holder.tv_imgLrd = (TextView) itemView.findViewById(R.id.product_check_image_load);

        ImageFromURL getImages = new ImageFromURL(holder.iv_image, holder.tv_imgLrd);
        getImages.execute("http://178.62.44.95:3000/cfs/files/images/" + CheckList.get(position).getImageID());
        holder.tv_name.setText(CheckList.get(position).getName()+"("+CheckList.get(position).getSize()+")");
        holder.tv_needCount.setText(""+CheckList.get(position).getNeedQuant());
        /********Checkbox controls goes here*******/
        if(CheckList.get(position).getFillQuantity()>0){
            holder.et_fillCount.setText(""+CheckList.get(position).getFillQuantity());
            holder.et_fillCount.setSelection(holder.et_fillCount.length());
        }else {
            holder.et_fillCount.setText("");
        }


        if(CheckList.get(position).isOrder()){
            holder.order_check.setChecked(true);
        }else{
            holder.order_check.setChecked(false);
        }


        if(CheckList.get(position).getNeedQuant() <= CheckList.get(position).getFillQuantity()){
            holder.checkBox.setChecked(true);
        }else{
            holder.checkBox.setChecked(false);
        }


        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    holder.et_fillCount.setText(""+CheckList.get(position).getNeedQuant());
                    holder.et_fillCount.setSelection(holder.et_fillCount.length());
                    int ProductFillQuant = CheckList.get(position).needQuant;

                    Object data[] = new Object[]{CheckList.get(position).getId(), ProductFillQuant};
                    mMeteor.call("updateProductFill",data);
                }else {
                    holder.et_fillCount.setText("");
                    holder.et_fillCount.setSelection(holder.et_fillCount.length());

                    Object data[] = new Object[]{CheckList.get(position).getId(), 0};
                    mMeteor.call("updateProductFill",data);
                }
            }
        });

        holder.et_fillCount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                int ProductFillQuant = 0;
                //EditText updateFillEdit = (EditText)findViewById(R.id.product_fill_number);
                if (s.toString() != null && !s.toString().isEmpty()) {
                    try {
                        ProductFillQuant = Integer.parseInt(s.toString());
                    } catch (Exception e) {
                        String newVal = s.toString().substring(0, start);
                        ProductFillQuant = Integer.parseInt(newVal);
                        holder.et_fillCount.setText(newVal);
                        holder.et_fillCount.setSelection(holder.et_fillCount.length());
                    }

                }

                Object data[] = new Object[]{CheckList.get(position).getId(), ProductFillQuant};
                mMeteor.call("updateProductFill",data);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        holder.order_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Object data[] = new Object[]{CheckList.get(position).getId(), isChecked};
                mMeteor.call("setOrderCheck",data);
            }
        });




        return itemView;
    }

    public class Holder {
        ImageView iv_image;
        TextView tv_name;
        TextView tv_needCount;
        CheckBox checkBox;
        EditText et_fillCount;
        TextView tv_imgLrd;
        CheckBox order_check;
    }
}
