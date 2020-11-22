package fr.android.nazim.foottracker2.repo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import fr.android.nazim.foottracker2.entity.MatchModel;

public class MatchIntRepository extends SQLiteOpenHelper implements FootRepository {

    public static final String MATCH_TABLE = "match";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_COMPET_NAME = "name";
    public static final String COLUMN_TEAM_1 = "team1";
    public static final String COLUMN_TEAM_2 = "team2";
    public static final String COLUMN_ISPRIVATE = "isPrivate";
    public static final String COLUMN_SCORE1 = "score1";
    public static final String COLUMN_SCORE2 = "score2";

    public MatchIntRepository(@Nullable Context context) {
        super(context, "match.db", null, 1);

        //Create database
        SQLiteDatabase db = this.getWritableDatabase();
        String createTableStatement = "CREATE TABLE IF NOT EXISTS `match` (`" + COLUMN_ID + "` INTEGER PRIMARY KEY, `" + COLUMN_COMPET_NAME + "` TEXT, `" + COLUMN_TEAM_1 + "` TEXT, `" + COLUMN_TEAM_2 + "` TEXT, `" + COLUMN_SCORE1 + "` INT, `" + COLUMN_SCORE2 + "` INT, `" + COLUMN_ISPRIVATE + "` BOOL)"; //will define the new table
        db.execSQL(createTableStatement);
        Log.i("REPOSITORY_sqlite", "Database created");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //create a method addOne record the new DataBase
    public void insertMatch(MatchModel matchModel ){
        SQLiteDatabase db = this.getWritableDatabase();//came from the properties of SQLiteOpenHelper
        //getWritableDatabase for insert actions
        //getReadableDatabase to select (read) actions

        ContentValues cv = new ContentValues();//stores data in pairs : cv.put("name",value) cv.getString("name") work like an Hashmap
        //like to put Extras in an Intent

        //not necessary to provide an ID number when inserting a new record.  ID beacause is auto increment dataBase
        cv.put(COLUMN_COMPET_NAME, matchModel.getCompetitionType());
        cv.put(COLUMN_TEAM_1, matchModel.getTeam1());
        cv.put(COLUMN_TEAM_2, matchModel.getTeam2());
        cv.put(COLUMN_ISPRIVATE, matchModel.isPrivate());
        cv.put(COLUMN_SCORE1, matchModel.getScoreTeam1());
        cv.put(COLUMN_SCORE2, matchModel.getScoreTeam2());

        db.insert(MATCH_TABLE, null ,cv);
    }

    public ArrayList<MatchModel>  getMatchs() {
        //Create an empty list
        //Fill it from the database query
        //Return it to the MainActivity

        ArrayList<MatchModel> returnList  = new ArrayList<>();

        //get data from the dataBase

        String queryString = "SELECT * FROM `" + MATCH_TABLE + "`";
        //use getWritableDatabase only when you plan to insert, update or delete records : locks the data file so other process may not access it
        //use getReadableDatabase when you plan to SELECT items from the database
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);// <-- cursor return type

        //moveToFirst Returns a true if there where items selected
        if (cursor.moveToFirst()){
            //loop through the cursor (result set) and create new match objects. Put them into the return List
            do {
                int matchID = cursor.getInt(0);
                String competName = cursor.getString(1);
                String team1Name = cursor.getString(2);
                String team2Name = cursor.getString(3);
                Integer score_team1 = cursor.getInt(4);
                Integer score_team2 = cursor.getInt(5);
                Boolean isPrivateStatut = cursor.getInt (6) == 1 ? true: false; //we have to convert the result from an int to a bool /

                MatchModel newMatchModel = new MatchModel(
                        competName,
                        team1Name,
                        team2Name,
                        isPrivateStatut,
                        score_team1,
                        score_team2 ,
                        matchID);
                returnList.add(newMatchModel);

            }while(cursor.moveToNext()); //<-- should be move to Next
        }
        else{
            //failure. do not anything to the list.
            //fisher
        }

        //close both the cursor and the db when done.
        cursor.close();
        db.close();
        return returnList;
    }

    public void closeConnection() {

    }

}
