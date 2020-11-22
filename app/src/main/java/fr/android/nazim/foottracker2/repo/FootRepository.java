package fr.android.nazim.foottracker2.repo;

import java.sql.SQLException;
import java.util.ArrayList;

import fr.android.nazim.foottracker2.entity.MatchModel;

public interface FootRepository {
    void insertMatch(MatchModel match);
    ArrayList<MatchModel> getMatchs();
    void closeConnection();
}