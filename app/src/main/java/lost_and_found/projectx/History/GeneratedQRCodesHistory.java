package lost_and_found.projectx.History;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import lost_and_found.projectx.Adapters.GridViewAdapter;
import lost_and_found.projectx.R;

/**
 * Created by ahmad on 10/06/2017.
 */

public class GeneratedQRCodesHistory extends AppCompatActivity {
    private List<String> FilePathStrings;
    private List<String> FileNameStrings;
    private File[] listFile;
    GridView grid;
    GridViewAdapter adapter;
    File file;
    TextView noDesigns;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_designs);
        noDesigns = (TextView) findViewById(R.id.NoDesigns);
        noDesigns.setVisibility(View.GONE);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setTitle("QR-Codes");
//        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Check for SD Card
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            Toast.makeText(this, "Error! No SDCARD Found!", Toast.LENGTH_LONG)
                    .show();
        } else {
            // Locate the image folder in your SD Card
            file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                    + File.separator + "QRCodes");
            // Create a new folder if no folder named SDImageTutorial exist
            file.mkdirs();
        }

        if (file.isDirectory() && file.exists()) {
            listFile = file.listFiles();
            if (listFile.equals(null)||listFile.length == 0){
//                Toast.makeText(this, "No Designs to Show", Toast.LENGTH_LONG)
//                        .show();
                noDesigns.setVisibility(View.VISIBLE);
            }
            // Create a String array for FilePathStrings
            FilePathStrings = new ArrayList<>();
            // Create a String array for FileNameStrings
            FileNameStrings = new ArrayList<>();

            for (int i = 0; i < listFile.length; i++) {
                // Get the path of the image file
                FilePathStrings.add(listFile[i].getAbsolutePath());
                // Get the name image file
                FileNameStrings.add(listFile[i].getName());
            }
        }

        // Locate the GridView in gridview_main.xml
        grid = (GridView) findViewById(R.id.gridview);
        // Pass String arrays to LazyAdapter Class
        adapter = new GridViewAdapter(this, FilePathStrings, FileNameStrings);
        // Set the LazyAdapter to the GridView
        grid.setAdapter(adapter);

        // Capture gridview item click
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

//                Intent i = new Intent(MyDesigns.this, ViewImage.class);
//                // Pass String arrays FilePathStrings
//                i.putExtra("filepath", FilePathStrings);
//                // Pass String arrays FileNameStrings
//                i.putExtra("filename", FileNameStrings);
//                // Pass click position
//                i.putExtra("position", position);
//                startActivity(i);
            }

        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
