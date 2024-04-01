package P3RddTransformations;

import java.util.Arrays;
import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.Optional;

import scala.Tuple2;

public class SparkTransformations {
	public static void main(String args[]) {
		SparkConf conf=new SparkConf().setMaster("local[*]").setAppName("SparkTransformation");
		JavaSparkContext sc=new JavaSparkContext(conf);
		
		//Union,Intersection and Distinct
		JavaRDD<Integer> rdd1=sc.parallelize(Arrays.asList(1,2,3,4,5));
		JavaRDD<Integer> rdd2=sc.parallelize(Arrays.asList(6,2,8,2,5,1,8));
		JavaRDD<Integer>union= rdd1.union(rdd2);
		JavaRDD<Integer>intersection= rdd1.intersection(rdd2);
		JavaRDD<Integer>distinct= rdd2.distinct();		
				
		//Repartition - to increase or decrease partition
		JavaRDD<Integer> partitionedRdd= rdd1.repartition(5); 
		System.out.println(rdd1.getNumPartitions());
		
		//SortBy, SortByKey, GroupByKey, ReduceByKey
		List<Tuple2<Character,Integer>> tuples1= Arrays.asList(
				new Tuple2<>('b',1),new Tuple2<>('c',1),new Tuple2<>('a',1),new Tuple2<>('a',2)
		);
		List<Tuple2<Character,Integer>> tuples2= Arrays.asList(
				new Tuple2<>('a',2),new Tuple2<>('b',1)
		);
		JavaPairRDD<Character,Integer> tuplesRdd1=sc.parallelizePairs(tuples1);
		JavaPairRDD<Character,Integer> tuplesRdd2=sc.parallelizePairs(tuples2);
		JavaPairRDD<Character,Integer> sbk= tuplesRdd1.sortByKey(true); //ascending
		JavaPairRDD<Character, Iterable<Integer>> gbk= tuplesRdd1.groupByKey();
		JavaPairRDD<Character,Integer> rbk =tuplesRdd1.reduceByKey((x,y)->x+y);
		JavaPairRDD<Character,Tuple2<Integer,Integer>>innerjoin=tuplesRdd1.join(tuplesRdd2);
		JavaPairRDD<Character, Tuple2<Integer, Optional<Integer>>>leftjoin=tuplesRdd1.leftOuterJoin(tuplesRdd2);
		JavaPairRDD<Character, Tuple2<Optional<Integer>, Integer>>rightjoin=tuplesRdd1.rightOuterJoin(tuplesRdd2);
		
		//Map,Filter
		JavaRDD<Integer> rdd3=sc.parallelize(Arrays.asList(1,2,3,4,5));
		JavaRDD<Integer> map=rdd3.map(x-> x+2);
		JavaRDD<Object> d = tuplesRdd1.map(x -> new Tuple2<>(x._1,x._2));
		JavaRDD<Integer> filter=rdd3.filter(x-> x%2==0);
		
		//FlatMap
		JavaRDD<String> lines=sc.parallelize(Arrays.asList("Hello world","hello wordl 3","pratham"));
		JavaRDD<String> words=lines.flatMap(line-> Arrays.asList(line.split(" ")).iterator());
		words.collect().forEach(System.out::println);
		
	}
}
