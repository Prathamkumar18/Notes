package P6AccumBcast;

import java.util.Arrays;
import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.broadcast.Broadcast;
import org.apache.spark.util.LongAccumulator;


public class AccumulatorAndBroadcast {
	public static void main(String args[]) {
		SparkConf conf=new SparkConf().setMaster("local[*]").setAppName("word_count");
		JavaSparkContext sc=new JavaSparkContext(conf);
		
		//Lambda function can use local variables that are defined in it. and to access outside variable we use accumulator.
		List<Integer> list=Arrays.asList(1,2,3,4,5);
		JavaRDD<Integer> rdd=sc.parallelize(list);
		LongAccumulator sum=sc.sc().longAccumulator("sum");
		rdd.collect().forEach(x-> sum.add(x));
		System.out.print(sum.value());
		
		//Without a broadcast variable: Each node loads the dataset independently, which could be inefficient.
		//With a broadcast variable: The dataset is sent to each node just once and is cached in memory, which can be more efficient.
		Broadcast<List<Integer>> bv=sc.broadcast(list);
		JavaRDD<String> ans=rdd.map(nums-> {
				return "BroadCast Array Value"+ bv.value().toString();
			}
		);
		ans.collect().forEach(System.out::println);
	}
}
