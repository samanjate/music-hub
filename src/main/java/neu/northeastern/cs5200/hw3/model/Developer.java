package neu.northeastern.cs5200.hw3.model;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity(name = "Developer")
public class Developer extends Person {

	private String developerKey;

	@OneToMany(mappedBy = "developer", orphanRemoval = true)
	private Collection<Website> websites;

	// Getters and Setters

	public String getDeveloperKey() {
		return developerKey;
	}

	public void setDeveloperKey(String developerKey) {
		this.developerKey = developerKey;
	}

	public Collection<Website> getWebsites() {
		return websites;
	}

	public void setWebsites(Collection<Website> websites) {
		this.websites = websites;
	}

	/**
	 * default constructor
	 */
	public Developer() {
	}

	/**
	 * parameterized constructor
	 * 
	 * @param devId
	 * @param dob
	 * @param firstName
	 * @param lastName
	 * @param username
	 * @param password
	 * @param email
	 * @param devKey
	 */
	public Developer(int devId, Date dob, String firstName, String lastName, String username, String password,
			String email, String devKey) {
		setId(devId);
		setUsername(username);
		setPassword(password);
		setFirstName(firstName);
		setLastName(lastName);
		setEmail(email);
		setDeveloperKey(devKey);
		setDob(dob);
	}

}
