package tgo1014.projectx.App;

import android.app.Application;
import android.support.multidex.MultiDexApplication;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by ahmad on 09/06/2017.
 */

public class CustomApplication extends MultiDexApplication {

    @Override
    public void onCreate() {

        super.onCreate();
        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .name(Realm.DEFAULT_REALM_NAME)
                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);

    }
}
