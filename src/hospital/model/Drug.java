package hospital.model;

public class Drug {
	
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

}
