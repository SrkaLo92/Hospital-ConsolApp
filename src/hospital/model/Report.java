package hospital.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Report implements Document {
	
	private LocalDate publishDate;
	private boolean signed;
	private Hospital hospital;
	private Patient patient;
	private Doctor doctor;
	private List<Diagnosis> diagnoses;
	private List<Recipe> recipes;
	
	public Report(Hospital hospital, Patient patient, Doctor doctor, List<Diagnosis> diagnoses, List<Recipe> recipes) {
		publishDate = LocalDate.now();
		signed = false;
		this.hospital = hospital;
		this.patient = patient;
		this.doctor = doctor;
		this.diagnoses = diagnoses;
		this.recipes = recipes;
		
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

}
