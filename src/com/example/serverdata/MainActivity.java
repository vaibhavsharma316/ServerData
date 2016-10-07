package com.example.serverdata;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
EditText serverId;
Button get;
String row;
ProgressDialog pd;
InputStream is=null;
TextView result;
String name;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	pd=new ProgressDialog(MainActivity.this);
	pd.setMessage("Fetching results");
		serverId=(EditText)findViewById(R.id.editText1);
	get=(Button)findViewById(R.id.button1);
	result=(TextView)findViewById(R.id.textView1);

	get.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
		//	pd.show();
			Data();
			result.setText(name);
		}
	});
	
	
	}

	public void Data()
	{
		final String id=serverId.getText().toString();
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				HttpClient http=new DefaultHttpClient();
				HttpPost post=new HttpPost("http://www.companyreviews.url.ph/json.php");
				ArrayList<NameValuePair>list=new ArrayList<NameValuePair>();
				list.add(new BasicNameValuePair("fetch", id));
				try {
					post.setEntity(new UrlEncodedFormEntity(list));	
					HttpResponse res=http.execute(post);
					is=res.getEntity().getContent();
					Log.v("Response", "Reached");
					BufferedReader reader=new BufferedReader(new InputStreamReader(is));
					StringBuilder builder = new StringBuilder();
					while((row=reader.readLine()) != null)
					{
						builder.append(row+"\n");
						Log.v("DATA", row+"\n");
					
					}
					is.close();
					String rs=row;
					Log.v("STRING BUILDER", "REACHED");
					//JSONArray jarray=new JSONArray(rs);
					Log.v("JSON ARRAY", "CREATED");
					
					JSONObject object=new JSONObject();
				
					//	for (int j = 0; j < jarray.length(); j++) {
				//	object=jarray.getJSONObject(j);
					//	name=object.getString("name");
					object.getJSONArray(rs.toString());	
			//		}
					
				
					
					
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				
				
				
			}
		}).start();		
		
		
		
		
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
