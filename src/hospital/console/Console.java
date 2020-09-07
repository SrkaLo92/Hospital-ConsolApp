package hospital.console;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import hospital.model.Diagnosis;
import hospital.model.Doctor;
import hospital.model.Drug;
import hospital.model.Examination;
import hospital.model.HealthCard;
import hospital.model.Hospital;
import hospital.model.Patient;
import hospital.model.Recipe;
import hospital.model.Report;
import hospital.model.User;

public class Console {
	
	private Scanner input;
	private Hospital hospital; // Ovo treba da se refaktorise.
	private User loggedUser;
	
	public Console(Hospital hospital) {
		this.hospital = hospital;
		input = new Scanner(System.in);
	}
	
	public void run() {
		System.out.println("Welcome to the hospital application!");
		
		
		while(true) {			
			boolean isValid = login();
			if(!isValid) {
				continue;
			}
			
			while(true ) {
				String option = menu();		
				if(option.equals("0")) {
					break;
				} else if(option.equals("1")) {
					createPatient();
				} else if(option.equals("2")) {
					createDoctor();
				} else if(option.equals("3")) {
					createDrug();
				} else if(option.equals("4")) {
					createDiagnosis();
				} else if(option.equals("5")) {
					deleteDrug();
				} else if(option.equals("6")) {
					deleteDiagnosis();
				} else if(option.equals("7")) {
					createExamination();
				} else if(option.equals("8")) {
					extendHealthCard();
				} else if(option.equals("9")) {
					printNextExaminations();
				} else if(option.equals("10")) {
					doExamination();
				} else if(option.equals("11")) {
					printPatientExaminations();
				}
			}
		}
		
	}
	
	private boolean login() {
		System.out.println("Login");
		
		System.out.print("Username: ");
		String username = input.nextLine();
		
		System.out.print("Password: ");
		String password = input.nextLine();
		
		User user = hospital.findUser(username);
		if(user == null) {
			System.out.println("Invalid username or password!"); // Refaktorisati.
			return false;
		}
		boolean isValidUser = user.checkPassword(password);
		if(!isValidUser) {
			System.out.println("Invalid username or password!");
		} else {
			loggedUser = user;
		}
		
		return isValidUser;
	}
	
	private String menu() {
		
		System.out.println("Menu: ");
		System.out.println("1. Create patient");
		System.out.println("2. Create doctor");
		System.out.println("3. Create drug");
		System.out.println("4. Create diagnosis");
		System.out.println("5. Delete drug");
		System.out.println("6. Delete diagnosis");
		System.out.println("7. Create termin");
		System.out.println("8. Extend health card");
		System.out.println("9. Next examination list");
		System.out.println("10. Examination");
		System.out.println("11. List diagnosis for patient");
		System.out.println("0. Logout");
		System.out.print("Enter option: ");
		return input.nextLine(); //Koristimo nextLine umesto nextInt, vraticemo se kasnije na to...
		
	}
	
	private void createPatient() {
		System.out.println("Creation of a patient");
		
		System.out.print("First name: ");
		String firstName = input.nextLine();
		
		System.out.print("Last name: ");
		String lastName = input.nextLine();
		
		System.out.print("Username: ");
		String username = input.nextLine();
		
		System.out.print("Password: ");
		String password = input.nextLine();
		
		Patient patient = new Patient(firstName, lastName, username, password);
		hospital.addPatient(patient);
	}
	
	private void createDoctor() {
		System.out.println("Creation of a doctor");
		
		System.out.print("First name: ");
		String firstName = input.nextLine();
		
		System.out.print("Last name: ");
		String lastName = input.nextLine();
		
		System.out.print("Username: ");
		String username = input.nextLine();
		
		System.out.print("Password: ");
		String password = input.nextLine();
		
		System.out.print("Specialization: ");
		String specialization = input.nextLine();
		
		Doctor doctor = new Doctor(firstName, lastName, username, password, specialization);
		hospital.addDoctor(doctor);
	}
	
	private void createDrug() {
		System.out.println("Creation of a drug");
		
		System.out.print("Name: ");
		String name = input.nextLine();
		
		System.out.print("Is imported (Y/N): ");
		String imported = input.nextLine();
		
		boolean imp = imported.equals("Y");
		
		Drug drug = new Drug(name, imp);
		hospital.addDrug(drug);
	}
	
