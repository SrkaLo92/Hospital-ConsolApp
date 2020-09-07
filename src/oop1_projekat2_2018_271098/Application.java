package oop1_projekat2_2018_271098;

import hospital.console.Console;
import hospital.model.Administrator;
import hospital.model.Hospital;

public class Application {

	public static void main(String[] args) {
		
		Hospital lazaLazarevic = new Hospital("Laza Lazarevic");
		
		lazaLazarevic.addAdministrator(new Administrator("admin", "admin", "admin", "admin"));
		
		Console c = new Console(lazaLazarevic);
		
		c.run();

	}

}
