package com.example.altan.appmob;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Spinner spinner = (Spinner) findViewById(R.id.lng);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.langues, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        findViewById(R.id.poster3).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent affiche = new Intent(v.getContext(), info.class);
                affiche.putExtra("langue", (String)((Spinner) findViewById(R.id.lng)).getSelectedItem());
                affiche.putExtra("id", "337167");
                startActivity(affiche);
            }
        });

        findViewById(R.id.poster2).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent affiche = new Intent(v.getContext(), info.class);
                affiche.putExtra("langue", (String)((Spinner) findViewById(R.id.lng)).getSelectedItem());
                affiche.putExtra("id", "401981");
                startActivity(affiche);
            }
        });

        findViewById(R.id.go2).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent affiche = new Intent(v.getContext(), info.class);
                affiche.putExtra("langue", (String)((Spinner) findViewById(R.id.lng)).getSelectedItem());
                affiche.putExtra("id", ((EditText)findViewById(R.id.search)).getText().toString());
                startActivity(affiche);
            }
        });

        new DownloadImageTask((ImageView)findViewById(R.id.poster3))
                .execute("https://image.tmdb.org/t/p/w500/jjPJ4s3DWZZvI4vw8Xfi4Vqa1Q8.jpg");

        new DownloadImageTask((ImageView)findViewById(R.id.poster2))
                .execute("https://image.tmdb.org/t/p/w500/vLCogyfQGxVLDC1gqUdNAIkc29L.jpg");
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
