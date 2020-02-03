/**
 * 
 */
package com.iftikhar.spark.NASAApacheLog;

import java.io.IOException;
import java.util.Arrays;

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
public class ApacheLog {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Change Logging level to Warn to exclude all the red info from output.
		Logger.getLogger("org.apache").setLevel(Level.WARN);

		// Get a Java SparkConfigure.
		SparkConf conf = getSparkConf(("ApacheLog"), ("local[*]"));

		// Get a Spark Connection.
		JavaSparkContext sc = getSparkContext(conf);

		// Call function and chain the load RDD into mergedRDD.
		JavaRDD<String> mergedLogRDD = mergedRDD(sc);

		// Initialize new hostsRDD and retrieve hosts from the merged RDD.
		JavaRDD<String> hostsRDD = gethosts(mergedLogRDD);
		
		// Before writing to a file check if the file already exists.
		checkIfFileExists(conf, sc, hostsRDD);
		
		// Print the final console program ended message.
		System.out.println(hostsRDD.count() 
				+ " Distinct records of hosts found excluding the ip address\n"
				+ "out of a total 19998 records in both files excluding the headers.\n"
				+ "Files are successfully generated into the path:\n"
				+ "src/main/resources/contents/out");
		
		// Close the Spark Context to avoid memory leak.
		sc.close();
	} // end main()

	// This function will retrieve the
	private static JavaRDD<String> gethosts(JavaRDD<String> mergedLogRDD) {

		/*
		 * Initialize an RDD by first mapping it to an array 
		 * and splitting the lines by a delimiter tab and cast it to an ARRAY List.
		 * Next filter out the IP address by getting index 0. of the list for each line
		 * and exclude anything that matches the regex expression.
		 * finally retrieve unique items using distinct(). 
		 */
		JavaRDD<String> hostsRDD = mergedLogRDD
				.map(lines -> Arrays.asList(lines.toString().split("\t")).get(0))
				.filter(hosts -> !hosts.toString().matches("^\\d+(?:\\.\\d+){3}(?:\\R|$)"))
				.distinct();
		
		// return hosts RDD to main.
		return hostsRDD;
	} // end gethosts()

	// Merge the two files into one RDD and return to main method.
	private static JavaRDD<String> mergedRDD(JavaSparkContext sc) {

		// File path holders.
		String log1 = "src/main/resources/contents/in/log1.tsv";
		String log2 = "src/main/resources/contents/in/log2.tsv";
		
		// Initialize a new RDD by loading both files and merging at the same time.
		JavaRDD<String> mergedRDD = (loadRDD(sc, log1)).union(loadRDD(sc, log2));

		// return a union of the two merged RDDs.
		return mergedRDD;
	} // end mergedRDD.

	// This function loads an RDD with the file path parameter.
	private static JavaRDD<String> loadRDD(JavaSparkContext sc, String filePath) {

		// load the file into RDD and remove the header.
		JavaRDD<String> loadedRDD = sc.textFile(filePath);
		String header = loadedRDD.first();
		JavaRDD<String> filteredRDD = loadedRDD.filter(row -> !row.equals(header));

		// return the filteredRDD.
		return filteredRDD;
	} // end loadRDD.

	/*
	 * This function checks the existence of the file path and also calls the print
	 * method.
	 */
	private static void checkIfFileExists(SparkConf conf, JavaSparkContext sc, JavaRDD<String> apacheLogRDD) {

		// File path holder.
		String filePath = "src/main/resources/contents/out/nasa_logs_same_hosts.csv";

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
				writeToFile(filePath, apacheLogRDD);
			} else {
				// Delete the file or directory and then call the write math.
				hdfs.delete(path, true);
				writeToFile(filePath, apacheLogRDD);
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

} // End ApacheLog class.
