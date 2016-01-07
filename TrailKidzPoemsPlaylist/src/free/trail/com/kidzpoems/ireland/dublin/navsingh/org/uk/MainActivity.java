package free.trail.com.kidzpoems.ireland.dublin.navsingh.org.uk;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



import free.trail.com.kidzpoems.ireland.dublin.navsingh.org.uk.CustomAdapter.ViewHolder;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.net.ParseException;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	private final long ONE_DAY = 24 * 60 * 60 * 1000;
	ListView list;
    CustomAdapter adapter;
    public  MainActivity CustomListView = null;
    public  ArrayList<ListModel> CustomListViewValuesArr = new ArrayList<ListModel>();
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        
        
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
	    String installDate = preferences.getString("InstallDate", null);
	    if(installDate == null) {
	        // First run, so save the current date
	        SharedPreferences.Editor editor = preferences.edit();
	        Date now = new Date();
	        String dateString = formatter.format(now);
	        editor.putString("InstallDate", dateString);
	        // Commit the edits!
	        editor.commit();
	    }
	    else {
	        // This is not the 1st run, check install date
	        Date before = null;
			try {
				before = (Date)formatter.parse(installDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (java.text.ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        Date now = new Date();
	        long diff = now.getTime() - before.getTime();
	        long days = diff / ONE_DAY;
	        //long days = 2;
	        if(days > 7) { // More than 30 days?
	             // Expired !!!
	        	Toast.makeText(this,
	        			"Trail Expired, \n Please buy full \n version on playstore", 
	        			Toast.LENGTH_LONG)
	        	.show();
	        	/*
	        	final String appPackageName = "com.kidzpoems.ireland.dublin.navsingh"; // getPackageName() from Context or Activity object
	        	try {
	        	    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
	        	} catch (android.content.ActivityNotFoundException anfe) {
	        	    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName)));
	        	}
	        	*/
	        	
	        	Intent intent = new Intent(this, free.trail.com.kidzpoems.ireland.dublin.navsingh.org.uk.TrailExpired.class);
	        	startActivity(intent);
	        	finish();
	        }
        
	    }
        
        
		//dbConnectionforPoemPlaylistData();
CustomListView = this;
        
        /******** Take some data in Arraylist ( CustomListViewValuesArr ) ***********/
        setListData();
         
        Resources res =getResources();
        list= ( ListView )findViewById( R.id.list );  
        // List defined in XML ( See Below )
         
        /**************** Create Custom Adapter *********/
        adapter=new CustomAdapter( CustomListView, CustomListViewValuesArr,res );
        list.setAdapter( adapter );
	}
	
	/****** Function to set data in ArrayList *************/
	private void setListData() {
		String data;
		try {
            DefaultHttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet("http://navsingh.org.uk/KidzPoemPlaylist/getdata.php");
            HttpResponse response = client.execute(request);
            HttpEntity entity=response.getEntity();
            data=EntityUtils.toString(entity);
            Log.e("STRING", data);
            try {
		JSONArray json=new JSONArray(data);
		for (int i=0;i<json.length(); i++) {
			JSONObject obj=json.getJSONObject(i);
	          String name=obj.getString("name");
	          String link=obj.getString("link");
	          String image=obj.getString("image");
            final ListModel sched = new ListModel();
                 
              /******* Firstly take data in model object ******/
               sched.setHeadingName(name);
               sched.setLinkName(link);
               sched.setImageLinkName(image); 
              //If we have many images we can use this line to change the images
               // sched.setImage("image"+i);
               sched.setImage("image");
               
               
               
            /******** Take Model Object in ArrayList **********/
            CustomListViewValuesArr.add( sched );
        }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
               }
                   
                 } catch (ClientProtocolException e) {
                     Log.d("HTTPCLIENT", e.getLocalizedMessage());
                 } catch (IOException e) {
                     Log.d("HTTPCLIENT", e.getLocalizedMessage());
                 }
	}
	
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater()
				.inflate(R.menu.main, menu);
		return true;
	}
	
	/*****************  This function is used by adapter ****************/
    public void onItemClick(int mPosition)
    {
        ListModel tempValues = ( ListModel ) CustomListViewValuesArr.get(mPosition);

        Intent intent = new Intent(this, VideoPlayer.class); 
        String passLinkToVideoPlayer = tempValues.getLinkName();
        intent.putExtra("passLinkToVideoPlayer",passLinkToVideoPlayer);
       // Show Toast                  

        Toast.makeText(CustomListView, 
    			"Now Playing \n"+tempValues.getHeadingName(), 
    			Toast.LENGTH_LONG)
    	.show();
        
        startActivity(intent);
        
    }
    
    /*
    public void dbConnectionforPoemPlaylistData()
	{
    	String data;
        List<String> r = new ArrayList<String>();
        ArrayAdapter<String>adapter=new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,r);
       // ListView list=(ListView)findViewById(R.id.listView1);
          try {
              DefaultHttpClient client = new DefaultHttpClient();
              HttpGet request = new HttpGet("http://navsingh.org.uk/KidzPoemPlaylist/getdata.php");
              HttpResponse response = client.execute(request);
              HttpEntity entity=response.getEntity();
              data=EntityUtils.toString(entity);
              Log.e("STRING", data);
              try {
               
         JSONArray json=new JSONArray(data);
         for(int i=0;i<json.length(); i++)
         {
          JSONObject obj=json.getJSONObject(i);
          String name=obj.getString("name");
          String year=obj.getString("year");
          String age=obj.getString("age");
          Log.e("STRING", name);
          r.add(name);
          r.add(year);
          r.add(age);
          list.setAdapter(adapter);
          
         }
         
        } catch (JSONException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
        }
            
          } catch (ClientProtocolException e) {
              Log.d("HTTPCLIENT", e.getLocalizedMessage());
          } catch (IOException e) {
              Log.d("HTTPCLIENT", e.getLocalizedMessage());
          }
        
        
      }*/
}

	