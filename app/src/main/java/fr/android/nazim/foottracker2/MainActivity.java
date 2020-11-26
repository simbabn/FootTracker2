package fr.android.nazim.foottracker2;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

import fr.android.nazim.foottracker2.repo.FootRepository;
import fr.android.nazim.foottracker2.repo.MatchExtRepository;
import fr.android.nazim.foottracker2.repo.MatchIntRepository;

public class MainActivity extends AppCompatActivity {

    private Button newMatch, previousMatch, take_photo, changeLang;
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

    public void openTakePhoto(){
        Intent intent = new Intent(this, TakePhoto.class);
        startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Use SQL External database
        //repo = new MatchExtRepository();
        //Use SQLite internal database
        repo = new MatchIntRepository(this);

        newMatch = findViewById(R.id.button2);
        previousMatch = findViewById(R.id.button);
        textView = findViewById(R.id.textView);
        take_photo = findViewById(R.id.btn_takephoto);
        changeLang = findViewById(R.id.changeLang);

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
        take_photo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openTakePhoto();
            }
        });

        changeLang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangeLanguageDialog();
            }
        });

    }

    private void showChangeLanguageDialog(){
        //array of language to display in a alert dialog
        final String[] listItems =  {"Français","English"};
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
        mBuilder.setTitle("Choose Language...");
        //int which;
        mBuilder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0){
                    //French
                    Configuration conf = new Configuration();
                    conf.locale = new Locale("fr");
                    getResources().updateConfiguration(conf, getResources().getDisplayMetrics());
                    recreate();
                }
                else if(which == 1){
                    //english
                    Configuration conf = new Configuration();
                    conf.locale = new Locale("en");
                    getResources().updateConfiguration(conf, getResources().getDisplayMetrics());
                    recreate();
                }
                //dismiss alert dialog when language selected
                dialog.dismiss();
            }
        });
        AlertDialog mDialog = mBuilder.create();
        //show alert dialog
        mDialog.show();
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