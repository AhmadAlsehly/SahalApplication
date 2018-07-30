package com.android.sahal.sahalapplication.Model;

public class Buyer {
    public String buyerName;
    public String mobileNumber;
    public String address;

    public Buyer(String buyerName, String mobileNumber) {
        this.buyerName = buyerName;
        this.mobileNumber = mobileNumber;
        this.address = address;
    }

    public Buyer() {

    }

    public String getBuyerName() {
        return buyerName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

