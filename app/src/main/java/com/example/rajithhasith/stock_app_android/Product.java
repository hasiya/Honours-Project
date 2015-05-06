package com.example.rajithhasith.stock_app_android;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Comparator;

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
    String imageID;
    int fillQuantity;
    int needQuant;
    boolean order;



    public Product(String id, String name, String size,
                   String price, int defaultOrderQuant,
                   int tmpOrderQuant, boolean onShelf,
                   int tierNo, int leftPosition, int noOfColumns,
                   String imageID,
                   int fillQuantity, int needQuant,boolean order)
    {
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
        this.imageID = imageID;
        this.fillQuantity = fillQuantity;
        this.needQuant = needQuant;
        this.order = order;
    }

    public Product(Parcel source){
        id = source.readString();
        name = source.readString();
        size = source.readString();
        price = source.readString();
        defaultOrderQuant = source.readInt();
        tmpOrderQuant = source.readInt();
        onShelf = source.readByte() != 0;
        tierNo = source.readInt();
        leftPosition = source.readInt();
        noOfColumns = source.readInt();
        imageID = source.readString();
        fillQuantity = source.readInt();
        needQuant = source.readInt();
        order = source.readByte() != 0;
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
        dest.writeByte((byte)(onShelf ? 1:0));
        dest.writeInt(tierNo);
        dest.writeInt(leftPosition);
        dest.writeInt(noOfColumns);
        dest.writeString(imageID);
        dest.writeInt(fillQuantity);
        dest.writeInt(needQuant);
        dest.writeByte((byte)(order ? 1:0));
    }



    public static Comparator<Product> productLeftPos = new Comparator<Product>() {
        @Override
        public int compare(Product lhs, Product rhs) {
            int product1LeftPos = lhs.getLeftPosition();
            int product2LeftPos = rhs.getLeftPosition();

            return  product1LeftPos - product2LeftPos;
        }
    };


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

    public int getNoOfColumns() {
        return noOfColumns;
    }

    public void setNoOfColumns(int noOfColumns) {
        this.noOfColumns = noOfColumns;
    }

    public int getLeftPosition() {
        return leftPosition;
    }

    public void setLeftPosition(int leftPosition) {
        this.leftPosition = leftPosition;
    }

    public int getTierNo() {
        return tierNo;
    }

    public void setTierNo(int tierNo) {
        this.tierNo = tierNo;
    }

    public boolean isOnShelf() {
        return onShelf;
    }

    public void setOnShelf(boolean onShelf) {
        this.onShelf = onShelf;
    }

    public String getImageID() {
        return imageID;
    }

    public void setImageID(String imageID) {
        this.imageID = imageID;
    }

    public int getFillQuantity() {
        return fillQuantity;
    }

    public void setFillQuantity(int fillQuantity) {
        this.fillQuantity = fillQuantity;
    }

    public int getNeedQuant() {
        return needQuant;
    }

    public void setNeedQuant(int needQuant) {
        this.needQuant = needQuant;
    }

    public boolean isOrder() {
        return order;
    }

    public void setOrder(boolean order) {
        this.order = order;
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