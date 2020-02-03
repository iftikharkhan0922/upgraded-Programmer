/**
 * 
 */
package com.iftikhar.spark.airportsGreaterThanFourty;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

/**
 * @author Iftikhar Khan
 *
 */
public class AirportsOverFourtyLatitude {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Change Logging level to Warn to exclude all the red info from output.
		Logger.getLogger("org.apache").setLevel(Level.WARN);

		// Get a Java SparkConfigure.
		SparkConf conf = getSparkConf(("Airports"), ("local[*]"));

		// Get a Spark Connection.
		JavaSparkContext sc = getSparkContext(conf);

		// Create an RDD by loading the file airports.txt.
		JavaRDD<String> airportRDD = sc.textFile("src/main/resources/contents/in/airports.txt");

		// Create a new RDD and run the check latitude method to Initialize it.
		JavaRDD<String> latitudeCheckedRDD = checkAirportsLatitude(airportRDD);

		// Before writing to a file check if the file already exists.
		checkIfFileExists(conf, sc, latitudeCheckedRDD);

		// Print Confirmation message.
		System.out.println("Program run was a success!\n" + "Find the new output files located in "
				+ "the Project folder.\nthe output path is " + "src/main/resources/contents/out.");

		// Close the Spark context to avoid memory leak.
		sc.close();
	} // end main().

	/*
	 * This function checks the existence of the file path and also calls the print
	 * method.
	 */
	private static void checkIfFileExists(SparkConf conf, JavaSparkContext sc, JavaRDD<String> latitudeCheckedRDD) {

		// File path holder.
		String filePath = "src/main/resources/contents/out/airports_by_latitude.txt";

		// HDFS file system variable.
		FileSystem hdfs = null;

		// Surround the operation in a try catch block.
		try {
			// Initialize the hdfs File system.
			hdfs = org.apache.hadoop.fs.FileSystem.get(sc.hadoopConfiguration());
		} catch (IOException e) {
			System.out.println("Exception occured! on Initializing the hdfs File System object.");
			e.printStackTrace();
		} // end try-catch block.

		// Create File system Path object and initialize with String filePath.
		Path path = new Path(filePath);

		try {
			if (!hdfs.exists(path)) {
				// Call the write file function.
				writeToFile(filePath, latitudeCheckedRDD);
			} else {
				// Delete the file or directory and then call the write math.
				hdfs.delete(path, true);
				writeToFile(filePath, latitudeCheckedRDD);
			}
		} catch (IOException e) {
			System.out.println("Exception occured! on checking the file existence.");
			e.printStackTrace();
		} // end of try-catch block.
	} // end checkIfFileExists()

	// Function to write the content to a file.
	private static void writeToFile(String filePath, JavaRDD<String> printRDD) {
		printRDD.saveAsTextFile(filePath);
	} // end writeToFile.

	/*
	 * Function to filters the RDD and retrieve airports with latitude over 40.
	 */
	private static JavaRDD<String> checkAirportsLatitude(JavaRDD<String> checkRDD) {

		// Create a global delimiter for the file reader.
		String COMMA_DELIMITER = ",(?=([^\"]*\"[^\"]*\")*[^\"]*$)";

		// Check the latitude of the airports and return the
		JavaRDD<String> processedRDD = checkRDD.filter(line -> Float.valueOf(line.split(COMMA_DELIMITER)[6]) > 40)
				.map(line -> {
					String[] splits = line.split(COMMA_DELIMITER);
					return StringUtils.join(new String[] { splits[1], splits[6] }, ",");
				});
		// Return the processed RDD.
		return processedRDD;
	} // end checkAirportsLatitude().

	// Create a Spark Configure.
	private static SparkConf getSparkConf(String appName, String cores) {
		// Create and return a Spark Configure
		return (new SparkConf().setAppName(appName).setMaster(cores));
	} // end getSparkConf().

	// Create a SparkContext.
	private static JavaSparkContext getSparkContext(SparkConf conf) {
		// Create and return a Spark Context.
		return (new JavaSparkContext(conf));
	} // end getSparkContext().

} // End AirportsOverFourtyLatitude Class.
