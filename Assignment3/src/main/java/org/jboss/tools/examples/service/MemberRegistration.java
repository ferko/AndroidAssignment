package org.jboss.tools.examples.service;

import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.jboss.tools.examples.model.Member;
import org.jboss.tools.examples.model.User;

// The @Stateless annotation eliminates the need for manual transaction demarcation
@Stateless
public class MemberRegistration {

   @Inject
   private Logger log;

   @Inject
   private EntityManager em;

   @Inject
   private Event<Member> memberEventSrc;

   public void register(Member member) throws Exception {
      log.info("Registering " + member.getName());
      em.persist(member);
      memberEventSrc.fire(member);
   }
   public void create(User user) throws Exception {
	      em.persist(user);
	   }
}
