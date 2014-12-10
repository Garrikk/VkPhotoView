package ua.com.vkphotoview;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ua.com.vkphotoview.adapter.PhotoAlbumsAdapter;

public class AlbumsListActivity extends ActionBarActivity {

    private List<JSONObject> listPhotoAlbums = new ArrayList<JSONObject>();
    private ListView listViewPhotoAlbums;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_list);
        new loadPhotoAlbum().execute();

        listViewPhotoAlbums = (ListView) findViewById(R.id.listAlbums);
        listViewPhotoAlbums.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                JSONObject photoAlbum = listPhotoAlbums.get(position);
                Intent intent = new Intent(AlbumsListActivity.this, PhotosListActivity.class);
                try {
                    intent.putExtra("album_id", photoAlbum.getString("id"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                startActivity(intent);
                AlbumsListActivity.this.finish();
            }
        });
    }

    private void startPhotosListActivity() {
        startActivity(new Intent(this, PhotosListActivity.class));
    }

    private void getListPhotoAlbums() {

        VKRequest request = new VKRequest("photos.getAlbums",
                VKParameters.from("need_covers", 1));
        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {

                try {
                    JSONObject jObject = response.json.getJSONObject("response");
                    JSONArray jsonArray = jObject.getJSONArray("items");
                    for (int i = jsonArray.length() - 1; i >= 0; i--) {
                        JSONObject nValue = jsonArray.getJSONObject(i);
                        listPhotoAlbums.add(nValue);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                listViewPhotoAlbums = (ListView) findViewById(R.id.listAlbums);
                PhotoAlbumsAdapter adapter = new PhotoAlbumsAdapter(AlbumsListActivity.this, listPhotoAlbums);
                listViewPhotoAlbums.setAdapter(adapter);
            }

            @Override
            public void onError(VKError error) {
            }

            @Override
            public void attemptFailed(VKRequest request, int attemptNumber, int totalAttempts) {
            }
        });
    }

    protected class loadPhotoAlbum extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected Void doInBackground(Void... params) {
            getListPhotoAlbums();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
        }
    }
}
