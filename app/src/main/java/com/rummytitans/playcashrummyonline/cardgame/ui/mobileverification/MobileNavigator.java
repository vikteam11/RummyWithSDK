package com.rummytitans.playcashrummyonline.cardgame.ui.mobileverification;

public interface MobileNavigator {

    void sendOtp();

    void verifyOtp();

    void mobileVerified(boolean teamVerified);

    void resetPassword();

    void fireBranchEvent(String eventName,int userId);

}
