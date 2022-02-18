package com.iti.ipl;

import java.io.*;
import java.util.List;
import java.util.*;

public class Main {

    public static final int MATCH_ID = 0;
    public static final int SEASON = 1;
    public static final int TEAM1 = 4;
    public static final int TEAM2 = 5;
    public static final int  TOSS_WINNER= 6;
    public static final int MATCH_WINNER = 10;

    public static final int BOWLER = 8;
    public static final int BATING_TEAM = 2;
    public static final int EXTRA_RUNS = 16;
    public static final int TOTAL_RUNS = 17;

    static final String matchesFile = "/home/linuxubuntu/Downloads/archive/matches.csv";
    static final String deliveriesFile = "/home/linuxubuntu/Downloads/archive/deliveries.csv";

    public static void main(String[] args){
        List<Match> matchesData = getMatchesData();
        List<Deliveries> deliveriesData = getDeliveriesData();

        noOfMatchesPlayedInEachSeason(matchesData);
        noOfMatchesWonOfAllTeams(matchesData);
        noOfExtraRunsByTeamsPlayedIn2016(matchesData, deliveriesData);
        mostEconomicPlayerIn2015(matchesData, deliveriesData);
        countingTossWinsAsAScenario(matchesData);
    }

    private static void countingTossWinsAsAScenario(List<Match> matchesData) {
        Map<String, Integer> tossWonByAllTeams = new HashMap<>();
        for (Match matches: matchesData){
            if(tossWonByAllTeams.containsKey(matches.getTossWinner())){
                tossWonByAllTeams.put(matches.getTossWinner(),tossWonByAllTeams.get(matches.getTossWinner())+1);
            }
            else{
                tossWonByAllTeams.put(matches.getTossWinner(),1);
            }
        }
        System.out.println(tossWonByAllTeams);
    }

    private static void mostEconomicPlayerIn2015(List<Match> matchesData, List<Deliveries> deliveriesData) {
        List<Integer> matchIdForTeamsPlayedIn2015 = new ArrayList<>();
        Map<String, Float> ballsThrownByBaller = new HashMap<>();
        Map<String, Float> bowlerRate = new HashMap<>();
        for (Match matches:matchesData){
            if((matches.getSeason()).equals("2015")){
                matchIdForTeamsPlayedIn2015.add(matches.getMatchId());
            }
        }
        for (Deliveries delivery: deliveriesData){
            if(matchIdForTeamsPlayedIn2015.contains(delivery.getMatchId())){
                if (bowlerRate.containsKey(delivery.getBowler())){
                    bowlerRate.put(delivery.getBowler(), bowlerRate.get(delivery.getBowler())  +(float) (delivery.getTotalRun() - delivery.getExtraRun()) );
                }
                else {
                    bowlerRate.put(delivery.getBowler(), (float) (delivery.getTotalRun() - delivery.getExtraRun()));
                }
                if (ballsThrownByBaller.containsKey(delivery.getBowler())){
                    ballsThrownByBaller.put(delivery.getBowler(),ballsThrownByBaller.get(delivery.getBowler())+(float)1/6);
                }
                else{
                    ballsThrownByBaller.put(delivery.getBowler(),(float)(1/6));
                }
            }
        }
        for (Map.Entry<String, Float> mapElement : bowlerRate.entrySet()){
            bowlerRate.put(mapElement.getKey(),mapElement.getValue()/ballsThrownByBaller.get(mapElement.getKey()));
        }
        Float minValueOfEconomicPlayer = Collections.min(bowlerRate.values());
        for (Map.Entry<String, Float> mapEle : bowlerRate.entrySet()) {
            if ((mapEle.getValue()).equals(minValueOfEconomicPlayer)) {
                System.out.println(mapEle);
            }
        }
    }

