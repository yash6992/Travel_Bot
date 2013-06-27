package com.example.travelbot_4;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import org.json.JSONArray;



import android.app.ProgressDialog;

import android.os.AsyncTask;


public class EditCarActivity extends Activity
{

	// Progress Dialog
    private ProgressDialog pDialog;
 
    JSONParser jsonParser = new JSONParser();
    public String car_id,car_model, car_rating,car_acnonac,car_number,car_status,car_remaining_seats,car_seatingcapacity; 
	public EditText car_id_et,car_model_et, car_rating_et,car_acnonac_et,car_number_et,car_status_et,car_seatingcapacity_et; 
	
	
	Button btnSave;
	Button btnDelete;
	
	
	
	// single car url
	private static final String url_car_details = "http://yash6992.000space.com/travelbot/get_car_details.php";

	// url to update car
	private static final String url_update_car = "http://yash6992.000space.com/travelbot/update_car.php";
	
	// url to delete car
	private static final String url_delete_car = "http://yash6992.000space.com/travelbot/delete_car.php";
	private static final String TAG_SUCCESS = "success";
	
	//car_id,car_model, car_rating,car_acnonac,car_number,car_status,car_remaining_seats,car_seatingcapacity
	
	
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_car);

		// save button
		btnSave = (Button) findViewById(R.id.edit_car);
		btnDelete = (Button) findViewById(R.id.delete_car);

		// getting car details from intent
		Intent i = getIntent();
		
		// getting car id (car_id) from intent
		car_id = i.getStringExtra("car_id");

		car_model_et = (EditText) findViewById(R.id.car_model_edit);
		car_id_et = (EditText) findViewById(R.id.car_id_edit);
		car_rating_et = (EditText) findViewById(R.id.car_rating_edit);
		car_acnonac_et = (EditText) findViewById(R.id.car_acononac_edit);
		car_seatingcapacity_et =(EditText) findViewById(R.id.car_availability_edit);
		car_status_et=(EditText) findViewById(R.id.car_status_edit);
		car_number_et=(EditText) findViewById(R.id.car_reg_no_edit);
		
		
		
		car_id_et.setText(car_id);
		
		//bad way
		car_model_et.setText(i.getStringExtra("car_model"));
		
		car_rating_et.setText(i.getStringExtra("car_rating"));
		car_acnonac_et.setText(i.getStringExtra("car_acnonac"));
		car_seatingcapacity_et.setText(i.getStringExtra("car_seatingcapacity"));
		car_status_et.setText(i.getStringExtra("car_status"));
		car_number_et.setText(i.getStringExtra("car_number"));
		
		
		// Getting complete car details in background thread
	//	new GetCarDetails().execute();

		// save button click event
		btnSave.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// starting background task to update car
				new SaveCarDetails().execute();
			}
		});

		// Delete button click event
		btnDelete.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// deleting car in background thread
				new Deletecar().execute();
			}
		});

	}

	
	class GetCarDetails extends AsyncTask<String, String, String> {


		@Override
		protected void onPreExecute() 
		{
			super.onPreExecute();
			
			//Toast.makeText(getApplicationContext(),car_id , Toast.LENGTH_SHORT).show();
			
			pDialog = new ProgressDialog(EditCarActivity.this);
			pDialog.setMessage("Loading car details. Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
		
		
		
	//Original

		protected String doInBackground(String... params) 
		{

			// updating UI from Background Thread
			runOnUiThread(new Runnable() 
			{
				public void run() 
				{
					// Check for success tag
					int success;
					try 
					{
						// Building Parameters
						List<NameValuePair> params = new ArrayList<NameValuePair>();
						params.add(new BasicNameValuePair("car_id", car_id));

						// getting car details by making HTTP request
						// Note that car details url will use GET request
						JSONObject json = jsonParser.makeHttpRequest(	url_car_details, "GET", params);

						// check your log for json response
						Log.d("Single car Details", json.toString());
						
						// json success tag
						success = json.getInt(TAG_SUCCESS);
						if (success == 1) {
							// successfully received car details
							JSONArray carObj = json	.getJSONArray("car"); // JSON Array
							
							// get first car object from JSON Array
							JSONObject car = carObj.getJSONObject(0);

							// car with this car_id found
							// Edit Text
							
							//car_id,car_model, car_rating,car_acnonac,car_number,car_status,car_remaining_seats,
							//car_seatingcapacity
							
							car_model_et = (EditText) findViewById(R.id.car_model_edit);
							car_id_et = (EditText) findViewById(R.id.car_id_edit);
							car_rating_et = (EditText) findViewById(R.id.car_rating_edit);
							car_acnonac_et = (EditText) findViewById(R.id.car_acononac_edit);
							car_seatingcapacity_et =(EditText) findViewById(R.id.car_availability_edit);
							car_status_et=(EditText) findViewById(R.id.car_status_edit);
							car_number_et=(EditText) findViewById(R.id.car_reg_no_edit);
							//car_id_et,car_model_et, car_rating_et,car_acnonac_et,car_number_et,car_status_et,car_seatingcapacity_et
							
							
							// display car data in EditText
							car_model_et.setText(car.getString("car_model"));
							car_id_et.setText(car_id);
							car_rating_et.setText(car.getString("car_rating"));
							car_acnonac_et.setText(car.getString("car_acnonac"));
							car_seatingcapacity_et.setText(car.getString("car_seatingcapacity"));
							car_status_et.setText(car.getString("car_status"));
							car_number_et.setText(car.getString("car_number"));
							//,, ,,,,car_remaining_seats,
							//
							
							
							
							

						}else
						{
							// car with car_id not found
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			});

			return null;
		}

		
		
		
		/*
		 // new 
		
		protected String doInBackground(String... params2) 
		{

			
					// Check for success tag
					int success;
					try 
					{
						// Building Parameters
						List<NameValuePair> params = new ArrayList<NameValuePair>();
						params.add(new BasicNameValuePair("car_id", car_id));

						// getting car details by making HTTP request
						// Note that car details url will use GET request
						JSONObject json = jsonParser.makeHttpRequest(	url_car_details, "GET", params);

						// check your log for json response
						Log.d("Single car Details", json.toString());
						
						// json success tag
						success = json.getInt(TAG_SUCCESS);
						if (success == 1) {
							// successfully received car details
							JSONArray carObj = json
									.getJSONArray("car"); // JSON Array
							
							// get first car object from JSON Array
							JSONObject car = carObj.getJSONObject(0);

							// car with this car_id found
							// Edit Text
							
							//car_id,car_model, car_rating,car_acnonac,car_number,car_status,car_remaining_seats,
							//car_seatingcapacity
							
							car_model_et = (EditText) findViewById(R.id.car_model);
							car_id_et = (EditText) findViewById(R.id.car_id_edit);
							car_rating_et = (EditText) findViewById(R.id.car_rating_edit);
							car_acnonac_et = (EditText) findViewById(R.id.car_acononac_edit);
							car_seatingcapacity_et =(EditText) findViewById(R.id.car_availability_edit);
							car_status_et=(EditText) findViewById(R.id.car_status_edit);
							car_number_et=(EditText) findViewById(R.id.car_reg_no_edit);
							//car_id_et,car_model_et, car_rating_et,car_acnonac_et,car_number_et,car_status_et,car_seatingcapacity_et
							
							
							// display car data in EditText
							car_model_et.setText(car.getString("car_model"));
							car_id_et.setText(car_id);
							car_rating_et.setText(car.getString("car_rating"));
							car_acnonac_et.setText(car.getString("car_acnonac"));
							car_seatingcapacity_et.setText(car.getString("car_seatingcapacity"));
							car_status_et.setText(car.getString("car_status"));
							car_number_et.setText(car.getString("car_number"));
							//,, ,,,,car_remaining_seats,
							//
							
							
							
							

						}else
						{
							// car with car_id not found
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
			

			return null;
		}
*/
		protected void onPostExecute(String file_url)
		{
			// dismiss the dialog once got all details
			pDialog.dismiss();
		}
	}
	
	
	
	class SaveCarDetails extends AsyncTask<String, String, String> {


		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(EditCarActivity.this);
			pDialog.setMessage("Saving car ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}


		protected String doInBackground(String... args) 
		{

			// getting updated data from EditTexts
			//car_id,car_model, car_rating,car_acnonac,car_number,car_status,car_remaining_seats,
			//car_seatingcapacity
			
			//car_id_et,car_model_et, car_rating_et,car_acnonac_et,car_number_et,car_status_et,car_seatingcapacity_et
			
			 car_model = car_model_et.getText().toString();
			 car_rating = car_rating_et.getText().toString();
			 car_acnonac = car_acnonac_et.getText().toString();
			car_number=car_number_et.getText().toString();
			car_seatingcapacity=car_seatingcapacity_et.getText().toString();
			car_status=car_status_et.getText().toString();
			

			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("car_id", car_id));
			params.add(new BasicNameValuePair("car_model", car_model));
			params.add(new BasicNameValuePair("car_rating", car_rating));
			params.add(new BasicNameValuePair("car_number", car_number));
			params.add(new BasicNameValuePair("car_seatingcapacity", car_seatingcapacity));
			params.add(new BasicNameValuePair("car_status", car_status));
			params.add(new BasicNameValuePair("car_remaining_seats", car_seatingcapacity));
			params.add(new BasicNameValuePair("car_acnonac", car_acnonac));
			// sending modified data through http request
			// Notice that update car url accepts POST method
			JSONObject json = jsonParser.makeHttpRequest(url_update_car,
					"POST", params);

			// check json success tag
			try 
			{
				int success = json.getInt(TAG_SUCCESS);
				
				if (success == 1) 
				{
					
					// successfully updated
			/*		Intent i = getIntent();
					// send result code 100 to notify about car update
					setResult(100, i);
			*/		
					
					Intent in = new Intent(getApplicationContext(),	Admin_Home.class);
					startActivity(in);
					finish();
				} else {
					// failed to update car
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}


		protected void onPostExecute(String file_url) {
			// dismiss the dialog once car uupdated
			pDialog.dismiss();
		}
	}

	
	
	class Deletecar extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(EditCarActivity.this);
			pDialog.setMessage("Deleting car...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}


		protected String doInBackground(String... args) {

			// Check for success tag
			int success;
			try {
				// Building Parameters
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("car_id", car_id));

				// getting car details by making HTTP request
				JSONObject json = jsonParser.makeHttpRequest(
						url_delete_car, "POST", params);

				// check your log for json response
				Log.d("Delete car", json.toString());
				
				// json success tag
				success = json.getInt(TAG_SUCCESS);
				if (success == 1) {
					// car successfully deleted
					// notify previous activity by sending code 100
				//	Intent i = getIntent();
					// send result code 100 to notify about car deletion
					//setResult(100, i);
					Intent in = new Intent(getApplicationContext(),		Admin_Home.class);
					startActivity(in);
					finish();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}

		protected void onPostExecute(String file_url) {
			// dismiss the dialog once car deleted
			pDialog.dismiss();

		}

	}
	
	
}


