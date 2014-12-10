package ua.com.vkphotoview.adapter;


import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

import ua.com.vkphotoview.R;

public class PhotosAdapter extends ArrayAdapter<JSONObject> {

    private final List<JSONObject> list;
    private final Activity context;

    public PhotosAdapter(Activity context, List<JSONObject> list) {
        super(context, R.layout.adapter_list_photos, list);
        this.context = context;
        this.list = list;
    }

    class ViewHolder {
        protected ProgressBar bar;
        protected ImageView icon;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = null;
        if (convertView == null) {
            LayoutInflater inflator = context.getLayoutInflater();
            view = inflator.inflate(R.layout.adapter_list_photo_albums, null);

            final ViewHolder viewHolder = new ViewHolder();

            viewHolder.bar = (ProgressBar) view.findViewById(R.id.progressBarPhoto);
            viewHolder.icon = (ImageView) view.findViewById(R.id.imagePhotoAlbum);
            view.setTag(viewHolder);
        } else {
            view = convertView;
        }
        final ViewHolder holder = (ViewHolder) view.getTag();

        try {
            new DownloadImageTask((ImageView) view.findViewById(R.id.imagePhotoAlbum),
                    (ProgressBar) view.findViewById(R.id.progressBarAlbum))
                    .execute(list.get(position).getString("photo_130"));


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return view;
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
