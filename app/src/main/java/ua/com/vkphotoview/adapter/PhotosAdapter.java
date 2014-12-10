package ua.com.vkphotoview.adapter;


import android.app.Activity;
import android.content.Context;
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
    private LayoutInflater layoutInflater;

    public PhotosAdapter(Activity context, List<JSONObject> list) {
        super(context, R.layout.adapter_list_photos, list);
        this.context = context;
        this.list = list;
    }

    class ViewHolder {
        protected ProgressBar bar;
        protected ImageView icon;

        public ViewHolder(View view) {
            icon = (ImageView) view.findViewById(R.id.imagePhoto);
            bar = (ProgressBar) view.findViewById(R.id.progressBarPhoto);
        }
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.adapter_list_photos, null);
        }
        final ViewHolder holder = new ViewHolder(view);

        try {
            new DownloadImageTask(holder.icon, holder.bar)
                    .execute(list.get(position).getString("photo_130"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return view;
    }
}
