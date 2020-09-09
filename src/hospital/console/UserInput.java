package hospital.console;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class UserInput {
	
	private final static String DATE_FORMAT = "d.M.yyyy";
	private final static String TIME_FORMAT = "H:m";
	private final static String[] DATE_UNITS = {
			ChronoUnit.DAYS.toString().toLowerCase(), 
			ChronoUnit.MONTHS.toString().toLowerCase(), 
			ChronoUnit.YEARS.toString().toLowerCase()
	};
	private final static String DATE_UNITS_MESSAGE = String.join(", ", DATE_UNITS);
	
	private Scanner input;
 
	public UserInput() {
		input = new Scanner(System.in);
	}
	
	private void printLabel(String label) {
		System.out.print(label + ": ");
	}
	
	private void printLabel(String label, String additionalMessage) {
		System.out.print(label + " (" + additionalMessage + "): ");
	}

	private void printList(String header, List<String> dataList) {
		System.out.println(header + ":");
		for(String data: dataList) 
			System.out.println(data);
	}
	
	private void printOrderList(String header, List<String> dataList) {
		System.out.println(header + ":");
		int order = 1;
		for(String data: dataList) 
			System.out.println(order++ + ". " + data);
	}
	
	public boolean booleanInput(String label) {
		printLabel(label, "Y/N");
		String value = input.nextLine().trim();
		if(value.equalsIgnoreCase("Y")) {
			return true;
		}	
		
		if(value.equalsIgnoreCase("N")) {
			return false;
		}
		
		System.out.println("You must enter Y or N!");
		return booleanInput(label);
	}
	
	public String stringInput(String label) {
		printLabel(label);
		String value = input.nextLine().trim();
		if(value.isEmpty()) {
			System.out.println("You must enter some value!");
			return stringInput(label);
		}
		return value;		
	}
	
	public int integerInput(String label) {
		printLabel(label);
		String inputNumber = input.nextLine().trim();
		try {
			return Integer.parseInt(inputNumber);
		} catch (NumberFormatException nfe){
			System.out.println("You must enter a number");
			return integerInput(label);
		}
	}
	
	public String selectInput (String header, List<String> dataList, String label) {
		printList(header, dataList);
		return stringInput(label);
	}
	
	public int selectOrderInput (String header, List<String> dataList, String label) {
		printOrderList(header, dataList);
		return integerInput(label);
	}
	
	public String[] mulitpleStringInput(String header, List<String> dataList, String label) {
		printList(header, dataList);
		printLabel(label, "separate with comma");
		
		String userInput = input.nextLine().trim();
		if(userInput.isEmpty()) {
			System.out.println("You must enter some value!");
			return mulitpleStringInput(header, dataList, label);
		}
		
		String[] inputValues = userInput.split(",");
		for(int i = 0; i < inputValues.length; i++) 	
			inputValues[i] = inputValues[i].trim();	
		
		return inputValues;
	}
	
	public LocalDate dateInput(String label) {
		printLabel(label, DATE_FORMAT);
		String inputDate = input.nextLine().trim();
		try {
			return LocalDate.parse(inputDate, DateTimeFormatter.ofPattern(DATE_FORMAT));
		} catch (DateTimeParseException dtpe) {
			System.out.println("Date must be in format " + DATE_FORMAT + "!");
			return dateInput(label);
		}
	}
	
	public LocalTime timeInput(String label) {
		printLabel(label, TIME_FORMAT);
		String inputTime = input.nextLine().trim();
		try {
			return LocalTime.parse(inputTime, DateTimeFormatter.ofPattern(TIME_FORMAT));
		} catch (DateTimeParseException dtpe) {
			System.out.println("Time must be in format " + TIME_FORMAT + "!");
			return timeInput(label);
		}
	}
	
	public ChronoUnit dateUnitInput(String label) {
		printLabel(label, DATE_UNITS_MESSAGE);
		
		String inputUnit = input.nextLine().trim();
		List<String> dateUnits = Arrays.asList(DATE_UNITS);
		if(!dateUnits.contains(inputUnit)) {
			System.out.println("Unit must be " + DATE_UNITS_MESSAGE);
			return dateUnitInput(label);
		}
		return ChronoUnit.valueOf(inputUnit.toUpperCase());
		
	}
	
}
