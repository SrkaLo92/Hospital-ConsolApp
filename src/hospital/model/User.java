package hospital.model;

public abstract class User {
	
	protected String firstName;
	protected String lastName;
	protected String username;
	protected String password;
	
	public User(String firstName, String lastName, String username, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getUsername() {
		return username;
	}

	public boolean checkPassword(String password) {
		
		return this.password.equals(password);
	}

}
