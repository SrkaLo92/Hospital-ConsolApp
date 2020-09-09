package hospital.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import hospital.data.Saveable;
import hospital.util.StringUtil;

public class Examination implements Saveable {
	
	private static int durationInMinutes = 20;
	private LocalDateTime startDateTime;
	private Patient patient;
	private Doctor doctor;
	private Report report;
	
	public Examination(LocalDateTime startDateTime, Patient patient, Doctor doctor) {
		this.startDateTime = startDateTime;
		this.patient = patient;
		this.doctor = doctor;
	}
	
	@Override
	public String toString() {
		return startDateTime.format(DateTimeFormatter.ofPattern("d.M.yyyy HH:mm")) 
				+ " " + doctor.getFirstName() 
				+ " " + doctor.getLastName() 
				+ " " + doctor.getSpecialization();
	}

	public Patient getPatient() {
		return patient;
	}
	
	public Doctor getDoctor() {
		return doctor;
	}
	
	public boolean examinationIsPassed() {
		return startDateTime.isBefore(LocalDateTime.now());
	}
	
	public void setReport(Report report) {
		this.report = report;
	}
	
	public boolean isExamined() {
		return report != null;
	}
	
	public Report getReport() {
		return report;
	}

	@Override
	public String toCSV() {
		return StringUtil.qoute(startDateTime) + "," + StringUtil.qoute(patient.getUsername()) 
			+ "," + StringUtil.qoute(doctor.getUsername()) 
			+ "," + StringUtil.qoute(isExamined() ? report.getId() : "");
	}

	@Override
	public void parseCSV(String csv) {
		// TODO Auto-generated method stub
		
	}

}
