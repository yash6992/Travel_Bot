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
import org.apache.http.message.BasicNameValuePair;
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


public class Booked_History extends ListActivity implements OnClickListener 
{
	
	String cust_id;
	// Progress Dialog
	private ProgressDialog pDialog;
	TextView cust_id_tv;
	// Creating JSON Parser object
	JSONParser jParser = new JSONParser();

	ArrayList<HashMap<String, String>> BookingList;
	
	// url to get all car list
	private static String url_all_booking_list = "http://yash6992.000space.com/travelbot/view_booking_history_of_cust.php";

	
	// car JSONArray
	public JSONArray trip = null;
	
	
	
		// JSON Node car_models
	private static final String TAG_SUCCESS = "success";
		
	
	
	  // ME 
	 
	 public void onCreate(Bundle savedInstanceState) 
	 {
		 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.booked_history);

		// Hashmap for ListView
		BookingList = new ArrayList<HashMap<String, String>>();
		cust_id_tv=(TextView) findViewById(R.id.cust_id);
		Intent i = getIntent();
		cust_id = i.getStringExtra("cust_id");
		// Loading car in Background Thread
		new LoadAlltrip().execute();
		cust_id_tv.setText(cust_id);
		// Get listview
		ListView lv = getListView();

		
		/*
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
				String booking_id_pass = ((TextView) view.findViewById(R.id.booking_id)).getText()
						.toString();
				
				Toast.makeText(getApplicationContext(),"booking_id: "+booking_id_pass, Toast.LENGTH_SHORT).show();
			}	
		});
				
				/*// Starting new intent
				Intent in = new Intent(getApplicationContext(),
						EditCarActivity.class);
				// sending car_id to next activity
				in.putExtra(TAG_car_id, car_id_pass);
				try
				{
					JSONObject c;
					int id1 = 0;
					for (int i = 0; i < car.length(); i++) 
					{
						c = car.getJSONObject(i);
						if(c.getString(TAG_car_id).equals(car_id_pass))
						{
							id1=i;
							break;
						}
							
				
					}
					c = car.getJSONObject(id1);
					String car_model = c.getString(TAG_car_model);
					String car_rating=c.getString("car_rating");
					String car_acnonac=(c.getString("car_acnonac"));
					String car_number=c.getString("car_number");
					String car_status=c.getString("car_status");
					String car_remaining_seats=c.getString("car_remaining_seats");
					String car_seatingcapacity=c.getString("car_seatingcapacity");
					
					in.putExtra("car_model", car_model);
					in.putExtra("car_rating", car_rating);
					in.putExtra("car_acnonac", car_acnonac);
					in.putExtra("car_number", car_number);
					in.putExtra("car_status", car_status);
					in.putExtra("car_remaining_seats", car_remaining_seats);
					in.putExtra("car_seatingcapacity", car_seatingcapacity);
					
					
				}catch(Exception e)
				{}

