package fr.android.nazim.foottracker2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class SavedMatches extends AppCompatActivity {
    ListView lv_matchList;
    Button btn_home, btn_newMatch;

    //methode qui communique avec le système pour lui demander de démarrer une acitivité donnée
    public void openHome() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void openNewMatch() {
        Intent intent = new Intent(this, SQLiteDemo.class);
        startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_matches);

        lv_matchList = findViewById(R.id.lv_matchList);
        btn_home = findViewById(R.id.homeButton1);
        btn_newMatch = findViewById(R.id.savedMatches);

        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHome();
            }
        });

        btn_newMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewMatch();
            }
        });
    }
}