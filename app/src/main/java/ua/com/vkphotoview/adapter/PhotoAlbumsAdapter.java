package ua.com.vkphotoview.adapter;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import ua.com.vkphotoview.R;
import ua.com.vkphotoview.utils.DownloadImageTask;

public class PhotoAlbumsAdapter extends ArrayAdapter<JSONObject> {

    private final List<JSONObject> list;
    private final Activity context;
    private LayoutInflater layoutInflater;

    public PhotoAlbumsAdapter(Activity context, List<JSONObject> list) {
        super(context, R.layout.adapter_list_photo_albums, list);
        this.context = context;
        this.list = list;
    }

    class ViewHolder {
        protected TextView title;
        protected ImageView icon;
        protected ProgressBar bar;

        public ViewHolder(View view) {
            title = (TextView) view.findViewById(R.id.titlePhotoAlbum);
            icon = (ImageView) view.findViewById(R.id.imagePhotoAlbum);
            bar = (ProgressBar) view.findViewById(R.id.progressBarAlbum);
        }
    }


    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.adapter_list_photo_albums, null);
        }
        final ViewHolder holder = new ViewHolder(view);

        final JSONObject obj = getItem(position);
        try {
            holder.title.setText(obj.getString("title"));
            new DownloadImageTask(holder.icon, holder.bar)
                    .execute(obj.getString("thumb_src"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return view;
    }
}
