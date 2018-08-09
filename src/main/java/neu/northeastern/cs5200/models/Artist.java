package neu.northeastern.cs5200.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class Artist extends Person implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String artistLink;
	private String shortcut;
		
	@OneToMany(mappedBy = "artist")
	private List<Biodata> bios;
	
//	@OneToMany(mappedBy = "contributingArtist")
//	private List<Album> groupAlbums;
	
	@OneToMany(mappedBy = "leadingArtist")
	private List<Album> singleAlbums;
	
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
//	public List<Album> getGroupAlbums() {
//		return groupAlbums;
//	}
//	public void setGroupAlbums(List<Album> groupAlbums) {
//		this.groupAlbums = groupAlbums;
//	}
	public List<Album> getSingleAlbums() {
		return singleAlbums;
	}
	public void setSingleAlbums(List<Album> singleAlbums) {
		this.singleAlbums = singleAlbums;
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
