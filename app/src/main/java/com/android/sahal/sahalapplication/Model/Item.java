package com.android.sahal.sahalapplication.Model;

import android.media.Image;
import android.widget.ImageView;

import java.net.URI;
import java.util.ArrayList;

public class Item {
    public  String itemName;
    public  String itemDescr;
    public  Double itemPrice;
    public  String itemComp;
    public  String itemModel;
    public  String itemYear;
    public  String itemCatg;

    public Item(String itemName, String itemDescr, Double itemPrice, String itemComp, String itemModel, String itemYear, String itemCatg) {
        this.itemName = itemName;
        this.itemDescr = itemDescr;
        this.itemPrice = itemPrice;
        this.itemComp = itemComp;
        this.itemModel = itemModel;
        this.itemYear = itemYear;
        this.itemCatg = itemCatg;
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
}
