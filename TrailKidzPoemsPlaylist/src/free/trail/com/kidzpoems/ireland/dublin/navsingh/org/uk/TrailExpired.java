package free.trail.com.kidzpoems.ireland.dublin.navsingh.org.uk;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class TrailExpired extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_trail_expired);
	}

	public void buy(View v){
		final String appPackageName = "com.kidzpoems.ireland.dublin.navsingh"; // getPackageName() from Context or Activity object
    	try {
    	    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
    	} catch (android.content.ActivityNotFoundException anfe) {
    	    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName)));
    	}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.trail_expired, menu);
		return true;
	}

}
