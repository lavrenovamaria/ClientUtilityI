package com.csv.clientutility;

import com.csv.clientutility.domain.model.Person;
import com.csv.clientutility.filter.*;
import com.csv.clientutility.sorting.*;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.*;

@SpringBootApplication
public class ClientUtilityApplication implements CommandLineRunner {
	private static final Logger logger = LoggerFactory.getLogger(ClientUtilityApplication.class);
	// Inject PersonBirthdateValidator
	private final PersonValidator personValidator = new PersonBirthdateValidator();
	public static void main(String[] args) {
		SpringApplication.run(ClientUtilityApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		if (!containsArgument(args, "--in") || !containsArgument(args, "--out")) {
			throw new IllegalArgumentException("Usage: java -jar ClientUtilityApplication.jar --in <input_path> --out <output_path> [--only-men true] [--top 5] [--last 1]");
		}

		String inputFilePath = getArgumentValue(args, "--in");
		String outputFolderPath = getArgumentValue(args, "--out");

		// Optional parameters
		boolean onlyMen = getBooleanArgumentValue(args, "--males-only", false);
		boolean onlyWomen = getBooleanArgumentValue(args, "--females-only", false);

		boolean birthDateDescending = Arrays.stream(args).anyMatch(arg -> arg.contains("--birth-date-desc"));
		boolean lastNameAscending = Arrays.stream(args).anyMatch(arg -> arg.contains("--last-name-asc"));
		boolean lastNameDescending = Arrays.stream(args).anyMatch(arg -> arg.contains("--last-name-desc"));
		boolean firstNameAscending = Arrays.stream(args).anyMatch(arg -> arg.contains("--first-name-asc"));
		boolean firstNameDescending = Arrays.stream(args).anyMatch(arg -> arg.contains("--first-name-desc"));

		int topN = getIntArgumentValue(args, "--top", 0);
		int lastN = getIntArgumentValue(args, "--last", 0);

		List<Person> persons = loadPersonsFromCSV(inputFilePath);

		PersonSortingStrategy sortingStrategy = new BirthDateAscending();


		if(birthDateDescending){
			sortingStrategy = new BirthDateDescending();
		}

		if(lastNameAscending){
			sortingStrategy = new LastNameAscending();
		}

		if(lastNameDescending){
			sortingStrategy = new LastNameDescending();
		}

		if(firstNameAscending){
			sortingStrategy = new FirstNameAscending();
		}

		if(firstNameDescending){
			sortingStrategy = new FirstNameDescending();
		}


		List<PreFilter<Person>> preFilters = new ArrayList<>();

		if (onlyMen) {
			preFilters.add(new MenOnlyFilter(true));
		}

		if (onlyWomen) {
			preFilters.add(new WomenOnlyFilter(true));
		}

		preFilters.add(new LastNameAscendingWithDateFilterWrapper());


		List<PostFilter<Person>> postFilters = new ArrayList<>();

		if (topN > 0) {
			postFilters.add(new TopNFilter<>(topN));
		}

		if (lastN > 0) {
			postFilters.add(new LastNFilter<>(lastN));
		}


		PersonSorter sorter = new PersonSorter(sortingStrategy, preFilters, postFilters);

		List<Person> sortedList = sorter.sorted(persons);

		processSortedPersons(sortedList, outputFolderPath);
	}

	private boolean containsArgument(String[] args, String argName) {
		return Arrays.asList(args).contains(argName);
	}

	private String getArgumentValue(String[] args, String argName) {
		int index = Arrays.asList(args).indexOf(argName);
		if (index != -1 && index + 1 < args.length) {
			return args[index + 1];
		}
		throw new IllegalArgumentException("Missing value for argument: " + argName);
	}

	private boolean getBooleanArgumentValue(String[] args, String argName, boolean defaultValue) {
		int index = Arrays.asList(args).indexOf(argName);
		if (index != -1 && index + 1 < args.length) {
			return Boolean.parseBoolean(args[index + 1]);
		}
		return defaultValue;
	}

	private int getIntArgumentValue(String[] args, String argName, int defaultValue) {
		int index = Arrays.asList(args).indexOf(argName);
		if (index != -1 && index + 1 < args.length) {
			try {
				return Integer.parseInt(args[index + 1]);
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("Invalid value for argument " + argName + ": " + args[index + 1]);
			}
		}
		return defaultValue;
	}

	private void processSortedPersons(List<Person> sortedList, String outputFolderPath) {
		List<Person> validPersons = new ArrayList<>();
		List<Person> invalidPersons = new ArrayList<>();

		// Validate each person using the PersonBirthdateValidator
		for (Person person : sortedList) {
			if (personValidator.isValidPerson(person)) {
				validPersons.add(person);
			} else {
				invalidPersons.add(person);
				logInvalidPerson(person);
			}
		}

		System.out.println("Valid Persons:");
		for (int i = 0; i < validPersons.size(); i++) {
			Person person = validPersons.get(i);
			System.out.println((i + 1) + "\n" + person);
		}

		savePersonsToCSV(validPersons, outputFolderPath);

	}


	private void logInvalidPerson(Person person) {
		String logFolderPath = "/home/marialavrenova/IdeaProjects/ClientUtility/src/main/resources/logs"; // Update this path

		System.out.println("Log Folder Path: " + logFolderPath);

		try {
			Path logFolder = Paths.get(logFolderPath);
			if (!logFolder.toFile().exists()) {
				Files.createDirectories(logFolder);
				System.out.println("Log folder created successfully.");
			}
			System.out.println("Log Folder Path: " + logFolderPath);
			System.out.println("Log entry written successfully.");

			try (FileWriter logFileWriter = new FileWriter(Paths.get(logFolderPath, "log.txt").toString(), true)) {
				logFileWriter.write("Invalid person entry: " + person + "\n");
				System.out.println("Log entry written successfully.");
			} catch (IOException e) {
				logger.error("Error writing to log file", e);
			}

			logger.error("Invalid person entry: {}", person);
		} catch (IOException e) {

			logger.error("Error creating log folder: {}", e.getMessage());
		}
	}

	public List<Person> loadPersonsFromCSV(String folderPath) {
		List<Person> persons = new ArrayList<>();

		try {
			// List all files in the folder
			File folder = new File(folderPath);
			File[] files = folder.listFiles();

			if (files != null) {
				for (File file : files) {
					// Process only CSV files
					if (file.isFile() && file.getName().toLowerCase().endsWith(".csv")) {
						try (CSVReader reader = new CSVReader(new FileReader(file))) {
							String[] nextRecord;
							reader.readNext(); // Skip the header row

							while ((nextRecord = reader.readNext()) != null) {
								// Parse and process the data here
								String firstName = nextRecord[0];
								String lastName = nextRecord[1];
								String gender = nextRecord[2];

								try {
									// Extract year, day, and month from the date string
									int year = Integer.parseInt(nextRecord[3].substring(0, 4));
									int month = Integer.parseInt(nextRecord[3].substring(5, 7));
									int day = Integer.parseInt(nextRecord[3].substring(8, 10));

									LocalDate birthdate = LocalDate.of(year, month, day);
									// Create a Person object and add it to the list
									Person person = new Person(firstName, lastName, gender, birthdate);
									persons.add(person);
								} catch (DateTimeException | NumberFormatException e) {
									// Log or handle the exception if the date format is invalid or other issues
									logger.error("Error processing entry in file {}: {}", file.getName(), Arrays.toString(nextRecord));
								}
							}
						} catch (IOException | CsvValidationException e) {
							// Handle exceptions appropriately
							logger.error("Error reading CSV file {}: {}", file.getName(), e.getMessage());
						}
					}
				}
			}
		} catch (Exception e) {
			// Handle exceptions appropriately
			logger.error("Error listing files in folder {}: {}", folderPath, e.getMessage());
		}

		return persons;
	}

	private static void savePersonsToCSV(List<Person> persons, String folderPath) {
		try {
			// Create the output folder if it doesn't exist
			File outputFolder = new File(folderPath);
			if (!outputFolder.exists()) {
				outputFolder.mkdirs();
			}

			// Specify the output file name
			String outputFileName = "output.csv";
			String outputPath = Paths.get(folderPath, outputFileName).toString();

			// Check if the file already exists, and delete it
			File existingFile = new File(outputPath);
			if (existingFile.exists()) {
				existingFile.delete();
			}

			try (CSVWriter csvWriter = new CSVWriter(new FileWriter(outputPath))) {
				for (Person person : persons) {
					String[] record = {
							person.getFirstName(),
							person.getLastName(),
							String.valueOf(person.getGender()),
							person.getBirthdate().toString() // Assuming ISO_LOCAL_DATE format
					};
					csvWriter.writeNext(record);
				}
			}
		} catch (IOException e) {
			e.printStackTrace(); // Print the stack trace
			// Add additional logging or handle the exception as needed
		}
	}
}
