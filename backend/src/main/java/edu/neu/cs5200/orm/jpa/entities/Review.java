package edu.neu.cs5200.orm.jpa.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Review implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public Review() {}
	
	public Review(String title, String text, Critic author, Album album) {
		setTitle(title);
		setText(text);
		setAuthor(author);
		setAlbum(album);
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String title;
	private String text;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(referencedColumnName = "id", nullable = false, unique = false)
	@JsonIgnore
	private Critic author;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(referencedColumnName = "id", nullable = false, unique = false)
	@JsonIgnore
	private Album album;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Critic getAuthor() {
		return author;
	}

	public void setAuthor(Critic author) {
		this.author = author;
	}

	public Album getAlbum() {
		return album;
	}

	public void setAlbum(Album album) {
		this.album = album;
	}
}
