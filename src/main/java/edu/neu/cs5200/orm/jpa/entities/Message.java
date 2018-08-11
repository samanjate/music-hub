package edu.neu.cs5200.orm.jpa.entities;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Message implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private Person toPerson;
	private Person fromPerson;
	private String subject;
	private String text;
	private Date timestamp;
	
	@ManyToOne
	private Person person;
	
	private static final long serialVersionUID = 1L;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public Person getTo() {
		return toPerson;
	}
	
	public void setTo(Person to) {
		this.toPerson = to;
	}
	
	public Person getFrom() {
		return fromPerson;
	}
	
	public void setFrom(Person from) {
		this.fromPerson = from;
	}
	
	public String getSubject() {
		return subject;
	}
	
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public Date getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	
	public Person getPerson() {
		return person;
	}
	
	public void setPerson(Person person) {
		this.person = person;
	}
}
