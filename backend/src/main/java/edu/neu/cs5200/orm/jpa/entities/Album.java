package edu.neu.cs5200.orm.jpa.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
public class Album implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String name;
	private String albumLink;
	private String imageURL;
	
	@Column(nullable=true)
	@NotFound(action = NotFoundAction.IGNORE)
	private long napsterId;

	@ManyToMany
	@JoinTable(name = "TRACK2ALBUM", joinColumns = @JoinColumn(name = "ARTIST_ID", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "TRACK_ID", referencedColumnName = "ID"))
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Track> tracks;

	@Temporal(TemporalType.TIMESTAMP)
	private Date releaseDate;
	private String copyright;
	private String accountPartner;

	@ManyToOne
	@JoinColumn(referencedColumnName = "id", nullable = true)
	private Artist artist;

	@OneToMany(mappedBy = "album")
	private List<Review> reviews;

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
	
	public Artist getArtist() {
		return artist;
	}

	public void setArtist(Artist artist) {
		this.artist = artist;
	}

	public long getNapsterId() {
		return napsterId;
	}

	public void setNapsterId(long napsterId) {
		this.napsterId = napsterId;
	}
	
	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}
		
	public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}
}
