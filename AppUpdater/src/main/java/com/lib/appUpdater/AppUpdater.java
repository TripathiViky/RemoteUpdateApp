package com.lib.appUpdater;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.webkit.URLUtil;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Objects;


public class AppUpdater extends AppCompatActivity {

    Context context;

    public AppUpdater(Context context) {
        this.context = context;
    }

    public void updateAppWithUrl(String url, String fileName){
        
        if(URLUtil.isValidUrl(url)){
            new DownloadUpdatedVersion().execute(url, fileName);
        }
    }

    private class DownloadUpdatedVersion extends AsyncTask<String, Integer, Boolean> {


        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(context);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Updating...");

            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

        }

        @Override
        protected Boolean doInBackground(String... values) {
            String downloadUrl = values[0];
            String fileName = values[1];
            Boolean flag = false;
            int count;


                String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + "/";
                File outputFile = new File(path+""+fileName);

                while (outputFile.exists()) {
                    boolean deleted = outputFile.delete();

                }

                File directory = new File(path);
                if (!directory.exists()) {
                    directory.mkdirs();
                }

                try {
                    URL url = new URL(downloadUrl);
                    URLConnection connection = url.openConnection();
                    connection.connect();

                    Integer totalSize = connection.getContentLength();

                    InputStream input = new BufferedInputStream(url.openStream());

                    OutputStream output = new FileOutputStream(outputFile);

                    byte data[] = new byte[1024];

                    long total = 0;

                    while ((count = input.read(data)) != -1) {
                        total += count;

                        publishProgress(Integer.valueOf("" + (int) ((total * 100) / totalSize)));

                        output.write(data, 0, count);
                    }

                    output.flush();

                    output.close();
                    input.close();
                    openNewVersion(outputFile.getPath());
                    flag = true;
                } catch (Exception e) {
                    Log.e("Error: ", e.getMessage());
                    flag = false;
                }



            return flag;
        }

        private void openNewVersion(String path) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(
                    getUriFromFile(path),
                    "application/vnd.android.package-archive"
            );
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            context.startActivity(intent);
        }

        private Uri getUriFromFile(String path) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                return Uri.fromFile(new File(path));
            } else {

                return FileProvider.getUriForFile(
                        context,
                        context.getPackageName() + ".provider",
                        new File(path)
                );
            }
        }

        protected void onProgressUpdate(Integer... progress) {

            String msg = "";
            Integer updatedProgress = progress[0];
            if (updatedProgress != null) {
                progressDialog.setProgress(updatedProgress);
                if (updatedProgress > 99){
                    msg = "Finishing... ";
                } else{
                    msg = "Downloading... "+updatedProgress;
                }
            }

            progressDialog.setMessage(msg);

            progressDialog.setProgress(progress[0]);
        }


        @Override
        protected void onPostExecute(Boolean result) {

            progressDialog.dismiss();
            if (result != null && result) {
                Toast.makeText(context, "Please install the updated application.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Please try again later", Toast.LENGTH_SHORT).show();
            }
        }


    }


}
