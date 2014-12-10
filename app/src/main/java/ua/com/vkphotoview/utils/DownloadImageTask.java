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

    public DownloadImageTask(ImageView icon, ProgressBar bar) {
        this.bmImage = icon;
        this.bar = bar;
    }

    protected void onPreExecute() {
        bar.setVisibility(View.VISIBLE);
    }

    protected Bitmap doInBackground(String... urls) {
        String url = urls[0];
        Bitmap image = null;
        try {
            InputStream in = new URL(url).openStream();
            image = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return image;
    }

    protected void onPostExecute(Bitmap result) {
        bar.setVisibility(View.GONE);
        bmImage.setImageBitmap(result);
    }
}
