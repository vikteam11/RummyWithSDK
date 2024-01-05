package com.rummytitans.playcashrummyonline.cardgame.models;

public class PaymentWalletModel {
    public boolean isLinked = false;
    public boolean isSelected = false;
    public boolean isOfferAvailable = false;
    public int balance = 0;

    public PaymentWalletModel() {
    }

    public PaymentWalletModel(boolean isLinked, boolean isSelected, boolean isOfferAvailable, int balance) {
        this.isLinked = isLinked;
        this.isSelected = isSelected;
        this.isOfferAvailable = isOfferAvailable;
        this.balance = balance;
    }
}
