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
		
		//Union,Intersection,Distinct,Subtract and Cartesian
		JavaRDD<Integer> rdd1=sc.parallelize(Arrays.asList(1,2,3,4,5));
		JavaRDD<Integer> rdd2=sc.parallelize(Arrays.asList(6,2,8,2,5,1,8));
		JavaRDD<Integer>union= rdd1.union(rdd2);                   //all elements [1,2,3,4,5,6,2,8,2,5,1,8] including duplicates
		JavaRDD<Integer>intersection= rdd1.intersection(rdd2);     //common elements [1,2,5]
		JavaRDD<Integer>distinct= rdd2.distinct();		           //excluding duplicates [6,2,8,5,1]
		JavaRDD<Integer> differenceRDD = rdd1.subtract(rdd2);      //[3,4]
		JavaPairRDD<Integer,Integer> cartesianRDD = rdd1.cartesian(rdd2);  //[(1, 6), (1, 2), (1, 8), (1, 2), (1, 5), (1, 1), (1, 8), (2, 6), (2, 2),...]

		//Repartition - to increase or decrease partition
		JavaRDD<Integer> partitionedRdd= rdd1.repartition(5); 
		System.out.println(rdd1.getNumPartitions());
		//Coalesce - it reduces the number of partitions in an RDD to a specified number. 
		JavaRDD<T> coalescedRDD = rdd.coalesce(5);
		
		//SortBy, SortByKey, GroupByKey, ReduceByKey
		List<Tuple2<Character,Integer>> tuples1= Arrays.asList(
				new Tuple2<>('b',1),new Tuple2<>('c',1),new Tuple2<>('a',1),new Tuple2<>('a',2)
		);
		List<Tuple2<Character,Integer>> tuples2= Arrays.asList(
				new Tuple2<>('a',2),new Tuple2<>('b',1)
		);
		JavaPairRDD<Character,Integer> tuplesRdd1=sc.parallelizePairs(tuples1);
		JavaPairRDD<Character,Integer> tuplesRdd2=sc.parallelizePairs(tuples2);
		JavaPairRDD<Character,Integer> sbk= tuplesRdd1.sortByKey(true); //ascending  //[(a,1), (a,2), (b,1), (c,1)]
		JavaPairRDD<Character, Iterable<Integer>> gbk= tuplesRdd1.groupByKey();  //[(a,[1, 2]), (b,[1]), (c,[1])]
		JavaPairRDD<Character,Integer> rbk =tuplesRdd1.reduceByKey((x,y)->x+y);  //[(a,3), (b,1), (c,1)]
		JavaPairRDD<Character,Tuple2<Integer,Integer>>innerjoin=tuplesRdd1.join(tuplesRdd2);  //[(a,(1,2)), (a,(2,2)), (b,(1,1))]
		JavaPairRDD<Character, Tuple2<Integer, Optional<Integer>>>leftjoin=tuplesRdd1.leftOuterJoin(tuplesRdd2); //[(a,(1,Optional[2])), (a,(2,Optional[2])), (b,(1,Optional[1])), (c,(1,Optional.empty))]
		JavaPairRDD<Character, Tuple2<Optional<Integer>, Integer>>rightjoin=tuplesRdd1.rightOuterJoin(tuplesRdd2); //[(a,(Optional[1],2)), (a,(Optional[2],2)), (b,(Optional[1],1))]
		
		//Map,Filter
		JavaRDD<Integer> rdd3=sc.parallelize(Arrays.asList(1,2,3,4,5));
		JavaRDD<Integer> map=rdd3.map(x-> x+2);  //[3,4,5,6,7]
		JavaRDD<Object> d = tuplesRdd1.map(x -> new Tuple2<>(x._1,x._2));
		JavaRDD<Integer> filter=rdd3.filter(x-> x%2==0);  //[2,4]
		
		//FlatMap
		JavaRDD<String> lines=sc.parallelize(Arrays.asList("Hello world","hello wordl 3","pratham")); //List of List of strings -> List of strings
		JavaRDD<String> words=lines.flatMap(line-> Arrays.asList(line.split(" ")).iterator());
		words.collect().forEach(System.out::println);
		
	}
}
