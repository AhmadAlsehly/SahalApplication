package com.android.sahal.sahalapplication.Model;

import android.widget.ImageView;

import java.io.Serializable;
import java.util.List;

public class ModuleItem implements Serializable {
    String itemName, description, factoryName, type, category, year;
    String price;
    String carName ;
    List<String> itemImages;
    String sellerId;
    String status;
    String buyerId;
    String id ;



    public ModuleItem(String itemName,String carName ,String desc, String factoryName, String type
            , String category, String year, String price, List<String> itemImages, String sellerId, String status , String buyerId
                      ,String id
    ) {

        this.itemName = itemName;
        this.carName = carName ;
        this.description = desc;
        this.factoryName = factoryName;
        this.type = type;
        this.category = category;
        this.year = year;
        this.price = price;
        this.itemImages = itemImages;
        this.sellerId = sellerId;
        this.status = status;
        this.buyerId=buyerId;
        this.id=id;

    }

    public ModuleItem() {

    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String name) {
        this.itemName = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getFactoryName() {
        return factoryName;
    }

    public void setFactoryName(String factoryName) {
        this.factoryName = factoryName;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public List<String> getItemImages() {
        return itemImages;
    }

    public void setItemImages(List<String> itemImages) {
        this.itemImages = itemImages;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
