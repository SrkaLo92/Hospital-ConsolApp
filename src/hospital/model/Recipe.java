package hospital.model;

import java.util.List;

public class Recipe implements Document {
	
	private boolean signed;
	private Doctor doctor;
	private List<Drug> drugs;
	
	public Recipe(List<Drug> drugs) {
		signed = false;
		this.drugs = drugs;
	}

	@Override
	public String print() {
		
		String recipe = "";
		recipe += "Recipe\n";
		recipe += "Drugs: \n";
		for(Drug drug : drugs) {
			recipe += drug.getName() + "\n";
		}
		
		if(signed) {
			recipe += "Signed by " + doctor.getFirstName() + " " + doctor.getLastName();
		} else {
			recipe += "Not signed";
		}
		
		return recipe;
	}

	@Override
	public void sign(Doctor doctor) {
		
		signed = true;
		this.doctor = doctor;
		
	}

}
