package com.android.sahal.sahalapplication.Model;

import android.media.Image;
import android.net.Uri;
import android.widget.ImageView;

import com.google.firebase.database.Exclude;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class  Item {
    public String itemName;
    public String itemDescr;
    public Double itemPrice;
    public String itemComp;
    public String itemModel;
    public String itemYear;
    public String itemCatg;



    public  List<String>images ;
//    public List<Uri> images = new ArrayList<>();


    public Item(String itemName, String itemDescr, Double itemPrice, String itemComp, String itemModel, String itemYear, String itemCatg ,List<String> images) {
        this.itemName = itemName;
        this.itemDescr = itemDescr;
        this.itemPrice = itemPrice;
        this.itemComp = itemComp;
        this.itemModel = itemModel;
        this.itemYear = itemYear;
        this.itemCatg = itemCatg;
        this.images = images;

//        this.images = images;
    }

//    public List<Uri> getImages() {
//        return images;
//    }
//
//    public void setImages(List<Uri> images) {
//        this.images = images;
//    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDescr() {
        return itemDescr;
    }

    public void setItemDescr(String itemDescr) {
        this.itemDescr = itemDescr;
    }

    public Double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(Double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getItemComp() {
        return itemComp;
    }

    public void setItemComp(String itemComp) {
        this.itemComp = itemComp;
    }

    public String getItemModel() {
        return itemModel;
    }

    public void setItemModel(String itemModel) {
        this.itemModel = itemModel;
    }

    public String getItemYear() {
        return itemYear;
    }

    public void setItemYear(String itemYear) {
        this.itemYear = itemYear;
    }

    public String getItemCatg() {
        return itemCatg;
    }

    public void setItemCatg(String itemCatg) {
        this.itemCatg = itemCatg;
    }


    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("itemName", itemName);
        result.put("description", itemDescr);
        result.put("itemPrice", itemPrice);
        result.put("Company", itemComp);
        result.put("Model", itemModel);
        result.put("Year", itemYear);
        result.put("Category", itemCatg);


//        result.put("images", images);

        return result;
    }
}


