package neu.northeastern.cs5200.hw3.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name = "Phone")
public class Phone {

	@Id
	private int id;
	private String phoneNumber;
	private Boolean primaryPhone;

	@ManyToOne
	@JsonIgnore
	private Person person;

	// Getters and Setters

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Boolean getPrimaryPhone() {
		return primaryPhone;
	}

	public void setPrimaryPhone(Boolean primaryPhone) {
		this.primaryPhone = primaryPhone;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

}
