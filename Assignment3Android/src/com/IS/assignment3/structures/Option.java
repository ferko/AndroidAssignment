/**
 * 
 */
package com.IS.assignment3.structures;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Used to store options for a specific question
 * @author ferko1177gmail.com
 *
 */
public class Option implements Serializable {

	private int questionID;
	private String option;
	private int optionID;

	/**
	 * 
	 */
	public Option() {
		// TODO Auto-generated constructor stub
	}

	public Option(JSONObject job) {
		try {
			questionID = job.getInt("questionID");
			option = job.getString("option");
			optionID = job.getInt("optionID");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

	public int getOptionID() {
		return optionID;
	}

	public void setOptionID(int optionID) {
		this.optionID = optionID;
	}

	public int getQuestionID() {
		return questionID;
	}

	public void setQuestionID(int questionID) {
		this.questionID = questionID;
	}

}
