package neu.northeastern.cs5200.hw3.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name = "Priviledge")
public class Priviledge {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@JsonIgnore
	@ManyToOne
	private Developer developer;

	@ManyToOne
	@JsonIgnore
	private PriviledgeType priviledgeType;

	@JsonIgnore
	@ManyToOne
	private Page page;

	@JsonIgnore
	@ManyToOne
	private Website website;

	// Getters and Setters

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Developer getDeveloper() {
		return developer;
	}

	public void setDeveloper(Developer developer) {
		this.developer = developer;
	}


	public PriviledgeType getPriviledgeType() {
		return priviledgeType;
	}

	public void setPriviledgeType(PriviledgeType priviledgeType) {
		this.priviledgeType = priviledgeType;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public Website getWebsite() {
		return website;
	}

	public void setWebsite(Website website) {
		this.website = website;
	}

}
