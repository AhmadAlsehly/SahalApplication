package com.android.sahal.sahalapplication.Model;

public class Buyer {
    private String buyerName;
    private String mobileNumber;
    private String city;

    public Buyer(String buyerName, String mobileNumber,String city) {
        this.buyerName = buyerName;
        this.mobileNumber = mobileNumber;
        this.city = city;
    }

    public Buyer() {

    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }


    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }


}

