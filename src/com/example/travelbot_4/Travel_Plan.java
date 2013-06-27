package com.example.travelbot_4;


import com.example.travelbot_4.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;

import android.view.View;
import android.view.View.OnClickListener;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Travel_Plan extends Activity implements OnClickListener{
	
	public TextView user_name;
	Button submit; EditText from,to,dd,mm,yyyy,hh,min,nop; CheckBox exclusive,pick;
	String from_str,to_str,dd_str,mm_str,yyyy_str,hh_str,min_str,nop_str,exclusive_str,pick_str;
	
	public String cust_id;
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.travel_plan);
		
		user_name=(TextView) findViewById(R.id.textView3);
		Intent intent = getIntent();
		 cust_id = intent.getStringExtra("cust_id");
		user_name.setText(cust_id);
		
		submit=(Button)findViewById(R.id.submit);
		submit.setOnClickListener(this);
		
		from=(EditText)findViewById(R.id.from);
		to=(EditText)findViewById(R.id.to);
		dd=(EditText)findViewById(R.id.dd);
		mm=(EditText)findViewById(R.id.mm);
		yyyy=(EditText)findViewById(R.id.yyyy);
		hh=(EditText)findViewById(R.id.hh);
		nop=(EditText)findViewById(R.id.nop);
		min=(EditText)findViewById(R.id.min);
		
		exclusive=(CheckBox)findViewById(R.id.exclusive);
		pick=(CheckBox) findViewById(R.id.pick_at_home);
		
		
	}
	public void submit()
	{
	
		from_str=from.getText().toString();
		to_str=to.getText().toString();
		dd_str=dd.getText().toString();
		mm_str=mm.getText().toString();
		yyyy_str=yyyy.getText().toString();
		hh_str=hh.getText().toString();
		min_str=min.getText().toString();
		nop_str=nop.getText().toString();
		exclusive_str=exclusive.isChecked()==true?"1":"0";
		pick_str=pick.isChecked()==true?"1":"0";
		
		if(from_str.equals(null)||to_str.equals(null)||dd_str.equals(null)||mm_str.equals(null)||yyyy_str.equals(null)||hh_str.equals(null)||min_str.equals(null)||nop_str.equals(null))
		{
			Toast.makeText(getApplicationContext(),
				 	   "Fill all fields", Toast.LENGTH_SHORT).show();
		}
		else
		{
			
    		Toast.makeText(getApplicationContext(), "Let us search for the right cab.", Toast.LENGTH_SHORT).show();
    		if(exclusive_str.equals("0"))
    			
    		{
    			Intent submit_travel_plan_i = new Intent(Travel_Plan.this, Search_Results_Inclusive.class);
    			submit_travel_plan_i.putExtra("trip_from", from_str);
    		submit_travel_plan_i.putExtra("trip_to", to_str);
    		submit_travel_plan_i.putExtra("trip_date",yyyy_str+"-"+mm_str+"-"+dd_str);
    		submit_travel_plan_i.putExtra("no_of_passengers",nop_str);
    		submit_travel_plan_i.putExtra("trip_time", hh_str+":"+min_str+":00");
    		submit_travel_plan_i.putExtra("cust_id", cust_id);
    		
    		submit_travel_plan_i.putExtra("exclusive", exclusive_str);
    		submit_travel_plan_i.putExtra("pick", pick_str);
    		
    		startActivity(submit_travel_plan_i);
    		finish();
    		}
    		else
    		{
    			Intent submit_travel_plan_e = new Intent(Travel_Plan.this, Search_Results_Exclusive.class);
    		
    			Toast.makeText(getApplicationContext(), "Let us search for the right cab.", Toast.LENGTH_SHORT).show();
    		
    		
    			submit_travel_plan_e.putExtra("trip_from", from_str);
    		
    			submit_travel_plan_e.putExtra("trip_to", to_str);
    		
    			submit_travel_plan_e.putExtra("trip_date",yyyy_str+"-"+mm_str+"-"+dd_str);
    		
    			submit_travel_plan_e.putExtra("trip_time", hh_str+":"+min_str+":00");
    		
    			submit_travel_plan_e.putExtra("exclusive", exclusive_str);
    			submit_travel_plan_e.putExtra("pick", pick_str);
    			submit_travel_plan_e.putExtra("cust_id", cust_id);
    			
    			
    			startActivity(submit_travel_plan_e);
    			finish();
    		}
  		
    		
    		
	       
		} 
	
	}
	
	
	@Override
	public void onClick(View view) {

		switch(view.getId())
		{
		
		
		case R.id.submit: 
	    {submit(); break;}
		
		
		
		}

		
	}
	
	
	
}