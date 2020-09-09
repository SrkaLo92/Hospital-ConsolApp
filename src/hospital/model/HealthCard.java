package hospital.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import hospital.data.Saveable;
import hospital.util.Constants;
import hospital.util.StringUtil;

public class HealthCard implements Saveable {
	
	private int id;
	private LocalDate expirationDate;
	private InsuranceType insuranceType;
	private List<Examination> examinations;
	
	public HealthCard(InsuranceType insuranceType) {
		this.insuranceType = insuranceType;
		this.expirationDate = LocalDate.now().plusYears(Constants.EXPIRATION_DATE_OFFSET); 
		this.id = 0;
		this.examinations = new ArrayList<Examination>();
	}
	
	public boolean isValid() {
		return !expirationDate.isBefore(LocalDate.now());
	}
	
	public void extendExpirationDate(int amount, ChronoUnit unit) {
		expirationDate = expirationDate.plus(amount, unit);
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

	@Override
	public String toCSV() {
		return StringUtil.qoute(id) + "," + StringUtil.qoute(expirationDate) + "," 
				+ StringUtil.qoute(insuranceType);
	}

	@Override
	public void parseCSV(String csv) {
		// TODO Auto-generated method stub
		
	}

}