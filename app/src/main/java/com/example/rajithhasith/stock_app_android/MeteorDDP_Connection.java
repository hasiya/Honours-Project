/**
 * Created by Rajith Hasith on 14/04/2015.
 */
package com.example.rajithhasith.stock_app_android;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.Policy;
import java.util.ArrayList;

import im.delight.android.ddp.Meteor;
import im.delight.android.ddp.MeteorCallback;

public class MeteorDDP_Connection implements MeteorCallback {

    public static Meteor mMeteor;

    public static ArrayList<Product> productList;

    public MeteorDDP_Connection() {
        if(mMeteor == null || !mMeteor.isConnected()) {
            productList = new ArrayList<>();

            mMeteor = new Meteor("ws://178.62.29.188:3000/websocket");

            mMeteor.setCallback(this);
        }

    }

    @Override
    public void onConnect() {
        Log.d("Meteor", "connected");
        //System.out.println("Connected");

        // subscribe to data from the server
        mMeteor.subscribe("products");
        mMeteor.subscribe("barcodes");
    }

    @Override
    public void onDisconnect(int i, String s) {
        System.out.println("Disconnected");
        /*mMeteor = new Meteor("ws://178.62.44.95:3000/websocket");
        mMeteor.setCallback(this);*/
    }

    @Override
    public void onDataAdded(String collectionName, String documentID, String fieldsJson) {
        System.out.println("Data added to <"+collectionName+"> in document <"+documentID+">");
        System.out.println("    Added: "+fieldsJson);


        if(collectionName.equals("products")) {
            Boolean isInList = false;

            if(!productList.isEmpty()) {
                for (Product p : productList) {
                    if (p.getId() == documentID) {
                        isInList = true;
                    } else {
                        isInList = false;
                    }
                }
            }

            if(!isInList) {

                JSONObject ProductJson = null;
                try {
                    ProductJson = new JSONObject(fieldsJson);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String id = null;
                String name = null;
                String size = null;
                String price = null;
                String imageID = null;
                boolean onShelf = false;
                int tierNo = 0;
                int leftPosition = 0;
                int noOfColumns = 0;
                int defaultOrderQuant = 0;
                int tmpOrderQuant = 0;
                int fillQuantity = 0;
                int needQuant = 0;
                boolean order = false;

                try {
                    id = documentID;
                    assert ProductJson != null;
                    name = ProductJson.getString("name");
                    price = ProductJson.getString("price");
                    size = ProductJson.getString("size");
                    imageID = ProductJson.getString("picture");
                    defaultOrderQuant = ProductJson.getInt("defaultOrderQuant");
                    JSONObject position = ProductJson.getJSONObject("position");
                    onShelf = position.getBoolean("onShelf");
                    tierNo = position.getInt("tierNo");
                    leftPosition = position.getInt("leftPosition");
                    noOfColumns = position.getInt("noOfCols");
                    fillQuantity = ProductJson.getInt("fillQuant");
                    needQuant = ProductJson.getInt("needQuant");
                    order = position.getBoolean("Order");

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Product product = new Product(id, name, size, price,
                        defaultOrderQuant, tmpOrderQuant, onShelf,
                        tierNo, leftPosition, noOfColumns, imageID,
                        fillQuantity, needQuant,order);

                productList.add(product);
            }
        }

    }

    @Override
    public void onDataChanged(String collectionName, String documentID, String updatedValuesJson, String removedValuesJson) {
        System.out.println("Data changed in <"+collectionName+"> in document <"+documentID+">");
        System.out.println("    Updated: "+updatedValuesJson);
        System.out.println("    Removed: "+removedValuesJson);

        if(collectionName.equals("products")) {
            updateProductList(documentID, updatedValuesJson);
        }
    }

    @Override
    public void onDataRemoved(String collectionName, String documentID) {
        System.out.println("Data removed from <"+collectionName+"> in document <"+documentID+">");

        for(Product p:productList){
            if(p.getId() ==  documentID){
               productList.remove(p);
            }
        }
    }

    @Override
    public void onException(Exception e) {
        System.out.println("Exception");
        if (e != null) {
            e.printStackTrace();
        }
    }



    public void updateProductList(String documentID, String updatedValuesJson){
        JSONObject updatedJson = null;
        try {
            updatedJson = new JSONObject(updatedValuesJson);
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i=0; i<productList.size(); i++) {
            assert updatedJson != null;
            if(productList.get(i).getId().equals(documentID)){
                if (updatedJson.has("needQuant")) {
                    try {
                         int newNeedQuant = updatedJson.getInt("needQuant");
                        productList.get(i).setNeedQuant(newNeedQuant);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else if (updatedJson.has("name")) {
                    try {
                        String newName = updatedJson.getString("name");
                        productList.get(i).setName(newName);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else if (updatedJson.has("size")) {
                    try {
                        String newSize = updatedJson.getString("size");
                        productList.get(i).setSize(newSize);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else if (updatedJson.has("price")) {
                    try {
                        String newPrice = updatedJson.getString("price");
                        productList.get(i).setPrice(newPrice);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else if (updatedJson.has("defaultOrderQuant")) {
                    try {
                        int newDefOrderQuant = updatedJson.getInt("defaultOrderQuant");
                        productList.get(i).setDefaultOrderQuant(newDefOrderQuant);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else if (updatedJson.has("tmpOrderQuant")) {
                    try {
                        int newTmpOrderQuant = updatedJson.getInt("tmpOrderQuant");
                        productList.get(i).setTmpOrderQuant(newTmpOrderQuant);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else if (updatedJson.has("fillQuant")) {
                    try {
                        int newFillQuantity = updatedJson.getInt("fillQuant");
                        productList.get(i).setFillQuantity(newFillQuantity);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else if (updatedJson.has("Order")) {
                    try {
                        boolean newIsOrder = updatedJson.getBoolean("Order");
                        productList.get(i).setOrder(newIsOrder);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else if(updatedJson.has("position")){
                    try {
                        JSONObject ProductPositionJson = updatedJson.getJSONObject("position");
                        Boolean onShelf = ProductPositionJson.getBoolean("onShelf");
                        int tierNo = ProductPositionJson.getInt("tierNo");
                        int leftPos = ProductPositionJson.getInt("leftPosition");
                        int noOfCol = ProductPositionJson.getInt("noOfCols");

                        productList.get(i).setOnShelf(onShelf);
                        productList.get(i).setTierNo(tierNo);
                        productList.get(i).setLeftPosition(leftPos);
                        productList.get(i).setNoOfColumns(noOfCol);
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                }
            }


        }

    }

}


