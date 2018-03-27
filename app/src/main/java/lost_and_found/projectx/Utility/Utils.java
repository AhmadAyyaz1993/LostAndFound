package tgo1014.projectx.Utility;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by ahmad on 10/06/2017.
 */

public class Utils {
    public static void SaveImage(FrameLayout frameLayout, String fileName) {

        try {
        Bitmap finalBitmap = loadBitmapFromView(frameLayout);
        String root = Environment.getExternalStorageDirectory().getAbsolutePath();
        File myDir = new File(root + "/QRCodes");
        myDir.mkdirs();

        String fname = fileName;
        File file = new File (myDir, fname);
        if (file.exists ()) file.delete ();

            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void DeleteFile(String fileName) {

        String root = Environment.getExternalStorageDirectory().getAbsolutePath();
        File myDir = new File(root + "/QRCodes");
        myDir.mkdirs();

        String fname = fileName;
        File file = new File (myDir, fname);
        if (file.exists ()) file.delete ();
        try {
            file.delete();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void sharePost(Context context, FrameLayout view){
        try {
//            Bitmap b = ((BitmapDrawable) view.getDrawable()).getBitmap();
            Bitmap b = loadBitmapFromView(view);
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("image/jpeg");
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            b.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            String path = MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    b, "Title", null);
            Uri imageUri = Uri.parse(path);
            share.putExtra(Intent.EXTRA_STREAM, imageUri);
//            share.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=traveldestination.evonative.com.traveldestination");
            context.startActivity(Intent.createChooser(share, "Share Via"));
        }catch (Exception e){
            Toast.makeText(context,"CHECK PERMISSION FROM SETTINGS",Toast.LENGTH_LONG).show();
        }
    }
    public static Bitmap loadBitmapFromView(View v) {
        Bitmap bitmap;
        v.setDrawingCacheEnabled(true);
        bitmap = Bitmap.createBitmap(v.getDrawingCache());
        v.setDrawingCacheEnabled(false);
        return bitmap;
    }
}
