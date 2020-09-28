package com.example.sanaalgoworks;

import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Objects;

public class AsyncTaskFragment extends Fragment {
    View view;
    ImageView imageview;
    Button btnImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_async_task, container, false);
        init();
        return view;
    }

    public void init() {
        btnImage = view.findViewById(R.id.btnImage);
        imageview = view.findViewById(R.id.imageview);

        btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        ((MyTasksActivity) Objects.requireNonNull(getActivity())).loadImage(imageview, "https://wallpaperaccess.com/full/360436.jpg");
                    } else {
                        ((MyTasksActivity) getActivity()).loadImage(imageview, "https://wallpaperaccess.com/full/360436.jpg");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
