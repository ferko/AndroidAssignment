package org.jboss.tools.examples.rest;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.tools.examples.model.Answer;
import org.jboss.tools.examples.model.Question;
import org.jboss.tools.examples.model.User;
import org.jboss.tools.examples.service.DataAccess;
import org.jboss.tools.examples.service.MemberRegistration;

@Path("/quiz")
@RequestScoped
public class QuizService {
	@Inject
	private DataAccess da;

	@GET
	@Path("/questions")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Question> getAllQuestions() {
		return da.getQuestions();
	}

	@GET
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	public String getUser(@QueryParam("username") String username,
			@QueryParam("password") String password) {
		return da.validateUser(username, password);
	}

	@POST
	@Path("/create_user")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addUser(User user) {

		String result = da.createUser(user);
		return Response.status(201).entity(result).build();
	}

	@POST
	@Path("/score")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getScore(List<Answer> answers) {
		String result = da.getScore(answers);
		return Response.status(201).entity(result).build();
	}

}