package com.android.sahal.sahalapplication.Model;

import android.widget.ImageView;

import java.io.Serializable;
import java.util.List;

public class ModuleItem implements Serializable {
    String name, description, factoryName, type, category, year;
    double price;
    List<String> itemImages;
    String sellerId;
    Integer status;
    String buyerId;



    public ModuleItem(String name, String desc, String factoryName, String type
            , String category, String year, double price, List<String> itemImages, String sellerId, Integer status , String buyerId
    ) {

        this.name = name;
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


    }

    public ModuleItem() {

    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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


    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
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
}
