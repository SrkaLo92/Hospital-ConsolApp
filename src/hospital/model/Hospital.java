package hospital.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Hospital {
	
	private String name;
	private List<Doctor> doctors;
	private List<Patient> patients;
	private List<Administrator> administrators;
	
	private List<Drug> drugs;
	private List<Diagnosis> diagnoses;
	private List<Examination> examinations; //Proveriti da li je uopste potrebno
	
	public Hospital(String name) {
		this.name = name;
		doctors = new ArrayList<Doctor>();
		patients = new ArrayList<Patient>();
		administrators = new ArrayList<Administrator>();
		drugs = new ArrayList<Drug>();
		diagnoses = new ArrayList<Diagnosis>();
		examinations = new ArrayList<Examination>();
	}
	
	public void addPatient(Patient patient) {
		patients.add(patient);
	}
	
	public void addDoctor(Doctor doctor) {
		doctors.add(doctor);
	}
	
	public void addAdministrator(Administrator administrator) {
		administrators.add(administrator);
	}
	
	public User findUser(String username) {
		for(Patient patient : patients) {
			if(patient.username.equals(username)) {
				return patient;
			}
		}
		for(Doctor doctor: doctors) {
			if(doctor.username.equals(username)) {
				return doctor;
			}
		}
		for(Administrator administrator: administrators) {
			if(administrator.username.equals(username)) {
				return administrator;
			}
		}
		
		return null;
	}
	
	public void addDrug(Drug drug) {
		drugs.add(drug);
	}
	
	public void addDiagnosis(Diagnosis diagnosis) {
		diagnoses.add(diagnosis);
	}
	
	public List<String> drugList() {
		List<String> drugList = new ArrayList<String>();
		for(Drug drug : drugs) {
			drugList.add(drug.getName());
		}
		return drugList;
	}

	public List<String> diagnosisList() {
		List<String> diagnosisList = new ArrayList<String>();
		for(Diagnosis diagnosis : diagnoses) {
			diagnosisList.add("Name: " + diagnosis.getName() + " Code: " + diagnosis.getCode() );
		}
		return diagnosisList;
	}
	
	public List<String> doctorList() {
		List<String> doctorList = new ArrayList<String>();
		for(Doctor doctor : doctors) {
			doctorList.add(doctor.getFirstName() + " " + doctor.getLastName() + ", " + doctor.getSpecialization());
		}
		return doctorList;
	}
	
	public List<String> patientList(String firstName, String lastName) {	
		List<String> patientList = new ArrayList<String>();
		for(Patient patient : getPatients(firstName, lastName)) {
			patientList.add(patient.getFirstName() + " " + patient.getLastName() + ", " + patient.getUsername());
		}
		
		return patientList;
	}
	
	public List<String> nextDoctorExaminationList(String doctorUsername) {
		List<String> nextDoctorExaminationList = new ArrayList<String>();
		for(Examination examination : examinations) {
			if(examination.getDoctor().getUsername().equals(doctorUsername) && !examination.examinationIsPassed() && !examination.isExamined()) {
			 	nextDoctorExaminationList.add(examination.toString());
			}
		}
		return nextDoctorExaminationList;
	}
	
	public Drug findDrug(String name) {
		for(Drug drug : drugs) {
			if (drug.getName().equals(name)) {
				return drug;
			}
		}
		return null;
	}
	
	public boolean deleteDrug(String name) {
		return drugs.removeIf((drug) -> drug.getName().equals(name));
	}
	
	public boolean deleteDiagnosis(String code) {
		return diagnoses.removeIf((diagnosis) -> diagnosis.getCode().equals(code));
	}
	
	public void addExamination(Examination examination) {
		examinations.add(examination);
	}
	
	public Doctor getDoctor(int index) {
		return doctors.get(index);
	}
	
	public String nextPatientExaminationList(String patientUsername) {
		String nextPatientExaminationList = "";
		for(Examination examination : examinations) {
			if(examination.getPatient().getUsername().equals(patientUsername) && !examination.examinationIsPassed()) {
				nextPatientExaminationList += examination + "\n";
			}
		}
		return nextPatientExaminationList;
	}

	public String getName() {
		return name;
	}
	
	public Examination getExaminationForDoctor(int selectedOrderNumber, String doctorUsername) {
		int orderNumber = 1;
		for(Examination examination : examinations) {
			if(examination.getDoctor().getUsername().equals(doctorUsername) && !examination.examinationIsPassed() && !examination.isExamined()) {
				if(orderNumber == selectedOrderNumber) {
					return examination;
				}
			 	orderNumber++;
			}
		}
		
		return null;
	}

	public Diagnosis findDiagnosis(String codeDiagnosis) {
		
		for(Diagnosis diagnosis : diagnoses) {
			if (diagnosis.getCode().equals(codeDiagnosis)) {
				return diagnosis;
			}
		}
		return null;
	}
	
	public List<String> diagnosisDrugList(List<Diagnosis> selectedDiagnoses) {
		Set<String> drugNames = new HashSet<String>();
		for(Diagnosis diagnosis : diagnoses) {
			drugNames.addAll(diagnosis.getDrugNames());
		}
		return new ArrayList<String>(drugNames);
	}
	
	public Patient findPatientByHealthCardId(int healthCardId) {
		for(Patient patient : patients) {
			if(patient.getHealthCard().getId() == healthCardId) {
				return patient;
			}
		}
		return null;
	}
	
	private List<Patient> getPatients(String firstName, String lastName) {
		List<Patient> selectedPatients = new ArrayList<Patient>();
		for(Patient patient : patients) {
			if(patient.getFirstName().equals(firstName) && patient.getLastName().equals(lastName)) {
				selectedPatients.add(patient);
			}
		}
		return selectedPatients;
	}

	public Patient getPatientFromFilter(int index, String firstName, String lastName) {
		
		return getPatients(firstName, lastName).get(index);
	}
}
