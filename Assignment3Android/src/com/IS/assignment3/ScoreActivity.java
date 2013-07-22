package com.IS.assignment3;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.IS.assignment3.structures.Answer;

/**
 * This activity sends the answers to the restful server and then displays the
 * result returned from the restful server
 * 
 * @author frank ben steve
 *
 */
public class ScoreActivity extends Activity {
	ArrayList<Answer> answers;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scores_layout);
		findViewById(R.id.main_menu).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
				overridePendingTransition(0, 0);
			}
		});

		Bundle bundle = getIntent().getExtras();
		answers = (ArrayList<Answer>) bundle.get("score");
		new LongRunningGetIO().execute();

	}

	private class LongRunningGetIO extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... params) {
			String result = "error";
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			DefaultHttpClient httpclient = new DefaultHttpClient();
			HttpPost httpost = new HttpPost(
					"http://www.jointeams.com/Assignment3/rest/quiz/score");
			StringEntity se;
			try {
				JSONArray jarray = new JSONArray();
				for (int i = 0; i < answers.size(); i++) {
					jarray.put(answers.get(i).getJSON());
				}
				se = new StringEntity(jarray.toString());

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

			((TextView) findViewById(R.id.score_total)).setText(results);
		}
	}
}
