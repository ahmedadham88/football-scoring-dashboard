package com.example.entities;

/**
 * This class defines a Goal with attributes Player and Minute
 *
 * @author Ahmed Abouzeid
 *
 */
public class Goal {

    private Integer minute;

    private String player;

    public Goal(Integer minute, String player) {
        this.minute = minute;
        this.player = player;
    }

    public Integer getMinute() {
        return minute;
    }

    public void setMinute(Integer minute) {
        this.minute = minute;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }
}
