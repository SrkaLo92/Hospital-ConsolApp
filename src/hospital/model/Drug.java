package hospital.model;

import hospital.data.Saveable;
import hospital.util.StringUtil;

public class Drug implements Saveable{
	private String name;
	private boolean imported;
	
	public Drug(String name, boolean imported) {
		this.name = name;
		this.imported = imported;
	}

	public String getName() {
		return name;
	}
	
	public boolean isImported() {
		return imported;
	}

	@Override
	public String toCSV() {
		return StringUtil.qoute(name) + "," + StringUtil.qoute(imported);
	}

	@Override
	public void parseCSV(String csv) {
		
	}

}
