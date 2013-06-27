package com.example.travelbot_4;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.travelbot_4.ConfirmTripInclusive.TripConfirm;

public class Trip_Details extends Activity {
	
	
	TextView car_id_tv,driver_id_tv,travel_id_tv,trip_date_tv,trip_time_tv,trip_to_tv,trip_from_tv,success_tv,feedback_tv,overall_average_rating_tv,no_of_passengers_tv;
	String car_id,driver_id,travel_id,trip_date,trip_time,trip_to,trip_from,success,feedback,overall_average_rating,no_of_passengers;
	
	public void onCreate(Bundle savedInstanceState) 
	 {
		 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.trip_details);
		
		/*//in.putExtra("travel_id", trip_id_pass);
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
		in.putExtra("no_of_passengers", no_of_passengers);*/
		
		
		travel_id_tv=(TextView) findViewById(R.id.travel_id);
		car_id_tv=(TextView) findViewById(R.id.car_id);
		driver_id_tv=(TextView) findViewById(R.id.driver_id);
		trip_time_tv=(TextView) findViewById(R.id.trip_time);
		trip_from_tv=(TextView) findViewById(R.id.trip_from);
		trip_to_tv=(TextView) findViewById(R.id.trip_to);
		trip_date_tv=(TextView) findViewById(R.id.trip_date);
		success_tv=(TextView) findViewById(R.id.success);
		no_of_passengers_tv=(TextView) findViewById(R.id.no_of_passengers);
		overall_average_rating_tv=(TextView) findViewById(R.id.overall_average_rating);
		feedback_tv=(TextView) findViewById(R.id.feedback);
		
		Intent in=getIntent();
		feedback=in.getStringExtra("feedback");
		trip_time=in.getStringExtra("trip_time");
		overall_average_rating=in.getStringExtra("overall_average_rating");
		success = in.getStringExtra("success");
		travel_id = in.getStringExtra("travel_id");
		trip_from = in.getStringExtra("trip_from");
		trip_to = in.getStringExtra("trip_to");
		car_id= in.getStringExtra("car_id");
		driver_id = in.getStringExtra("driver_id");
		trip_date= in.getStringExtra("trip_date");
		no_of_passengers= in.getStringExtra("no_of_passengers");
		
		
		overall_average_rating_tv.setText(overall_average_rating);
		feedback_tv.setText(feedback);
		success_tv.setText(success);
		trip_time_tv.setText(trip_time);		
		trip_from_tv.setText(trip_from);
		trip_to_tv.setText(trip_to);
		car_id_tv.setText(car_id);
		driver_id_tv.setText(driver_id);
		trip_date_tv.setText(trip_date);
		travel_id_tv.setText(travel_id);
		no_of_passengers_tv.setText(no_of_passengers);
		
	
	 }	

}
