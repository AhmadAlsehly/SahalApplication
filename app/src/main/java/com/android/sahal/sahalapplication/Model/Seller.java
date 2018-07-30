package com.android.sahal.sahalapplication.Model;

public class Seller {
    private String bR ;
    private String companyName ;
    private String  mobileNumber ;


    public Seller(String bR, String company, String number) {
        this.bR = bR;
        this.companyName = company;
        this.mobileNumber = number;

    }

    public Seller() {

    }

    public String getbR() {
        return bR;
    }

    public void setbR(String bR) {
        this.bR = bR;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
}