				// starting new activity and expecting some response back
			//	startActivityForResult(in, 100);
				startActivity(in);
			}*/
			//});
		
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
			pDialog = new ProgressDialog(Booked_History.this);
			pDialog.setMessage("Loading trips. Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		
		
		protected String doInBackground(String... args) 
		{
			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			// getting JSON string from URL
			
			
			params.add(new BasicNameValuePair("cust_id", cust_id));
			
			/*
			 * 
			 * The query should search the client trip table and return all the columns of the travel table row
			 *  when the booking_id(in the client_trip table) ends with cust_id
			 * 
			 */
			JSONObject json = jParser.makeHttpRequest(url_all_booking_list, "GET", params);
			
			// Check your log cat for JSON reponse
			Log.d("All trip: ", json.toString());

			try {
				// Checking for SUCCESS TAG
				int success = json.getInt(TAG_SUCCESS);

				if (success == 1) {
					// car found
					// Getting Array of car
					trip = json.getJSONArray("trip");

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
						String trip_to=c.getString("trip_to");
						String trip_from=c.getString("trip_from");
						String overall_average_rating=c.getString("overall_average_rating");
						
						String  booking_id =c.getString("booking_id");
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
						String trip_to=c.getString("trip_to");
						String trip_from=c.getString("trip_from");
						String overall_average_rating=c.getString("overall_average_rating");*/
						
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
						map.put("overall_average_rating", overall_average_rating);
						map.put("booking_id", booking_id);

						// adding HashList to ArrayList
						BookingList.add(map);
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
			        String car_id = c.getString("car_id");
					String driver_id = c.getString("driver_id");
					String travel_id=c.getString("travel_id");
					String trip_date=c.getString("trip_date");
					String trip_time=c.getString("trip_time");
					String trip_to=c.getString("trip_to");
					String trip_from=c.getString("trip_from");
					String overall_average_rating=c.getString("overall_average_rating");*/
					
					cust_id_tv.setText(cust_id);
					
					ListAdapter adapter = new SimpleAdapter(
							Booked_History.this, BookingList,
							R.layout.booked_history_row, new String[] { "car_id",
									"driver_id","travel_id","trip_date",
									"trip_time","trip_to","trip_from",
									"overall_average_rating",
									"booking_id"},
							new int[] { R.id.car_id, R.id.driver_id,R.id.travel_id,
									R.id.trip_date,R.id.trip_time,R.id.trip_to,
									R.id.trip_from,R.id.overall_average_rating
									,R.id.booking_id});
					
					
				//	 updating listview
					setListAdapter(adapter);
					
					
				}
			}
		);

		}

	}





	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}
}

/*

	@Override
	public void onClick(View v) {
		switch(v.getId())
		{
		
		case R.id.add_cars: 
	    {view_cars(); break;}
		}
	}
*/



	/*private void view_cars()
	{
		Toast tbb= Toast.makeText(getApplicationContext(), "add_cars",Toast.LENGTH_LONG);
		tbb.show();
		Intent addcarz = new Intent(getApplicationContext(),AddCar.class);
		// sending car_id to next activity
		startActivity(addcarz);
		
	}
	*/

/*public class Booked_History extends ListActivity implements OnClickListener{
	
	//String username_booking_history;
	public TextView username_booking_history;
	ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
	public void onCreate(Bundle savedInstanceState) 
	{
		
		
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.booked_history);
		
		
		
		username_booking_history=(TextView) findViewById(R.id.username_booking_history);
		Intent intent = getIntent();
		final String cust_id = intent.getStringExtra("cust_id");
		username_booking_history.setText(cust_id);

		SimpleAdapter adapter = new SimpleAdapter(
        		this,
        		list,
        		R.layout.booked_history_row,
        		new String[] {"booking_id","trip_id","date","time","source","destination"},
        		new int[] {R.id.booking_id,R.id.trip_id,R.id.date,R.id.time,R.id.source,R.id.destination}
        		);
        populateList();
        setListAdapter(adapter);
		
		
		
	}
private void populateList() {
    	
    	
    	
    	HashMap<String,String> tempts1 = new HashMap<String,String>();
    	tempts1.put("booking_id","12343");
    	tempts1.put("trip_id","1234" );
    	tempts1.put("date","12.3.2013" );
    	tempts1.put("time","16:30" );
    	tempts1.put("source","Pilani" );
    	tempts1.put("destination","New Delhi" );
    	list.add(tempts1);
    
}



protected void onListItemClick(ListView l, View v, int position, long id) {

	//get selected items
	HashMap<String,String> temp1 = new HashMap<String,String>();
	temp1=(HashMap<String, String>) getListAdapter().getItem(position);
	String item_trip_id=temp1.get("trip_id");
	Toast.makeText(this,"direct to trip page with trip id: "+item_trip_id, Toast.LENGTH_SHORT).show();
	Intent ta = new Intent(this, TA_Main.class);
	ta.putExtra("url",ta_type_url );
	
			
   startActivity(ta);
    

}
	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
}*/