package neu.northeastern.cs5200.hw3.model;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name = "Page")
public class Page {

	@Id
	private int id;
	private String title;
	private String description;

	@Temporal(TemporalType.TIMESTAMP)
	private Date created;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updated;
	private int views;

	@ManyToOne
	@JsonIgnore
	private Website website;

	@OneToMany(mappedBy = "page", orphanRemoval = true)
	private Collection<Widget> widgets;

	// Getters and Setters

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public int getViews() {
		return views;
	}

	public void setViews(int views) {
		this.views = views;
	}

	public Collection<Widget> getWidgets() {
		return widgets;
	}

	public void setWidgets(Collection<Widget> widgets) {
		this.widgets = widgets;
	}

	public Website getWebsite() {
		return website;
	}

	public void setWebsite(Website website) {
		this.website = website;
	}

	public Boolean addWidget(Widget w) {
		return false;
	}

	public Boolean removeWidget(Widget w) {
		return false;
	}

	public Page() {

	}

	public Page(int id, String title, String description, Date created, Date updated, int views) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.created = created;
		this.updated = updated;
		this.views = views;
	}
}
