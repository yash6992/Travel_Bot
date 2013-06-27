package com.example.travelbot_4;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
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
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;



import java.util.Random;



import android.widget.AdapterView.OnItemClickListener;

public class ConfirmTripInclusive extends Activity
{

	Button confirm;
	public String car_model,car_rating,car_acnonac,car_number,no_of_passengers,car_status,car_remaining_seats,car_seatingcapacity,trip_from,trip_to,car_id,driver_id,trip_date,travel_id,cust_id,exclusive,booking_id,trip_time;
	public TextView car_model_tv,no_of_passengers_tv,car_rating_tv,car_acnonac_tv,car_number_tv,car_status_tv,car_remaining_seats_tv,car_seatingcapacity_tv,trip_from_tv,trip_to_tv,car_id_tv,driver_id_tv,trip_date_tv,travel_id_tv;

	private static final String TAG_SUCCESS = "success";
	// Progress Dialog
    private ProgressDialog pDialog;
    Intent in;
    JSONParser jsonParser = new JSONParser();
    
    private static final String url_trip_confirm_inclusive = "http://yash6992.000space.com/travelbot/confirm_trip_inclusive.php";
    
    private static final String url_trip_confirm_exclusive = "http://yash6992.000space.com/travelbot/confirm_trip_exclusive.php";
    
	 public void onCreate(Bundle savedInstanceState) 
	 {
		 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.confirm_trip_inclusive);
		
		confirm=(Button) findViewById(R.id.confirm_trip);
		
		car_model_tv=(TextView) findViewById(R.id.car_model);
		car_rating_tv=(TextView) findViewById(R.id.car_rating);
		car_acnonac_tv=(TextView) findViewById(R.id.car_acnonac);
		car_number_tv=(TextView) findViewById(R.id.car_number);
		//car_status_tv=(TextView) findViewById(R.id.car_model);
		car_remaining_seats_tv=(TextView) findViewById(R.id.car_remaining_seats);
		car_seatingcapacity_tv=(TextView) findViewById(R.id.car_seatingcapacity);
		trip_from_tv=(TextView) findViewById(R.id.trip_from);
		trip_to_tv=(TextView) findViewById(R.id.trip_to);
		car_id_tv=(TextView) findViewById(R.id.car_id);
		driver_id_tv=(TextView) findViewById(R.id.driver_id);
		trip_date_tv=(TextView) findViewById(R.id.trip_date);
		travel_id_tv=(TextView) findViewById(R.id.travel_id);
		no_of_passengers_tv=(TextView) findViewById(R.id.no_of_passengers);
		
		 in=getIntent();
		car_model=in.getStringExtra("car_model");
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
		
		car_model_tv.setText(car_model);
		car_rating_tv.setText(car_rating);
		car_acnonac_tv.setText(car_acnonac);
		car_number_tv.setText(car_number);
		car_remaining_seats_tv.setText(car_remaining_seats);
		car_seatingcapacity_tv.setText(car_seatingcapacity);
		trip_from_tv.setText(trip_from);
		trip_to_tv.setText(trip_to);
		car_id_tv.setText(car_id);
		driver_id_tv.setText(driver_id);
		trip_date_tv.setText(trip_date);
		travel_id_tv.setText(travel_id);
		no_of_passengers_tv.setText(no_of_passengers);
		
