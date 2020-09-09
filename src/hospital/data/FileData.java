package hospital.data;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class FileData {

	public void save(Saveable objectForSave, String fileName) {
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(new BufferedWriter(new FileWriter(fileName + ".csv", true)));
			writer.println(objectForSave.toCSV());
			writer.flush();
		} catch (Exception e) {
			
		} finally {
			if(writer != null) {
				writer.close();
			}
		}	
	}
	
	public void update(Saveable objectForUpdate, String csvOldObject, String fileName) {
		replace(objectForUpdate.toCSV(), csvOldObject, fileName);
	}
	
	public void delete(Saveable objectForDelete, String fileName) {
		replace("", objectForDelete.toCSV() + System.lineSeparator(), fileName);
	}
	
	private void replace(String newValue, String csvOldObject, String fileName) {

		PrintWriter writer = null;
		Scanner scanner = null;
		String fullFileName = fileName + ".csv";
		try {
			scanner = new Scanner(new File(fullFileName));
			StringBuffer buffer = new StringBuffer();
			while (scanner.hasNextLine()) {
				buffer.append(scanner.nextLine() + System.lineSeparator());
			}
			String fileContents = buffer.toString();

			fileContents = fileContents.replaceAll(csvOldObject, newValue);
			writer = new PrintWriter(fullFileName);
			writer.write(fileContents);
			writer.flush();
		} catch (Exception e) {

		} finally {
			if (scanner != null) {
				scanner.close();
			}
			if (writer != null) {
				writer.close();
			}

		}
	}
	
}
