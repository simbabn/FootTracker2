package fr.android.nazim.foottracker2.repo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import fr.android.nazim.foottracker2.entity.MatchModel;

public class MatchIntRepository extends SQLiteOpenHelper {

    public static final String MATCH_TABLE = "MATCH_TABLE";
    public static final String COLUM_COMPET_NAME = "COMPET_NAME";
    public static final String COLUMN_TEAM_1 = "TEAM1";
    public static final String COLUMN_TEAM_2 = "TEAM2";
    public static final String COLUMN_ISPRIVATE = "ISPRIVATE";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_SCORE1 = "SCORE1";
    public static final String COLUMN_SCORE2 = "SCORE2";

    public MatchIntRepository(@Nullable Context context) {
        //voir 40 min in vidéo
        super(context, "match.db", null, 1);
    }

    //this is called the first time a databse is accessed. There should be code in here to create a new database
    //onCreate method is automatically called when the app requests or inputs new data.
    //We need to create a new table inside this method
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + MATCH_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY, " + COLUM_COMPET_NAME + " TEXT, " + COLUMN_TEAM_1 + " TEXT, " + COLUMN_TEAM_2 + " TEXT, " + COLUMN_ISPRIVATE + " BOOL) " ; //will define the new table
        //MATCH_TABLE IS GONE TO BE USED OFTEN IN THE APP
        //REFACTOR -> INTRODUCE CONSTANT -> CONSTANT

        db.execSQL(createTableStatement);
    }

    //this is called if the database version number changes. It prevents previous users apps from breaking when you change the database design
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //create a method addOne record the new DataBase
    public boolean addOne(MatchModel matchModel ){
        SQLiteDatabase db = this.getWritableDatabase();//came from the properties of SQLiteOpenHelper
        //getWritableDatabase for insert actions
        //getReadableDatabase to select (read) actions

        ContentValues cv = new ContentValues();//stores data in pairs : cv.put("name",value) cv.getString("name") work like an Hashmap
        //like to put Extras in an Intent

        //not necessary to provide an ID number when inserting a new record.  ID beacause is auto increment dataBase
        cv.put(COLUM_COMPET_NAME, matchModel.getCompetitionType());
        cv.put(COLUMN_TEAM_1, matchModel.getTeam1());
        cv.put(COLUMN_TEAM_2, matchModel.getTeam2());
        cv.put(COLUMN_ISPRIVATE, matchModel.isPrivate());

        long insert = db.insert(MATCH_TABLE, null ,cv);
        if (insert == -1){
            return false;
        }
        else{
            return true;
        }
    }

    public boolean deleteOne(MatchModel matchModel){
        //find matchModel in the database. it it found, delete it and return true
        //it it is not found return false

        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + MATCH_TABLE + " WHERE " + COLUMN_ID + " = " + matchModel.getId();

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()){
            return true;
        }
        else {
            return false;
        }

    }

    public List<MatchModel> getAllMatches() {
        //Create an empty list
        //Fill it from the database query
        //Return it to the MainActivity

        List<MatchModel> returnList  = new ArrayList<>();

        //get data from the dataBase

        String queryString = "SELECT * FROM " + MATCH_TABLE;
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
                Boolean isPrivateStatut = cursor.getInt (4) == 1 ? true: false; //we have to convert the result from an int to a bool /
                //String dateMatch = cursor.getString(7);
                // Turnary Operator : see notes

                MatchModel newMatchModel = new MatchModel(
                        competName,
                        team1Name,
                        team2Name,
                        isPrivateStatut,
                        0,
                        0 ,
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

}
