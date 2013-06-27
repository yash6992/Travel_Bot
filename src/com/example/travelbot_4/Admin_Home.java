package com.example.travelbot_4;


import com.example.travelbot_4.R;
import com.parse.Parse;
import com.parse.ParseFacebookUtils.Permissions.User;
import com.parse.ParseUser;
//import com.parse.Parse.User;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import android.view.View;
import android.view.View.OnClickListener;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Admin_Home extends Activity implements OnClickListener{
	
	
	Button view_cars,view_drivers,view_trips,view_customers,logout_admin;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.admin_home);
		 
		
		view_cars=(Button) findViewById(R.id.view_cars);
		view_drivers=(Button) findViewById(R.id.view_drivers);
		view_trips=(Button) findViewById(R.id.view_trips);
		view_customers=(Button) findViewById(R.id.view_customers);
		logout_admin=(Button) findViewById(R.id.logout_admin);
		
		view_cars.setOnClickListener(this);
		view_drivers.setOnClickListener(this);
		view_trips.setOnClickListener(this);
		view_customers.setOnClickListener(this);
		
		
		logout_admin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) 
			{
				Intent login = new Intent(Admin_Home.this, Login.class);
				Toast.makeText(getApplicationContext(),"admin is logged out", Toast.LENGTH_SHORT).show();
			    startActivity(login);
				finish();
				
			}
		});
		
		
	}
	
	 public void view_cars() {	
		//	Toast tbb= Toast.makeText(getApplicationContext(), "view_cars",Toast.LENGTH_LONG);
		//	tbb.show();
			Intent view_cars = new Intent(Admin_Home.this, View_Cars.class);
		    startActivity(view_cars);
		    }
	 public void view_drivers() {	
			Toast tbb= Toast.makeText(getApplicationContext(), "view_drivers",Toast.LENGTH_LONG);
			tbb.show();
			Intent view_drivers = new Intent(Admin_Home.this, View_Drivers.class);
		    startActivity(view_drivers);
		    }
	 public void view_trips() {	
			Toast tbb= Toast.makeText(getApplicationContext(), "view_trips",Toast.LENGTH_LONG);
			tbb.show();
			Intent view_trips = new Intent(Admin_Home.this, View_Trips.class);
		    startActivity(view_trips);
		    }
	 public void view_customers() {	
			Toast tbb= Toast.makeText(getApplicationContext(), "view_customers",Toast.LENGTH_LONG);
			tbb.show();
			Intent view_customers = new Intent(Admin_Home.this, View_Customers.class);
		    startActivity(view_customers);
		    }
	 
	@Override
	public void onClick(View view) {
		switch(view.getId())
		{
		
		case R.id.view_cars: 
	    {view_cars(); break;}
		case R.id.view_drivers: 
	    {view_drivers(); break;}
		case R.id.view_trips: 
	    {view_trips(); break;}
		case R.id.view_customers: 
	    {view_customers(); break;}
		
		
		
		}
		
	}
	
	
	
	
}