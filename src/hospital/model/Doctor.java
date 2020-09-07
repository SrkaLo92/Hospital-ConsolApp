package hospital.model;

public class Doctor extends User {
	
	// Razmotriti tip
	private String specialization;
	
	public Doctor(String firstName, String lastName, String username, String password, String specialization) {
		super(firstName, lastName, username, password);
		this.specialization = specialization;
	}
	
	public String getSpecialization() {
		return specialization;
	}

}
