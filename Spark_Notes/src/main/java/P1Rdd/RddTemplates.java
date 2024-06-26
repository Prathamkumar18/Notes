package P1Rdd;

import java.util.Arrays;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

public class RddTemplates {
	public static void main(String args[]) {
		SparkConf sparkConf = new SparkConf().setMaster("local[*]").setAppName("word_count");
		JavaSparkContext sparkContext=new JavaSparkContext(sparkConf);
		
		//RDD using parallelize
		JavaRDD<Integer> data = sparkContext.parallelize(Arrays.asList(1, 2, 3, 4, 5),10); //divide these data into 10 partition.
		System.out.println(data.count());
		
		//RDD using files
		JavaRDD<String> wordFile=sparkContext.textFile("C:/Users/pratham.kumar/eclipse-workspace/spark_projects/src/main/resources/words.txt");
		System.out.println(wordFile.first());
		wordFile.take(3).forEach(System.out::println);
		
		// Reading input files from a folder and creating a PairRDD where the key is the file path and the value is the content of the file
		JavaPairRDD<String,String> inputFiles= sparkContext.wholeTextFiles("C:/Users/pratham.kumar/eclipse-workspace/spark_projects/src/main/resources");
		System.out.println(inputFiles.count());
		
		inputFiles.collect().forEach(tuple -> {
            System.out.println(tuple._1); // Printing the file path
            System.out.println(tuple._2); // Printing the content of the file
        });
	}
}