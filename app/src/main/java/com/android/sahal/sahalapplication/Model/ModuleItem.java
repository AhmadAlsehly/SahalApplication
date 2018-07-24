package com.android.sahal.sahalapplication.Model;

import android.widget.ImageView;

import java.io.Serializable;
import java.util.List;

public class ModuleItem implements Serializable{
    String name,description,nameOfFactory,type,category,yearOfCreat;
    double price;
     List<String> images ;






    public ModuleItem(String name,String desc,String nameOfFactory,String type
    ,String category,String yearOfCreat,double price,List<String> images){

        this.name=name;
        this.description=desc;
        this.nameOfFactory=nameOfFactory;
        this.type=type;
        this.category=category;
        this.yearOfCreat=yearOfCreat;
        this.price=price;
        this.images = images;

    }

    public ModuleItem() {

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

    public String getNameOfFactory() {
        return nameOfFactory;
    }

    public void setNameOfFactory(String nameOfFactory) {
        this.nameOfFactory = nameOfFactory;
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

    public String getYearOfCreat() {
        return yearOfCreat;
    }

    public void setYearOfCreat(String yearOfCreat) {
        this.yearOfCreat = yearOfCreat;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
