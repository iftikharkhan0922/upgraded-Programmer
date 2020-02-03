/**
 * 
 */
package com.iftikhar.spark.wordCount;

import java.util.Arrays;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import scala.Tuple2;

/**
 * @author Iftikhar Khan
 *
 */
public class WordCount {

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

		// Create file path holder.
		String filePath = "src/main/resources/contents/in/word_count.txt";

		// Create an RDD by loading the file airports.txt.
		JavaRDD<String> linesRDD = loadRDD(filePath, sc);

		// Create a new RDD and call get Words to separate the words.
		JavaRDD<String> wordsRDD = getWords(linesRDD);

		// print RDD to the Console..
		wordsRDD.collect().forEach(System.out::println);

		// Close spark context.
		sc.close();
	} // end main().

	// Function to separate the words delimited by spaces.
	private static JavaRDD<String> getWords(JavaRDD<String> linesRDD) {

		// Read lines to the RDD and split on the spaces.
		// Also trim the leading or trailing leftover typo spaces. using trim().
		JavaRDD<String> sentenceRDD = linesRDD.map(sentences -> sentences.replaceAll("[^a-zA-z\\s]", ""))
				.flatMap(words -> Arrays.asList(words.split(" ")).iterator())
				.filter(spaces -> spaces.trim().length() > 0);

		// Put the RDD into a Pair RDD and Pair every entry with a literal 1.
		// Next reduceByKey and add all the literal 1s.
		JavaPairRDD<String, Long> wordCountRDD = sentenceRDD
				.mapToPair(word -> new Tuple2<String, Long>(word.toLowerCase(), 1L))
				.reduceByKey((value1, value2) -> value1 + value2);

		// Call sortRDD and return the results.
		return sortRDD(wordCountRDD);
	} // end getWords().

	// Sort the RDD and return.
	private static JavaRDD<String> sortRDD(JavaPairRDD<String, Long> pairRDD) {

		// switch the fields to make numeric key and sort picking distinct only.
		JavaPairRDD<Long, String> sortedRDD = pairRDD.mapToPair(tuple -> new Tuple2<Long, String>(tuple._2, tuple._1))
				.distinct().sortByKey(false);

		// return sortedRDD in the format of "word : count".
		return sortedRDD.map(words -> String.format(words._2 + " : " + words._1).toString());
	} // end sortRDD().

	// Read data from the word_count.txt file into the RDD.
	private static JavaRDD<String> loadRDD(String filePath, JavaSparkContext sc) {
		return sc.textFile(filePath);
	} // end loadRDD.

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

} // End Class WordCount.
