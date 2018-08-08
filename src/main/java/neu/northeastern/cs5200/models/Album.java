package neu.northeastern.cs5200.models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Album implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String name;
	private String albumLink;
	
//	private List<Track> tracks;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date releaseDate;
	private String copyright;
	private String accountPartner;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(referencedColumnName = "id", nullable = false, unique = true)
	private Artist leadingArtist;
	
//	private List<Artist> contributingArtist;
	
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
//	public List<Track> getTracks() {
//		return tracks;
//	}
//	public void setTracks(List<Track> tracks) {
//		this.tracks = tracks;
//	}
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
	public Artist getLeadingArtist() {
		return leadingArtist;
	}
	public void setLeadingArtist(Artist leadingArtist) {
		this.leadingArtist = leadingArtist;
	}
//	public List<Artist> getContributingArtist() {
//		return contributingArtist;
//	}
//	public void setContributingArtist(List<Artist> contributingArtist) {
//		this.contributingArtist = contributingArtist;
//	}
	

}
