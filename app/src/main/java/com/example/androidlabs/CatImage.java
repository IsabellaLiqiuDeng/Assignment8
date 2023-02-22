package com.example.androidlabs;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

// show The Image in a ImageView

public class CatImage extends AsyncTask<String, Integer, Bitmap> {

    private Context mContext;
    private ProgressBar mProgressBar;
    private ImageView mImageView;

    public CatImage(Context context, ProgressBar progressBar, ImageView imageView) {
        mContext = context;
        mProgressBar = progressBar;
        mImageView = imageView;
    }



    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        String imageUrl = params[0];
        Bitmap bitmap = null;
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            bitmap = BitmapFactory.decodeStream(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        mProgressBar.setProgress(values[0]);
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        mProgressBar.setVisibility(View.GONE);
        if (result != null) {
            mImageView.setImageBitmap(result);
        } else {
            Toast.makeText(mContext, "Failed to load image", Toast.LENGTH_SHORT).show();
        }
    }
}