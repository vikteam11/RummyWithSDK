package com.rummytitans.playcashrummyonline.cardgame.models;

import java.io.Serializable;

public class RealTimeJoinModel implements Serializable {
    public String message;
    public boolean tokenExpire;
    public boolean status;
    public String response;
    public String currentDate;
    public Long leagueID;
}
