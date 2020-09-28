package com.example.sanaalgoworks;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.InputStream;

public class MyTasksActivity extends AppCompatActivity {
    private ImageView imgBack;
    Fragment fragment;
    public ProgressDialog progressDialog;
    private TextView text1, text2, text3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tasks);

        progressDialog = new ProgressDialog(MyTasksActivity.this);
        text1 = findViewById(R.id.text1);
        text2 = findViewById(R.id.text2);
        text3 = findViewById(R.id.text3);
        imgBack = findViewById(R.id.imgBack);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        fragment = new AsyncTaskFragment();
        loadfragment(fragment);

        text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment = new AsyncTaskFragment();
                loadfragment(fragment);
            }
        });


        text2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment = new ServiceFragment();
                loadfragment(fragment);
            }
        });

        text3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment = new ThreadFragment();
                loadfragment(fragment);
            }
        });

    }

    public void loadfragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.commit();


    }

    public void loadImage(ImageView imageView, String url) {
        new ImageDownloader(imageView).execute(url);
    }

    public class ImageDownloader extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;

        ImageDownloader(ImageView imageView) {
            this.imageView = imageView;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(MyTasksActivity.this);
            progressDialog.setTitle("Downloading Image Using AsyncTask");
            progressDialog.setMessage("Please wait...");
            progressDialog.setIndeterminate(false);
            progressDialog.show();
        }

        @Override
        protected Bitmap doInBackground(String... URL) {
            String imageURL = URL[0];
            Bitmap bitmap = null;
            try {
                InputStream input = new java.net.URL(imageURL).openStream();
                bitmap = BitmapFactory.decodeStream(input);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;


        }

        @Override
        protected void onPostExecute(Bitmap s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            imageView.setImageBitmap(s);
        }
    }

}


