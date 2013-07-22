/**
 * 
 */
package org.jboss.tools.examples.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author ferko1177gmail.com
 * 
 */
@Entity
@Table(name = "QuestionOptions")
public class Option implements Serializable {

	@Column(name = "QuestionID")
	private int questionID;
	@Column(name = "OptionText")
	private String option;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "OptionID")
	private int optionID;

	/**
	 * 
	 */
	public Option() {
		// TODO Auto-generated constructor stub
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
