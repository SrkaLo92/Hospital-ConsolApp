package hospital.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import hospital.data.Saveable;
import hospital.util.StringUtil;

public class Diagnosis implements Saveable {
	
	private String name;
	private String code;
	private List<Drug> drugs;
	
	public Diagnosis(String name, String code, List<Drug> drugs) {
		this.name = name;
		this.code = code;
		this.drugs = drugs;
	}

	public String getName() {
		return name;
	}

	public String getCode() {
		return code;
	}
	
	public Set<String> getDrugNames() {
		
		Set<String> drugSet = new HashSet<String>();
		for(Drug drug : drugs) {
			drugSet.add(drug.getName());
		}
		return drugSet;
	}
	
	public boolean checkForDrug(String drugName) {
		for(Drug drug: drugs) {
			if(drug.getName().equals(drugName)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String toCSV() {
		String csv = StringUtil.qoute(name) + "," + StringUtil.qoute(code) + ",";
		String drugsCSV = "";
		for(Drug drug: drugs) {
			drugsCSV += drug.getName() + " ";
		}
		return csv + StringUtil.qoute(drugsCSV.trim());
	}

	@Override
	public void parseCSV(String csv) {
		// TODO Auto-generated method stub
		
	}

}