		confirm.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v)
			{
				if(exclusive.equalsIgnoreCase("Y"))
					new TripConfirmExclusive().execute();
				else	
					new TripConfirm().execute();
			}
		});
		
	 }	
	 
	 class TripConfirm extends AsyncTask<String, String, String> 
	 {


	 	


		@Override
	 	protected void onPreExecute() {
	 		super.onPreExecute();
	 		pDialog = new ProgressDialog(ConfirmTripInclusive.this);
	 		pDialog.setMessage("Confirming your booking ...");
	 		pDialog.setIndeterminate(false);
	 		pDialog.setCancelable(true);
	 		pDialog.show();
	 	}


	 	protected String doInBackground(String... args) 
	 	{

	 		
	 		
	 		

	 		List<NameValuePair> params = new ArrayList<NameValuePair>();
	 		params.add(new BasicNameValuePair("car_id", car_id));
	 		params.add(new BasicNameValuePair("travel_id", travel_id));
	 		params.add(new BasicNameValuePair("cust_id", cust_id));
	 		params.add(new BasicNameValuePair("no_of_passengers", no_of_passengers));
	 		

	 		JSONObject json = jsonParser.makeHttpRequest(url_trip_confirm_inclusive,
	 				"GET", params);
	 		Log.i("tagconvertstr", "["+json+"]");

	 		// check json success tag
	 		try 
	 		{
	 			int success = json.getInt(TAG_SUCCESS);
	 			
	 			if (success == 1) 
	 			{
	 				 				
	 				Intent in = new Intent(getApplicationContext(),	User_Home.class);
	 				in.putExtra("cust_id", cust_id);
	 				startActivity(in);
	 				finish();
	 			} else 
	 			{
	 				// failed to update car
	 			}
	 		} catch (JSONException e) {
	 			e.printStackTrace();
	 		}

	 		return null;
	 	}


	 	protected void onPostExecute(String file_url) {
	 		// dismiss the dialog once car uupdated
	 		Toast.makeText(getApplicationContext(), travel_id+cust_id +" : Booking done", Toast.LENGTH_LONG).show();
	 		pDialog.dismiss();
	 	}
	 }

	 class TripConfirmExclusive extends AsyncTask<String, String, String> 
	 {


	 	


		@Override
	 	protected void onPreExecute() {
	 		super.onPreExecute();
	 		pDialog = new ProgressDialog(ConfirmTripInclusive.this);
	 		pDialog.setMessage("Confirming your Exclusive booking ...");
	 		pDialog.setIndeterminate(false);
	 		pDialog.setCancelable(true);
	 		pDialog.show();
	 	}


	 	protected String doInBackground(String... args) 
	 	{

	 		Random lol=new Random();
	 		
	 		travel_id=car_id+lol.nextInt(100);

	 		List<NameValuePair> params = new ArrayList<NameValuePair>();
	 		params.add(new BasicNameValuePair("car_id", car_id));
	 		params.add(new BasicNameValuePair("travel_id", travel_id));
	 		params.add(new BasicNameValuePair("cust_id", cust_id));
	 		params.add(new BasicNameValuePair("trip_from", trip_from));
	 		params.add(new BasicNameValuePair("trip_to", trip_to));
	 		params.add(new BasicNameValuePair("trip_date", trip_date));
	 		
	 		trip_time=in.getStringExtra("trip_time");
	 		params.add(new BasicNameValuePair("trip_time", trip_time));
	 		
	 		
	 		booking_id=travel_id+cust_id;
	 		params.add(new BasicNameValuePair("booking_id",booking_id ));
	 		params.add(new BasicNameValuePair("no_of_passengers", no_of_passengers));


	 		JSONObject json = jsonParser.makeHttpRequest(url_trip_confirm_exclusive,
	 				"GET", params);
	 		Log.i("tagconvertstr", "["+json+"]");

	 		// check json success tag
	 		try 
	 		{
	 			int success = json.getInt(TAG_SUCCESS);
	 			
	 			if (success == 1) 
	 			{
	 				 				
	 				Intent in = new Intent(getApplicationContext(),	User_Home.class);
	 				in.putExtra("cust_id", cust_id);
	 				startActivity(in);
	 				finish();
	 			} else 
	 			{
	 				// failed to update car
	 			}
	 		} catch (JSONException e) {
	 			e.printStackTrace();
	 		}

	 		return null;
	 	}


	 	protected void onPostExecute(String file_url) 
	 	{
	 		// dismiss the dialog once car uupdated
	 		Toast.makeText(getApplicationContext(), booking_id+ " is your booking id", Toast.LENGTH_LONG).show();
	 		pDialog.dismiss();
	 	}
	 }

	
}



