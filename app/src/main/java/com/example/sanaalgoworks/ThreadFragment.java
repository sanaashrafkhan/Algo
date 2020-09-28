package com.example.sanaalgoworks;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;


public class ThreadFragment extends Fragment {
    View view;
    Button btnStart;
    TextView tv1, tv2;
    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_thread, container, false);
        init();
        return view;

    }

    public void init() {
        tv1 = view.findViewById(R.id.tv1);
        tv2 = view.findViewById(R.id.tv2);
        btnStart = view.findViewById(R.id.btnStart);
        btnStart.setText("Start");
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startProgress();
                btnStart.setText("Started");
            }
        });
    }

    public void startProgress() {
        progressBar.setProgress(0);
        tv1.setText("Progressing");
        tv2.setText("0");
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <= 10; i++) {
                    final int value = i;
                    doMyWork();

                    progressBar.post(new Runnable() {
                        @Override
                        public void run() {
                            tv1.setText("Progressing");
                            tv2.setText(String.valueOf(value));
                            progressBar.setProgress(value);
                            if (value == 10) {
                                btnStart.setText("Restart");

                            }
                        }
                    });
                }
            }
        };
        new Thread(runnable).start();
    }

    private void doMyWork() {
        try {
            SystemClock.sleep(5000);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}