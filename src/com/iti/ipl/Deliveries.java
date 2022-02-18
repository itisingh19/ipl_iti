package com.iti.ipl;

public class Deliveries {
    private Integer matchId;
    private Integer inning;
    private String battingTeam;
    private String bowlingTeam;
    private Integer over;
    private String bowler;
    private Integer extraRun;
    private Integer totalRun;

    public Integer getMatchId() {
        return matchId;
    }

    public void setMatchId(Integer matchId) {
        this.matchId = matchId;
    }

    public Integer getInning() {
        return inning;
    }

    public void setInning(Integer inning) {
        this.inning = inning;
    }

    public String getBattingTeam() {
        return battingTeam;
    }

    public void setBattingTeam(String battingTeam) {
        this.battingTeam = battingTeam;
    }

    public String getBowlingTeam() {
        return bowlingTeam;
    }

    public void setBowlingTeam(String bowlingTeam) {
        this.bowlingTeam = bowlingTeam;
    }

    public Integer getOver() {
        return over;
    }

    public void setOver(Integer over) {
        this.over = over;
    }

    public String getBowler() {
        return bowler;
    }

    public void setBowler(String bowler) {
        this.bowler = bowler;
    }

    public Integer getExtraRun() {
        return extraRun;
    }

    public void setExtraRun(Integer extraRun) {
        this.extraRun = extraRun;
    }

    public Integer getTotalRun() {
        return totalRun;
    }

    public void setTotalRun(Integer totalRun) {
        this.totalRun = totalRun;
    }


}
