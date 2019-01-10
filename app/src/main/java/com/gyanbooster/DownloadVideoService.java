package com.gyanbooster;


import android.app.IntentService;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.ResultReceiver;
import android.support.annotation.Nullable;
import android.util.Log;

import com.gyanbooster.constants.Constants;
import com.gyanbooster.utility.Util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public  class DownloadVideoService extends IntentService {

    public static final int UPDATE_PROGRESS = 8344;
    private String downloadUrl;
    private String videoTitle;
    private ResultReceiver receiver;



    public DownloadVideoService() {
        super(null); // That was the lacking constructor
    }

    public DownloadVideoService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        downloadUrl = intent.getStringExtra("downloadUrl");
        videoTitle = intent.getStringExtra("videoTitle");
        receiver = (ResultReceiver) intent.getParcelableExtra("receiver");

        DownloadVideo PB = new DownloadVideo(downloadUrl,videoTitle,receiver);
        PB.execute("");
    }


    private class DownloadVideo extends AsyncTask<String, Integer, Object> {

        String downloadUrl;
        String videoTitle;
        ResultReceiver receiver;


        public DownloadVideo(String downloadUrl, String videoTitle, ResultReceiver receiver) {
            this. downloadUrl=downloadUrl;
            this. videoTitle=videoTitle;
            this. receiver=receiver;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Util.showCenteredToast(DownloadVideoService.this, getString(R.string.downloaded_started));

        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);

        }

        @Override
        protected Object doInBackground(String... voids) {
            try {

                downloadVideo(downloadUrl,videoTitle+".mp4");

            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object response) {
            super.onPostExecute(response);
            Util.showCenteredToast(DownloadVideoService.this, getString(R.string.downloaded));

            Bundle resultData = new Bundle();
            resultData.putInt("progress" ,100);
            receiver.send(UPDATE_PROGRESS, resultData);

        }
    }

    private void downloadVideo(String fileURL, String fileName) {
        try {
            String rootDir = Environment.getExternalStorageDirectory()
                    + File.separator + Constants.APP_FOLDER_NAME;
            File rootFile = new File(rootDir);
            rootFile.mkdir();
            URL url = new URL(fileURL);
            HttpURLConnection c = (HttpURLConnection) url.openConnection();
            c.setRequestMethod("GET");
            c.setDoOutput(true);
            c.connect();
            FileOutputStream f = new FileOutputStream(new File(rootFile,
                    fileName));
            InputStream in = c.getInputStream();
            byte[] buffer = new byte[1024];
            int len1 = 0;
            long total = 0;
            int count = 0;

            int fileLength = c.getContentLength();


            while ((len1 = in.read(buffer)) > 0) {

                total += count;

                Bundle resultData = new Bundle();
                resultData.putInt("progress" ,(int) (total * 100 / fileLength));
                receiver.send(UPDATE_PROGRESS, resultData);


                f.write(buffer, 0, len1);
            }
            f.close();
        } catch (IOException e) {
            Log.d("Error....", e.toString());
        }
    }

}
