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
	
	public String drugList() {
		if(drugs.size() == 0) {
			return "";
		}
		String drugList = "";
		for(Drug drug : drugs) {
			drugList += drug.getName() + "\n";
		}
		return drugList.substring(0, drugList.length() - 1);
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
	
	public String diagnosisList() {
		if(diagnoses.size() == 0) {
			return "";
		}
		String diagnosisList = "";
		for(Diagnosis diagnosis : diagnoses) {
			diagnosisList += "Name: " + diagnosis.getName() + " Code: " + diagnosis.getCode() + "\n";
		}
		return diagnosisList.substring(0, diagnosisList.length() - 1);
	}
	
	public boolean deleteDiagnosis(String code) {
		return diagnoses.removeIf((diagnosis) -> diagnosis.getCode().equals(code));
	}
	
	public void addExamination(Examination examination) {
		examinations.add(examination);
	}
	
	public String doctorList() {
		if(doctors.size() == 0) {
			return "";
		}
		String doctorList = "";
		int orderNumber = 1;
		for(Doctor doctor : doctors) {
			doctorList +=orderNumber + ". " + doctor.getFirstName() + " " + doctor.getLastName() + ", " + doctor.getSpecialization() + "\n";
			orderNumber++;
		}
		return doctorList.substring(0, doctorList.length() - 1);
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
	
	public String nextDoctorExaminationList(String doctorUsername) {
		String nextDoctorExaminationList = "";
		int orderNumber = 1;
		for(Examination examination : examinations) {
			if(examination.getDoctor().getUsername().equals(doctorUsername) && !examination.examinationIsPassed() && !examination.isExamined()) {
			 	nextDoctorExaminationList += orderNumber + ". " + examination + "\n";
			 	orderNumber++;
			}
		}
		return nextDoctorExaminationList;
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
	
	public String diagnosisDrugList(List<Diagnosis> selectedDiagnoses) {
		
		Set<String> drugNames = new HashSet<String>();
		for(Diagnosis diagnosis : diagnoses) {
			drugNames.addAll(diagnosis.getDrugNames());
		}
		String drugList = "";
		for(String drugName: drugNames) {
			drugList += drugName + "\n";
		}
		return drugList;
	}
	
	public Patient findPatientByHealthCardId(int healthCardId) {
		for(Patient patient : patients) {
			if(patient.getHealthCard().getId() == healthCardId) {
				return patient;
			}
		}
		return null;
	}
	
	public String patientList(String firstName, String lastName) {
		
		String patientList = "";
		
		int orderNumber = 1;
		for(Patient patient : getPatients(firstName, lastName)) {
			patientList += orderNumber + ". " + patient.getFirstName() + " " + patient.getLastName() + ", " + patient.getUsername() + "\n";
		}
		
		return patientList;
		
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
