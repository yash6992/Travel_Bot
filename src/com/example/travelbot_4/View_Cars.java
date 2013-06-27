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


public class View_Cars extends ListActivity implements OnClickListener 
{
	// Progress Dialog
	private ProgressDialog pDialog;

	// Creating JSON Parser object
	JSONParser jParser = new JSONParser();

	ArrayList<HashMap<String, String>> carList;
	
	// url to get all car list
		private static String url_all_car = "http://yash6992.000space.com/travelbot/view_all_cars_admin.php";

	
	// car JSONArray
		public JSONArray car = null;
	
	
	
		// JSON Node car_models
		private static final String TAG_SUCCESS = "success";
		private static final String TAG_car = "car";
		private static final String TAG_car_id = "car_id";
		private static final String TAG_car_model = "car_model";
	
	
	Button add_cars;
	ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
	
	
	  // ME 
	 
	 public void onCreate(Bundle savedInstanceState) 
	 {
		 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_cars);

		// Hashmap for ListView
		carList = new ArrayList<HashMap<String, String>>();

		add_cars=(Button) findViewById(R.id.add_cars);
		add_cars.setOnClickListener(this);
		
		// Loading car in Background Thread
		new LoadAllcar().execute();

		// Get listview
		ListView lv = getListView();

		// on seleting single car
		// launching Edit car Screen
		lv.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) 
			{
				
				
				
				// getting values from selected ListItem
				String car_id_pass = ((TextView) view.findViewById(R.id.car_id)).getText()
						.toString();
				
				//Toast.makeText(getApplicationContext(),"car_id: "+car_id_pass, Toast.LENGTH_SHORT).show();
				
				// Starting new intent
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
	  
	class LoadAllcar extends AsyncTask<String, String, String> 
	{

		// Before starting background thread Show Progress Dialog
		  
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(View_Cars.this);
			pDialog.setMessage("Loading car. Please wait...");
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
			JSONObject json = jParser.makeHttpRequest(url_all_car, "GET", params);
			
			// Check your log cat for JSON reponse
			Log.d("All car: ", json.toString());

			try {
				// Checking for SUCCESS TAG
				int success = json.getInt(TAG_SUCCESS);

				if (success == 1) {
					// car found
					// Getting Array of car
					car = json.getJSONArray(TAG_car);

					// looping through All car
					for (int i = 0; i < car.length(); i++) 
					{
						JSONObject c = car.getJSONObject(i);

						// Storing each json item in variable
						String car_id = c.getString(TAG_car_id);
						String car_model = c.getString(TAG_car_model);
						String car_rating=c.getString("car_rating");
						String car_acnonac=(c.getString("car_acnonac")=="1"?"Y":"N");
						String car_number=c.getString("car_number");
						String car_status=c.getString("car_status")=="1"?"Y":"N";
						String car_remaining_seats=c.getString("car_remaining_seats");
						String car_seatingcapacity=c.getString("car_seatingcapacity");
						
						
						/**
						 * 
						 * Remember about DRIVERID
						 */
						
						

						
						// creating new HashMap
						HashMap<String, String> map = new HashMap<String, String>();

						// adding each child node to HashMap key => value
						map.put(TAG_car_id, car_id);
						map.put(TAG_car_model, car_model);
						map.put("car_rating", car_rating);
						map.put("car_acnonac", car_acnonac);
						map.put("car_number", car_number);
						map.put("car_status", car_status);
						map.put("car_remaining_seats", car_remaining_seats);
						map.put("car_seatingcapacity", car_seatingcapacity);
						

						// adding HashList to ArrayList
						carList.add(map);
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
					 
					/*$car["car_acnonac"] = $row["car_acnonac"];
				        $car["car_number"] = $row["car_number"];
				        $car["car_status"] = $row["car_status"];
						$car["car_remaining_seats"] = $row["car_remaining_seats"];
						$car["car_seatingcapacity"] = $row["car_seatingcapacity"];*/
					
					
					ListAdapter adapter = new SimpleAdapter(
							View_Cars.this, carList,
							R.layout.view_cars_row, new String[] { TAG_car_id,
									TAG_car_model,"car_number","car_status","car_remaining_seats","car_seatingcapacity","car_acnonac"},
							new int[] { R.id.car_id, R.id.car_model,R.id.reg_no,R.id.car_status,R.id.availability,R.id.capacity,R.id.ac });
					
					
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
		
		case R.id.add_cars: 
	    {view_cars(); break;}
		}
	}




	private void view_cars()
	{
		Toast tbb= Toast.makeText(getApplicationContext(), "add_cars",Toast.LENGTH_LONG);
		tbb.show();
		Intent addcarz = new Intent(getApplicationContext(),AddCar.class);
		// sending car_id to next activity
		startActivity(addcarz);
		
	}
	
}