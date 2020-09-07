package hospital.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HealthCard {
	
	private int id;
	private LocalDate expirationDate;
	private InsuranceType insuranceType;
	private List<Examination> examinations;
	
	public HealthCard(InsuranceType insuranceType) {
		this.insuranceType = insuranceType;
		this.expirationDate = LocalDate.now().plusYears(1); //Jedinicu promeniti
		this.id = 0; //Generisati jedinstven broj
		this.examinations = new ArrayList<Examination>();
	}
	
	public boolean isValid() {
		return !expirationDate.isBefore(LocalDate.now());
	}
	
	public void extendExpirationDate(int amount, ChronoUnit unit) {
		expirationDate.plus(amount, unit);
	}
	
	public int getId() {
		return id;
	}
	
	public void addExamination(Examination examination) {
		examinations.add(examination);
	}
	
	public String distinctDiagnosisList() {
		Set<String> diagnosisSet = new HashSet<String>();
		for(Examination examination : examinations) {
			
			if(examination.isExamined()) {
				diagnosisSet.addAll(examination.getReport().diagnosisList());
			}
		}
		
		String string = "";
		
		for(String diagnosis : diagnosisSet) {
			string += diagnosis + "\n";
		}
		
		return string;
	}

}