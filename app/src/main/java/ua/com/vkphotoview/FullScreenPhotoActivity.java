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

import ua.com.vkphotoview.utils.DownloadImageTask;

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

        new DownloadImageTask(icon, bar).
                execute(getIntent().getExtras().getString("photo"));
    }
}
