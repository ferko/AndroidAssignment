package com.IS.assignment3;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import android.R.string;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


/**
 * 
 * This active is responsible for taking the users name and password,  sending them as a get to the restful service
 * if the return is success then it loads the main menu
 * @author frank, steve ben
 *
 */
public class LoginActivity extends Activity {
	String user;
	String pass;

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_layout);
		findViewById(R.id.error).setVisibility(4);
		findViewById(R.id.login_button).setOnClickListener(
				new OnClickListener() {
					/**
					 * gets the username and password from the view when then user presses the login button.
					 * it then executes the restful request in a new thread.
					 */
					@Override
					public void onClick(View v) {
						EditText et = (EditText) findViewById(R.id.username);
						EditText et2 = (EditText) findViewById(R.id.password);
						user = et.getText().toString().trim();
						pass = et2.getText().toString().trim();
						findViewById(R.id.login_button).setClickable(false);
						new LongRunningGetIO().execute();

					}
				});

	}

	/**
	 * Ends the game if the request is set to one in another activity.
	 */
	protected void onActivityResult(final int requestCode,
			final int resultCode, Intent data) {
		switch (resultCode) {
		case 1:
			finish();
			break;
		}
	}

	/**
	 * 
	 * This is the thread where the restful service request is executed
	 * @author frank steve ben
	 *
	 */
	private class LongRunningGetIO extends AsyncTask<Void, Void, String> {
		/**
		 * Converts the entity returned from the request into a string.
		 * 
		 * @param entity
		 * @return the entity to string
		 * @throws IllegalStateException
		 * @throws IOException
		 */
		protected String getASCIIContentFromEntity(HttpEntity entity)
				throws IllegalStateException, IOException {
			InputStream in = entity.getContent();
			StringBuffer out = new StringBuffer();
			int n = 1;
			while (n > 0) {
				byte[] b = new byte[4096];
				n = in.read(b);
				if (n > 0)
					out.append(new String(b, 0, n));
			}
			return out.toString();
		}

		/**
		 * send the request to the request server and obtains the result
		 */
		@Override
		protected String doInBackground(Void... params) {
			Log.d(user, pass);
			HttpClient httpClient = new DefaultHttpClient();
			HttpContext localContext = new BasicHttpContext();
			HttpGet httpGet = new HttpGet(
					"http://www.jointeams.com/Assignment3/rest/quiz/login?username="
							+ user + "&password=" + pass);
			String text = null;
			try {
				HttpResponse response = httpClient.execute(httpGet,
						localContext);
				HttpEntity entity = response.getEntity();
				text = getASCIIContentFromEntity(entity);
			} catch (Exception e) {
				return e.getLocalizedMessage();
			}
			return text;
		}

		/**
		 * this is executed after the request is returned.  if result is success then the
		 * activity for the main menu otherwise a message is displayed.
		 */
		protected void onPostExecute(String results) {
			Log.d(results, results);
			if (results.equals("success")) {

				try {
					Intent i = new Intent(LoginActivity.this,
							MenuActivity.class);
					i.putExtra("username", user);
					i.putExtra("password", pass);
					startActivityForResult(i, 0);
					overridePendingTransition(0, 0);
				}

				catch (ActivityNotFoundException e) {
					e.printStackTrace();
				}

			} else {
				findViewById(R.id.error).setVisibility(0);
				findViewById(R.id.login_button).setClickable(true);
			}
		}
	}
}
