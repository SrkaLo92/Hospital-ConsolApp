package hospital.model;

import java.util.List;

import hospital.data.Saveable;
import hospital.util.StringUtil;

public class Recipe implements Document, Saveable{
	
	private boolean signed;
	private Doctor doctor;
	private List<Drug> drugs;
	private int id;
	
	public Recipe(List<Drug> drugs) {
		signed = false;
		id = 0;
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

	public int getId() {
		return id;
	}

	@Override
	public String toCSV() {
		String csv = StringUtil.qoute(doctor.getUsername()) + "," + StringUtil.qoute(signed);
		String drugsCSV = "";
		for(Drug drug: drugs) {
			drugsCSV += drug.getName() + " ";
		}
		
		return csv + "," + StringUtil.qoute(drugsCSV.trim());
	}

	@Override
	public void parseCSV(String csv) {
		// TODO Auto-generated method stub
		
	}

}
