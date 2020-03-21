package beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@ManagedBean
@SessionScoped

public class User {
	@Size(max = 15, min = 5)
	@NotNull
	private String firstName;
	
	@Size(max = 15, min = 5)
	@NotNull
	private String lastName;
	
	public User() {
		firstName = "Jacob";
		lastName = "Jingleheimer";
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
	
	
	
}
