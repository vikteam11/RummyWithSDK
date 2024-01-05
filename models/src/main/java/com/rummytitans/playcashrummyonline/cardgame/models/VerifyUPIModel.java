package com.rummytitans.playcashrummyonline.cardgame.models;

public class VerifyUPIModel {
    public String vpa;
    public String status;
    public String customer_name;
    static private final String rightStatusForm="VALID";
    public  boolean isUpiValid(){
        return status.equals(rightStatusForm);
    }
}


