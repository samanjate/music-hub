package edu.neu.cs5200.orm.jpa.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class Writer extends Person implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@OneToMany(mappedBy = "writer")
	private List<Biodata> biodataWritten;

	public List<Biodata> getBiodataWritten() {
		return biodataWritten;
	}

	public void setBiodataWritten(List<Biodata> biodataWritten) {
		this.biodataWritten = biodataWritten;
	}
	

}
