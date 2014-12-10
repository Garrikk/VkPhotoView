package ua.com.vkphotoview.adapter;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import ua.com.vkphotoview.R;
import ua.com.vkphotoview.utils.DownloadImageTask;

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
            view = inflator.inflate(R.layout.adapter_list_photos, null);

            final ViewHolder viewHolder = new ViewHolder();

            viewHolder.icon = (ImageView) view.findViewById(R.id.imagePhotoAlbum);
            viewHolder.bar = (ProgressBar) view.findViewById(R.id.progressBarPhoto);
            view.setTag(viewHolder);
        } else {
            view = convertView;
        }
        final ViewHolder holder = (ViewHolder) view.getTag();

        try {
            new DownloadImageTask(holder.icon, holder.bar)
                    .execute(list.get(position).getString("photo_130"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return view;
    }
}
