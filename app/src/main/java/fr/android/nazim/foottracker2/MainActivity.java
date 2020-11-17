package fr.android.nazim.foottracker2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static android.os.Build.VERSION_CODES.O;

public class MainActivity extends AppCompatActivity {

    private Button newMatch, previousMatch;
    private TextView textView;

    //methode qui communique avec le système pour lui demander de démarrer une acitivité donnée
    public void openActivity2(){
        Intent intent = new Intent(this, SQLiteDemo.class);
        startActivity(intent);
    }

    public void openSavedMatches(){
        Intent intent = new Intent(this, Affichage.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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


}