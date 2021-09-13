package com.demo.appUpdater;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.lib.appUpdater.AppUpdater;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressDialog = new ProgressDialog(this);


        Button down2 = (Button) findViewById(R.id.down);
        down2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                progressDialog.show();

                String url = "https://github.com/TripathiViky/AndroidAppDeploymentTest/blob/main/app-debug.apk?raw=true";

                AppUpdater app = new AppUpdater(MainActivity.this);

                app.updateAppWithUrl(url, "Update 2.0");


            }
        });
    }
}
