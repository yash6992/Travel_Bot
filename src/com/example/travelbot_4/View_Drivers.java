package com.example.travelbot_4;


import java.util.ArrayList;
import java.util.HashMap;


import com.example.travelbot_4.R;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;


public class View_Drivers extends ListActivity implements OnClickListener 
{
	// Progress Dialog
	private ProgressDialog pDialog;

	// Creating JSON Parser object
	JSONParser jParser = new JSONParser();

	ArrayList<HashMap<String, String>> driverList;
	
	// url to get all car list
		private static String url_all_driver = "http://yash6992.000space.com/travelbot/view_all_drivers_admin.php";

	
	// car JSONArray
		public JSONArray driver = null;
	
	
	
		// JSON Node car_models
		/*private static final String TAG_SUCCESS = "success";
		private static final String TAG_car = "car";
		private static final String TAG_car_id = "car_id";
		private static final String TAG_car_model = "car_model"*/;
	
	
	Button add_driver;
	
	
	
	  // ME 
	 
	 public void onCreate(Bundle savedInstanceState) 
	 {
		 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_driver);

		// Hashmap for ListView
		driverList = new ArrayList<HashMap<String, String>>();

		add_driver=(Button) findViewById(R.id.add_driver);
		add_driver.setOnClickListener(this);
		
		// Loading car in Background Thread
		new LoadAlldriver().execute();

		// Get listview
		ListView lv = getListView();

		// on seleting single car
		// launching Edit car Screen
		
		/*
		 * 
		 * IMPORTANT EDIT
		 * 
		 */
		lv.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) 
			{
	/*			
				
				
				// getting values from selected ListItem
				String driver_id_pass = ((TextView) view.findViewById(R.id.driver_id)).getText()
						.toString();
				
				//Toast.makeText(getApplicationContext(),"car_id: "+car_id_pass, Toast.LENGTH_SHORT).show();
				
				// Starting new intent
				Intent in = new Intent(getApplicationContext(),
						EditDriverActivity.class);
				// sending car_id to next activity
				in.putExtra("driver_id", driver_id_pass);
				try
				{
					JSONObject c;
					int id1 = 0;
					for (int i = 0; i < driver.length(); i++) 
					{
						c = driver.getJSONObject(i);
						if(c.getString("driver_id").equals(driver_id_pass))
						{
							id1=i;
							break;
						}
							
				
					}
					c = driver.getJSONObject(id1);

					
					String driver_id = c.getString("driver_id");
					String driver_name = c.getString("driver_name");
					String driver_mobile=c.getString("driver_mobile");
					String driver_status=(c.getString("driver_status")=="1"?"Busy":"Free");
					String driver_address=c.getString("driver_address");
					String driver_note=c.getString("driver_note");
					String driver_rating=c.getString("driver_rating");
					
					in.putExtra("driver_id", driver_id);
					in.putExtra("driver_name", driver_name);
					in.putExtra("driver_mobile", driver_mobile);
					in.putExtra("driver_status", driver_status);
					in.putExtra("driver_address", driver_address);
					in.putExtra("driver_note", driver_note);
					in.putExtra("driver_rating", driver_rating);
					
					
				}catch(Exception e)
				{}

				// starting new activity and expecting some response back
			//	startActivityForResult(in, 100);
				startActivity(in);
			*/
			}
			
		});
		
		/*
		add_cars.setOnClickListener(new OnClickListener()
		{
			public void onClick(View view) 
			{
				Intent addcarz = new Intent(View_Cars.this,AddCar.class);
				// sending car_id to next activity
				startActivity(addcarz);
				
			}
		});
*/
	} 
	 
	
	

	
	// Background Async Task to Load all car by making HTTP Request
	  
	@SuppressLint("NewApi")
	class LoadAlldriver extends AsyncTask<String, String, String> 
	{

		// Before starting background thread Show Progress Dialog
		  
		@SuppressLint("NewApi")
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(View_Drivers.this);
			pDialog.setMessage("Loading Drivers. Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		
		// getting All car from url
		
		protected String doInBackground(String... args) 
		{
			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			// getting JSON string from URL
			JSONObject json = jParser.makeHttpRequest(url_all_driver, "GET", params);
			
			// Check your log cat for JSON response
			Log.d("All driver: ", json.toString());

			try {
				// Checking for SUCCESS TAG
				int success = json.getInt("success");

				if (success == 1) {
					// car found
					// Getting Array of car
					driver = json.getJSONArray("driver");

					// looping through All car
					for (int i = 0; i < driver.length(); i++) 
					{
						JSONObject c = driver.getJSONObject(i);

						// Storing each json item in variable
						String driver_id = c.getString("driver_id");
						String driver_name = c.getString("driver_name");
						String driver_mobile=c.getString("driver_mobile");
						String driver_status=(c.getString("driver_status")=="1"?"Busy":"Free");
						String driver_address=c.getString("driver_address");
						String driver_note=c.getString("driver_note");
						String driver_rating=c.getString("driver_rating");
						
						
						/**
						 * 
						 * Remember about DRIVERID
						 */
						
						
						/*
				         $driver["driver_id"] = $row["driver_id"];
        $driver["driver_name"] = $row["driver_name"];
        $driver["driver_mobile"] = $row["driver_mobile"];
		
        $driver["driver_address"] = $row["driver_address"];
        $driver["driver_note"] = $row["driver_note"];
        $driver["driver_rating"] = $row["driver_rating"];
		$driver["driver_status"] = $row["driver_status"];;*/
						
						// creating new HashMap
						HashMap<String, String> map = new HashMap<String, String>();

						// adding each child node to HashMap key => value
						
						map.put("driver_id", driver_id);
						map.put("driver_name", driver_name);
						map.put("driver_mobile", driver_mobile);
						map.put("driver_status", driver_status);
						

						// adding HashList to ArrayList
						driverList.add(map);
					}
				} else {
					// no car found
					// Launch Add New car Activity
				/*		
	Intent add_cars = new Intent(View_Cars.this, Add_Cars.class);
   
					// Closing all previous activities
					add_cars.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					 startActivity(add_cars);
					 */
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}

		// After completing background task Dismiss the progress dialog
		
		protected void onPostExecute(String file_url)
		{
			// dismiss the dialog after getting all car
			pDialog.dismiss();
			// updating UI from Background Thread
			runOnUiThread(new Runnable() {
				public void run() {
					//Updating parsed JSON data into ListView
					 
					/*
						map.put("driver_id", driver_id);
						map.put("driver_name", driver_name);
						map.put("driver_mobile", driver_mobile);
						map.put("driver_status", driver_status);*/
					
					
					ListAdapter adapter = new SimpleAdapter(
							View_Drivers.this, driverList,
							R.layout.view_driver_row, new String[] {"driver_id", "driver_name","driver_mobile","driver_status"},
							new int[] { R.id.driver_id, R.id.driver_name,R.id.driver_mobile,R.id.driver_status});
					
					
				//	 updating listview
					setListAdapter(adapter);
					
					
				}
			}
		);

		}

	}




	@Override
	public void onClick(View v) {
		switch(v.getId())
		{
		
		case R.id.add_driver: 
	    {add_driver(); break;}
		}
	}




	private void add_driver()
	{
		Toast tbb= Toast.makeText(getApplicationContext(), "add driver",Toast.LENGTH_LONG);
		tbb.show();
		Intent add_driver = new Intent(getApplicationContext(),AddDriver.class);
		// sending car_id to next activity
		startActivity(add_driver);
		
	}
	
}