package hospital.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import hospital.data.Saveable;
import hospital.util.StringUtil;

public class Report implements Document, Saveable {
	
	private LocalDate publishDate;
	private boolean signed;
	private Hospital hospital;
	private Patient patient;
	private Doctor doctor;
	private List<Diagnosis> diagnoses;
	private List<Recipe> recipes;
	private int id;
	
	public Report(Hospital hospital, Patient patient, Doctor doctor, List<Diagnosis> diagnoses, List<Recipe> recipes) {
		publishDate = LocalDate.now();
		signed = false;
		this.hospital = hospital;
		this.patient = patient;
		this.doctor = doctor;
		this.diagnoses = diagnoses;
		this.recipes = recipes;
		id = 0; // napisi generator
	}

	@Override
	public String print() {
		
		String report = "";
		
		report += hospital.getName() + "\n";
		report += "Patient: " + patient.getFirstName() + " " + patient.getLastName() + ", " + patient.getUsername() + "\n";
		report += "Doctor: " + doctor.getFirstName() + " " + doctor.getLastName() + ", " + doctor.getSpecialization() + "\n";
		
		report += "Diagnoses: " + "\n";
		for(Diagnosis diagnosis : diagnoses) {
			report += diagnosis.getName() + " " + diagnosis.getCode() + "\n";
		}
		
		report += "Recipes: " + "\n";
		for(Recipe recipe : recipes) {
			report += recipe.print() + "\n";
		}
		
		if(signed) {
			report += "Signed by " + doctor.getFirstName() + " " + doctor.getLastName();
		} else {
			report += "Not signed";
		}
		
		report += "Publish date: " + publishDate.format(DateTimeFormatter.ofPattern("d.M.yyyy"));
		
		return report;
	}

	@Override
	public void sign(Doctor doctor) {
		
		if(this.doctor.getUsername().equals(doctor.getUsername())) {
			signed = true;
		}
		
	}
	
	public Set<String> diagnosisList() {
		Set<String> diagnosisSet = new HashSet<String>();
		
		for(Diagnosis diagnosis : diagnoses) {
			diagnosisSet.add(diagnosis.getName() + " " + diagnosis.getCode());
		}
		
		return diagnosisSet;
	}

	public int getId() {
		return id;
	}

	@Override
	public String toCSV() {	
		String csv = StringUtil.qoute(publishDate) + "," + StringUtil.qoute(patient.getUsername())
			+ "," + StringUtil.qoute(doctor.getUsername()) + "," + StringUtil.qoute(signed);
		String diagnosesCSV = "";
		for(Diagnosis diagnosis: diagnoses) {
			diagnosesCSV += diagnosis.getCode() + " ";
		}
		
		String recipesCSV = "";
		for(Recipe recipe: recipes) {
			recipesCSV += recipe.getId() + " ";
		}
		
		return csv + "," + StringUtil.qoute(diagnosesCSV.trim()) + "," + StringUtil.qoute(recipesCSV.trim());
	}

	@Override
	public void parseCSV(String csv) {
		// TODO Auto-generated method stub
		
	}

}
