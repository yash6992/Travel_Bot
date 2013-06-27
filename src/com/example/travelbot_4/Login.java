package com.example.travelbot_4;


import com.example.travelbot_4.Login;
import com.example.travelbot_4.User_Home;
import com.example.travelbot_4.R;
//import com.mysql.jdbc.Statement;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;

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
import org.json.JSONArray;








import org.json.JSONException;
import org.json.JSONObject;

//import android.app.Activity;
import android.app.ProgressDialog;
//import android.content.Intent;
import android.os.AsyncTask;

public class Login extends Activity 
{
	
	public EditText username_login,pwd_login;
	public Button login,register,close_button;
	
	

	// Progress Dialog
    private ProgressDialog pDialog;
 
    // JSON parser class
    JSONParser jsonParser = new JSONParser();
 
    // single product url
    public static final String url_client_details = "http://yash6992.000space.com/travelbot/get_user_login.php";
 
	// JSON Node names
	public static final String TAG_SUCCESS = "success";
	public static final String TAG_client = "client";
	public static final String TAG_cust_id = "cust_id";
	public static final String TAG_cust_name = "cust_name";
	public static final String TAG_cust_mobile = "cust_mobile";
	public static final String TAG_password = "password";
	
	
	public String cust_id,password,dbpass,dbuser ;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) 
    
    
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
	
       username_login=(EditText) findViewById(R.id.user_login);
       pwd_login=(EditText) findViewById(R.id.pwd_login);
        login=(Button) findViewById(R.id.login);
        register=(Button) findViewById(R.id.register);
        close_button=(Button) findViewById(R.id.close_button);
   
        close_button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                finish();
                System.exit(0);
            }
        });
        
        
        login.setOnClickListener(new View.OnClickListener()        
        {

			@Override
			public void onClick(View view)
			{

			
				cust_id=username_login.getText().toString();
		    	
				password=pwd_login.getText().toString();
		    	
		    	    	
		    	
		    	if( (!password.equals("")) && (!cust_id.equals("")))
		    	{
		    	
		    		
		    		if(cust_id.equals("admin") && password.equals("tb"))
		    		{
		    			Toast tbb= Toast.makeText(getApplicationContext(), "Welcome, Admin",Toast.LENGTH_LONG);
		    			tbb.show();
		    			Intent admin = new Intent(Login.this, Admin_Home.class);
		    		    startActivity(admin);	
		    			
		    		}
		
		    		else
		    			
		    			
		    		{
		    			
		    			new GetClientDetails().execute();
		    			
		    		}
	
		    		
		    		
		 
		    		
		    	}
		    	
		    	else
		    	{
		    			Toast.makeText(getApplicationContext(), "Username or Password Empty!", Toast.LENGTH_SHORT).show();
		    	}
			}
		});
        
        
        register.setOnClickListener(new View.OnClickListener() 
		
		{

			@Override
			public void onClick(View view) 
			{
				Intent register = new Intent(Login.this, Register.class);

		    startActivity(register);
		    
		    }
		});
        
         
           
    }

   
	/**
	 * Background Async Task to Get complete client details
	 * */
	class GetClientDetails extends AsyncTask<String, String, String>
	{

		boolean successlogin=false;
		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		//UNCOMMENT THIS -NO CHANGE
	  
	 	protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Login.this);
			pDialog.setMessage("Loading client details. Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		/**
		 * Getting client details in background thread original
		 * */
	

		
		
		protected String doInBackground(String... args) 
		{


			int success;
 
			cust_id= username_login.getText().toString();
			password=pwd_login.getText().toString();
		//	Toast.makeText(getApplicationContext(), cust_id+" "+password+"	Thread running",Toast.LENGTH_LONG).show();
			
			try 
			{
				
						
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("cust_id", cust_id));
				params.add(new BasicNameValuePair("password", password));

				
				  
				JSONObject json = jsonParser.makeHttpRequest(url_client_details, "GET", params);
				
			
							
		
							
							
				Log.d("Single Client Details", json.toString());
								
							// json success tag
				success = json.getInt(TAG_SUCCESS);
				if (success == 1)
				{
								// successfully received client details
					JSONArray clientObj = json.getJSONArray(TAG_client); // JSON Array
									
					// get first client object from JSON Array
					JSONObject client = clientObj.getJSONObject(0);

					successlogin=true;
									
					String cust_name=		client.getString("cust_name");
					String cust_mobile=		client.getString("cust_mobile");
											
									
				//	Toast.makeText(getApplicationContext(), cust_id+" exists",Toast.LENGTH_LONG).show();
					    		
					Intent user_home = new Intent(Login.this, User_Home.class);
					user_home.putExtra("cust_id", cust_id);
					user_home.putExtra("cust_name", cust_name);
					user_home.putExtra("cust_mobile", cust_mobile);
					user_home.putExtra("password", password);
					    			
					    			
					    			
					    			
					startActivity(user_home);
				//	finish();
				}
				
				else
				{
					
				}
				
			}
			
			catch (JSONException e)
						
			{
						
				e.printStackTrace();
			}

			return null;
		}
		
		
		
		
		
		
			//UNCOMMENT THIS -NO CHANGE
		protected void onPostExecute(String file_url) 
		{
			// dismiss the dialog once got all details
			pDialog.dismiss();
			if(successlogin==false)
				Toast.makeText(getApplicationContext(), cust_id+" not found, please register",Toast.LENGTH_LONG).show();
			else
				Toast.makeText(getApplicationContext(), "Welcome, "+cust_id,Toast.LENGTH_LONG).show();
				
		}
		
	}
	
}
