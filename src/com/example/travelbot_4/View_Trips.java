package com.example.travelbot_4;


import java.util.ArrayList;
import java.util.HashMap;


import com.example.travelbot_4.R;

import android.os.Bundle;
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


public class View_Trips extends ListActivity implements OnClickListener 
{
	// Progress Dialog
	private ProgressDialog pDialog;

	// Creating JSON Parser object
	JSONParser jParser = new JSONParser();

	ArrayList<HashMap<String, String>> tripList;
	
	// url to get all car list
		private static String url_all_trip = "http://yash6992.000space.com/travelbot/view_all_trip_admin.php";

	
	// car JSONArray
		public JSONArray trip = null;
	

	  // ME 
	 
	 public void onCreate(Bundle savedInstanceState) 
	 {
		 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_trip);

		// Hashmap for ListView
		tripList = new ArrayList<HashMap<String, String>>();

		
		// Loading car in Background Thread
		new LoadAlltrip().execute();

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
				
				
				
				// getting values from selected ListItem
				String trip_id_pass = ((TextView) view.findViewById(R.id.travel_id)).getText()
						.toString();
				
				//Toast.makeText(getApplicationContext(),"car_id: "+car_id_pass, Toast.LENGTH_SHORT).show();
				
				// Starting new intent
				Intent in = new Intent(getApplicationContext(),
						Trip_Details.class);
				// sending car_id to next activity
				in.putExtra("travel_id", trip_id_pass);
				try
				{
					JSONObject c;
					int id1 = 0;
					for (int i = 0; i < trip.length(); i++) 
					{
						c = trip.getJSONObject(i);
						if(c.getString("travel_id").equals(trip_id_pass))
						{
							id1=i;
							break;
						}
							
				
					}
						
					/*
					 * String car_id = c.getString("car_id");
						String driver_id = c.getString("driver_id");
						String travel_id=c.getString("travel_id");
						String trip_date=c.getString("trip_date");
						String trip_time=c.getString("trip_time");
						String trip_from=c.getString("trip_from");
						String trip_to=c.getString("trip_to");
						String trip_success=c.getString("success")=="1"?"Y":"N";
						String feedback=c.getString("feedback");
						String overall_average_rating=c.getString("overall_average_rating");
						String no_of_passengers=c.getString("no_of_passengers");
					 */
					c = trip.getJSONObject(id1);
					String car_id = c.getString("car_id");
					String driver_id = c.getString("driver_id");
					String travel_id=c.getString("travel_id");
					String trip_date=c.getString("trip_date");
					String trip_time=c.getString("trip_time");
					String trip_from=c.getString("trip_from");
					String trip_to=c.getString("trip_to");
					String trip_success=c.getString("success");
					String feedback=c.getString("feedback");
					String overall_average_rating=c.getString("overall_average_rating");
					String no_of_passengers=c.getString("no_of_passengers");
					
					//in.putExtra("travel_id", trip_id_pass);
					in.putExtra("car_id", car_id);
					in.putExtra("driver_id", driver_id);
					in.putExtra("travel_id", travel_id);
					in.putExtra("trip_date", trip_date);
					in.putExtra("trip_time", trip_time);
					in.putExtra("trip_from", trip_from);
					in.putExtra("trip_to", trip_to);
					in.putExtra("success", trip_success);
					in.putExtra("feedback", feedback);
					in.putExtra("overall_average_rating", overall_average_rating);
					in.putExtra("no_of_passengers", no_of_passengers);
					
					
				}catch(Exception e)
				{}

				// starting new activity and expecting some response back
			//	startActivityForResult(in, 100);
				startActivity(in);
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
	  
	class LoadAlltrip extends AsyncTask<String, String, String> 
	{

		// Before starting background thread Show Progress Dialog
		  
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(View_Trips.this);
			pDialog.setMessage("Loading trips. Please wait...");
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
			JSONObject json = jParser.makeHttpRequest(url_all_trip, "GET", params);
			/*
			 * 
			 * PHP file exactly same as view_all_cars_admin.php .....just change car table to travel table and few other minor changes..
			 * 
			 * 
			 */
			// Check your log cat for JSON reponse
			Log.d("All trip: ", json.toString());

			try {
				// Checking for SUCCESS TAG
				int success = json.getInt("success");

				if (success == 1) {
					// car found
					// Getting Array of car
					trip = json.getJSONArray("travel");

					// looping through All car
					for (int i = 0; i < trip.length(); i++) 
					{
						JSONObject c = trip.getJSONObject(i);

						// Storing each json item in variable
						String car_id = c.getString("car_id");
						String driver_id = c.getString("driver_id");
						String travel_id=c.getString("travel_id");
						String trip_date=c.getString("trip_date");
						String trip_time=c.getString("trip_time");
						String trip_from=c.getString("trip_from");
						String trip_to=c.getString("trip_to");
						String trip_success=c.getString("success");
						String feedback=c.getString("feedback");
						String overall_average_rating=c.getString("overall_average_rating");
						String no_of_passengers=c.getString("no_of_passengers");
						
						
						/**
						 * 
						 * Remember about DRIVERID
						 */
						
						
						/*
				        String car_id = c.getString("car_id");
						String driver_id = c.getString("driver_id");
						String travel_id=c.getString("travel_id");
						String trip_date=c.getString("trip_date");
						String trip_time=c.getString("trip_time");
						String trip_from=c.getString("trip_from");
						String trip_to=c.getString("trip_to");
						String trip_success=c.getString("success")=="1"?"Y":"N";
						String feedback=c.getString("feedback");
						String overall_average_rating=c.getString("overall_average_rating");
						String no_of_passengers=c.getString("no_of_passengers");*/
						
						// creating new HashMap
						HashMap<String, String> map = new HashMap<String, String>();

						// adding each child node to HashMap key => value
						map.put("car_id", car_id);
						map.put("driver_id", driver_id);
						map.put("travel_id", travel_id);
						map.put("trip_date", trip_date);
						map.put("trip_time", trip_time);
						map.put("trip_to", trip_to);
						map.put("trip_from", trip_from);
						
						

						// adding HashList to ArrayList
						tripList.add(map);
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
					 
					/*map.put("car_id", car_id);
						map.put("driver_id", driver_id);
						map.put("travel_id", travel_id);
						map.put("trip_date", trip_date);
						map.put("trip_time", trip_time);
						map.put("trip_to", trip_to);
						map.put("trip_from", trip_from);*/
					
					
					ListAdapter adapter = new SimpleAdapter(
							View_Trips.this, tripList,
							R.layout.view_trip_row, new String[] { "car_id",
									"driver_id","travel_id","trip_date","trip_time","trip_to","trip_from"},
							new int[] { R.id.car_id, R.id.driver_id,R.id.travel_id,R.id.trip_date,R.id.trip_time,R.id.trip_to,R.id.trip_from });
					
					
				//	 updating listview
					setListAdapter(adapter);
					
					
				}
			}
		);

		}

	}




	@Override
	public void onClick(View v) {
		
	}


/*

	private void view_cars()
	{
		Toast tbb= Toast.makeText(getApplicationContext(), "add_cars",Toast.LENGTH_LONG);
		tbb.show();
		Intent addcarz = new Intent(getApplicationContext(),AddCar.class);
		// sending car_id to next activity
		startActivity(addcarz);
		
	}*/
	
}