package lost_and_found.projectx;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;

import com.google.zxing.Result;

import io.realm.Realm;
import it.auron.library.mecard.MeCard;
import it.auron.library.mecard.MeCardParser;
import me.dm7.barcodescanner.zxing.ZXingScannerView;
import lost_and_found.projectx.Models.QRData;
import lost_and_found.projectx.Realm.RealmController;

public class SimpleScannerActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {


    private ZXingScannerView mScannerView;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_scanner);
        mScannerView = new ZXingScannerView(this);
        ViewGroup contentFrame = (ViewGroup) findViewById(R.id.content_frame);
        mScannerView = new ZXingScannerView(this);
        contentFrame.addView(mScannerView);
        //get realm instance
        this.realm = RealmController.with(this).getRealm();
    }
    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(final Result result) {
        String meCardString = result.getText();
        try {
            MeCard meCard = MeCardParser.parse(meCardString);
            String name = meCard.getName();
            Intent intent = new Intent(SimpleScannerActivity.this,ScannedResultActivity.class);
            intent.putExtra("qrDataResult",meCardString);
            startActivity(intent);

        }catch (Exception e){
            QRData qrData = new QRData();
            qrData.setId((int) System.currentTimeMillis());
            qrData.setName(result.getText());
            qrData.setAddress("");
            qrData.setEmail("");
            qrData.setPhoneNumber("");
            qrData.setFbProfileLink("");
            realm.beginTransaction();
            realm.copyToRealm(qrData);
            realm.commitTransaction();
            AlertDialog.Builder builder = new AlertDialog.Builder(SimpleScannerActivity.this)
                    .setTitle("QRCode")
                    .setMessage(result.getText())
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            mScannerView.resumeCameraPreview(SimpleScannerActivity.this);
                        }
                    });
            builder.show();
        }
    }
}
