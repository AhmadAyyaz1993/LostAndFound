package lost_and_found.projectx;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import lost_and_found.projectx.History.GeneratedQRCodesHistory;
import lost_and_found.projectx.History.ScannedHistoryActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
    private Button btnGerarQR;
    private Button btnLerQR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView editSobre = (TextView) findViewById(R.id.txtSobre);
        Button btnLerQR = (Button) findViewById(R.id.btnLerQR);
        Button btnGerarQR = (Button) findViewById(R.id.btnGerarQR);
        Button btnScannedHistory = (Button) findViewById(R.id.btnScannedHistory);
        Button btnGenQRHistory = (Button) findViewById(R.id.btnGenQRHistory);



        btnLerQR.setOnClickListener(this);
        btnGerarQR.setOnClickListener(this);
        btnScannedHistory.setOnClickListener(this);
        btnGenQRHistory.setOnClickListener(this);
        editSobre.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.btnGerarQR:
                intent = new Intent(this, GeneratorActivity.class);
                startActivity(intent);
                break;
            case R.id.btnLerQR:
                //Inicializa camera para scannear QRCode
                //start the scanning activity from the com.google.zxing.client.android.SCAN intent
                intent = new Intent(this, SimpleScannerActivity.class);
                startActivity(intent);

                break;
            case R.id.btnScannedHistory:
                intent = new Intent(this, ScannedHistoryActivity.class);
                startActivity(intent);
                break;
            case R.id.btnGenQRHistory:
                intent = new Intent(this, GeneratedQRCodesHistory.class);
                startActivity(intent);
                break;
            case R.id.txtSobre:

//                new LibsBuilder()
//                        //provide a style (optional) (LIGHT, DARK, LIGHT_DARK_TOOLBAR)
//                        .withActivityStyle(Libs.ActivityStyle.LIGHT_DARK_TOOLBAR)
//                        .withAboutIconShown(true)
//                        .withAboutVersionShown(true)
//                        .withLicenseShown(true)
//                        .withAboutDescription(getString(R.string.strDesenvolvidoPor) + "<br>" +
//                                getString(R.string.strFeedback) + "tgo1014@gmail.com")
//                        .start(this);
                break;
        }
    }
}
