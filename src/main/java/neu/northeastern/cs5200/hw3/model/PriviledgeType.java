package neu.northeastern.cs5200.hw3.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "PriviledgeType")
public class PriviledgeType {

	@Id
	private int id;
	private String priviledgeName;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPriviledgeName() {
		return priviledgeName;
	}

	public void setPriviledgeName(String priviledgeName) {
		this.priviledgeName = priviledgeName;
	}

	public PriviledgeType() {
	}

	public PriviledgeType(int id, String priviledgeName) {
		this.id = id;
		this.priviledgeName = priviledgeName;
	}
}
