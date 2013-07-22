package org.jboss.tools.examples.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "Answers")
public class Answer implements Serializable {

	@Id
	@Column(name = "QuestionID")
	private int questionID;
	@Column(name = "Answer")
	private int answer;

	public Answer() {
		// TODO Auto-generated constructor stub
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

}
