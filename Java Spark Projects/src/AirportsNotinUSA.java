package com.iftikhar.spark.airportsNotinUSA;

import java.io.IOException;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.PairFunction;

import scala.Tuple2;

public class AirportsNotinUSA {

	// Create a global delimiter for the file reader.
	static String COMMA_DELIMITER = ",(?=([^\"]*\"[^\"]*\")*[^\"]*$)";

	public static void main(String[] args) {
		// Change Logging level to Warn to exclude all the red info from output.
		Logger.getLogger("org.apache").setLevel(Level.WARN);

		// Get a Java SparkConfigure.
		SparkConf conf = getSparkConf(("Airports"), ("local[*]"));

		// Get a Spark Connection.
		JavaSparkContext sc = getSparkContext(conf);

		// Create file path holder.
		String filePath = "src/main/resources/contents/in/airports.txt";

		// Create RDD and initialize by reading file. Call load method.
		JavaPairRDD<String, String> airportsNoInUSARDD = loadRDD(filePath, sc);

		// Before writing to a file check if the file already exists.
		checkIfFileExists(conf, sc, airportsNoInUSARDD);

		// print RDD to the console.
		airportsNoInUSARDD.collect().forEach(System.out::println);

		// Close Spark Context.
		sc.close();
	} // end main().

	// Read data from the word_count.txt file into the RDD.
	private static JavaPairRDD<String, String> loadRDD(String filePath, JavaSparkContext sc) {

		// Create an RDD by loading the file airports.txt.
		JavaRDD<String> initialRDD = sc.textFile(filePath);

		// Map the RDD first to Separate all the values then retrieve the two
		// required columns and remove the  method on the country column.		
		JavaPairRDD<String, String> airportsNotInUSARDD = initialRDD.mapToPair(getFilterdRDD())
				.filter(keyValue -> !keyValue._2().equals("\"United States\""));	

		// call getPairRDD method() and return the result to main.
		return airportsNotInUSARDD;
	} // end loadRDD.

	// Function to split the RDD and retrieve the required columns
	private static PairFunction<String, String, String> getFilterdRDD() {
		// return a paired RDD in the form of Tuple2 by retrieving colum1 and column3 using split().
		return (PairFunction<String, String, String>) line -> new Tuple2<>(line.split(COMMA_DELIMITER)[1],
				line.split(COMMA_DELIMITER)[3]);
	} // end getFilterd().

	/*
	 * This function checks the existence of the file path and also calls the print
	 * method.
	 */
	private static void checkIfFileExists(SparkConf conf, JavaSparkContext sc, JavaPairRDD<String, String> printRDD) {

		// File path holder.
		String filePath = "src/main/resources/contents/out/airports_not_in_usa_pair_rdd.txt";

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
				writeToFile(filePath, printRDD);
			} else {
				// Delete the file or directory and then call the write math.
				hdfs.delete(path, true);
				writeToFile(filePath, printRDD);
			}
		} catch (IOException e) {
			System.out.println("Exception occured! on checking the file existence.");
			e.printStackTrace();
		} // end of try-catch block.
	} // end checkIfFileExists()

	// Function to write the content to a file.
	private static void writeToFile(String filePath, JavaPairRDD<String, String> printRDD) {
		printRDD.saveAsTextFile(filePath);
	} // end writeToFile.

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

} // End Class AirportsNotinUSA.
