package fr.android.nazim.foottracker2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import fr.android.nazim.foottracker2.entity.MatchModel;
import fr.android.nazim.foottracker2.repo.MatchIntRepository;

public class ListMatch extends AppCompatActivity {

    Button btnHome, btnNewMatch;
    ListView listMatch;

    ArrayAdapter matchArrayAdapter;

    public void homePageReturn() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void addNewMatch() {
        Intent intent = new Intent(this, CreateMatch.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_match);

        btnHome = findViewById(R.id.btnHome);
        listMatch = findViewById(R.id.lv_matchlist2);
        btnNewMatch = findViewById(R.id.new_Match);


        //Run to thread for not blocking UI
        new Thread(new Runnable() {
            public void run() {
                //Create matchs array
                matchArrayAdapter = new ArrayAdapter<MatchModel>(
                        ListMatch.this,
                        android.R.layout.simple_list_item_1,
                        MainActivity.getRepo().getMatchs()
                );
                //Display match array
                listMatch.setAdapter(matchArrayAdapter);
            }
        }).start();


        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homePageReturn();
            }
        });

        btnNewMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewMatch();
            }
        });

    }
}