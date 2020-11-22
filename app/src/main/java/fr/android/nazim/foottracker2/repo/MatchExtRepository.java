package fr.android.nazim.foottracker2.repo;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import fr.android.nazim.foottracker2.entity.MatchModel;

public class MatchExtRepository implements FootRepository{

    private Connection connection;
    private Thread thrdConnection;

    public MatchExtRepository() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        new Thread(new Runnable() {
            public void run() {
                while (!Thread.interrupted()) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e1) {

                    }
                    if (connection == null) {
                        try {
                            connection = DriverManager.getConnection("jdbc:mysql://10.0.2.2:3306/foot", "user", "root");
                            Log.i("REPOSITORY_jdbc", "Connected");
                        } catch (SQLException e) {
                            e.printStackTrace();
                            connection = null;
                        }
                    }
                }
            }
        }).start();
    }

    public void insertMatch(final MatchModel match){
        Log.i("REPOSITORY_jdbc_add", match.toString());
        new Thread(new Runnable() {
            public void run() {
                try {
                    String sql = "INSERT INTO `match` (`name`, `team1`, `team2`, `isPrivate`, `score1`, `score2`) VALUES (?,?,?,?,?,?)";
                    PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(sql);
                    stmt.setString(1, match.getCompetitionType());
                    stmt.setString(2, match.getTeam1());
                    stmt.setString(3, match.getTeam2());
                    stmt.setBoolean(4, match.isPrivate());
                    stmt.setInt(5, match.getScoreTeam1());
                    stmt.setInt(6, match.getScoreTeam2());
                    int affectedEntry = stmt.executeUpdate();
                }
                catch (SQLException e){
                    Log.e("REPOSITORY_jdbc", e.getMessage());
                }
            }
        }).start();
    }

    public ArrayList<MatchModel> getMatchs() {
        Log.i("REPOSITORY_jdbc_get", "all matchs");
        ArrayList<MatchModel> matchs = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            String myQuery = "select * from `match`";
            ResultSet rs = stmt.executeQuery(myQuery);
            while (rs.next()) {
                matchs.add(new MatchModel(
                        rs.getString("name"),
                        rs.getString("team1"),
                        rs.getString("team2"),
                        rs.getBoolean("isPrivate"),
                        rs.getInt("score1"),
                        rs.getInt("score2"),
                        rs.getInt("id")
                    )
                );
            }
            rs.close();
            stmt.close();
        } catch (SQLException e){
            Log.e("REPOSITORY_jdbc", e.getMessage());
        }
        return matchs;
    }

    public void closeConnection() {
        try{
            this.connection.close();
        }
        catch (Exception ignored){

        }
    }
}
