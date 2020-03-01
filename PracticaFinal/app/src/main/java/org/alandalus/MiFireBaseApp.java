package org.alandalus;

import com.google.firebase.database.FirebaseDatabase;

public class MiFireBaseApp extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}