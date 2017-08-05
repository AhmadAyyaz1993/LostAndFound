package tgo1014.projectx;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import io.realm.Realm;
import it.auron.library.mecard.MeCard;
import it.auron.library.mecard.MeCardParser;
import tgo1014.projectx.Models.QRData;
import tgo1014.projectx.Realm.RealmController;

public class ScannedResultActivity extends AppCompatActivity {

    private android.widget.TextView editTextoQR,etAddress,etEmail,etPhoneNumber, etFBURL;
    private Realm realm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanned_result);
        this.realm = RealmController.with(this).getRealm();
        this.editTextoQR = (TextView) findViewById(R.id.editTextoQR);
        this.etAddress = (TextView) findViewById(R.id.etAddress);
        this.etEmail = (TextView) findViewById(R.id.etEmail);
        this.etPhoneNumber = (TextView) findViewById(R.id.etPhoneNumber);
        this.etFBURL = (TextView) findViewById(R.id.etFBURL);
        etPhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:"+etPhoneNumber.getText().toString()));
                startActivity(callIntent);
            }
        });
        String scannedResult = getIntent().getStringExtra("qrDataResult");
        MeCard meCard = MeCardParser.parse(scannedResult);
        String name = meCard.getName();
        String address = meCard.getAddress();
        String email = meCard.getEmail();
        String phoneNumber = meCard.getNote();
        String fbURL = meCard.getUrl();
        name = name == null?"":name;
        address = address==null?"":address;
        email = email==null?"":email;
        phoneNumber = phoneNumber==null?"":phoneNumber;
        fbURL = fbURL==null?"":fbURL;
        editTextoQR.setText(name);
        etAddress.setText(address);
        etEmail.setText(email);
        etPhoneNumber.setText(phoneNumber);
        etFBURL.setText(fbURL);
        QRData qrData = new QRData();
        qrData.setId((int) System.currentTimeMillis());
        qrData.setName(name);
        qrData.setAddress(address);
        qrData.setEmail(email);
        qrData.setPhoneNumber(phoneNumber);
        qrData.setFbProfileLink(fbURL);
        realm.beginTransaction();
        realm.copyToRealm(qrData);
        realm.commitTransaction();

    }
}
