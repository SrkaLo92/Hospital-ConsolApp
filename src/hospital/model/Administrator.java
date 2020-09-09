package hospital.model;

import hospital.data.Saveable;
import hospital.util.StringUtil;

public class Administrator extends User implements Saveable{

	public Administrator(String firstName, String lastName, String username, String password) {
		super(firstName, lastName, username, password);
	}

	@Override
	public String[] getMenu() {
		String[] menu = new String[6];
		menu[0] = "1. Create patient";
		menu[1] = "2. Create doctor";
		menu[2] = "3. Create drug";
		menu[3] = "4. Create diagnosis";
		menu[4] = "5. Delete drug";
		menu[5] = "6. Delete diagnosis";
		
		return menu;
	}

	@Override
	public int offset() {
		return 0;
	}

	@Override
	public String toCSV() {
		return StringUtil.qoute(firstName) + "," 
				+ StringUtil.qoute(lastName) + "," 
				+ StringUtil.qoute(username) + "," 
				+ StringUtil.qoute(password) + ","; // password need to be hashed 
	}

	@Override
	public void parseCSV(String csv) {
		// TODO Auto-generated method stub
		
	}
	
	
	

}