	private void deleteDrug() {
		System.out.println("Delete a drug");
		
		System.out.println("Drugs: ");
		System.out.println(hospital.drugList());
		
		System.out.print("Enter drug name: ");
		String inputDrugName = input.nextLine();
		
		boolean deleteSuccess = hospital.deleteDrug(inputDrugName);
		
		if(!deleteSuccess) {
			System.out.println("There is no drug with name " + inputDrugName);
		}
	}
	
	private void createDiagnosis() {
		System.out.println("Creation of a diagnosis");
		
		System.out.print("Name: ");
		String name = input.nextLine();
		
		System.out.print("Code: ");
		String code = input.nextLine();
		
		System.out.println("Drugs: ");
		System.out.println(hospital.drugList());
		
		System.out.print("Enter drugs (separated with comma): ");
		String inputDrugs = input.nextLine();
		
		String[] splittedDrugs = inputDrugs.split(",");
		
		List<Drug> drugs = new ArrayList<Drug>(); //Dodati mogucnost za ne unosenje leka
		for(String inputDrug : splittedDrugs) {
			Drug drug = hospital.findDrug(inputDrug);
			if(drug == null) {
				return; //Odradi ponovo ispisivanje drug-ova
			}
			drugs.add(drug);
		}
		
		Diagnosis diagnosis = new Diagnosis(name, code, drugs);
		hospital.addDiagnosis(diagnosis);
	}
	
	private void deleteDiagnosis() {
		System.out.println("Delete a diagnosis");
		
		System.out.println("Diagnoses: ");
		System.out.println(hospital.diagnosisList());
		
		System.out.print("Enter diagnosis code: ");
		String inputDiagnosisCode = input.nextLine();
		
		boolean deleteSuccess = hospital.deleteDiagnosis(inputDiagnosisCode);
		
		if(!deleteSuccess) {
			System.out.println("There is no diagnosis with code " + inputDiagnosisCode);
		}
	}
	
	private void createExamination() {
		System.out.println("Creation of a termin");
		
		Patient patient = (Patient)loggedUser;
		
		if(!patient.isValidHealthCard()) {
			System.out.println("Your health card has been expired!");
			return;
		}
		
		System.out.print("Enter a date (d.M.yyyy): ");
		String inputDate = input.nextLine();
		LocalDate date = LocalDate.parse(inputDate, DateTimeFormatter.ofPattern("d.M.yyyy"));
		
		System.out.print("Enter a time (HH:mm): ");
		String inputTime = input.nextLine();
		LocalTime time = LocalTime.parse(inputTime, DateTimeFormatter.ofPattern("HH:mm"));
		
		System.out.println("Doctors: ");
		System.out.println(hospital.doctorList());
		
		System.out.print("Enter number for selecting doctor: ");
		String inputOrderNumber = input.nextLine();
		int orderNumber = Integer.parseInt(inputOrderNumber);
		
		Doctor doctor = hospital.getDoctor(orderNumber - 1);
		
		Examination examination = new Examination(LocalDateTime.of(date, time), patient, doctor);
		hospital.addExamination(examination);
		patient.getHealthCard().addExamination(examination);
	}
	
	private void extendHealthCard() {
		System.out.println("Extend expiration date for health card");
		
		System.out.print("Enter number and unit (number unit): ");
		String inputNumberAndUnit = input.nextLine();
		
		String[] numberAndUnit = inputNumberAndUnit.split(" ");
		
		if(numberAndUnit.length != 2) {
			System.out.println("Wrong format!");
			return;
		}
		
		int number = Integer.parseInt(numberAndUnit[0]);
		ChronoUnit unit = ChronoUnit.valueOf(numberAndUnit[1].toUpperCase());
		
		
		Patient patient = (Patient)loggedUser;
		patient.extendHealthCard(number, unit);
		
	}
	
	private void printNextExaminations() {
		System.out.println("Next examinations");
		
		Patient patient = (Patient)loggedUser;
		
		System.out.println(hospital.nextPatientExaminationList(patient.getUsername()));
	}
	
