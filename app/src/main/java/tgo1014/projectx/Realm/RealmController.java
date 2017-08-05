package tgo1014.projectx.Realm;

import android.app.Activity;
import android.app.Application;
import android.app.Fragment;

import io.realm.Realm;
import io.realm.RealmResults;
import tgo1014.projectx.Models.GeneratedQRCodes;
import tgo1014.projectx.Models.QRData;

/**
 * Created by ahmad on 09/06/2017.
 */

public class RealmController {
    private static RealmController instance;
    private final Realm realm;

    public RealmController(Application application) {
        realm = Realm.getDefaultInstance();
    }

    public static RealmController with(Fragment fragment) {

        if (instance == null) {
            instance = new RealmController(fragment.getActivity().getApplication());
        }
        return instance;
    }

    public static RealmController with(Activity activity) {

        if (instance == null) {
            instance = new RealmController(activity.getApplication());
        }
        return instance;
    }

    public static RealmController with(Application application) {

        if (instance == null) {
            instance = new RealmController(application);
        }
        return instance;
    }

    public static RealmController getInstance() {

        return instance;
    }

    public Realm getRealm() {

        return realm;
    }

    //Refresh the realm istance
    public void refresh() {

        realm.refresh();
    }

    //clear all objects from Book.class
    public void clearAll() {

        realm.beginTransaction();
        realm.clear(QRData.class);
        realm.clear(GeneratedQRCodes.class);
        realm.commitTransaction();
    }

    //find all objects in the Book.class
    public RealmResults<QRData> getQRData() {

        return realm.where(QRData.class).findAll();
    }

    //find all objects in the Book.class
    public RealmResults<GeneratedQRCodes> getGeneratedQrdata() {

        return realm.where(GeneratedQRCodes.class).findAll();
    }

    //query a single item with the given id
    public QRData getQrData(String id) {

        return realm.where(QRData.class).equalTo("id", id).findFirst();
    }

    //query a single item with the given id
    public GeneratedQRCodes getGenQRCodeData(String id) {

        return realm.where(GeneratedQRCodes.class).equalTo("stringId", id).findFirst();
    }
    //check if Book.class is empty
    public boolean hasBooks() {

        return !realm.allObjects(QRData.class).isEmpty();
    }

    //query example
    public RealmResults<QRData> queryedBooks() {

        return realm.where(QRData.class)
                .contains("author", "Author 0")
                .or()
                .contains("title", "Realm")
                .findAll();

    }
}
