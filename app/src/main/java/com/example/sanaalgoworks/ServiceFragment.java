package com.example.sanaalgoworks;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import static android.app.Activity.RESULT_OK;

public class ServiceFragment extends Fragment {
    View view;
    private TextView textView, txtPath;
    Button button1;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_service, container, false);
        init();
        return view;
    }

    public void init() {
        textView = view.findViewById(R.id.status);
        txtPath = view.findViewById(R.id.txtPath);
        button1 = view.findViewById(R.id.button1);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, 111);
            }
        });
    }


    public void checkPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(getActivity(), permission) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{permission}, requestCode);
        } else {
            btnClick();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 111) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getActivity(), "Storage Permission Granted", Toast.LENGTH_SHORT).show();
                btnClick();
            } else {
                Toast.makeText(getActivity(), "Storage Permission Denied", Toast.LENGTH_SHORT).show();
                checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, 111);
            }
        }
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            try {

                Bundle bundle = intent.getExtras();
                if (bundle != null) {
                    String string = bundle.getString(MyDownloadService.FILEPATH);
                    int resultCode = bundle.getInt(MyDownloadService.RESULT);
                    if (resultCode == RESULT_OK) {
                        Toast.makeText(getActivity(), "Download complete", Toast.LENGTH_LONG).show();
                        textView.setText("Download done");
                        txtPath.setText("Storage Path: " + string);
                        progressDialog.hide();
                    } else {
                        Toast.makeText(getActivity(), "Download failed", Toast.LENGTH_LONG).show();
                        textView.setText("Download failed");
                        progressDialog.hide();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        getActivity().registerReceiver(receiver, new IntentFilter(MyDownloadService.NOTIFY_UPDATE));
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(receiver);
    }

    public void btnClick() {

        Intent intent = new Intent(getActivity(), MyDownloadService.class);
        intent.putExtra(MyDownloadService.FILENAME, "maven.pdf");
        intent.putExtra(MyDownloadService.URL, "http://maven.apache.org/maven-1.x/maven.pdf");
        getActivity().startService(intent);
        textView.setText("Service started");

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Downloading File Using Service");
        progressDialog.setMessage("Please wait...");
        progressDialog.setIndeterminate(false);
        progressDialog.show();
    }

}
