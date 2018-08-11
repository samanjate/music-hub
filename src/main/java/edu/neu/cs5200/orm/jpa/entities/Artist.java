package edu.neu.cs5200.orm.jpa.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@DiscriminatorValue("ARTIST")
public class Artist extends Person implements Serializable {

	private static final long serialVersionUID = 1L;

	private String artistLink;
	private String shortcut;
	
	@OneToMany(mappedBy = "artist")
	private List<Biodata> bios;
	
	@ManyToMany
	@JoinTable(name="ALBUM2ARTIST", 
	joinColumns=@JoinColumn(name="ARTIST_ID", 
	referencedColumnName="ID"),
	inverseJoinColumns=@JoinColumn(name="ALBUM_ID", 
	referencedColumnName="ID"))
	@LazyCollection(LazyCollectionOption.FALSE)
	@JsonIgnore
	private List<Album> albums;
	
	@OneToMany(mappedBy = "artist")
	private List<Track> tracks;
	
	public String getArtistLink() {
		return artistLink;
	}
	
	public void setArtistLink(String artistLink) {
		this.artistLink = artistLink;
	}
	
	public String getShortcut() {
		return shortcut;
	}
	
	public void setShortcut(String shortcut) {
		this.shortcut = shortcut;
	}
	
	public List<Biodata> getBios() {
		return bios;
	}
	public void setBios(List<Biodata> bios) {
		this.bios = bios;
	}
	
	public List<Album> getSingleAlbums() {
		return albums;
	}
	
	public void setSingleAlbums(List<Album> albums) {
		this.albums = albums;
	}
	
	public List<Track> getTracks() {
		return tracks;
	}
	
	public void setTracks(List<Track> tracks) {
		this.tracks = tracks;
	}
	
	public Artist() {
		
	}
	
	public Artist(Person p) {
		this.setFirstName(p.getFirstName());
		this.setLastName(p.getLastName());
		this.setEmail(p.getEmail());
		this.setPassword(p.getPassword());
	}
}
