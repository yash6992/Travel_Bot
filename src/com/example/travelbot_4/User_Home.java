package com.example.travelbot_4;


import com.example.travelbot_4.R;
import com.example.travelbot_4.Register.CreateNewClient;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;

import android.view.View;
import android.view.View.OnClickListener;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class User_Home extends Activity
{
	
	TextView user_name;
	Button plan,booking_history,logout;
	

	//String name = intent.getStringExtra("name");
	
	//ParseUser currentUser;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_name);
		 
		user_name=(TextView) findViewById(R.id.user_name);
		plan=(Button) findViewById(R.id.plan);
		booking_history=(Button) findViewById(R.id.booking_history);
		
		
		
		Intent intent = getIntent();
		final String cust_id = intent.getStringExtra("cust_id");
		user_name.setText(cust_id);
		plan.setOnClickListener(new View.OnClickListener() 
		
		{

			@Override
			public void onClick(View view) 
			{

				Intent plan = new Intent(User_Home.this, Travel_Plan.class);
				plan.putExtra("cust_id", cust_id);
			    startActivity(plan);
				
			}
		});
		booking_history.setOnClickListener(new View.OnClickListener() 
		
		{

			@Override
			public void onClick(View view) 
			{

				Intent booking_history = new Intent(User_Home.this, Booked_History.class);
				booking_history.putExtra("cust_id", cust_id);
			    startActivity(booking_history);
				
			}
		});
		logout=(Button) findViewById(R.id.logout);
		logout.setOnClickListener(new View.OnClickListener() 
		
		{

			@Override
			public void onClick(View view) 
			{

				Intent login = new Intent(User_Home.this, Login.class);
				Toast.makeText(getApplicationContext(),cust_id+ "	is logged out", Toast.LENGTH_SHORT).show();
			    startActivity(login);
				finish();
			}
		});
		
		
		
		
		
	}
	
	/* public void plan() {	
			//Toast tbb= Toast.makeText(getApplicationContext(), "login",Toast.LENGTH_LONG);
			//tbb.show();
			Intent plan = new Intent(User_Home.this, Travel_Plan.class);
		    startActivity(plan);
		    }
	 public void booking_history() {	
			//Toast tbb= Toast.makeText(getApplicationContext(), "login",Toast.LENGTH_LONG);
			//tbb.show();
			Intent booking_history = new Intent(User_Home.this, Booked_History.class);
		    startActivity(booking_history);
		    }
	 
	@Override
	public void onClick(View view) {
		switch(view.getId())
		{
		
		case R.id.plan: 
	    {plan(); break;}
		case R.id.booking_history: 
	    {booking_history(); break;}
		
		
		
		}
		
	}
	*/
	
	
	
}