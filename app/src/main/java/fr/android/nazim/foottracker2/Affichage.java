package fr.android.nazim.foottracker2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class Affichage extends AppCompatActivity {

    Button btnHome,btnNewMatch;
    ListView listMatch;

    ArrayAdapter matchArrayAdapter;
    DataBaseHelper dataBaseHelper;

    public void homePageReturn(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void addNewMatch(){
        Intent intent = new Intent(this, SQLiteDemo.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affichage);
        
        btnHome = findViewById(R.id.btnHome);
        listMatch = findViewById(R.id.lv_matchlist2);
        btnNewMatch = findViewById(R.id.new_Match);

        dataBaseHelper = new DataBaseHelper(Affichage.this);


        //Display/add in ListView
        showMatchOnListView(dataBaseHelper);

        btnHome.setOnClickListener(new View.OnClickListener(){
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
    //method to display/add matches in listView
    private void showMatchOnListView(DataBaseHelper dataBaseHelper2) {
        matchArrayAdapter = new ArrayAdapter<MatchModel>(Affichage.this, android.R.layout.simple_list_item_1, dataBaseHelper2.getAllMatches());
        listMatch.setAdapter(matchArrayAdapter);
    }
}