package com.example.travelbot_4;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import com.example.travelbot_4.R;


import android.os.AsyncTask;
import android.os.Bundle;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;

import android.util.Log;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import android.widget.AdapterView.OnItemClickListener;




public class Search_Results_Inclusive extends ListActivity
{
	
	
	// Progress Dialog
		private ProgressDialog pDialog;

		// Creating JSON Parser object
		JSONParser jParser = new JSONParser();

		ArrayList<HashMap<String, String>> tripList;
		
		// url to get all trip list
			private static String url_all_trip = "http://yash6992.000space.com/travelbot/view_all_search_results_trip_for_booking.php";

		
		// trip JSONArray
			public JSONArray trip = null;
		
			public String trip_from ,trip_to,trip_date,no_of_passengers,trip_time,cust_id,exclusive,pick;
		
			// JSON Node trip_models
			private static final String TAG_SUCCESS = "success";
		
   
		TextView to,from,date,time;
		  // ME 
		 
		 public void onCreate(Bundle savedInstanceState) 
		 {
			 
			super.onCreate(savedInstanceState);
			setContentView(R.layout.search_result);

			// Hashmap for ListView
			tripList = new ArrayList<HashMap<String, String>>();
			
			Intent i = getIntent();
			trip_from = i.getStringExtra("trip_from");
			trip_to= i.getStringExtra("trip_to");

			trip_date= i.getStringExtra("trip_date");

			no_of_passengers =i.getStringExtra("no_of_passengers");

			trip_time= i.getStringExtra("trip_time");

			cust_id =i.getStringExtra("cust_id");

			exclusive= i.getStringExtra("exclusive");

			pick= i.getStringExtra("pick");
			
			to=(TextView) findViewById(R.id.destination);
			from=(TextView) findViewById(R.id.source);
			date=(TextView) findViewById(R.id.date);
			time=(TextView) findViewById(R.id.time);
			
			
			//trip_from ,trip_to,trip_date,no_of_passengers,trip_time,cust_id
			// Loading trip in Background Thread
			new LoadAllTrip().execute();

			// Get listview
			ListView lv = getListView();
			
			// on seleting single trip
			// launching Edit trip Screen
			lv.setOnItemClickListener(new OnItemClickListener()
			{

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) 
				{
					
					
					
					// getting values from selected ListItem
					String travel_id_pass = ((TextView) view.findViewById(R.id.travel_id)).getText()
							.toString();
					
					//Toast.makeText(getApplicationContext(),"travel_id: "+travel_id_pass, Toast.LENGTH_SHORT).show();
					
					// Starting new intent
					Intent in = new Intent(getApplicationContext(),
							ConfirmTripInclusive.class);
					// sending travel_id to next activity
					in.putExtra("travel_id", travel_id_pass);
					try
					{
						JSONObject c;
						int id1 = 0;
						for (int i = 0; i < trip.length(); i++) 
						{
							c = trip.getJSONObject(i);
							if(c.getString("travel_id").equals(travel_id_pass))
							{
								id1=i;
								break;
							}
								
					
						}
						c = trip.getJSONObject(id1);
						String car_model = c.getString("car_model");
						String car_rating=c.getString("car_rating");
						String car_acnonac=(c.getString("car_acnonac"));
						String car_number=c.getString("car_number");
						String car_status=c.getString("car_status");
						String car_remaining_seats=c.getString("car_remaining_seats");
						String car_seatingcapacity=c.getString("car_seatingcapacity");
						String car_id= c.getString("car_id");
						String driver_id= c.getString("driver_id");
						String trip_date = c.getString("trip_date");
						
						
						in.putExtra("car_model", car_model);
						in.putExtra("car_rating", car_rating);
						in.putExtra("car_acnonac", car_acnonac);
						in.putExtra("car_number", car_number);
						in.putExtra("car_status", car_status);
						in.putExtra("car_remaining_seats", car_remaining_seats);
						in.putExtra("car_seatingcapacity", car_seatingcapacity);
						in.putExtra("no_of_passengers", no_of_passengers);
						in.putExtra("trip_from", trip_from);
						in.putExtra("trip_to", trip_to);
						in.putExtra("car_id", car_id);
						in.putExtra("driver_id", driver_id);
						in.putExtra("trip_date", trip_date);
						in.putExtra("cust_id", cust_id) ;
						
						
						
					}catch(Exception e)
					{}

					startActivity(in);
				}
			});
			
			
		} 
		 
		
		

		
		// Background Async Task to Load all trip by making HTTP Request
		  
		class LoadAllTrip extends AsyncTask<String, String, String> 
		{

			// Before starting background thread Show Progress Dialog
			  
			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				pDialog = new ProgressDialog(Search_Results_Inclusive.this);
				pDialog.setMessage("Loading trip. Please wait...");
				pDialog.setIndeterminate(false);
				pDialog.setCancelable(false);
				pDialog.show();
			}

			
			// getting All trip from url
			
			protected String doInBackground(String... args) 
			{
				// Building Parameters
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				/*
				 * 
				 * $trip_date=$_GET["trip_date"];
				$no_of_passengers_needed=$_GET["no_of_passengers_needed"];
				$trip_to 	=$_GET["trip_to"]
				$trip_from = $_GET["trip_from"]
				 * 
				 * */
				params.add(new BasicNameValuePair("trip_date", trip_date));
				params.add(new BasicNameValuePair("no_of_passengers_needed", no_of_passengers));
				params.add(new BasicNameValuePair("trip_to", trip_to));
				params.add(new BasicNameValuePair("trip_from", trip_from));
				
				
				// getting JSON string from URL
				JSONObject json = jParser.makeHttpRequest(url_all_trip, "GET", params);
				Log.i("tagconvertstr", "["+json+"]");
				// Check your log cat for JSON reponse
				Log.d("All trip: ", json.toString());

				try 
				{
					// Checking for SUCCESS TAG
					int success = json.getInt(TAG_SUCCESS);

					if (success == 1)
					{
						// trip found
						// Getting Array of trip
						trip = json.getJSONArray("trip");

						Log.d("abhinav trip: ", trip.toString());
						
						// looping through All trip
						for (int i = 0; i < trip.length(); i++) 
						{
							JSONObject c = trip.getJSONObject(i);

							// Storing each json item in variable
							String travel_id = c.getString("travel_id");
							String car_model = c.getString("car_model");
							String car_rating=c.getString("car_rating");
							String car_acnonac=c.getString("car_acnonac");
									//=="1"?"Y":"N");
							String car_number=c.getString("car_number");
							String car_status=c.getString("car_status");
							//=="1"?"Y":"N";
							String car_remaining_seats=c.getString("car_remaining_seats");
							String car_seatingcapacity=c.getString("car_seatingcapacity");
							
							String driver_id = c.getString("driver_id");
							String no_of_passengers = c.getString("no_of_passengers");
							String trip_date = c.getString("trip_date");
							String car_id = c.getString("car_id");

							// creating new HashMap
							HashMap<String, String> map = new HashMap<String, String>();

							// adding each child node to HashMap key => value
							map.put("travel_id", travel_id);
							map.put("car_id", car_id);
							map.put("car_model", car_model);
							//map.put("car_rating", car_rating);
							map.put("car_acnonac", car_acnonac);
							//map.put("car_number", car_number);
							map.put("trip_date", trip_date);
							map.put("car_remaining_seats", car_remaining_seats+"/"+car_seatingcapacity);
							map.put("car_seatingcapacity", car_seatingcapacity);

							// adding HashList to ArrayList
							tripList.add(map);
							
							Log.d("yash trip: ", c.toString());
							
						}
					}
					
					
					
					
					
					else {
						// no trip found

								
						Intent add_trips = new Intent(Search_Results_Inclusive.this, User_Home.class);
					   
										// Closing all previous activities
										add_trips.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
										 startActivity(add_trips);
										
						
						
					/*		
		Intent add_trips = new Intent(View_trips.this, Add_trips.class);
	   
						// Closing all previous activities
						add_trips.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						 startActivity(add_trips);
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
				// dismiss the dialog after getting all trip
				pDialog.dismiss();
				// updating UI from Background Thread
				runOnUiThread(new Runnable() {
					public void run() {
						//Updating parsed JSON data into ListView
						 
						/*$trip["trip_acnonac"] = $row["trip_acnonac"];
					        $trip["trip_number"] = $row["trip_number"];
					        $trip["trip_status"] = $row["trip_status"];
							$trip["trip_remaining_seats"] = $row["trip_remaining_seats"];
							$trip["trip_seatingcapacity"] = $row["trip_seatingcapacity"];*/
						to.setText(trip_to);
						from.setText(trip_from);
						date.setText(trip_date);
						time.setText(trip_time);
						
						ListAdapter adapter = new SimpleAdapter(
								Search_Results_Inclusive.this, tripList,
								R.layout.search_result_row, new String[] { 
										"travel_id",
										"car_model",
										"car_id",
										"trip_date",
										  "car_remaining_seats",
										"car_seatingcapacity",
										  "car_acnonac"},
								new int[] { 
										R.id.travel_id, 
										R.id.car_model,
										R.id.car_id,
										R.id.trip_date,
										R.id.car_remaining_seats,
										R.id.car_seatingcapacity,
										R.id.car_acnonac });
						
						
					//	 updating listview
						setListAdapter(adapter);
						
						
					}
				}
			);

			}

		}

}