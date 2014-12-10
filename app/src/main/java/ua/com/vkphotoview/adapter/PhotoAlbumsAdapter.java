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
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.List;

import ua.com.vkphotoview.R;
import ua.com.vkphotoview.utils.DownloadImageTask;

public class PhotoAlbumsAdapter extends ArrayAdapter<JSONObject> {

    private final List<JSONObject> list;
    private final Activity context;

    public PhotoAlbumsAdapter(Activity context, List<JSONObject> list) {
        super(context, R.layout.adapter_list_photo_albums, list);
        this.context = context;
        this.list = list;
    }

    class ViewHolder {
        protected TextView title;
        protected ImageView icon;
        protected ProgressBar bar;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = null;
        if (convertView == null) {
            LayoutInflater inflator = context.getLayoutInflater();
            view = inflator.inflate(R.layout.adapter_list_photo_albums, null);

            final ViewHolder viewHolder = new ViewHolder();

            viewHolder.title = (TextView) view.findViewById(R.id.titlePhotoAlbum);
            viewHolder.icon = (ImageView) view.findViewById(R.id.imagePhotoAlbum);
            viewHolder.bar = (ProgressBar) view.findViewById(R.id.progressBarAlbum);
            view.setTag(viewHolder);
        } else {
            view = convertView;
        }
        final ViewHolder holder = (ViewHolder) view.getTag();
        try {
            holder.title.setText(list.get(position).getString("title"));

            new DownloadImageTask(holder.icon,holder.bar)
                    .execute(list.get(position).getString("thumb_src"));


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return view;
    }
}
