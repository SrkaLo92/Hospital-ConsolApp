package hospital.data;

public interface Saveable {

	public String toCSV();
	public void parseCSV(String csv);
	
}
