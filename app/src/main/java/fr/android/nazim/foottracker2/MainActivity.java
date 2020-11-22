package fr.android.nazim.foottracker2;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import fr.android.nazim.foottracker2.repo.FootRepository;
import fr.android.nazim.foottracker2.repo.MatchExtRepository;
import fr.android.nazim.foottracker2.repo.MatchIntRepository;

public class MainActivity extends AppCompatActivity {

    private Button newMatch, previousMatch;
    private TextView textView;
    private static FootRepository repo;

    //methode qui communique avec le système pour lui demander de démarrer une acitivité donnée
    public void openActivity2(){
        Intent intent = new Intent(this, CreateMatch.class);
        startActivity(intent);
    }

    public void openSavedMatches(){
        Intent intent = new Intent(this, ListMatch.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Use SQL External database
        repo = new MatchExtRepository();
        //Use SQLite internal database
        //repo = new MatchIntRepository(this);

        newMatch = findViewById(R.id.button2);
        previousMatch = findViewById(R.id.button);
        textView = findViewById(R.id.textView);

        newMatch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openActivity2();
            }
        });

        previousMatch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openSavedMatches();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        repo.closeConnection();
    }

    public static FootRepository getRepo(){
        return repo;
    }
}