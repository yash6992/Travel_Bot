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


public class View_Customers extends ListActivity implements OnClickListener 
{
	// Progress Dialog
	private ProgressDialog pDialog;

	// Creating JSON Parser object
	JSONParser jParser = new JSONParser();

	ArrayList<HashMap<String, String>> clientList;
	
	// url to get all car list
		private static String url_all_client = "http://yash6992.000space.com/travelbot/view_all_clients_admin.php";

	
	// car JSONArray
		public JSONArray client = null;
	

	  // ME 
	 
	 public void onCreate(Bundle savedInstanceState) 
	 {
		 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_customer);

		// Hashmap for ListView
		clientList = new ArrayList<HashMap<String, String>>();

		
		// Loading car in Background Thread
		new LoadAllclient().execute();

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
				String cust_id_pass = ((TextView) view.findViewById(R.id.cust_id)).getText()
						.toString();
				
				//Toast.makeText(getApplicationContext(),"car_id: "+car_id_pass, Toast.LENGTH_SHORT).show();
				
				// Starting new intent
				Intent in = new Intent(getApplicationContext(),
						Customer_Details.class);
				// sending car_id to next activity
				in.putExtra("cust_id", cust_id_pass);
				try
				{
					JSONObject c;
					int id1 = 0;
					for (int i = 0; i < client.length(); i++) 
					{
						c = client.getJSONObject(i);
						if(c.getString("cust_id").equals(cust_id_pass))
						{
							id1=i;
							break;
						}
							
				
					}
						
					/*
						String cust_id = c.getString("cust_id");
						String cust_name = c.getString("cust_name");
						String cust_mobile=c.getString("cust_mobile");
						String cust_email=c.getString("cust_email");
						String password=c.getString("password");
					 */
					c = client.getJSONObject(id1);
					String cust_id = c.getString("cust_id");
					String cust_name = c.getString("cust_name");
					String cust_mobile=c.getString("cust_mobile");
					String cust_email=c.getString("cust_email");
					String password=c.getString("password");
					
					
					//in.putExtra("travel_id", trip_id_pass);
					in.putExtra("cust_id", cust_id);
					in.putExtra("cust_name", cust_name);
					in.putExtra("cust_mobile", cust_mobile);
					in.putExtra("cust_email", cust_email);
					in.putExtra("password", password);
					
					
					
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
	  
	class LoadAllclient extends AsyncTask<String, String, String> 
	{

		// Before starting background thread Show Progress Dialog
		  
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(View_Customers.this);
			pDialog.setMessage("Loading Customers. Please wait...");
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
			JSONObject json = jParser.makeHttpRequest(url_all_client, "GET", params);
			
			// Check your log cat for JSON reponse
			Log.d("All client: ", json.toString());

			try {
				// Checking for SUCCESS TAG
				int success = json.getInt("success");

				if (success == 1) {
					// car found
					// Getting Array of car
					client = json.getJSONArray("client");

					// looping through All car
					for (int i = 0; i < client.length(); i++) 
					{
						JSONObject c = client.getJSONObject(i);

						// Storing each json item in variable
						String cust_id = c.getString("cust_id");
						String cust_name = c.getString("cust_name");
						String cust_mobile=c.getString("cust_mobile");
						String cust_email=c.getString("cust_email");
						String password=c.getString("password");
						
						
						
						/**
						 * 
						 * Remember about DRIVERID
						 */
						
						
						/*
				        $client["cust_id"] = $row["cust_id"];
        				$client["cust_name"] = $row["cust_name"];
        				$client["cust_mobile"] = $row["cust_mobile"];
						$client["cust_email"] = $row["cust_email"];
						$client["password"] = $row["password"];
		*/
						
						// creating new HashMap
						HashMap<String, String> map = new HashMap<String, String>();

						// adding each child node to HashMap key => value
						map.put("cust_name", cust_name);
						map.put("cust_id", cust_id);
						map.put("cust_mobile", cust_mobile);
						
						

						// adding HashList to ArrayList
						clientList.add(map);
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
							View_Customers.this, clientList,
							R.layout.view_customer_row, new String[] { "cust_name",
									"cust_id","cust_mobile"},
							new int[] { R.id.cust_name, R.id.cust_id,R.id.cust_mobile });
					
					
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