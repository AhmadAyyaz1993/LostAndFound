package tgo1014.projectx.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

import tgo1014.projectx.History.GeneratedQRCodesHistory;
import tgo1014.projectx.Models.GeneratedQRCodes;
import tgo1014.projectx.R;
import tgo1014.projectx.Realm.RealmController;
import tgo1014.projectx.ScannedResultActivity;
import tgo1014.projectx.Utility.Utils;

/**
 * Created by ahmad on 10/06/2017.
 */

public class GridViewAdapter extends BaseAdapter {

    // Declare variables
    private Activity activity;
    private List<String> filepath;
    private List<String> filename;
    Context context;
    private static LayoutInflater inflater = null;

    public GridViewAdapter(Activity a, List<String> fpath, List<String> fname) {
        activity = a;
        filepath = fpath;
        filename = fname;
        inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public int getCount() {
        return filepath.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, final View convertView, ViewGroup parent) {
        View vi = convertView;
        if (convertView == null)
            vi = inflater.inflate(R.layout.gridview_item, null);
        // Locate the TextView in gridview_item.xml
        TextView text = (TextView) vi.findViewById(R.id.text);
        // Locate the ImageView in gridview_item.xml
        final ImageView image = (ImageView) vi.findViewById(R.id.image);
        Button export = (Button) vi.findViewById(R.id.btnExport);
        Button delete = (Button) vi.findViewById(R.id.btnDelete);
        final FrameLayout qrFrame = (FrameLayout) vi.findViewById(R.id.qrFrame);
        export.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.sharePost(activity,qrFrame);
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.DeleteFile(filename.get(position));
                filename.remove(position);
                filepath.remove(position);
                notifyDataSetChanged();
            }
        });
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GeneratedQRCodes generatedQRCodes = RealmController.with(activity).getGenQRCodeData(filename.get(position));
                String data = generatedQRCodes.getName();
                Intent intent = new Intent(activity,ScannedResultActivity.class);
                intent.putExtra("qrDataResult",data);
                activity.startActivity(intent);
            }
        });

        // Set file name to the TextView followed by the position
        String qrName = filename.get(position).substring(filename.get(position).indexOf("-")+1,filename.get(position).indexOf("_"));
        text.setText(qrName);
        // Picasso.with(activity).load(filepath[position]).into(image);
        // Decode the filepath with BitmapFactory followed by the position
        Bitmap bmp = BitmapFactory.decodeFile(filepath.get(position));
//
//        // Set the decoded bitmap into ImageView
        image.setImageBitmap(bmp);
        return vi;
    }
}