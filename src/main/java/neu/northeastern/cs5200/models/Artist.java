package neu.northeastern.cs5200.models;

import java.io.Serializable;
import java.util.List;

public class Artist extends Person implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String artistLink;
	private String shortcut;
	private List<String> blurbs;
	private List<Biodata> bios;
	private List<Album> groupAlbums;
	private List<Album> singleAlbums;
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
	public List<String> getBlurbs() {
		return blurbs;
	}
	public void setBlurbs(List<String> blurbs) {
		this.blurbs = blurbs;
	}
	public List<Biodata> getBios() {
		return bios;
	}
	public void setBios(List<Biodata> bios) {
		this.bios = bios;
	}
	public List<Album> getGroupAlbums() {
		return groupAlbums;
	}
	public void setGroupAlbums(List<Album> groupAlbums) {
		this.groupAlbums = groupAlbums;
	}
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
	
}
