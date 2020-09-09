package hospital.model;

import java.time.temporal.ChronoUnit;

import hospital.data.Saveable;
import hospital.util.StringUtil;

public class Patient extends User implements Saveable{
	
	private HealthCard healthCard;

	public Patient(String firstName, String lastName, String username, String password) {
		super(firstName, lastName, username, password);
		healthCard = new HealthCard(InsuranceType.EMPLOYED);
	}

	public HealthCard getHealthCard() {
		return healthCard;
	}

	public void setHealthCard(HealthCard healthCard) {
		this.healthCard = healthCard;
	}
	
	public boolean isValidHealthCard() {
		return healthCard != null && healthCard.isValid();
	}
	
	public void extendHealthCard(int amount, ChronoUnit unit) {
		healthCard.extendExpirationDate(amount, unit);
	}

	@Override
	public String[] getMenu() {
		String[] menu = new String[3];
		menu[0] = "1. Create termin";
		menu[1] = "2. Extend health card";
		menu[2] = "3. See next examination list";
		
		return menu;
	}
	
	@Override
	public int offset() {
		return 6;
	}

	@Override
	public String toCSV() {
		return StringUtil.qoute(firstName) + "," 
				+ StringUtil.qoute(lastName) + "," 
				+ StringUtil.qoute(username) + "," 
				+ StringUtil.qoute(password) + "," // password need to be hashed 
				+ healthCard.toCSV(); 
	}

	@Override
	public void parseCSV(String csv) {
		// TODO Auto-generated method stub
		
	}

}
