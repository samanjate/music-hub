package edu.neu.cs5200.orm.jpa.services;

import javax.servlet.http.HttpSession;

import edu.neu.cs5200.orm.jpa.entities.Person;

public class Session {
	
	private final String SESSION_USER = "sessionUser";
	private HttpSession currentSession;
	
	private static Session instance;
	private Session() {}
	
	public static Session getInstance() {
		if(instance == null) {
			instance = new Session();
		}
		return instance;
	}
	
	public void setSession(HttpSession session, Person person) {
		session.setAttribute(SESSION_USER, person);
		currentSession = session;
	}
	
	public void clearSession(HttpSession session) {
		session.setAttribute(SESSION_USER, null);
		currentSession = null;
	}
	
	public Person checkSession() {
		if (currentSession != null) {
			return (Person) currentSession.getAttribute(SESSION_USER);
		} else {
			return null;
		}
			
	}

}