	private void doExamination() {
		System.out.println("Examination");
		
		System.out.println("Examinations: ");
		
		Doctor doctor = (Doctor)loggedUser;
		System.out.println(hospital.nextDoctorExaminationList(doctor.getUsername()));
		
		String inputOrderNumber = input.nextLine();
		int orderNumber = Integer.parseInt(inputOrderNumber);
		
		Examination examination = hospital.getExaminationForDoctor(orderNumber, doctor.getUsername());
		
		System.out.println("Do you want to add a diagnoses? (Y/N)");
		String inputAddDiagnoses = input.nextLine();
		
		boolean addDiagnoses = inputAddDiagnoses.equals("Y");
		
		List<Diagnosis> diagnoses = new ArrayList<Diagnosis>();
		
		if(addDiagnoses) {
			System.out.println(hospital.diagnosisList());
			System.out.print("Enter code diagnoses separated with comma: ");
			String inputCodeDiagnoses = input.nextLine();
			
			String[] codeDiagnoses = inputCodeDiagnoses.split(",");
			
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
			System.out.println("Do you want to add a recipe? (Y/N)");			
			String inputAddrecipe = input.nextLine();
			
			boolean addRecipe = inputAddrecipe.equals("Y");
			
			if(!addRecipe) {
				break;
			} 
			System.out.println("Drugs: ");
			System.out.println(hospital.diagnosisDrugList(diagnoses));
			
			System.out.print("Enter drugs (separated with comma): ");
			String inputDrugs = input.nextLine();
			
			String[] splittedDrugs = inputDrugs.split(",");
			
			List<Drug> drugs = new ArrayList<Drug>(); //Dodati mogucnost za ne unosenje leka
			for(String inputDrug : splittedDrugs) {
				boolean drugExist = checkDrugInDiagnoses(inputDrug, diagnoses);
				if(!drugExist ) {
					return;
				}
				Drug drug = hospital.findDrug(inputDrug);
				if(drug == null) {
					return; //Odradi ponovo ispisivanje drug-ova
				}
				drugs.add(drug);
			}
			
			Recipe recipe = new Recipe(drugs);
			recipes.add(recipe);
		}
		
		for(Recipe recipe : recipes) {
			System.out.print("Sign a recipe (Press enter to continue): ");
			input.nextLine();
			recipe.sign(doctor);
			System.out.println(recipe.print());
		}
		
		
		Report report = new Report(hospital, examination.getPatient(), doctor, diagnoses, recipes);
		System.out.print("Sign a report (Press enter to continue): ");
		input.nextLine();
		report.sign(doctor);
		System.out.println(report.print());
		
		examination.setReport(report);
	}
	
	private boolean checkDrugInDiagnoses(String drugName, List<Diagnosis> selectedDiagnoses) {
		for(Diagnosis diagnosis : selectedDiagnoses) {
			if(diagnosis.checkForDrug(drugName)) {
				return true;
			}
		}
		return false;
	}
	
	private void printPatientExaminations() {
		System.out.println("Patiens Examinations");
		System.out.println("1. Enter health card id");
		System.out.println("2. Enter first name and last name");
		System.out.print("Select option: ");
		String inputOption = input.nextLine();
		
		int option = Integer.parseInt(inputOption);
		
		Patient patient;
		
		if(option == 1) {
			
			System.out.print("Enter health card id: ");
			String inputHealthCardId = input.nextLine();
			
			int healthCardId = Integer.parseInt(inputHealthCardId);
			
			Patient selectedPatient = hospital.findPatientByHealthCardId(healthCardId);
			
			if(selectedPatient == null) {
				System.out.println("User doesn't exist");
				return;
			}
			
			patient = selectedPatient;
			
		} else if(option == 2) {
			System.out.print("Enter first name: ");
			String firstName = input.nextLine();
			
			System.out.println("Enter last name: ");
			String lastName = input.nextLine();
			
			System.out.println(hospital.patientList(firstName, lastName));
			
			System.out.print("Enter number for selecting patient: ");
			String inputOrderNumber = input.nextLine();
			int orderNumber = Integer.parseInt(inputOrderNumber);
			
			Patient selectedPatient= hospital.getPatientFromFilter(orderNumber - 1, firstName, lastName);
			
			if(selectedPatient == null) {
				System.out.println("User doesn't exist");
				return;
			}
			
			patient = selectedPatient;
		} else {
			System.out.println("There is no option");
			return;
		}
		
		HealthCard healthCard = patient.getHealthCard();
		
		System.out.println(healthCard.distinctDiagnosisList());
		
	}

}
