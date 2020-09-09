package hospital.console;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import hospital.data.FileData;
import hospital.model.Diagnosis;
import hospital.model.Doctor;
import hospital.model.Drug;
import hospital.model.Examination;
import hospital.model.Hospital;
import hospital.model.Patient;
import hospital.model.Recipe;
import hospital.model.Report;
import hospital.model.User;

public class Console {
	private UserInput userInput;
	private Hospital hospital;
	private User loggedUser;
	private FileData fileData;
	
	public Console(Hospital hospital) {
		this.hospital = hospital;
		userInput = new UserInput();
		fileData = new FileData();
	}
	
	public void run() {
		System.out.println("Welcome to the hospital application!");
				
		while(true) {			
			boolean isValid = login();
			if(!isValid) {
				continue;
			}
			
			while(true ) {
				
				int option = menu();		
				if(option == 0) {
					break;
				}
				option += loggedUser.offset();
				if(option ==  1) {
					createPatient();
				} else if(option == 2) {
					createDoctor();
				} else if(option == 3) {
					createDrug();
				} else if(option == 4) {
					createDiagnosis();
				} else if(option == 5) {
					deleteDrug();
				} else if(option == 6) {
					deleteDiagnosis();
				} else if(option == 7) {
					createExamination();
				} else if(option == 8) {
					extendHealthCard();
				} else if(option == 9) {
					printNextExaminations();
				} else if(option == 10) {
					doExamination();
				} else if(option == 11) {
					printPatientExaminations();
				}
			}
		}
		
	}
	
	private boolean login() {
		System.out.println("Login");
		
		String username = userInput.stringInput("Username");
		String password = userInput.stringInput("Password");
		
		User user = hospital.findUser(username);
		if(user == null) {
			System.out.println("Invalid username or password!"); // Refaktorisati.
			return false;
		}
		
		boolean isValidUser = user.checkPassword(password);
		if(!isValidUser) {
			System.out.println("Invalid username or password!");
			return false;
		}
		
		loggedUser = user;
		return isValidUser;
	}
	
	private int menu() {
		System.out.println("Menu: ");
		for(String menuItem: loggedUser.getMenu())
			System.out.println(menuItem);
		System.out.println("0. Logout");
		
		return userInput.integerInput("Enter option");
	}
	
	private void createPatient() {
		System.out.println("Creation of a patient");
		
		String firstName = userInput.stringInput("First name");	
		String lastName = userInput.stringInput("Last name");
		String username = userInput.stringInput("Username");
		String password = userInput.stringInput("Password");
		
		Patient patient = new Patient(firstName, lastName, username, password);
		hospital.addPatient(patient);
		fileData.save(patient, "patient");
	}
	
	private void createDoctor() {
		System.out.println("Creation of a doctor");
		
		String firstName = userInput.stringInput("First name");	
		String lastName = userInput.stringInput("Last name");
		String username = userInput.stringInput("Username");
		String password = userInput.stringInput("Password");		
		String specialization =  userInput.stringInput("Specialization");
		
		Doctor doctor = new Doctor(firstName, lastName, username, password, specialization);
		hospital.addDoctor(doctor);
		fileData.save(doctor, "doctor");
	}
	
	private void createDrug() {
		System.out.println("Creation of a drug");
		
		String name =  userInput.stringInput("Name");
		boolean imported =  userInput.booleanInput("Is imported");
		
		Drug drug = new Drug(name, imported);
		hospital.addDrug(drug);
		fileData.save(drug, "drug");		
	}
	
	private void deleteDrug() {
		System.out.println("Delete a drug");
		
		String drugName = userInput.selectInput("Drugs: ", hospital.drugList(), "Enter drug name");
		
		Drug drugForDelete = hospital.findDrug(drugName);
		boolean deleteSuccess = hospital.deleteDrug(drugName);
		
		if(!deleteSuccess) {
			System.out.println("There is no drug with name " + drugName);
			return;
		}
		
		fileData.delete(drugForDelete, "drug");
	}
	
	private void createDiagnosis() {
		System.out.println("Creation of a diagnosis");
		
		String name = userInput.stringInput("Name");
		String code = userInput.stringInput("Code");
		List<Drug> drugs = selectDrugs(hospital.drugList());
		
		Diagnosis diagnosis = new Diagnosis(name, code, drugs);
		hospital.addDiagnosis(diagnosis);
		fileData.save(diagnosis, "diagnosis");		

	}
	
	private List<Drug> selectDrugs(List<String> drugList) {
		boolean addDrugs = userInput.booleanInput("Do you want to add a drugs?");

		List<Drug> drugs = new ArrayList<Drug>(); 				
		if(!addDrugs) {	
			return drugs;
		}
		
		String[] inputDrugs = userInput.mulitpleStringInput("Drugs", drugList, "Enter drug names");
		for(String inputDrug : inputDrugs) {
			Drug drug = hospital.findDrug(inputDrug);
			if(drug == null) {
				System.out.println("One or more drug doesn't exist.");
				return selectDrugs(drugList);
			}
			drugs.add(drug);
		}
		
		return drugs;
	}
	
	private void deleteDiagnosis() {
		System.out.println("Delete a diagnosis");
		
		String diagnosisCode = userInput.selectInput("Diagnoses: ", hospital.diagnosisList(), "Enter diagnosis code");
		
		Diagnosis diagnosisForDelete = hospital.findDiagnosis(diagnosisCode);
		boolean deleteSuccess = hospital.deleteDiagnosis(diagnosisCode);
		
		if(!deleteSuccess) {
			System.out.println("There is no diagnosis with code " + diagnosisCode);
			return;
		}
		
		fileData.delete(diagnosisForDelete, "diagnosis");
	}
	
