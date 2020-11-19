package fr.android.nazim.foottracker2;

import java.util.Date;
import java.time.LocalDateTime;


// Class MatchModel with all the value of our object
public class MatchModel {
    private int id;
    private String competitionType, team1, team2, scoreTeam1, scoreTeam2;
    private boolean isPrivate;
    private String dateMatch;

    //Construteurs
    public MatchModel(int id, String competitionType, String team1, String team2, boolean isPrivate) {
        this.id = id;
        this.competitionType = competitionType;
        this.team1 = team1;
        this.team2 = team2;
        this.isPrivate = isPrivate;
        //this.scoreTeam1 = scoreTeam1;
        //this.scoreTeam2 = scoreTeam2;
        //this.dateMatch = dateMatch;
    }
    //
    public MatchModel() {
    }
    //Geters & Seters
    public String getScoreTeam1() {
        return scoreTeam1;
    }

    public void setScoreTeam1(String scoreTeam1) {
        this.scoreTeam1 = scoreTeam1;
    }

    public String getScoreTeam2() {
        return scoreTeam2;
    }

    public void setScoreTeam2(String scoreTeam2) {
        this.scoreTeam2 = scoreTeam2;
    }
    /*
    public String getDateMatch() {
        return dateMatch;
    }*/

    public void setDateMatch(String dateMatch) {
        this.dateMatch = dateMatch;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getCompetitionType() {
        return competitionType;
    }
    public void setCompetitionType(String competitionType) {
        this.competitionType = competitionType;
    }
    public String getTeam1() {
        return team1;
    }
    public void setTeam1(String team1) {
        this.team1 = team1;
    }
    public String getTeam2() {
        return team2;
    }
    public void setTeam2(String team2) {
        this.team2 = team2;
    }
    public boolean isPrivate() {
        return isPrivate;
    }
    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    //toString : printing the content of a class object

    @Override
    public String toString() {
        return "MatchModel{" +
                "id=" + id +
                ", competitionType='" + competitionType + '\'' +
                ", team1='" + team1 + '\'' +
                ", team2='" + team2 + '\'' +
                ", scoreTeam1='" + scoreTeam1 + '\'' +
                ", scoreTeam2='" + scoreTeam2 + '\'' +
                ", isPrivate=" + isPrivate +
                ", dateMatch=" + dateMatch +
                '}';
    }
}
