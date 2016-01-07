package com.kidzpoems.ireland.dublin.navsingh;

import java.io.IOException;
import java.util.ArrayList;
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

import com.kidzpoems.ireland.dublin.navsingh.CustomAdapter.ViewHolder;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {
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

	