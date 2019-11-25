package com.hbw.shortvideo;

import com.aliyun.player.AliPlayer;

public class PlayerInfo {
    private String playURL;
    private AliPlayer aliPlayer;
    private int position;

    public PlayerInfo() {

    }

    public PlayerInfo(String playURL, AliPlayer aliPlayer, int position) {
        this.playURL = playURL;
        this.aliPlayer = aliPlayer;
        this.position = position;
    }

    public String getPlayURL() {
        return playURL;
    }

    public void setPlayURL(String playURL) {
        this.playURL = playURL;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public AliPlayer getAliPlayer() {
        return aliPlayer;
    }

    public void setAliPlayer(AliPlayer aliPlayer) {
        this.aliPlayer = aliPlayer;
    }
}
