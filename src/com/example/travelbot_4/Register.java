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
/*
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import com.parse.Parse;
import com.parse.ParseUser;
 
import net.sourceforge.jtds.jdbc.*;
*/

import java.util.ArrayList;
import java.util.List;
 
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;




 

import android.app.ProgressDialog;

import android.os.AsyncTask;



public class Register extends Activity 

{
	// Progress Dialog
    private ProgressDialog pDialog;
 
    JSONParser jsonParser = new JSONParser();
    public String cust_id, cust_name, cust_mobile,cust_email,password; 
	public EditText user_name_r,confirm_pwd,enter_pwd,mobile,email; 
 
    // url to create new product
    private static String url_create_client = "http://yash6992.000space.com/travelbot/create_client.php";
 
    // JSON Node names
    private static final String TAG_SUCCESS = "success";
	
	Button submit; 

	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		
		user_name_r= (EditText) findViewById(R.id.user_name_r);
		
		mobile=(EditText) findViewById(R.id.mobile);
		email=		(EditText) findViewById(R.id.email);
		enter_pwd=(EditText) findViewById(R.id.enter_pwd);
		confirm_pwd= (EditText) findViewById(R.id.confirm_pwd);

		cust_name= user_name_r.getText().toString();
		password=enter_pwd.getText().toString();
		cust_mobile=mobile.getText().toString();
		cust_email=email.getText().toString();
		
		submit=(Button) findViewById(R.id.submit);
		submit.setOnClickListener(new View.OnClickListener() 
		
		{

			@Override
			public void onClick(View view) {
				// creating new product in background thread
				 if(enter_pwd.getText().toString().equals(confirm_pwd.getText().toString()))
				 {
					  new CreateNewClient().execute();	
					  
					
					  
				 }
				 else
				 {
						Toast.makeText(getApplicationContext(), "Password Didn't Match!", Toast.LENGTH_SHORT).show();
				 }
			}
		});
		
		
	}
	 
		/**
		 * Background Async Task to Create new product
		 * */
		class CreateNewClient extends AsyncTask<String, String, String> 
		{

			/**
			 * Before starting background thread Show Progress Dialog
			 * */
			@Override
			protected void onPreExecute() 
			{
				super.onPreExecute();
				pDialog = new ProgressDialog(Register.this);
				pDialog.setMessage("Creating Client..");
				pDialog.setIndeterminate(false);
				pDialog.setCancelable(true);
				pDialog.show();
			}

			/**
			 * Creating client
			 * */
			protected String doInBackground(String... args) 
			{
				
				cust_name= user_name_r.getText().toString();
				password=enter_pwd.getText().toString();
				cust_mobile=mobile.getText().toString();
				cust_email=email.getText().toString();
				
				
				String tempid=new String(cust_name);
				
				cust_id=tempid.replaceAll("\\s","").toLowerCase()+cust_mobile;
				
				// Building Parameters
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("cust_id", cust_id));
				
				params.add(new BasicNameValuePair("cust_name", cust_name));
				params.add(new BasicNameValuePair("cust_mobile", cust_mobile));
				params.add(new BasicNameValuePair("cust_email", cust_email));
				params.add(new BasicNameValuePair("password", password));

				// getting JSON Object
				// Note that create product url accepts POST method
				JSONObject json = jsonParser.makeHttpRequest(url_create_client,	"POST", params);
				
				// check log cat fro response
				Log.d("Create Response", json.toString());

				// check for success tag
				try 
				{
					int success = json.getInt(TAG_SUCCESS);

					if (success == 1) 
					{
						//"successfully created "+cust_id
						Intent i = new Intent(getApplicationContext(), Login.class);
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
				Toast.makeText(getApplicationContext(),cust_id+ "	successfully created", Toast.LENGTH_LONG).show();
			}

		}
	
}