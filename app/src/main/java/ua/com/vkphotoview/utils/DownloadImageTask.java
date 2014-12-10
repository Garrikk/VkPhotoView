package ua.com.vkphotoview.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.InputStream;
import java.net.URL;


public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    ProgressBar bar;
    ImageView bmImage;

    public DownloadImageTask(ImageView bmImage, ProgressBar bar) {
        this.bmImage = bmImage;
        this.bar = bar;
    }

    protected void onPreExecute() {
        bar.setVisibility(View.VISIBLE);
    }

    protected Bitmap doInBackground(String... urls) {
        String url = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new URL(url).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {
        bar.setVisibility(View.GONE);
        bmImage.setImageBitmap(result);
    }
}
