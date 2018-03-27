package tgo1014.projectx.Adapters;

import android.content.Context;

import io.realm.RealmResults;
import tgo1014.projectx.Models.QRData;

/**
 * Created by ahmad on 09/06/2017.
 */

public class RealmQRDataAdapter extends RealmModelAdapter<QRData> {

    public RealmQRDataAdapter(Context context, RealmResults<QRData> realmResults, boolean automaticUpdate) {

        super(context, realmResults, automaticUpdate);
    }
}
