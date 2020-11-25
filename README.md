# Android APP

`Nazim Bensouiki - Maxime Chartier`

## Development
### Run external database into docker container

```bash
docker-compose up -d
```

### Features

- [x] [Take photo](#take-photo-and-display-result)
- [x] [BDD externe](#database)
- [x] [BDD interne](#database)
- [x] [Maps geo](#maps-location-subscribe)
- [ ] [Rotate](#rotation)
- [ ] [Internationalization](#internationalization)

### Database

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

Interface d'un **repository**
- [`FootRepository`](app/src/main/java/fr/android/nazim/foottracker2/repo/FootRepository.java)
```java
//...repository.FootRepository.java
...
public interface FootRepository {
    void insertMatch(MatchModel match);
    ArrayList<MatchModel> getMatchs();
    void closeConnection();
}
```

 - [`Repository BDD SQLite`](app/src/main/java/fr/android/nazim/foottracker2/repo/MatchIntRepository.java)
 - [`Repository BDD MariaDB (with JDBC and Mysql-connector)`](app/src/main/java/fr/android/nazim/foottracker2/repo/MatchExtRepository.java)

 **Selection** du repository à utiliser :
 - [`MainActivity`](app/src/main/java/fr/android/nazim/foottracker2/MainActivity.java)
```java
...
//Use SQL External database
repo = new MatchExtRepository();
//Use SQLite internal database
repo = new MatchIntRepository(this);
...
```

### Maps location subscribe

 - [`LocationMatch`](app/src/main/java/fr/android/nazim/foottracker2/LocationMatch.java)
 ```java 
LocationRequest locationRequest;
...
locationRequest = new LocationRequest();
locationRequest.setInterval(1000 * DEFAULT_UPDATE_INTERVAL);
locationRequest.setFastestInterval(1000 * FAST_UPDATE_INTERVAL);
locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
locationCallBack = new LocationCallback() {
    @Override
    public void onLocationResult(LocationResult locationResult) {
        super.onLocationResult(locationResult);
        updateValues(locationResult.getLastLocation());
    }
};
...
private void updateValues(Location location) {
    varLat.setText(String.valueOf(location.getLatitude()));
    varLong.setText(String.valueOf(location.getLongitude()));
    Geocoder geocoder = new Geocoder(LocationMatch.this);
    try{
        List<Address> address = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(),1);
        varAddress.setText(address.get(0).getAddressLine(0));
    }
    catch (Exception e){
        varAddress.setText("Unable to get street address");
    }
}
```

### Rotation

 - [`land/activity_main.xml`](app/src/main/res/layout-land/activity_main.xml)

### Internationalization



### Take Photo and display result

 - [`LocationMatch`](app/src/main/java/fr/android/nazim/foottracker2/TakePhoto.java)
 ```java 
static final int REQUEST_IMAGE_CAPTURE = 1;
private void dispatchTakePictureIntent() {
    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
}
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
        Bundle extras = data.getExtras();
        Bitmap imageBitmap = (Bitmap) extras.get("data");
        selectedImage.setImageBitmap(imageBitmap);
    }
}
```