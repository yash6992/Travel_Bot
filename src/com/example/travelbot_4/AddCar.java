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



public class AddCar extends Activity 

{
	// Progress Dialog
    private ProgressDialog pDialog;
 
    JSONParser jsonParser = new JSONParser();
    public String car_id,car_model, car_rating,car_acnonac,car_number,car_status,car_remaining_seats,car_seatingcapacity; 
	public EditText car_id_et,car_model_et, car_rating_et,car_acnonac_et,car_number_et,car_status_et,car_seatingcapacity_et; 
 
    // url to create new product
    private static String url_create_car = "http://yash6992.000space.com/travelbot/create_car.php";
 
    // JSON Node names
    private static final String TAG_SUCCESS = "success";
	
	Button submit; 

	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_car);
		
		car_id_et= (EditText) findViewById(R.id.car_id);
		car_model_et=(EditText) findViewById(R.id.car_model);
		car_rating_et=		(EditText) findViewById(R.id.car_rating);
		car_acnonac_et=(EditText) findViewById(R.id.car_acononac);
		car_number_et=		(EditText) findViewById(R.id.car_reg_no);
		car_status_et=(EditText) findViewById(R.id.car_status);
		car_seatingcapacity_et = (EditText) findViewById(R.id.car_availability);

		car_id= car_id_et.getText().toString();
		car_model=car_model_et.getText().toString();
		car_rating=car_rating_et.getText().toString();
		car_acnonac=car_acnonac_et.getText().toString();
		car_number=car_number_et.getText().toString();
		car_status=car_status_et.getText().toString();
		car_seatingcapacity=car_seatingcapacity_et.getText().toString();
		
		
		submit=(Button) findViewById(R.id.add_car);
		
		submit.setOnClickListener(new OnClickListener() 
		
		{

			@Override
			public void onClick(View view) {
				// creating new product in background thread
			
				 if(car_id.equals(null) || car_model.equals(null) ||  car_rating.equals(null) || car_acnonac.equals(null) || car_number.equals(null) || car_status.equals(null) || car_seatingcapacity.equals(null))
				 {
						Toast.makeText(getApplicationContext(), "Enter all fields", Toast.LENGTH_SHORT).show();
				 }
				 else
				 {
					  new CreateNewCar().execute();	
										  
				 }
				 
				//new CreateNewCar().execute();	
			}
		});
		
		
	}
	
		/**
		 * Background Async Task to Create new product
		 * */
		class CreateNewCar extends AsyncTask<String, String, String> 
		{

			/**
			 * Before starting background thread Show Progress Dialog
			 * */
			@Override
			protected void onPreExecute() 
			{
				super.onPreExecute();
				pDialog = new ProgressDialog(AddCar.this);
				pDialog.setMessage("Creating Car..");
				pDialog.setIndeterminate(false);
				pDialog.setCancelable(true);
				pDialog.show();
			}

			/**
			 * Creating client
			 * */
			protected String doInBackground(String... args) 
			{
				
				car_id= car_id_et.getText().toString();
				car_model=car_model_et.getText().toString();
				car_rating=car_rating_et.getText().toString();
				car_acnonac=car_acnonac_et.getText().toString();
				
				car_number=car_number_et.getText().toString();
				car_status=car_status_et.getText().toString();
				car_seatingcapacity=car_seatingcapacity_et.getText().toString();
				
				
				
				
				// Building Parameters
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				
				params.add(new BasicNameValuePair("car_id", car_id));
				params.add(new BasicNameValuePair("car_model", car_model));
				params.add(new BasicNameValuePair("car_rating", car_rating));
				params.add(new BasicNameValuePair("car_acnonac", car_acnonac));
				params.add(new BasicNameValuePair("car_number", car_number));
				params.add(new BasicNameValuePair("car_status", car_status));
				params.add(new BasicNameValuePair("car_seatingcapacity", car_seatingcapacity));
				params.add(new BasicNameValuePair("car_remaining_seats", car_seatingcapacity));

				// getting JSON Object
				// Note that create product url accepts POST method
				JSONObject json = jsonParser.makeHttpRequest(url_create_car,	"POST", params);
				
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
				Toast.makeText(getApplicationContext(),car_id+ "	car created", Toast.LENGTH_LONG).show();
			}

		}
	
}