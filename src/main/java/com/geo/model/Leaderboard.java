package com.geo.model;

import lombok.Data;

@Data
public class Leaderboard {

    private String nickname;
    private int score;
    private String leaderboardDetail;

    public Leaderboard(String nickname, int score) {
        this.nickname = nickname;
        this.score = score;
        this.leaderboardDetail= new StringBuilder().append(this.nickname).append(": ").append(this.score).toString();
    }
}
