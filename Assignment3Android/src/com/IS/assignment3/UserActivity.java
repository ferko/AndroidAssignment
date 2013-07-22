package com.IS.assignment3;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.IS.assignment3.structures.User;

/**
 * This activity takes inputs from the user, creates a user object and then sends it to the server as a json
 * object. it preforms simple validation to make user the passwords match and makes sure the nothing is left
 * empty.  if the user already exists it displays a message and if user is created it returns the app to the
 * main menu and a toast is then display.
 * 
 * @author frank ben steve
 *
 */
public class UserActivity extends Activity {
	User user;
	Context context;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_user_layout);
		findViewById(R.id.cancel_button).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						finish();
						overridePendingTransition(0, 0);
					}
				});
		findViewById(R.id.create_button).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						String passVerify;
						user = new User();
						user.setFirstName(((EditText) findViewById(R.id.fn))
								.getText().toString());
						user.setLastName(((EditText) findViewById(R.id.ln))
								.getText().toString());
						user.setPassword(((EditText) findViewById(R.id.password))
								.getText().toString());
						user.setStudentNumber(((EditText) findViewById(R.id.sn))
								.getText().toString());
						user.setUserName(((EditText) findViewById(R.id.username))
								.getText().toString());
						passVerify = ((EditText) findViewById(R.id.reenter))
								.getText().toString();
						if (!user.isValid()) {
							((TextView) findViewById(R.id.error_label))
									.setText("Fields cannot be left blank");
						} else if (!user.getPassword().equals(passVerify)) {
							((TextView) findViewById(R.id.error_label))
									.setText("Passwords don't match");
						} else {
							context = v.getContext();
							new LongRunningGetIO().execute();
						}

					}
				});
	}

	private class LongRunningGetIO extends AsyncTask<Void, Void, String> {
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

		// www.jointeams.com/Assignment3/rest/quiz/login?username=sinjin&password=password
		@Override
		protected String doInBackground(Void... params) {

			String result = "error";
			ResponseHandler responseHandler = new BasicResponseHandler();
			DefaultHttpClient httpclient = new DefaultHttpClient();
			HttpPost httpost = new HttpPost(
					"http://www.jointeams.com/Assignment3/rest/quiz/create_user");
			StringEntity se;
			try {
				se = new StringEntity(user.getJSON());
				se.setContentType("application/json");
				httpost.setEntity(se);
				result = httpclient.execute(httpost, responseHandler);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return result;
		}

		protected void onPostExecute(String results) {
			if (results.equals("failure")) {
				((TextView) findViewById(R.id.error_label))
						.setText("User already exists");
			} else {
				Toast.makeText(context, "User successfully created", 3).show();
				finish();
				overridePendingTransition(0, 0);
			}
		}
	}
}
