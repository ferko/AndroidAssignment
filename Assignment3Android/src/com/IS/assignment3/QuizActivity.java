package com.IS.assignment3;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.IS.assignment3.structures.Answer;
import com.IS.assignment3.structures.Question;

/**
 * This activity request questions from the server, parses them into a list of
 * questions, shuffles them and then picks the first four and uses an array
 * adapter to display the questions as radio groups. it then retrieves the
 * responses into answer objects and passes them to the score intent.
 * 
 * @author frank ben steve
 * 
 */
public class QuizActivity extends Activity {
	ArrayList<Question> questions = new ArrayList<Question>();
	private ArrayAdapter<Question> adapter;
	Context context;
	private ArrayList<Answer> answers = new ArrayList<Answer>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.quiz_layout);
		context = this;
		new LongRunningGetIO().execute();
		Button submit = (Button) findViewById(R.id.quiz_submit);
		submit.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				ListView lv = (ListView) findViewById(R.id.quiz_list);
				Intent i = new Intent(QuizActivity.this, ScoreActivity.class);
				i.putExtra("score", answers);
				startActivity(i);
				overridePendingTransition(0, 0);
				finish();
				overridePendingTransition(0, 0);
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

		@Override
		protected String doInBackground(Void... params) {
			HttpClient httpClient = new DefaultHttpClient();
			HttpContext localContext = new BasicHttpContext();
			HttpGet httpGet = new HttpGet(
					"http://www.jointeams.com/Assignment3/rest/quiz/questions");
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
		 * coverts the json objects into an array of questions, shuffles them and then assigns the first
		 * four
		 */
		protected void onPostExecute(String results) {
			try {
				JSONArray jarray = new JSONArray(results);
				for (int i = 0; i < jarray.length(); i++) {
					questions.add(new Question(jarray.getJSONObject(i)));
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Collections.shuffle(questions, new Random(System.nanoTime()));
			for (int i = 0; i < 4; i++) {
				answers.add(new Answer(1, Integer.parseInt(questions.get(i)
						.getQuestionID())));
			}
			adapter = new QuestionAdapter(context, R.layout.quiz_item_layout,
					new ArrayList<Question>(questions.subList(0, 4)),
					getApplicationContext()); // sets ArrayList
			// adapter
			ListView quizList = (ListView) findViewById(R.id.quiz_list);
			quizList.setAdapter(adapter); // sets adapter to list
			adapter.notifyDataSetChanged();
		}
	}

	/**
	 * Array adapter used to convert the questions into radio groups so that the answer can be saved
	 * 
	 * @author frank ben steve
	 * 
	 */
	class QuestionAdapter extends ArrayAdapter<Question> {

		private ArrayList<Question> items;

		private Context context;

		public QuestionAdapter(Context context, int textViewResourceId,
				ArrayList<Question> items, Context appContext) {
			super(context, textViewResourceId, items);
			this.items = items;
			this.context = context;
		}

		@Override
		public View getView(final int position, final View convertView,
				final ViewGroup parent) {
			View v = convertView;

			if (v == null) {
				LayoutInflater vi = (LayoutInflater) context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.quiz_item_layout, null);
			}

			Question question = items.get(position);

			if (question != null) {
				TextView tv = (TextView) v.findViewById(R.id.question);
				tv.setText(question.getQuestion());

				RadioGroup rg = (RadioGroup) v.findViewById(R.id.radio_group);
				rg.setTag(question.getQuestionID());

				rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						int questionID = Integer.parseInt((String) group
								.getTag());
						int answer = Integer.parseInt((String) group
								.findViewById(checkedId).getTag());
						for (Answer temp : answers) {
							if (temp.getQuestionID() == questionID) {
								temp.setAnswer(answer);
								break;
							}

						}
					}
				});

				for (int i = 0; i < rg.getChildCount(); i++) {
					((RadioButton) rg.getChildAt(i)).setText(question
							.getOptions().get(i).getOption());
				}
			}
			return v;
		}
	}
}
