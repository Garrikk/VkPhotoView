package ua.com.vkphotoview;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.InputStream;
import java.net.URL;

public class FullScreenPhotoActivity extends ActionBarActivity {

    private ImageView icon;
    private ProgressBar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_fullscreen);
        icon = (ImageView) findViewById(R.id.imageFullScreenPhoto);
        bar = (ProgressBar) findViewById(R.id.progressFullScreen);

        new DownloadImageTask(icon, bar).execute(getIntent().getExtras().getString("photo"));


    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

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
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new URL(urldisplay).openStream();
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
}
