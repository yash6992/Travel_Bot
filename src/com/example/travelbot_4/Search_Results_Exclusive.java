package com.example.travelbot_4;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

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
import android.view.View.OnClickListener;

import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import android.widget.AdapterView.OnItemClickListener;

public class Search_Results_Exclusive extends ListActivity implements OnClickListener 
{
	
	
	// Progress Dialog
		private ProgressDialog pDialog;

		// Creating JSON Parser object
		JSONParser jParser = new JSONParser();

		ArrayList<HashMap<String, String>> tripList,carList;
		
		// url to get all trip list
			private static String url_all_trip = "http://yash6992.000space.com/travelbot/view_all_tripis_exclusive_car_search.php";

		
		// trip JSONArray
			public JSONArray trip = null, car=null;
		
			public String trip_from ,trip_to,trip_date,no_of_passengers,trip_time,cust_id,exclusive,pick,travel_id;
		
			// JSON Node trip_models
			private static final String TAG_SUCCESS = "success";
		
   
		TextView to,from,date,time;
		  // ME 
		 
		 public void onCreate(Bundle savedInstanceState) 
		 {
			 
			super.onCreate(savedInstanceState);
			setContentView(R.layout.search_result);

			// Hashmap for ListView
		//	tripList = new ArrayList<HashMap<String, String>>();
			carList = new ArrayList<HashMap<String, String>>();
			
			
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
			new LoadAllcar().execute();

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
					String travel_id_pass = ((TextView) view.findViewById(R.id.car_id)).getText()
							.toString();
					
					//Toast.makeText(getApplicationContext(),"travel_id: "+travel_id_pass, Toast.LENGTH_SHORT).show();
					
					// Starting new intent
					Intent in = new Intent(getApplicationContext(),
							ConfirmTripInclusive.class);
					// sending travel_id to next activity
					in.putExtra("car_id", travel_id_pass);
					try
					{
						JSONObject c;
						int id1 = 0;
						for (int i = 0; i < car.length(); i++) 
						{
							c = car.getJSONObject(i);
							if(c.getString("car_id").equals(travel_id_pass))
							{
								id1=i;
								break;
							}
						
						}
						c = car.getJSONObject(id1);
						String car_model = c.getString("car_model");
						String car_rating=c.getString("car_rating");
						String car_acnonac=(c.getString("car_acnonac"));
						String car_number=c.getString("car_number");
						String car_status=c.getString("car_status");
						String car_remaining_seats=c.getString("car_remaining_seats");
						String car_seatingcapacity=c.getString("car_seatingcapacity");
				//		String car_id= c.getString("car_id");
				//		String driver_id= c.getString("driver_id");
				//		String trip_date = c.getString("trip_date");
						
						
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
					
						
						in.putExtra("trip_time", trip_time);
						in.putExtra("car_id", travel_id_pass);
						in.putExtra("trip_date", trip_date);
						in.putExtra("cust_id", cust_id) ;
						in.putExtra("exclusive", exclusive);
						
						/*
						 * 		car_model=in.getStringExtra("car_model");
		car_rating=in.getStringExtra("car_rating");
		car_acnonac=in.getStringExtra("car_acnonac");
		car_number = in.getStringExtra("car_number");
		car_status = in.getStringExtra("car_status");
		car_remaining_seats = in.getStringExtra("car_remaining_seats");
		car_seatingcapacity = in.getStringExtra("car_seatingcapacity");
		travel_id = in.getStringExtra("travel_id");
		trip_from = in.getStringExtra("trip_from");
		trip_to = in.getStringExtra("trip_to");
		car_id= in.getStringExtra("car_id");
		driver_id = in.getStringExtra("driver_id");
		trip_date= in.getStringExtra("trip_date");
		no_of_passengers= in.getStringExtra("no_of_passengers");
		cust_id= in.getStringExtra("cust_id");
		exclusive=in.getStringExtra("exclusive");
						 * 
						 * */
						
						
						
						
				 		Random lol=new Random();
				 			
						in.putExtra("travel_id", trip_from+trip_to+cust_id+(lol.nextInt(100)));
						in.putExtra("driver_id", "TO BE ALLOTTED AS SOON AS CONFIRMATION");
						startActivity(in);
						
					}
					catch(Exception e)
					{
						
					}

					
				}
			});
			
			
		} 
		 
		
		
		 class LoadAllcar extends AsyncTask<String, String, String> 
			{

				// Before starting background thread Show Progress Dialog
				  
				@Override
				protected void onPreExecute() {
					super.onPreExecute();
					pDialog = new ProgressDialog(Search_Results_Exclusive.this);
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
					params.add(new BasicNameValuePair("no_of_passengers_needed", no_of_passengers));
					// getting JSON string from URL
					JSONObject json = jParser.makeHttpRequest(url_all_trip, "GET", params);
					
					// Check your log cat for JSON reponse
					Log.d("All car: ", json.toString());

					try {
						// Checking for SUCCESS TAG
						int success = json.getInt(TAG_SUCCESS);

						if (success == 1) {
							// car found
							// Getting Array of car
							car = json.getJSONArray("car");

							// looping through All car
							for (int i = 0; i < car.length(); i++) 
							{
								JSONObject c = car.getJSONObject(i);

								// Storing each json item in variable
								String car_id = c.getString("car_id");
								String car_model = c.getString("car_model");
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
								map.put("car_id", car_id);
								map.put("car_model", car_model);
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
							to.setText(trip_to);
							from.setText(trip_from);
							date.setText(trip_date);
							time.setText(trip_time);
						/*	
							ListAdapter adapter = new SimpleAdapter(
									Search_Results_Exclusive.this, carList,
									R.layout.search_result_row, new String[] 
											{ "car_id",
											"car_model",
											"car_number",
											"car_status",
											"car_remaining_seats",
											"car_seatingcapacity",
											"car_acnonac"},
									new int[] { R.id.car_id,
											R.id.car_model,
											R.id.reg_no,
											R.id.car_status,
											R.id.availability,
											R.id.capacity,
											R.id.ac }); */
							
							 
				ListAdapter adapter = new SimpleAdapter(
								Search_Results_Exclusive.this, carList,
								R.layout.search_result_row, new String[] { 
										"car_model",
										"car_id",
										"trip_date",
										  "car_remaining_seats",
										"car_seatingcapacity",
										  "car_acnonac"},
								new int[] { 
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