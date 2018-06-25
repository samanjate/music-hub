package neu.northeastern.cs5200.hw3.model;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name = "Website")
public class Website {

	@Id
	private int id;
	private String name;
	private String description;
	private Date created;
	private Date updated;
	private int visits;

	@ManyToOne
	@JsonIgnore
	private Developer developer;

	@OneToMany(mappedBy = "website", orphanRemoval = true)
	private Collection<Page> pages;

	// Getters and Setters

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public int getVisits() {
		return visits;
	}

	public void setVisits(int visits) {
		this.visits = visits;
	}

	public Collection<Page> getPages() {
		return pages;
	}

	public void setPages(Collection<Page> pages) {
		this.pages = pages;
	}

	public Developer getDeveloper() {
		return developer;
	}

	public void setDeveloper(Developer developer) {
		this.developer = developer;
	}

	public Boolean addPage(Page p) {
		return false;
	}

	public Boolean removePage(Page p) {
		return false;
	}
	
	public Website() {}
	
	public Website(int id, String name, String description,Date created, Date updated,int visits) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.created = created;
		this.updated = updated;
	}
}
