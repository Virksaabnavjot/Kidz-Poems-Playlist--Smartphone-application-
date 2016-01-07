package com.kidzpoems.ireland.dublin.navsingh;


import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.Menu;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

public class VideoPlayer extends Activity {
	private TextView textytext;
	 private VideoView video;
	 private MediaController ctlr;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video_player);
		Intent intent = getIntent();
		String passLinkToVideoPlayer = intent.getExtras().getString("passLinkToVideoPlayer");
		String path = passLinkToVideoPlayer;
		//textytext = (TextView)findViewById(R.id.show);
		//textytext.setText(passLinkToVideoPlayer);
		/*
		video = (VideoView) findViewById(R.id.videoView1);
		Uri uri=Uri.parse(path);
        video.setVideoURI(uri);
        //ctlr = new MediaController(this);
       // ctlr.setMediaPlayer(video);
        //video.setMediaController(ctlr);
       // video.requestFocus();
        video.start();
        */
		final ProgressDialog pDialog = new ProgressDialog(this);

	    // Set progressbar message
	    pDialog.setMessage("Buffering...");
	    pDialog.setIndeterminate(false);
	    pDialog.setCancelable(false);
	    // Show progressbar
	    pDialog.show();

	    try {
	        // Start the MediaController
	    	video = (VideoView) findViewById(R.id.videoView1);
	        MediaController mediacontroller = new MediaController(this);
	       mediacontroller.setAnchorView(video);      
	        
	        Uri videoUri = Uri.parse(path);
	        video.setMediaController(mediacontroller);
	        video.setVideoURI(videoUri);

	    } catch (Exception e) {

	        e.printStackTrace();
	    }

	    video.requestFocus();
	    video.setOnPreparedListener(new OnPreparedListener() {
	        // Close the progress bar and play the video
	        public void onPrepared(MediaPlayer mp) {
	            pDialog.dismiss();
	            video.start();
	        }
	    });
	    video.setOnCompletionListener(new OnCompletionListener() {

	        public void onCompletion(MediaPlayer mp) {
	            if (pDialog.isShowing()) {
	                pDialog.dismiss();
	            }
	            finish();               
	        }
	    });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.video_player, menu);
		return true;
	}
	
	@Override
    public void onBackPressed() {
            super.onBackPressed();
            this.finish();
    }

}
