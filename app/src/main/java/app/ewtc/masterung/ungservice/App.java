package app.ewtc.masterung.ungservice;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by masterung on 15/11/2017 AD.
 */

public class App extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
//       FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
