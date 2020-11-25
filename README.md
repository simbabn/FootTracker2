# Android APP

`Nazim Bensouiki - Maxime Chartier`

## Development
### Run external database into docker container

```bash
docker-compose up -d
```

### Features

- [ ] Intent externe photo
- [x] BDD externe
- [x] BDD interne
- [x] Maps geo
- [ ] Rotate
- [ ] Internationalization

### Database

## Repository interface

Interface Java pour définir comment doit se comporter un repository.
Le repository est instancié de manière static dans la class `MainActivity`.
Il est accessible depuis un `getter` public pour toutes les autres classes.

L'objet `MatchModel` représente un match par une class Java
```java
//...entity.MatchModel.java
...
public class MatchModel {
    private int id;
    private String competitionType, team1, team2;
    private int scoreTeam1, scoreTeam2;
    private boolean isPrivate;
    private String dateMatch;
...
```

Interface d'un `repository`
```java
//...repository.FootRepository.java
...
public interface FootRepository {
    void insertMatch(MatchModel match);
    ArrayList<MatchModel> getMatchs();
    void closeConnection();
}
```

[`Repository BDD SQLite`](app/src/main/java/fr/android/nazim/foottracker2/repo/MatchIntRepository.java)
[`Repository BDD MariaDB (with JDBC and Mysql-connector`](app/src/main/java/fr/android/nazim/foottracker2/repo/MatchExtRepository.java)

