package com.example.saint.hophacksthesequel;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int GALLERY_REQUEST = 1;
    public static final String SERVER = "http://34.226.247.55/";

    ImageView IVuploadImage, IVdownloadImage;
    EditText ETuploadImage, ETdownloadImage;
    Button BuploadImage, BdownloadImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        IVdownloadImage = (ImageView) findViewById(R.id.IVdownloadImage);
        IVuploadImage = (ImageView) findViewById(R.id.IVuploadImage);
        ETdownloadImage = (EditText) findViewById(R.id.ETdownloadImage);
        ETuploadImage = (EditText) findViewById(R.id.ETuploadImage);
        BdownloadImage = (Button) findViewById(R.id.BdownloadImage);
        BuploadImage = (Button) findViewById(R.id.BuploadImage);

        IVuploadImage.setOnClickListener(this);
        BuploadImage.setOnClickListener(this);
        BdownloadImage.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.IVuploadImage:
                Intent selectFromGalley = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(selectFromGalley, GALLERY_REQUEST);
                break;

            case R.id.BuploadImage:
                Bitmap image = ((BitmapDrawable) IVuploadImage.getDrawable()).getBitmap();
                new UploadImage(ETuploadImage.getText().toString(),image).execute();
                break;

            case R.id.BdownloadImage:
                break;
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GALLERY_REQUEST && resultCode == RESULT_OK && data != null)
        {
            Uri selectedImage = data.getData();
            IVuploadImage.setImageURI(selectedImage);
        }
    }

    private class UploadImage extends AsyncTask<Void, Void, Void>
    {
        String name;
        Bitmap image;

        public UploadImage(String name, Bitmap image)
        {
            this.name = name;
            this.image = image;
        }

        @Override
        protected Void doInBackground(Void... params) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            String encodedImage = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);

            ArrayList<NameValuePair> data = new ArrayList<>();
            data.add(new BasicNameValuePair("name",name));
            data.add(new BasicNameValuePair("image",encodedImage));

            HttpClient client = new DefaultHttpClient(getHttpParams());
            HttpPost post = new HttpPost(SERVER + "www");

            try {
                Log.e("x", "x");
                post.setEntity(new UrlEncodedFormEntity(data));
                client.execute(post);

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(getApplicationContext(), "Image Uploaded", Toast.LENGTH_LONG).show();
        }
    }

    private HttpParams getHttpParams()
    {
        HttpParams myParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(myParams, 1000 *30);
        HttpConnectionParams.setSoTimeout(myParams, 1000 * 30);
        return myParams;
    }
}