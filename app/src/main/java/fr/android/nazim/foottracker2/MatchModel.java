package fr.android.nazim.foottracker2;

// Class MatchModel with all the value of our object
public class MatchModel {
    private int id;
    private String competitionType, team1, team2;
    private boolean isPrivate;

    //Construteurs
    public MatchModel(int id, String competitionType, String team1, String team2, boolean isPrivate) {
        this.id = id;
        this.competitionType = competitionType;
        this.team1 = team1;
        this.team2 = team2;
        this.isPrivate = isPrivate;
    }
    //
    public MatchModel() {
    }

    //Geters & Seters
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
                ", isPrivate=" + isPrivate +
                '}';
    }
}
