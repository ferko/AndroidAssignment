/**
 * 
 */
package com.IS.assignment3.structures;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Used to store user objects so that they can be converted to json for the server.
 * 
 * @author ferko1177gmail.com
 *
 */
public class User implements Serializable {

	private String firstName;
	private String lastName;
	private String password;
	private String userName;
	private String studentNumber;

	/**
	 * 
	 */
	public User() {
		// TODO Auto-generated constructor stub
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getStudentNumber() {
		return studentNumber;
	}

	public void setStudentNumber(String studentNumber) {
		this.studentNumber = studentNumber;
	}

	public String getJSON() {
		JSONObject object = new JSONObject();
		try {
			object.put("firstName", firstName);
			object.put("lastName", lastName);
			object.put("password", password);
			object.put("userName", userName);
			object.put("studentNumber", studentNumber);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return object.toString();
	}

	public boolean isValid() {
		boolean result = true;
		if (firstName.trim().length() == 0 || lastName.trim().length() == 0
				|| password.trim().length() == 0
				|| userName.trim().length() == 0
				|| studentNumber.trim().length() == 0) {
			result = false;
		}
		return result;
	}
}
