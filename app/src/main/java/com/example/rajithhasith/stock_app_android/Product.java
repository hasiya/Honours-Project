package com.example.rajithhasith.stock_app_android;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Rajith Hasith on 19/03/2015.
 */
public class Product implements Parcelable {
    String id;
    String name;
    String size;
    String price;
    int defaultOrderQuant;
    int tmpOrderQuant;
    boolean onShelf;
    int tierNo;
    int leftPosition;
    int noOfColumns;


    /*ArrayList<Integer> barcodes;

    public ArrayList<Integer> getBarcodes() {
        return barcodes;
    }

    public void setBarcodes(ArrayList<Integer> barcodes) {
        this.barcodes = barcodes;
    }

*/

    public Product(String id, String name, String size, String price, int defaultOrderQuant, int tmpOrderQuant, boolean onShelf, int tierNo, int leftPosition, int noOfColumns) {
        this.id = id;
        this.name = name;
        this.size = size;
        this.price = price;
        this.defaultOrderQuant = defaultOrderQuant;
        this.tmpOrderQuant = tmpOrderQuant;
        this.onShelf = onShelf;
        this.tierNo = tierNo;
        this.leftPosition = leftPosition;
        this.noOfColumns = noOfColumns;
    }

    public Product(Parcel source){
        id = source.readString();
        name = source.readString();
        size = source.readString();
        price = source.readString();
        defaultOrderQuant = source.readInt();
        tmpOrderQuant = source.readInt();
    }


    public int describeContents() {
        return this.hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(size);
        dest.writeString(price);
        dest.writeInt(defaultOrderQuant);
        dest.writeInt(tmpOrderQuant);
    }




    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getDefaultOrderQuant() {
        return defaultOrderQuant;
    }

    public void setDefaultOrderQuant(int defaultOrderQuant) {
        this.defaultOrderQuant = defaultOrderQuant;
    }

    public int getTmpOrderQuant() {
        return tmpOrderQuant;
    }

    public void setTmpOrderQuant(int tmpOrderQuant) {
        this.tmpOrderQuant = tmpOrderQuant;
    }


    public static final Creator<Product> CREATOR = new Creator<Product>(){
        public Product createFromParcel(Parcel in){
            return new Product(in);
        }

        public Product[] newArray(int size){
            return new Product[size];
        }

    };


}
