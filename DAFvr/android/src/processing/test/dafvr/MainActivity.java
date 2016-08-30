package processing.test.dafvr;
import android.os.Bundle;
import processing.core.PApplet;
import processing.cardboard.PCardboard;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import java.util.ArrayList;
import android.content.DialogInterface;
import android.Manifest;
public class MainActivity extends PCardboard {
  private static final int REQUEST_PERMISSIONS = 1;
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    PApplet sketch = new DAFvr();
    setSketch(sketch);
    init(sketch);
    setConvertTapIntoTrigger(true);
  }
  @Override
  public void onStart() {
    super.onStart();
    ArrayList<String> needed = new ArrayList<String>();
    int check;
    boolean danger = false;
    check = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
    if (check != PackageManager.PERMISSION_GRANTED) {
      needed.add(Manifest.permission.READ_EXTERNAL_STORAGE);
    } else {
      danger = true;
    }
    check = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    if (check != PackageManager.PERMISSION_GRANTED) {
      needed.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
    } else {
      danger = true;
    }
    if (!needed.isEmpty()) {
      ActivityCompat.requestPermissions(this, needed.toArray(new String[needed.size()]), REQUEST_PERMISSIONS);
    } else if (danger) {
      onPermissionsGranted();
    }
  }
  @Override
  public void onRequestPermissionsResult(int requestCode,
                                         String permissions[], int[] grantResults) {
    if (requestCode == REQUEST_PERMISSIONS) {
      if (grantResults.length > 0) {
        boolean granted = true;
        for (int i = 0; i < grantResults.length; i++) {
          if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
            granted = false;
            break;
          }
        }
        if (granted) onPermissionsGranted();
      }
    }
  }
}
