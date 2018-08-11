package edu.neu.cs5200.orm.jpa.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Album implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String name;
	private String albumLink;
	
	@ManyToMany
	@JoinTable(name="TRACK2ALBUM", 
	joinColumns=@JoinColumn(name="ARTIST_ID", 
	referencedColumnName="ID"),
	inverseJoinColumns=@JoinColumn(name="TRACK_ID", 
	referencedColumnName="ID"))
	@LazyCollection(LazyCollectionOption.FALSE)
	@JsonIgnore
	private List<Track> tracks;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date releaseDate;
	private String copyright;
	private String accountPartner;
	
	@ManyToMany(fetch = FetchType.EAGER, mappedBy="albums", cascade = CascadeType.ALL)
	@Fetch(value = FetchMode.SUBSELECT)
	@JsonIgnore
	private List<Artist> artists;
	
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
	
	public String getAlbumLink() {
		return albumLink;
	}
	
	public void setAlbumLink(String albumLink) {
		this.albumLink = albumLink;
	}
	
	public List<Track> getTracks() {
		return tracks;
	}
	
	public void setTracks(List<Track> tracks) {
		this.tracks = tracks;
	}
	
	public Date getReleaseDate() {
		return releaseDate;
	}
	
	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}
	
	public String getCopyright() {
		return copyright;
	}
	
	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}
	
	public String getAccountPartner() {
		return accountPartner;
	}
	
	public void setAccountPartner(String accountPartner) {
		this.accountPartner = accountPartner;
	}
	
	public List<Artist> getArtists() {
		return artists;
	}
	
	public void setArtists(List<Artist> artists) {
		this.artists = artists;
	}
}
