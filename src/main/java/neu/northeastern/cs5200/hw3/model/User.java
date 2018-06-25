package neu.northeastern.cs5200.hw3.model;

import javax.persistence.Entity;

@Entity(name = "User")
public class User extends Person{

	private Boolean userAgreement;

	// Getters and Setters

	public Boolean getUserAgreement() {
		return userAgreement;
	}

	public void setUserAgreement(Boolean userAgreement) {
		this.userAgreement = userAgreement;
	}
	
	
}
