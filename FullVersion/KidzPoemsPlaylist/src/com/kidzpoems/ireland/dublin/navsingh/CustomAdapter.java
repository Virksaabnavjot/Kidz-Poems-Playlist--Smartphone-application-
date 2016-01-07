package com.kidzpoems.ireland.dublin.navsingh;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/***********
 * A navsingh Studios Production
 * 
 * @author Navjot Singh Virk
 * Website: Navsingh.org.uk
 * Company: NAVSINGH Studio
 * Date: 21/12/2015
 * Time: 20:21 Irish standard time
 ***********/

public class CustomAdapter extends BaseAdapter implements OnClickListener{

	/*********** Declare Variables *********/
    private Activity activity;
    private ArrayList data;
    private static LayoutInflater inflater=null;
    public Resources res;
    ListModel tempValues=null;
    int i=0;
    
    /*************  CustomAdapter Constructor *****************/
    public CustomAdapter(Activity a, ArrayList d,Resources resLocal) {
         
           /********** Take passed values **********/
            activity = a;
            data=d;
            res = resLocal;
         
            /***********  Layout inflator to call external xml layout () ***********/
             inflater = ( LayoutInflater )activity.
                                         getSystemService(Context.LAYOUT_INFLATER_SERVICE);
         
    }
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		/******** What is the size of Passed Arraylist  ************/
		if(data.size()<=0)
            return 1;
        return data.size();
		
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	/********* Create a holder to contain inflated xml file elements ***********/
    public static class ViewHolder{
    	
        public TextView text;
        
        public TextView textWide;
        public ImageView image;

    }
	
	@SuppressLint("NewApi")
	@Override
	/****** Depends upon data size called for each row , Create each ListView row *****/
	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
        ViewHolder holder;
        
        if(convertView==null){
            
            /****** Inflate tabitem.xml file for each row  *******/
            vi = inflater.inflate(R.layout.tabitem, null);
            
            
            /****** View Holder Object to contain tabitem.xml file elements ******/

            holder = new ViewHolder();
            holder.text = (TextView) vi.findViewById(R.id.text);
            
            holder.image=(ImageView)vi.findViewById(R.id.image);
             
           /************  Set holder with LayoutInflater ************/
            vi.setTag( holder );
        }
        else 
            holder=(ViewHolder)vi.getTag();
         
        if(data.size()<=0)
        {
            holder.text.setText("Sorry,No Data available");
             
        }
        else
        {
            /***** Get each Model object from Arraylist ********/
            tempValues=null;
            tempValues = ( ListModel ) data.get( position );
             
            /************  Set Model values in Holder elements ***********/

             holder.text.setText( tempValues.getHeadingName() );
            
              holder.image.setImageResource(
                          res.getIdentifier(
                        		  "navsingh.org.uk.interviewproject.custom.listview:drawable/"+tempValues.getImage()
                                  ,null,null));
              
                     /******** Set Item Click Listner for LayoutInflater for each row *******/
              
              //String path ="http://cdn.vogue.com.au/media/articles/1/2/9/0/12917-1_n.jpg";
              
              //next 4 lines to set the bacgroungd image of list item from the database
              String path = tempValues.getImageLinkName().toString();
              Bitmap myImage = getBitmapFromURL(path);
              if(path == null){
            	  /*
            	  String imagenaryPath = "http://cdn.vogue.com.au/media/articles/1/2/9/0/12917-1_n.jpg";
            	  Bitmap temporaryPath = getBitmapFromURL(imagenaryPath);
            	  Drawable tdr = new BitmapDrawable(temporaryPath);
                  vi.setBackgroundDrawable(tdr); 
                  */
              }
              else{
            	  Drawable dr = new BitmapDrawable(myImage);
                  vi.setBackgroundDrawable(dr);
                  //the end
              }
              
                     vi.setOnClickListener(new OnItemClickListener( position ));
                }
                return vi;
            }
             
	

	public Bitmap getBitmapFromURL(String imageUrl) {
	    try {
	        URL url = new URL(imageUrl);
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	        connection.setDoInput(true);
	        connection.connect();
	        InputStream input = connection.getInputStream();
	        Bitmap myBitmap = BitmapFactory.decodeStream(input);
	        return myBitmap;
	    } catch (IOException e) {
	        e.printStackTrace();
	        return null;
	    }
	}

			@Override
            public void onClick(View v) {
                    Log.v("CustomAdapter", "=====Row button clicked=====");
            }
             
            /********* Called when Item click in ListView ************/
            private class OnItemClickListener  implements OnClickListener{           
                private int mPosition;
                 
                OnItemClickListener(int position){
                     mPosition = position;
                }
                 
                @Override
                public void onClick(View arg0) {
    
           
                  MainActivity sct = (MainActivity)activity;
    
              
    
                    sct.onItemClick(mPosition);
                }               
            }   
        }