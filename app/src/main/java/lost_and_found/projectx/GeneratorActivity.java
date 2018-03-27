package tgo1014.projectx;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import io.realm.Realm;
import it.auron.library.mecard.MeCard;
import tgo1014.projectx.Models.GeneratedQRCodes;
import tgo1014.projectx.Realm.RealmController;
import tgo1014.projectx.Utility.Utils;

public class GeneratorActivity extends AppCompatActivity {

    private android.widget.EditText editTextoQR,etAddress,etEmail,etPhoneNumber, etFBURL;
    private android.widget.Button btnGerarQR,btnGenAgain,btnSaveQR;
    private LinearLayout llInputFields,llQRContainer;
    ProgressDialog progressDoalog;
    private FrameLayout qrFrame;
    ImageView qrCode;
    private Realm realm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generator);
        progressDoalog = new ProgressDialog(GeneratorActivity.this);
        this.btnGerarQR = (Button) findViewById(R.id.btnGerarQR);
        this.editTextoQR = (EditText) findViewById(R.id.editTextoQR);
        this.etAddress = (EditText) findViewById(R.id.etAddress);
        this.etEmail = (EditText) findViewById(R.id.etEmail);
        this.etPhoneNumber = (EditText) findViewById(R.id.etPhoneNumber);
        this.etFBURL = (EditText) findViewById(R.id.etFBURL);
        this.llInputFields = (LinearLayout) findViewById(R.id.llInputFields);
        this.llQRContainer = (LinearLayout) findViewById(R.id.llQRContainer);
        this.btnGenAgain = (Button) findViewById(R.id.btnGenAgain);
        this.btnSaveQR = (Button) findViewById(R.id.btnSaveQRCode);
        this.qrCode = (ImageView)findViewById(R.id.imageView);
        this.qrFrame = (FrameLayout) findViewById(R.id.qrFrame);
        this.realm = RealmController.with(this).getRealm();
        btnGenAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llInputFields.setVisibility(View.VISIBLE);
                llQRContainer.setVisibility(View.GONE);
            }
        });
        btnSaveQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.sharePost(GeneratorActivity.this,qrFrame);
            }
        });
        btnGerarQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressDoalog.setTitle("Generating QR Code");
                progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDoalog.show();
                MeCard meCard = new MeCard();
                meCard.setName(editTextoQR.getText().toString());
                meCard.setAddress(etAddress.getText().toString());
                meCard.setEmail(etEmail.getText().toString());
                meCard.setNote(etPhoneNumber.getText().toString());
                meCard.setUrl(etFBURL.getText().toString());
                String cardContent = meCard.buildString();

                geraQR(cardContent);
            }
        });
    }

    private void geraQR(final String texto){
        QRCodeWriter writer = new QRCodeWriter();

        try {
            BitMatrix bitMatrix = writer.encode(texto, BarcodeFormat.QR_CODE, 512, 512);
            int width = 512;
            int height = 512;
            final Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    if (bitMatrix.get(x,y))
                        bmp.setPixel(x, y, Color.BLACK);
                    else
                        bmp.setPixel(x, y, Color.WHITE);
                }
            }
            progressDoalog.dismiss();


            LayoutInflater li = LayoutInflater.from(GeneratorActivity.this);
            View promptsView = li.inflate(R.layout.alertdiaog_layout, null);

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    GeneratorActivity.this);

            // set prompts.xml to alertdialog builder
            alertDialogBuilder.setView(promptsView);

            final EditText userInput = (EditText) promptsView
                    .findViewById(R.id.editTextDialogUserInput);

            // set dialog message
            alertDialogBuilder
                    .setCancelable(false)
                    .setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(final DialogInterface dialog, int id) {
                                    // get user input and set it to result
                                    // edit text
                                    if (userInput.getText().toString().isEmpty()){
                                        Toast.makeText(GeneratorActivity.this,"Please enter some name",Toast.LENGTH_LONG).show();
                                        return;
                                    }
                                    llInputFields.setVisibility(View.GONE);
                                    llQRContainer.setVisibility(View.VISIBLE);
                                    qrCode.setImageBitmap(bmp);
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            final String id = System.currentTimeMillis()+"";
                                            final GeneratedQRCodes generatedQRCodes = new GeneratedQRCodes();
                                            generatedQRCodes.setId((int) System.currentTimeMillis());
                                            generatedQRCodes.setName(texto);
                                            generatedQRCodes.setStringId("QR-"+  userInput.getText().toString()+"_"+id  +".png");

                                            qrFrame.post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Utils.SaveImage(qrFrame, "QR-"+ userInput.getText().toString()+"_"+id +".png");
                                                }
                                            });

                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {


                                                    realm.beginTransaction();
                                                    realm.copyToRealm(generatedQRCodes);
                                                    realm.commitTransaction();
                                                    dialog.cancel();
                                                }
                                            });

                                        }
                                    }).start();
                                }
                            })
                    .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {
                                    dialog.cancel();
                                }
                            });

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();

        } catch (WriterException e) {
            Log.e("QR ERROR", e.toString());
            progressDoalog.dismiss();
            Toast.makeText(GeneratorActivity.this,"An error occured while generating code, Please try again",Toast.LENGTH_LONG).show();
        }
    }
}