	private void createExamination() {
		System.out.println("Creation of a termin");
		
		Patient patient = (Patient)loggedUser;
		
		if(!patient.isValidHealthCard()) {
			System.out.println("Your health card has been expired!");
			return;
		}
		
		LocalDate date = userInput.dateInput("Enter a date");	
		LocalTime time = userInput.timeInput("Enter a time");
		int order = userInput.selectOrderInput("Doctors", hospital.doctorList(),"Enter number for selecting doctor");
		
		Doctor doctor = hospital.getDoctor(order - 1);
		
		Examination examination = new Examination(LocalDateTime.of(date, time), patient, doctor);
		hospital.addExamination(examination);
		patient.getHealthCard().addExamination(examination);
		fileData.save(examination, "examination");
	}
	
	private void extendHealthCard() {
		System.out.println("Extend expiration date for health card");
		
		ChronoUnit unit = userInput.dateUnitInput("Enter unit ");
		int number = userInput.integerInput("Enter number of " + unit.toString().toLowerCase());
		
		Patient patient = (Patient)loggedUser;
		
		String oldPatientCSV = patient.toCSV();
		patient.extendHealthCard(number, unit);
		fileData.update(patient, oldPatientCSV, "patient");
	}
	
	private void printNextExaminations() {
		System.out.println("Next examinations");
		
		Patient patient = (Patient)loggedUser;
		
		System.out.println(hospital.nextPatientExaminationList(patient.getUsername()));
	}
	
	private void doExamination() {
		System.out.println("Examination");
				
		Doctor doctor = (Doctor)loggedUser;
		
		int orderNumber = userInput.selectOrderInput("Examinations", 
				hospital.nextDoctorExaminationList(doctor.getUsername()), "Enter number of examination");
		
		Examination examination = hospital.getExaminationForDoctor(orderNumber, doctor.getUsername());
		
		boolean addDiagnoses = userInput.booleanInput("Do you want to add a diagnoses?");
		
		List<Diagnosis> diagnoses = new ArrayList<Diagnosis>();
		
		if(addDiagnoses) {
			String[] codeDiagnoses = userInput.mulitpleStringInput("Diagnoses", hospital.diagnosisList(), 
					"Enter code diagnoses");
			
			for(String codeDiagnosis : codeDiagnoses) {
				
				Diagnosis diagnosis = hospital.findDiagnosis(codeDiagnosis);
				if(diagnosis == null) {
					System.out.println("There is no diagnosis with code " + codeDiagnosis);
					return;
				}
				diagnoses.add(diagnosis);
			}
			
		}
		
		System.out.println("Recipes: ");
		List<Recipe> recipes = new ArrayList<Recipe>();
		
		while(true) {
			boolean addRecipe = userInput.booleanInput("Do you want to add a recipe?");
			
			if(!addRecipe) {
				break;
			}
			
			List<Drug> drugs = selectDrugs(hospital.diagnosisDrugList(diagnoses));
			
			boolean drugExist = checkDrugsInDiagnoses(drugs, diagnoses);
			if(!drugExist ) {
				System.out.println("You enter something wrong!");
				return;
			}
			
			Recipe recipe = new Recipe(drugs);
			recipes.add(recipe);
		}
		
		for(Recipe recipe : recipes) {
			userInput.stringInput("Sign a recipe (Press enter to continue)");
			recipe.sign(doctor);
			System.out.println(recipe.print());

			fileData.save(recipe, "recipe");
		}
		
		
		Report report = new Report(hospital, examination.getPatient(), doctor, diagnoses, recipes);
		userInput.stringInput("Sign a report (Press enter to continue): ");
		report.sign(doctor);
		System.out.println(report.print());
		
		String oldExaminationCSV = examination.toCSV();
		examination.setReport(report);
		fileData.save(report, "report");
		fileData.update(examination, oldExaminationCSV, "examination");

	}
	
	private boolean checkDrugsInDiagnoses(List<Drug> selectedDrugs, List<Diagnosis> selectedDiagnoses) {
		for(Drug drug: selectedDrugs) {
			for(Diagnosis diagnosis : selectedDiagnoses) {
				if(!diagnosis.checkForDrug(drug.getName())) {
					return false;
				}
			}
		}
		return true;
	}
	
	private void printPatientExaminations() {
		System.out.println("Patiens Examinations");
		System.out.println("1. Enter health card id");
		System.out.println("2. Enter first name and last name");
		
		int option = userInput.integerInput("Select option");
		
		Patient patient = null;
		
		if(option == 1) {		
			int healthCardId = userInput.integerInput("Enter health card id");	
			
			patient = hospital.findPatientByHealthCardId(healthCardId);
		} else if(option == 2) {
			String firstName = userInput.stringInput("First name");	
			String lastName = userInput.stringInput("Last name");			
			int orderNumber = userInput.selectOrderInput("Patients", hospital.patientList(firstName, lastName), 
					"Enter number to select patient");
			
			patient = hospital.getPatientFromFilter(orderNumber - 1, firstName, lastName);
		}
		
		if(patient == null) {
			System.out.println("User doesn't exist");
			return;
		}
				
		System.out.println(patient.getHealthCard().distinctDiagnosisList());
		
	}

}
