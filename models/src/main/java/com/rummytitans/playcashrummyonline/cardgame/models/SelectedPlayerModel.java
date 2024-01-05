package com.rummytitans.playcashrummyonline.cardgame.models;


import java.io.Serializable;

public class SelectedPlayerModel implements Serializable {

    public int PlayerId;
    public String PlayerType;

    public SelectedPlayerModel(int captainId, int viceCaptain, int playerId) {
        PlayerId = playerId;
    }

    public SelectedPlayerModel(int playerId,String playerType) {
        PlayerType = playerType;
        PlayerId = playerId;
    }
}
