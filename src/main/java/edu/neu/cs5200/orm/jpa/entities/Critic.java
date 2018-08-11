package edu.neu.cs5200.orm.jpa.entities;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("CRITIC")
public class Critic extends Person implements Serializable {
	
	private static final long serialVersionUID = 1L;
}
