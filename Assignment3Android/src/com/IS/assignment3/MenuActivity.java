package com.IS.assignment3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

/**
 * 
 * this activity has 3 buttons. One to start the create user activity, the quiz activity and to exit
 * the app
 * 
 * @author frank steve ben
 *
 */
public class MenuActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_layout);
		String username = getIntent().getStringExtra("username");
		TextView tv = (TextView) findViewById(R.id.username);
		tv.setText(" " + username);

		/**
		 * sets the button listener for the create user activity
		 */
		findViewById(R.id.user_button).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent myIntent = new Intent(v.getContext(),
								UserActivity.class);
						startActivity(myIntent);
						overridePendingTransition(0, 0);
					}
				});

		/**
		 * sets the button for the quiz activity
		 */
		findViewById(R.id.quiz_button).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent myIntent = new Intent(v.getContext(),
								QuizActivity.class);
						startActivity(myIntent);
						overridePendingTransition(0, 0);
					}
				});

		/**
		 * sets the button for the exit activity
		 */
		findViewById(R.id.exit_button).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						setResult(1);
						finish();
					}
				});

	}

	/**
	 * disables tne back button so that the user cannot go back to the login screen when logged in
	 */
	public void onBackPressed() {
	}

}
