package com.test.audiotest.Activity;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestPermissions();
    }

    private void requestPermissions(){
        readExternalStoragePermission.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
        writeExternalStoragePermission.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    private ActivityResultLauncher<String> readExternalStoragePermission =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    Log.i(isGranted.toString(),isGranted.toString());
                    // Permission is granted. Continue the action or workflow in your
                    // app.
                } else {

                    // Explain to the user that the feature is unavailable because the
                    // features requires a permission that the user has denied. At the
                    // same time, respect the user's decision. Don't link to system
                    // settings in an effort to convince the user to change their
                    // decision.
                }
            });

    private ActivityResultLauncher<String> writeExternalStoragePermission =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    Log.i(isGranted.toString(),isGranted.toString());

                    // Permission is granted. Continue the action or workflow in your
                    // app.
                } else {

                    // Explain to the user that the feature is unavailable because the
                    // features requires a permission that the user has denied. At the
                    // same time, respect the user's decision. Don't link to system
                    // settings in an effort to convince the user to change their
                    // decision.
                }
            });
}
