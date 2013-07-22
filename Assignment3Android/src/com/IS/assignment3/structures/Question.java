/**
 * 
 */
package com.IS.assignment3.structures;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Used to hold questions and a list of options.
 * 
 * @author ferko1177gmail.com
 *
 */
public class Question implements Serializable {

	private String question;
	private String questionID;
	private List<Option> options = new ArrayList<Option>();

	public List<Option> getOptions() {
		return options;
	}

	public void setOptions(List<Option> options) {
		this.options = options;
	}

	/**
	 * 
	 */
	public Question() {
		// TODO Auto-generated constructor stub
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getQuestionID() {
		return questionID;
	}

	public void setQuestionID(String questionID) {
		this.questionID = questionID;
	}

	public Question(JSONObject job) {
		try {
			question = job.getString("question");
			questionID = job.getString("questionID");
			JSONArray jarray = job.getJSONArray("options");
			for (int i = 0; i < jarray.length(); i++) {

				options.add(new Option(jarray.getJSONObject(i)));

			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
