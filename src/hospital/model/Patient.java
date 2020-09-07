package hospital.model;

import java.time.temporal.ChronoUnit;

public class Patient extends User {
	
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

}
