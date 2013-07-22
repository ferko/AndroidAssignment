package com.IS.assignment3.structures;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * this class is used to store the answers the user have entered.
 * 
 * @author frank ben steve
 *
 */
public class Answer implements Serializable {

	private int questionID;
	private int answer;

	public Answer() {
		// TODO Auto-generated constructor stub
	}

	public Answer(int answer, int questionID) {
		this.questionID = questionID;
		this.answer = answer;
	}

	public int getQuestionID() {
		return questionID;
	}

	public void setQuestionID(int questionID) {
		this.questionID = questionID;
	}

	public int getAnswer() {
		return answer;
	}

	public void setAnswer(int answer) {
		this.answer = answer;
	}

	public JSONObject getJSON() {
		JSONObject object = new JSONObject();
		try {
			object.put("questionID", questionID);
			object.put("answer", answer);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return object;
	}
}
