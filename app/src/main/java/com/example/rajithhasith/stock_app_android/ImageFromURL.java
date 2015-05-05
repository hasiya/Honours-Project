package com.example.rajithhasith.stock_app_android;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;

/**
 * This class if for get image from a URL
 * extends AsyncTask.
 * Created by Rajith Hasith on 14/04/2015.
 */
public class ImageFromURL extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage;

    TextView LoadingTxt;

    /*
    * Constructor of the class
    * @param {ImageView} bmImage
    * @param {TextView} loadingTxt
    * */
    public ImageFromURL(ImageView bmImage , TextView loadingTxt) {
        this.bmImage = bmImage;
        this.LoadingTxt = loadingTxt;

    }

    /*
    * override function that run in background*/
    @Override
    protected Bitmap doInBackground(String... params) {

        String urldisplay = params[0];
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

    @Override
    protected void onPreExecute() {
        LoadingTxt.setText("Image Loading...");
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        LoadingTxt.setText("");
        bmImage.setImageBitmap(bitmap);
    }

}
