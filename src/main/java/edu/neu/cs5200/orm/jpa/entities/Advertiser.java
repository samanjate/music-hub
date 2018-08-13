package edu.neu.cs5200.orm.jpa.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
@DiscriminatorValue("ADVERTISER")
public class Advertiser extends Person implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public Advertiser() {
		
	}
	
	public Advertiser(Person p) {
		this.setFirstName(p.getFirstName());
		this.setLastName(p.getLastName());
		this.setUsername(p.getUsername());
		this.setEmail(p.getEmail());
		this.setPassword(p.getPassword());
	}

	@OneToMany(mappedBy = "advertiser")
	private List<Advertisement> ads;

	public List<Advertisement> getAds() {
		return ads;
	}

	public void setAds(List<Advertisement> ads) {
		this.ads = ads;
	}
	
}
