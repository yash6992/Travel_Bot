package com.example.travelbot_4;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.travelbot_4.ConfirmTripInclusive.TripConfirm;

public class Customer_Details extends Activity implements OnClickListener {
	
	
	TextView cust_id_tv,cust_name_tv,cust_mobile_tv,cust_email_tv,password_tv;
	String cust_id,cust_name,cust_mobile,cust_email,password;
	Button user_booking_history;
	
	public void onCreate(Bundle savedInstanceState) 
	 {
		 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.customer_details);
		
		/*
		in.putExtra("cust_id", cust_id);
		in.putExtra("cust_name", cust_name);
		in.putExtra("cust_mobile", cust_mobile);
		in.putExtra("cust_email", cust_email);
		in.putExtra("password", password);*/
		
		user_booking_history=(Button) findViewById(R.id.user_booking_history);
		user_booking_history.setOnClickListener(this);
		
		cust_id_tv=(TextView) findViewById(R.id.cust_id);
		cust_name_tv=(TextView) findViewById(R.id.cust_name);
		cust_mobile_tv=(TextView) findViewById(R.id.cust_mobile);
		cust_email_tv=(TextView) findViewById(R.id.cust_email);
		password_tv=(TextView) findViewById(R.id.password);
		
		
		Intent in=getIntent();
		
		cust_id=in.getStringExtra("cust_id");
		cust_name=in.getStringExtra("cust_name");
		cust_mobile=in.getStringExtra("cust_mobile");
		cust_email = in.getStringExtra("cust_email");
		password = in.getStringExtra("password");
		
		
		
		cust_id_tv.setText(cust_id);
		cust_name_tv.setText(cust_name);
		cust_mobile_tv.setText(cust_mobile);
		cust_email_tv.setText(cust_email);
		password_tv.setText(password);
		
	
	 }
	

	@Override
	public void onClick(View v) {
		switch(v.getId())
		{
		
		case R.id.user_booking_history: 
	    {user_booking_history(); break;}
		}
	}




	private void user_booking_history()
	{
		Toast tbb= Toast.makeText(getApplicationContext(), "user_booking_history: "+ cust_id ,Toast.LENGTH_LONG);
		tbb.show();
		Intent ubh = new Intent(getApplicationContext(),Booked_History.class);
		ubh.putExtra("cust_id", cust_id);

		startActivity(ubh);
		
	}

}

	
	
	
	
	


