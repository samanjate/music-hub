package neu.northeastern.cs5200.hw3.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "RoleType")
public class RoleType {

	@Id
	private int id;
	private String roleName;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public RoleType(){}
	
	public RoleType(int id, String roleName){
		this.id= id;
		this.roleName = roleName;
	}

}
