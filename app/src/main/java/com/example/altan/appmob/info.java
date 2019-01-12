package com.example.altan.appmob;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;

import okhttp3.*;

public class info extends AppCompatActivity {

    OkHttpClient client = new OkHttpClient();

    String url = "https://api.themoviedb.org/3/movie/"; // La base url du site API
    String langue = "en-EN"; // Par défaut

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.retour).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                close();
            }
        });


        OkHttpHandler handler = new OkHttpHandler();
        Intent i = getIntent();
        langue = i.getStringExtra("langue");
        handler.execute((String)i.getStringExtra("id")); //284054

    }

    public void close(){
        this.finish();
    }

    class OkHttpHandler extends AsyncTask {
        OkHttpClient client = new OkHttpClient();

        protected void onPostExecute(Object o){
            super.onPostExecute(o);
            try {
                JSONObject json = new JSONObject((String)o);
                ((TextView)findViewById(R.id.oriTitle)).setText(json.getString("original_title"));
                ((TextView)findViewById(R.id.date)).setText(json.getString("release_date"));
                ((TextView)findViewById(R.id.overview)).setText(json.getString("overview"));
                ((TextView)findViewById(R.id.TITRE)).setText(json.getString("title"));
                ((TextView)findViewById(R.id.pop)).setText(json.getString("popularity"));
                ((TextView)findViewById(R.id.notes)).setText(json.getString("vote_average") + "/10  (" + json.getString("vote_count") + "votes)");

                String genres = "";
                JSONArray a = json.getJSONArray("genres");
                for(int i = 0; i < a.length(); ++i){
                    genres += (i == 0 ? "" : " / ") + ((JSONObject)a.get(i)).getString("name");
                }
                ((TextView)findViewById(R.id.genre)).setText(genres);

                new DownloadImageTask((ImageView)findViewById(R.id.poster))
                        .execute("https://image.tmdb.org/t/p/w500/" + json.getString("poster_path"));


                Toast.makeText(getApplicationContext(),
                        "Chargement réussie !",
                        Toast.LENGTH_LONG).show();
            } catch (JSONException e) {
                e.printStackTrace();
                ((TextView)findViewById(R.id.TITRE)).setText("Champs chelou");
            }
        }

        @Override
        protected Object doInBackground(Object[] params) {
            Log.i("test", "bonjour");

            HttpUrl.Builder u = HttpUrl.parse(url + params[0]).newBuilder();
            u.addQueryParameter("api_key", "e466bb63afb8268760a6ea44735f4729");
            u.addQueryParameter("language", langue);

            Request request = new Request.Builder()
                                .url(u.build().toString())
                                .build();

            try {
                Response response = client.newCall(request).execute();
                return response.body().string();
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
            Toast.makeText(getApplicationContext(),
                    "Image chargée !",
                    Toast.LENGTH_LONG).show();
        }
    }


}
