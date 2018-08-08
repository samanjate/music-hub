package neu.northeastern.cs5200.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Lob;
import javax.persistence.OneToMany;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "DTYPE")
public class Person implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String firstName;
	private String lastName;

	private String email;
	private String password;

	@Lob
	private String selfBio;

	@OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL, fetch = FetchType.LAZY)	
	private List<Playlist> playlists;
	@OneToMany(mappedBy = "id",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Person> followers;
	@OneToMany(mappedBy = "id",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Person> following;
	@OneToMany(mappedBy = "id",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Person> likes;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Person() {
		super();
	}

	public String getSelfBio() {
		return selfBio;
	}

	public void setSelfBio(String selfBio) {
		this.selfBio = selfBio;
	}

	public List<Playlist> getPlaylists() {
		return playlists;
	}

	public void setPlaylists(List<Playlist> playlists) {
		this.playlists = playlists;
	}

	public List<Person> getFollowers() {
		return followers;
	}

	public void setFollowers(List<Person> followers) {
		this.followers = followers;
	}

	public List<Person> getFollowing() {
		return following;
	}

	public void setFollowing(List<Person> following) {
		this.following = following;
	}

	public List<Person> getLikes() {
		return likes;
	}

	public void setLikes(List<Person> likes) {
		this.likes = likes;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	

}
