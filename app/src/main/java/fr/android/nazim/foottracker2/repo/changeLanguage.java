package fr.android.nazim.foottracker2.repo;

import android.content.res.Configuration;
import android.content.res.Resources;

import java.util.Locale;

public class changeLanguage {
    public static void changeLanguage(Resources res, String locale){
        Configuration config;
        config = new Configuration(res.getConfiguration());

        switch (locale){
            case "fr":
                config.locale = new Locale("es");
                break;
            case "en":
                config.locale = new Locale("en");
                break;
            default:
                config.locale = Locale.ENGLISH;
                break;
        }
        res.updateConfiguration(config, res.getDisplayMetrics());
        //relaod files
    }
}
