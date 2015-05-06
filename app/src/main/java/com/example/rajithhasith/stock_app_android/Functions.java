package com.example.rajithhasith.stock_app_android;

import java.util.ArrayList;
import java.util.Collections;

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

    public ArrayList<Product> getFillProductsList(){

        ArrayList<Product> tmpNeedList = new ArrayList<>();
        ArrayList<Product> needProductList;

        for (Product p:MeteorDDP_Connection.productList){
            if(p.getNeedQuant()>0){
                tmpNeedList.add(p);
            }
        }
        needProductList = sortCheckProductList(tmpNeedList);
        return needProductList;
    }

    public ArrayList<Product> sortCheckProductList(ArrayList<Product> list){

        ArrayList<Product> sortedList = new ArrayList<>();

        for(int i=1; i<5; i++){
            for (Product p:list){
                if(p.getTierNo() == i){
                    sortedList.add(p);
                }
            }
            Collections.sort(sortedList, Product.productLeftPos);
        }
        return sortedList;
    }
}
