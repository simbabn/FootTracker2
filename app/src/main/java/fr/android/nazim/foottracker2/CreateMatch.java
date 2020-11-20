package fr.android.nazim.foottracker2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;


import fr.android.nazim.foottracker2.entity.MatchModel;
import fr.android.nazim.foottracker2.repo.MatchIntRepository;

public class CreateMatch extends AppCompatActivity implements View.OnClickListener  {
    //references to buttons and other controls on the Layout
    Button btn_add, btn_viewAll, btn_setLoc;
    EditText et_compet, et_team1, et_team2, scoreTeam1, scoreTeam2;
    //String dateMatch;
    Switch sw_private;
    //ListView lv_matchList;

    ArrayAdapter matchArrayAdapter;
    MatchIntRepository matchIntRepository;

    public void homePageReturn(){
        Intent intent = new Intent(this, ListMatch.class);
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

        setContentView(R.layout.activity_create_match);
        btn_viewAll = findViewById(R.id.bt_viewAll);
        btn_add = findViewById(R.id.bt_add);
        et_compet = findViewById(R.id.et_competition);
        et_team1 = findViewById(R.id.et_Team1);
        et_team2 = findViewById(R.id.et_Team2);
        sw_private = findViewById(R.id.sw_active);
        scoreTeam1 = findViewById(R.id.scoreTeam1);
        scoreTeam2 = findViewById(R.id.scoreTeam2);
        btn_setLoc  = findViewById(R.id.setLocation);

        //Display/add in ListView
        showMatchOnListView(matchIntRepository);

        //button listeners
        btn_add.setOnClickListener(this);
        btn_setLoc.setOnClickListener(this);
        btn_viewAll.setOnClickListener(this);


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
    private void showMatchOnListView(MatchIntRepository matchIntRepository2) {
        //matchArrayAdapter = new ArrayAdapter<MatchModel>(CreateMatch.this, android.R.layout.simple_list_item_1, dataBaseHelper2.getAllMatches());
        //lv_matchList.setAdapter(matchArrayAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.setLocation:
                setLocation();
                break;
            case R.id.bt_viewAll:
                homePageReturn();

                MatchIntRepository matchIntRepository = new MatchIntRepository(CreateMatch.this);

                //Display in ListView
                showMatchOnListView(matchIntRepository);

                //Toast.makeText(SQLiteDemo.this, dataBaseHelper.getAllMatches().toString(), Toast.LENGTH_LONG).show();
                break;
            case R.id.bt_add:
                MatchModel matchModel;

                //Create Match object with ui values
                matchModel = new MatchModel(
                        et_compet.getText().toString(),
                        et_team1.getText().toString(),
                        et_team2.getText().toString(),
                        sw_private.isChecked(),
                        Integer.parseInt(scoreTeam1.getText().toString()),
                        Integer.parseInt(scoreTeam2.getText().toString()),
                        0
                );

                //Insert Match object into database
                MainActivity.getRepo().insertMatch(matchModel) ;
                Toast.makeText(CreateMatch.this, "Added", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}