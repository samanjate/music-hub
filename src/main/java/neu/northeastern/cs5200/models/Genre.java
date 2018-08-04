package neu.northeastern.cs5200.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

public class Genre implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String name;
	@Lob
	private String description;
	private List<Genre> childGenres;
	private List<Genre> parentGenres;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<Genre> getChildGenres() {
		return childGenres;
	}
	public void setChildGenres(List<Genre> childGenres) {
		this.childGenres = childGenres;
	}
	public List<Genre> getParentGenres() {
		return parentGenres;
	}
	public void setParentGenres(List<Genre> parentGenres) {
		this.parentGenres = parentGenres;
	}

}
