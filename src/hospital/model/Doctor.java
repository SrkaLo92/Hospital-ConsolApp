package hospital.model;

import hospital.data.Saveable;
import hospital.util.StringUtil;

public class Doctor extends User implements Saveable{
	
	// Razmotriti tip
	private String specialization;
	
	public Doctor(String firstName, String lastName, String username, String password, String specialization) {
		super(firstName, lastName, username, password);
		this.specialization = specialization;
	}
	
	public String getSpecialization() {
		return specialization;
	}
	
	@Override
	public String[] getMenu() {
		String[] menu = new String[2];
		menu[0] = "1. Examination";
		menu[1] = "2. List diagnosis for patient";

		return menu;
	}
	
	@Override
	public int offset() {
		return 9;
	}

	@Override
	public String toCSV() {
		return StringUtil.qoute(firstName) + "," 
				+ StringUtil.qoute(lastName) + "," 
				+ StringUtil.qoute(username) + "," 
				+ StringUtil.qoute(password) + "," // password need to be hashed 
				+ StringUtil.qoute(specialization) + ",";
	}

	@Override
	public void parseCSV(String csv) {
		// TODO Auto-generated method stub
		
	}

}
