package com.example.rajithhasith.stock_app_android;

import java.util.ArrayList;

/**
 * Created by Rajith Hasith on 05/05/2015.
 */
public class Functions {
    public Functions() {
    }

    public ArrayList<Product> getTierList(int tierNo, ArrayList<Product> fullProductList){
        ArrayList<Product> tierList = new ArrayList<>();

        for(int i=0; i < fullProductList.size(); i++){
            Product tempProduct = fullProductList.get(i);

            if(tempProduct.isOnShelf()){
                if(tempProduct.getTierNo() == tierNo){
                    tierList.add(tempProduct);
                }
            }

        }
        return tierList;
    }

    public ArrayList<Product> getFillProductsList(ArrayList<Product> productList){

        return productList;
    }
}
