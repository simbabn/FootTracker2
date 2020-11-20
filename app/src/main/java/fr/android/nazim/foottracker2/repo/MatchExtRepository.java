package fr.android.nazim.foottracker2.repo;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
        thrdConnection = new Thread(new Runnable() {
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
        });
        if ((thrdConnection != null) && (!thrdConnection.isAlive())) thrdConnection.start();
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
        ArrayList<MatchModel> matchs = new ArrayList<>();
        matchs.add(new MatchModel(
                "test",
                "ee",
                "rr",
                false,
                12,
                13,
                0
        ));
        return matchs;
    }

    public void closeConnection() {
        try{
            this.connection.close();
        }
        catch (Exception ignored){

        }
    }

    /*

    thrd2 = new Thread(new Runnable() {
        public void run() {
            while (!Thread.interrupted()) {

                if (con != null) {
                    try {
                        //   con = DriverManager.getConnection("jdbc:mysql://192.168.1.45:3306/deneme", "ali", "12345");
                        Statement st = con.createStatement();
                        String ali = "'fff'";
                        st.execute("INSERT INTO deneme (name) VALUES(" + ali + ")");
                        //  ResultSet rs = st.executeQuery("select * from deneme");
                        //  ResultSetMetaData rsmd = rs.getMetaData();
                        //  String result = new String();


                        //  while (rs.next()) {
                        //      result += rsmd.getColumnName(1) + ": " + rs.getInt(1) + "\n";
                        //       result += rsmd.getColumnName(2) + ": " + rs.getString(2) + "\n";


                        //   }

                    } catch (SQLException e) {
                        e.printStackTrace();
                        con = null;
                    }

                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    });*/

}
