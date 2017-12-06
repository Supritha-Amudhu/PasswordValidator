package edu.sfsu.cs.supritha.gladly;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Pattern;

public class PasswordValidator {

	private static final String ERROR_VOWEL = "Password must contain atleast one vowel";
	private static final String ERROR_CONSECUTIVE_VOWEL_CONSONANTS = "Password must not contain more than two consecutive vowels or consonants";
	private static final String ERROR_CONSECUTIVE_OCCURRENCES = "Password must not contain two consecutive occurrences of the same alphabet";
	private static final String ERROR_PASSWORD_LENGTH = "Password must be between 1-20 characters long";
	private static final String ERROR_LOWER_CASE = "Password must contain only lower case alphabets";

	private void validatePasswords(String inputFilePath, String outputFilePath) {
		File fileInput = new File(inputFilePath);
		File fileOutput = new File(outputFilePath);
		// If a file under the same name does not exist, it creates a new output
		// text file.

		String password;
		try {
			if (!fileInput.exists()) {
				System.out.println("Invalid input file path.");
				return;
			}
			if (!fileOutput.exists()) {
				fileOutput.createNewFile();
			}
			BufferedReader reader = new BufferedReader(new FileReader(fileInput));
			// Open BufferedWriter in Write mode
			BufferedWriter writer = new BufferedWriter(new FileWriter(fileOutput, false));
			while ((password = reader.readLine()) != null) {
				// Checks if the length of the password is between 1 to 20
				// characters long.
				if (password.length() > 20 || password.length() < 1) {
					writer.write(password + ", " + ERROR_PASSWORD_LENGTH + "\n");
				}
				// Checks if the password contains only lower case alphabets.
				else if (!password.equals(password.toLowerCase())) {
					writer.write(password + ", " + ERROR_LOWER_CASE + "\n");
				}
				// Checks if the password contains at least one vowel.
				else if (!password.contains("a") && !password.contains("e") && !password.contains("i")
						&& !password.contains("o") && !password.contains("u")) {
					writer.write(password + ", " + ERROR_VOWEL + "\n");
				}
				// Checks if the password contains two consecutive occurrences
				// of the same alphabet.
				else if (Pattern.compile("(\\w)\\1+").matcher(password).find()) {
					writer.write(password + ", " + ERROR_CONSECUTIVE_OCCURRENCES + "\n");
				}
				// Checks if the password contains more than two consecutive
				// vowels or two consecutive consonants.
				else if (Pattern.compile("[aeiou]{3}").matcher(password).find()
						|| Pattern.compile("[a-z&&[^aeiou]]{3}").matcher(password).find()) {
					writer.write(password + ", " + ERROR_CONSECUTIVE_VOWEL_CONSONANTS + "\n");
				}
				// To differentiate valid passwords from invalid ones, printing
				// it on the console.
				else {
					System.out.println("Valid Password: " + password);
				}
			}
			reader.close();
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		PasswordValidator passwordValidator = new PasswordValidator();
		passwordValidator.validatePasswords("InputPasswordList.txt", "OutputInvalidPasswordResults.txt");
	}

}
