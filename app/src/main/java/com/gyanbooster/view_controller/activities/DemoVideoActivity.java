package com.gyanbooster.view_controller.activities;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.MediaController;
import android.widget.VideoView;

import com.gyanbooster.R;
import com.gyanbooster.databinding.DemoVideoBinding;
import com.gyanbooster.utility.Util;


public class DemoVideoActivity extends Activity {

    DemoVideoBinding demoVideoBinding;

    VideoView view;
       String URL = "https://archive.org/download/ksnn_compilation_master_the_internet/ksnn_compilation_master_the_internet_512kb.mp4";
//    String URL = "https://www.androidbegin.com/tutorial/AndroidCommercial.3gp";

    /* (non-Javadoc) * @see android.app.Activity#onCreate(android.os.Bundle)*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
// TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        demoVideoBinding = DataBindingUtil.setContentView(this,R.layout.demo_video);
        demoVideoBinding.setClick(this);

        view = findViewById(R.id.videoView1);
        Util.showProDialog(this);

        try {

            MediaController controller = new MediaController(this);
            controller.setAnchorView(view);
            Uri vid = Uri.parse(URL);
            view.setMediaController(controller);
            view.setVideoURI(vid);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        view.requestFocus();
        view.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            public void onPrepared(MediaPlayer mp) {
// TODO Auto-generated method stub
                Util.dismissProDialog();
                view.start();
            }
        });
    }
}