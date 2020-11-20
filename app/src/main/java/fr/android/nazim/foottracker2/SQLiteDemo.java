package fr.android.nazim.foottracker2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;


import java.util.Date;
import java.time.LocalDateTime;
import java.util.List;

public class SQLiteDemo extends AppCompatActivity {
    //references to buttons and other controls on the Layout
    Button btn_add, btn_viewAll, btn_setLoc;
    EditText et_compet, et_team1, et_team2, scoreTeam1, scoreTeam2;
    //String dateMatch;
    Switch sw_private;
    //ListView lv_matchList;

    ArrayAdapter matchArrayAdapter;
    DataBaseHelper dataBaseHelper;

    public void homePageReturn(){
        Intent intent = new Intent(this, Affichage.class);
        startActivity(intent);
    }

    public void setLocation(){
        Intent intent = new Intent(this, LocationMatch.class);
        startActivity(intent);
    }

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s_q_lite_demo);

        btn_viewAll = findViewById(R.id.bt_viewAll);
        btn_add = findViewById(R.id.bt_add);
        et_compet = findViewById(R.id.et_competition);
        et_team1 = findViewById(R.id.et_Team1);
        et_team2 = findViewById(R.id.et_Team2);
        sw_private = findViewById(R.id.sw_active);
        scoreTeam1 = findViewById(R.id.scoreTeam1);
        scoreTeam2 = findViewById(R.id.scoreTeam2);
        btn_setLoc  = findViewById(R.id.setLocation);
        //dateMatch = findViewById(R.id.insertdate);

        //lv_matchList = findViewById(R.id.lv_matchList);

        dataBaseHelper = new DataBaseHelper(SQLiteDemo.this);

        //Display/add in ListView
        showMatchOnListView(dataBaseHelper);

        //button listeners
        btn_add.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                MatchModel matchModel;

                try {
                    matchModel = new MatchModel(1, et_compet.getText().toString(),et_team1.getText().toString(), et_team2.getText().toString(),  sw_private.isChecked());
                    //Toast.makeText(SQLiteDemo.this, matchModel.toString(), Toast.LENGTH_LONG ).show();
                }catch(Exception e){
                    Toast.makeText(SQLiteDemo.this, "Error creating customer", Toast.LENGTH_LONG ).show();
                    matchModel = new MatchModel(-1, "error", "error","error",false);
                }
                //making a reference to the new MatchDataBase
                DataBaseHelper dataBaseHelper = new DataBaseHelper(SQLiteDemo.this);

                boolean success = dataBaseHelper.addOne(matchModel);

                Toast.makeText(SQLiteDemo.this, "Succces = " + success, Toast.LENGTH_SHORT).show();
                //Display/add in ListView
                showMatchOnListView(dataBaseHelper);

            }
        });


        btn_viewAll.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                homePageReturn();

                DataBaseHelper dataBaseHelper = new DataBaseHelper(SQLiteDemo.this);

                //Display in ListView
                showMatchOnListView(dataBaseHelper);

                //Toast.makeText(SQLiteDemo.this, dataBaseHelper.getAllMatches().toString(), Toast.LENGTH_LONG).show();
            }
        });

        btn_setLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            setLocation();
            }
        });

        /* ________ NE PAS ENLEVER : ListView (AFFICHAGE DES MATCHS) _______*/
        /*lv_matchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MatchModel clickedMatch = (MatchModel) parent.getItemAtPosition(position);
                dataBaseHelper.deleteOne(clickedMatch);
                showMatchOnListView(dataBaseHelper);
                Toast.makeText(SQLiteDemo.this, "DELETED "+clickedMatch,Toast.LENGTH_LONG).show();
            }
        });*/


    }

    //method to display/add matches in listView
    private void showMatchOnListView(DataBaseHelper dataBaseHelper2) {
        matchArrayAdapter = new ArrayAdapter<MatchModel>(SQLiteDemo.this, android.R.layout.simple_list_item_1, dataBaseHelper2.getAllMatches());
        //lv_matchList.setAdapter(matchArrayAdapter);
    }
}