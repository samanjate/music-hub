package edu.neu.cs5200.orm.jpa.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIgnore;

import edu.neu.cs5200.orm.jpa.types.Privacy;

@Entity
public class Playlist implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String name;
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastModifiedDate;
	private int trackCount;
	
	private Privacy privacy;
	
	@Lob
	private String description;
	private Boolean freePlayCompliant;
	
	@ManyToMany
	@JoinTable(name="PLAYLIST2TRACK", 
	joinColumns=@JoinColumn(name="PLAYLIST_ID", 
	referencedColumnName="ID"),
	inverseJoinColumns=@JoinColumn(name="TRACK_ID", 
	referencedColumnName="ID"))
	@LazyCollection(LazyCollectionOption.FALSE)
	@JsonIgnore
	private List<Track> tracks;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(referencedColumnName = "id", nullable = false, unique = true)
	private Person createdBy;

	public Person getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Person createdBy) {
		this.createdBy = createdBy;
	}

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

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public int getTrackCount() {
		return trackCount;
	}

	public void setTrackCount(int trackCount) {
		this.trackCount = trackCount;
	}

	public Privacy getPrivacy() {
		return privacy;
	}

	public void setPrivacy(Privacy privacy) {
		this.privacy = privacy;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getFreePlayCompliant() {
		return freePlayCompliant;
	}

	public void setFreePlayCompliant(Boolean freePlayCompliant) {
		this.freePlayCompliant = freePlayCompliant;
	}

	public List<Track> getTracks() {
		return tracks;
	}

	public void setTracks(List<Track> tracks) {
		this.tracks = tracks;
	}

}
