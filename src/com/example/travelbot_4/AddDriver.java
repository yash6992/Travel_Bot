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




 

import android.app.ProgressDialog;

import android.os.AsyncTask;



public class AddDriver extends Activity 

{
	// Progress Dialog
    private ProgressDialog pDialog;
 
    JSONParser jsonParser = new JSONParser();
    public String driver_id,driver_name, driver_mobile,driver_status,driver_address,driver_note,driver_rating; 
	public EditText driver_id_et,driver_name_et, driver_mobile_et,driver_status_et,driver_address_et,driver_note_et,driver_rating_et; 
 
    // url to create new product
    private static String url_create_driver = "http://yash6992.000space.com/travelbot/create_driver.php";
 
    // JSON Node names
    private static final String TAG_SUCCESS = "success";
	
	Button submit; 

	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_driver);
		
		/*in.putExtra("driver_id", driver_id);
		in.putExtra("driver_name", driver_name);
		in.putExtra("driver_mobile", driver_mobile);
		in.putExtra("driver_status", driver_status);
		in.putExtra("driver_address", driver_address);
		in.putExtra("driver_note", driver_note);
		in.putExtra("driver_rating", driver_rating);*/
		
		driver_id_et= (EditText) findViewById(R.id.driver_id);
		driver_name_et=(EditText) findViewById(R.id.driver_name);
		driver_mobile_et=		(EditText) findViewById(R.id.driver_mobile);
		driver_status_et=(EditText) findViewById(R.id.driver_status);
		driver_address_et=		(EditText) findViewById(R.id.driver_address);
		driver_note_et=(EditText) findViewById(R.id.driver_note);
		driver_rating_et = (EditText) findViewById(R.id.driver_rating);

		driver_id= driver_id_et.getText().toString();
		driver_name=driver_name_et.getText().toString();
		driver_mobile=driver_mobile_et.getText().toString();
		driver_status=driver_status_et.getText().toString();
		driver_address=driver_address_et.getText().toString();
		driver_note=driver_note_et.getText().toString();
		driver_rating=driver_rating_et.getText().toString();
		
		
		submit=(Button) findViewById(R.id.add_driver);
		
		submit.setOnClickListener(new OnClickListener() 
		
		{

			@Override
			public void onClick(View view) {
				// creating new product in background thread
			
				 if(driver_id.equals(null) || driver_name.equals(null) ||  driver_mobile.equals(null) || driver_status.equals(null) || driver_address.equals(null) || driver_note.equals(null) || driver_rating.equals(null))
				 {
						Toast.makeText(getApplicationContext(), "Enter all fields", Toast.LENGTH_SHORT).show();
				 }
				 else
				 {
					  new CreateNewDriver().execute();	
										  
				 }
				 
				//new CreateNewCar().execute();	
			}
		});
		
		
	}
	
		/**
		 * Background Async Task to Create new product
		 * */
		class CreateNewDriver extends AsyncTask<String, String, String> 
		{

			/**
			 * Before starting background thread Show Progress Dialog
			 * */
			@Override
			protected void onPreExecute() 
			{
				super.onPreExecute();
				pDialog = new ProgressDialog(AddDriver.this);
				pDialog.setMessage("Creating Driver..");
				pDialog.setIndeterminate(false);
				pDialog.setCancelable(true);
				pDialog.show();
			}

			/**
			 * Creating client
			 * */
			protected String doInBackground(String... args) 
			{
				/*
				car_id= car_id_et.getText().toString();
				car_model=car_model_et.getText().toString();
				car_rating=car_rating_et.getText().toString();
				car_acnonac=car_acnonac_et.getText().toString();
				
				car_number=car_number_et.getText().toString();
				car_status=car_status_et.getText().toString();
				car_seatingcapacity=car_seatingcapacity_et.getText().toString();*/
				
				driver_id= driver_id_et.getText().toString();
				driver_name=driver_name_et.getText().toString();
				driver_mobile=driver_mobile_et.getText().toString();
				driver_status=driver_status_et.getText().toString();
				driver_address=driver_address_et.getText().toString();
				driver_note=driver_note_et.getText().toString();
				driver_rating=driver_rating_et.getText().toString();
				
				
				
				// Building Parameters
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				
				params.add(new BasicNameValuePair("driver_id", driver_id));
				params.add(new BasicNameValuePair("driver_name", driver_name));
				params.add(new BasicNameValuePair("driver_mobile", driver_mobile));
				params.add(new BasicNameValuePair("driver_status", driver_status));
				params.add(new BasicNameValuePair("driver_address", driver_address));
				params.add(new BasicNameValuePair("driver_note", driver_note));
				params.add(new BasicNameValuePair("driver_rating", driver_rating));
				

				// getting JSON Object
				// Note that create product url accepts POST method
				JSONObject json = jsonParser.makeHttpRequest(url_create_driver,	"POST", params);
				
				// check log cat fro response
				Log.d("Create Response", json.toString());

				// check for success tag
				try 
				{
					int success = json.getInt(TAG_SUCCESS);

					if (success == 1) 
					{
						//"successfully created "+cust_id
						Intent i = new Intent(getApplicationContext(), Admin_Home.class);
					//	Toast.makeText(Register.this,cust_id+ "	successfully created", Toast.LENGTH_LONG).show();
						startActivity(i);
						
						// closing this screen
						finish();
					} else 
					{
						// failed to create product
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}

				return null;
			}

			/**
			 * After completing background task Dismiss the progress dialog
			 * **/
			protected void onPostExecute(String file_url)
			{
				// dismiss the dialog once done
				pDialog.dismiss();
				Toast.makeText(getApplicationContext(),driver_id+ "	driver created", Toast.LENGTH_LONG).show();
			}

		}
	
}