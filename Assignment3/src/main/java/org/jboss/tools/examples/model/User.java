/**
 * 
 */
package org.jboss.tools.examples.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author ferko1177gmail.com
 * 
 */
@Entity
@Table(name = "Users")
public class User implements Serializable {

	@Column(name = "FirstName")
	private String firstName;
	@Column(name = "LastName")
	private String lastName;
	@Column(name = "Password")
	private String password;
	@Id
	@Column(name = "UserName")
	private String userName;
	@Column(name = "StudentNumber")
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

	public static User cloneUser(User user) {
		User temp = new User();
		temp.setFirstName("fn");
		temp.setLastName("fn");
		temp.setPassword("fn");
		temp.setStudentNumber("fn");
		temp.setUserName("fn");
		return temp;
	}
}