    private static void noOfExtraRunsByTeamsPlayedIn2016( List<Match> matchesData ,List<Deliveries> deliveriesData) {
        List<Integer> matchIdForTeamsPlayedIn2016 = new ArrayList<>();
        Map<String, Integer> extraRunsByTeamsPlayedIn2016 = new HashMap<>();
        for (Match matches: matchesData){
            if((matches.getSeason()).equals("2016")){
                extraRunsByTeamsPlayedIn2016.put(matches.getTeam1(),0);
                extraRunsByTeamsPlayedIn2016.put(matches.getTeam2(),0);
                matchIdForTeamsPlayedIn2016.add(matches.getMatchId());
            }
        }
        for(Deliveries delivery: deliveriesData){
            if(matchIdForTeamsPlayedIn2016.contains(delivery.getMatchId())){
                extraRunsByTeamsPlayedIn2016.put(delivery.getBattingTeam(),extraRunsByTeamsPlayedIn2016.get(delivery.getBattingTeam()) + delivery.getExtraRun());
            }
        }
        System.out.println(extraRunsByTeamsPlayedIn2016);
    }

    private static void noOfMatchesWonOfAllTeams(List<Match> matchesData) {
        Map<String,Integer> matchesWonOffAllTeams = new HashMap<>();
        for (Match matches: matchesData){
            if(matchesWonOffAllTeams.containsKey(matches.getWinner())){
                matchesWonOffAllTeams.put(matches.getWinner(),matchesWonOffAllTeams.get(matches.getWinner())+1);
            }
            else if(!(matches.getWinner()).equals("")){
                matchesWonOffAllTeams.put(matches.getWinner(),1);
            }
        }
        System.out.println(matchesWonOffAllTeams);
    }

    public static void noOfMatchesPlayedInEachSeason(List<Match> matchesData) {
        Map<String,Integer> matchesPlayedInEachSeason = new HashMap<>();
        for (Match matches : matchesData) {
            if(matchesPlayedInEachSeason.containsKey(matches.getSeason())){
                matchesPlayedInEachSeason.put(matches.getSeason(),matchesPlayedInEachSeason.get(matches.getSeason())+1);
            }
            else{
                matchesPlayedInEachSeason.put(matches.getSeason(),1);
            }

        }
        System.out.println(matchesPlayedInEachSeason);
    }

    private static List<Deliveries> getDeliveriesData() {
        List<Deliveries> allDeliveriesData = new ArrayList<>();
        try{
            String deliveriesInfo;
            BufferedReader buffer = new BufferedReader(new FileReader(deliveriesFile));
            buffer.readLine();
            while ((deliveriesInfo = buffer.readLine()) != null){
                String[] deliveriesLine = deliveriesInfo.split(",");
                Deliveries delivery = new Deliveries();
                delivery.setMatchId(Integer.parseInt(deliveriesLine[MATCH_ID]));
                delivery.setBattingTeam(deliveriesLine[BATING_TEAM]);
                delivery.setBowler(deliveriesLine[BOWLER]);
                delivery.setExtraRun(Integer.parseInt(deliveriesLine[EXTRA_RUNS]));
                delivery.setTotalRun(Integer.parseInt(deliveriesLine[TOTAL_RUNS]));
                allDeliveriesData.add(delivery);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("ERROR READING DELIVERIES FILE.............");
        }
        return allDeliveriesData;
    }

    private static List<Match> getMatchesData() {
        List<Match> allMatchesData = new ArrayList<>();
        try {
            String matchesInfo;
            BufferedReader buffer = new BufferedReader(new FileReader(matchesFile));
            buffer.readLine();
            while ((matchesInfo = buffer.readLine()) != null) {
                String[] matchesLine = matchesInfo.split(",");
                Match match  = new Match();
                match.setMatchId(Integer.parseInt(matchesLine[MATCH_ID]));
                match.setSeason(matchesLine[SEASON]);
                match.setTeam1(matchesLine[TEAM1]);
                match.setTeam2(matchesLine[TEAM2]);
                match.setWinner(matchesLine[MATCH_WINNER]);
                match.setTossWinner(matchesLine[TOSS_WINNER]);
                allMatchesData.add(match);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("ERROR READING MATCHES FILE.............");
        }
        return allMatchesData;
    }
}
