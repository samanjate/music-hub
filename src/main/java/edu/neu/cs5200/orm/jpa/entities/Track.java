package edu.neu.cs5200.orm.jpa.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Track implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(unique=true, nullable=true)
	private long napsterTrackId;

	private String name;
	private int playbackSeconds;

	@ManyToMany(fetch = FetchType.EAGER, mappedBy = "tracks", cascade = CascadeType.ALL)
	@Fetch(value = FetchMode.SUBSELECT)
	@JsonIgnore
	private List<Album> albums;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(referencedColumnName = "id", nullable = false)
	@JsonIgnore
	private Artist artist;

	@ManyToMany
	@JoinTable(name = "TRACK2GENRE", joinColumns = @JoinColumn(name = "TRACK_ID", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "GENRE_ID", referencedColumnName = "ID"))
	@LazyCollection(LazyCollectionOption.FALSE)
	@JsonIgnore
	private List<Genre> genres;

	@ManyToMany(fetch = FetchType.EAGER, mappedBy = "tracks", cascade = CascadeType.ALL)
	@Fetch(value = FetchMode.SUBSELECT)
	@JsonIgnore
	private List<Genre> playlists;

	@OneToMany(mappedBy = "track")
	private List<Rating> ratings;

	@ManyToMany
	@JoinTable(name = "LIKES", joinColumns = @JoinColumn(name = "TRACK_ID", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "CRITIC_ID", referencedColumnName = "ID"))
	@LazyCollection(LazyCollectionOption.FALSE)
	@JsonIgnore
	private List<Critic> likers;

	private String previewURL;

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

	public int getPlaybackSeconds() {
		return playbackSeconds;
	}

	public void setPlaybackSeconds(int playbackSeconds) {
		this.playbackSeconds = playbackSeconds;
	}

	public List<Album> getAlbums() {
		return albums;
	}

	public void setAlbums(List<Album> albums) {
		this.albums = albums;
	}

	public Artist getArtist() {
		return artist;
	}

	public void setArtist(Artist artist) {
		this.artist = artist;
	}

	public List<Genre> getGenres() {
		return genres;
	}

	public void setGenres(List<Genre> genres) {
		this.genres = genres;
	}

	

	public String getPreviewURL() {
		return previewURL;
	}

	public void setPreviewURL(String previewURL) {
		this.previewURL = previewURL;
	}

	public List<Rating> getRatings() {
		return ratings;
	}

	public void setRatings(List<Rating> ratings) {
		this.ratings = ratings;
	}

	public List<Genre> getPlaylists() {
		return playlists;
	}

	public void setPlaylists(List<Genre> playlists) {
		this.playlists = playlists;
	}

	public List<Critic> getLikers() {
		return likers;
	}

	public void setLikers(List<Critic> likers) {
		this.likers = likers;
	}
	
	public long getNapsterTrackId() {
		return napsterTrackId;
	}

	public void setNapsterTrackId(long napsterTrackId) {
		this.napsterTrackId = napsterTrackId;
	}
}
