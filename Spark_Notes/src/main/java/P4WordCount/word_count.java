package P4WordCount;

import java.util.Arrays;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import scala.Tuple2;

public class word_count {
	public static void main(String args[]) {
		SparkConf conf=new SparkConf().setMaster("local[*]").setAppName("word_count");
		JavaSparkContext context=new JavaSparkContext(conf);
		
        JavaRDD<String> lines=context.textFile("C:/Users/pratham.kumar/eclipse-workspace/spark_projects/src/main/resources/words.txt");
        
        JavaRDD<String> words=lines.flatMap(line -> Arrays.asList(line.split(" ")).iterator());
        JavaPairRDD<String,Integer> pairs= words.mapToPair(x -> new Tuple2<>(x,1));
        JavaPairRDD<String,Integer> wordCount= pairs.reduceByKey(Integer::sum);
        //JavaPairRDD<String, Integer> wordCount = pairs.reduceByKey((count1, count2) -> count1 + count2);
        wordCount.collect().forEach(System.out::println);
        
        //To Print top 5 max count words.
        wordCount.mapToPair(tuple -> new Tuple2<>(tuple._2,tuple._1))
        		 .sortByKey(false).take(5).forEach(System.out::println);
	}
}
