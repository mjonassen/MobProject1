package com.example.project1;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

public class PictureField extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_picture_field);
		
		Bitmap bitmap = DownloadImage(
	            "http://www.ansatt.hig.no/simonm/images/VikingMe150.png");
	        //Then display the image to a view
	    ImageView img = (ImageView) findViewById(R.id.picture_field);
	    img.setImageBitmap(bitmap);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.picture_field, menu);
		return true;
	}
	
	public void exitActivity (View view) {
		finish();
	}
	
	/**
     * @author Simon McCallum
     *
     *This function downloads the image at the URL
     *location passed and then returns the bitmap
     * @param  URL     an absolute URL giving the base location and name of the image
     * @return bitmap  the image at the specified URL
     *
     */
    private Bitmap DownloadImage(String URL)
    {        
        Bitmap bitmap = null;
        InputStream in = null;        
        try {
            in = OpenHttpConnection(URL);
            bitmap = BitmapFactory.decodeStream(in);
            in.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return bitmap;                
    }
    
    /**
     * @author Simon McCallum
     *
     * Handles some of the complexity of opening up a HTTP connection
     * @param  URL   a String containing the absolute URL giving the base location and name of the content
     * @return in    an inputStream which will be the stream of text from the server
     *
     */    
    private InputStream OpenHttpConnection(String urlString) throws IOException
    {
        InputStream in = null;
        int response = -1;
               
        URL url = new URL(urlString); 
        URLConnection conn = url.openConnection();
                 
        if (!(conn instanceof HttpURLConnection))                     
            throw new IOException("Not an HTTP connection");
        
        try{
            HttpURLConnection httpConn = (HttpURLConnection) conn;
            httpConn.setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestMethod("GET");
            httpConn.connect(); 

            response = httpConn.getResponseCode();                 
            if (response == HttpURLConnection.HTTP_OK) {
                in = httpConn.getInputStream();                                 
            }                     
        }
        catch (Exception ex)
        {
            throw new IOException("Error connecting");            
        }
        return in;     
    }

}
