package com.android.sahal.sahalapplication;

public class ModuleItem {
    String name,description,nameOfFactory,type,category,yearOfCreat;
    double price;
    int image1,image2,image3,image4;


    public ModuleItem(String name,String desc,String nameOfFactory,String type
    ,String category,String yearOfCreat,double price,int image1){

        this.name=name;
        this.description=desc;
        this.nameOfFactory=nameOfFactory;
        this.type=type;
        this.category=category;
        this.yearOfCreat=yearOfCreat;
        this.price=price;
        this.image1=image1;

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

    public int getImage1() {
        return image1;
    }

    public void setImage1(int image1) {
        this.image1 = image1;
    }

    public int getImage2() {
        return image2;
    }

    public void setImage2(int image2) {
        this.image2 = image2;
    }

    public int getImage3() {
        return image3;
    }

    public void setImage3(int image3) {
        this.image3 = image3;
    }

    public int getImage4() {
        return image4;
    }

    public void setImage4(int image4) {
        this.image4 = image4;
    }




}
