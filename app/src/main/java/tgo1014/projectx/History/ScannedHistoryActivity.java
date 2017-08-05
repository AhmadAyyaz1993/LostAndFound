package tgo1014.projectx.History;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import io.realm.Realm;
import io.realm.RealmResults;
import tgo1014.projectx.Adapters.QRDataAdapter;
import tgo1014.projectx.Adapters.RealmQRDataAdapter;
import tgo1014.projectx.Models.QRData;
import tgo1014.projectx.R;
import tgo1014.projectx.Realm.RealmController;

public class ScannedHistoryActivity extends AppCompatActivity {
    private QRDataAdapter adapter;
    private Realm realm;
    private LayoutInflater inflater;
    private RecyclerView recycler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanned_history);
        recycler = (RecyclerView) findViewById(R.id.recycler);
        setupRecycler();
        //get realm instance
        this.realm = RealmController.with(this).getRealm();
        RealmController.with(this).refresh();
        // get all persisted objects
        // create the helper adapter and notify data set changes
        // changes will be reflected automatically
        setRealmAdapter(RealmController.with(this).getQRData());
    }
    public void setRealmAdapter(RealmResults<QRData> qrData) {

        RealmQRDataAdapter realmAdapter = new RealmQRDataAdapter(this.getApplicationContext(), qrData, true);
        // Set the data and tell the RecyclerView to draw
        adapter.setRealmAdapter(realmAdapter);
        adapter.notifyDataSetChanged();
    }
    private void setupRecycler() {
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recycler.setHasFixedSize(true);

        // use a linear layout manager since the cards are vertically scrollable
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(layoutManager);

        // create an empty adapter and add it to the recycler view
        adapter = new QRDataAdapter(this);
        recycler.setAdapter(adapter);
    }
}
