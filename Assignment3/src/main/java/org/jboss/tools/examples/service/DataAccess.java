package org.jboss.tools.examples.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.jboss.tools.examples.model.Answer;
import org.jboss.tools.examples.model.Question;
import org.jboss.tools.examples.model.User;

// The @Stateless annotation eliminates the need for manual transaction demarcation
@Stateless
public class DataAccess {

	@Inject
	private EntityManager em;

	public String validateUser(String username, String password) {
		String result = "failure";
		User temp = em.find(User.class, username);
		if (temp != null) {
			if (temp.getPassword().equals(password)) {
				result = "success";
			}
		}
		return result;
	}

	public String createUser(User user) {

		String result = "failure";
		User temp = em.find(User.class, user.getUserName());
		if (temp == null) {
			try {
				em.persist(user);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			result = "succes";
		}
		return result;
	}

	public List<Question> getQuestions() {
		List<Question> questions = em.createQuery("select s from Question s",
				Question.class).getResultList();
		return questions;
	}

	public String getScore(List<Answer> answers) {
		int score = 0;
		Answer newAnswer;
		for (Answer temp : answers) {
			newAnswer = em.find(Answer.class, temp.getQuestionID());
			if (newAnswer != null && newAnswer.getAnswer() == temp.getAnswer()) {
				score++;
			}
		}
		return Integer.toString(score);
	}
}
