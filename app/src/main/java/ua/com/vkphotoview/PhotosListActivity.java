package ua.com.vkphotoview;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ua.com.vkphotoview.adapter.PhotosAdapter;

public class PhotosListActivity extends ActionBarActivity {

    private List<JSONObject> listPhotos = new ArrayList<JSONObject>();
    private GridView listViewPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos_list);
        new loadPhoto().execute();

        listViewPhoto = (GridView) findViewById(R.id.listPhotos);
        listViewPhoto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                JSONObject photoAlbum = listPhotos.get(position);
                Intent intent = new Intent(PhotosListActivity.this, FullScreenPhotoActivity.class);
                try {
                    intent.putExtra("photo", photoAlbum.getString("photo_130"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                startActivity(intent);
                PhotosListActivity.this.finish();
            }
        });

    }

    private void getListPhotos() {

        String id = getIntent().getExtras().getString("album_id");
        VKRequest request = new VKRequest("photos.get",
                VKParameters.from(VKApiConst.ALBUM_ID, id));
        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {

                try {
                    JSONObject jObject = response.json.getJSONObject("response");
                    JSONArray jsonArray = jObject.getJSONArray("items");
                    for (int i = jsonArray.length() - 1; i >= 0; i--) {
                        JSONObject nValue = jsonArray.getJSONObject(i);
                        listPhotos.add(nValue);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                listViewPhoto = (GridView) findViewById(R.id.listPhotos);
                PhotosAdapter adapter = new PhotosAdapter(PhotosListActivity.this, listPhotos);
                listViewPhoto.setAdapter(adapter);
            }

            @Override
            public void attemptFailed(VKRequest request, int attemptNumber, int totalAttempts) {
            }

            @Override
            public void onError(VKError error) {
            }
        });
    }

    protected class loadPhoto extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected Void doInBackground(Void... params) {
            getListPhotos();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
        }
    }
}
