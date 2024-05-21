package P2RddActions;

import java.util.Arrays;
import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

public class SparkActions {
	public static void main(String args[]) {
	SparkConf sparkConf=new SparkConf().setMaster("local[*]").setAppName("SparkActions");
        JavaSparkContext sc = new JavaSparkContext(sparkConf);

        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);
        JavaRDD<Integer> rdd = sc.parallelize(list);

        // collect: Retrieve all elements as a list
        List<Integer> allElements = rdd.collect();

        // count: Count the number of elements in the RDD
        long elementCount = rdd.count();

        // take: Retrieve the first n elements as a list
        List<Integer> firstFive = rdd.take(5);

        // first: Retrieve the first element
        int firstElement = rdd.first();

        // reduce: It combines the elements of the RDD pairwise until only a single result remains. 
        int sumAll = rdd.reduce((a, b) -> a + b); //sum of all the elements.
        int min = rdd.reduce((a, b) -> Math.min(a, b)); // min of all elements.

        // fold: Sum all elements with initial value as 0.
        int sumAllFolded = rdd.fold(0,(a, b) -> a + b);
        
        int maxOfPartitionsSum = rdd.aggregate(0, Integer::sum, Integer::max);//first function will get the sum from each partition and second will take max of all partition.
//        eg.let say there are 9 element and 3 Partitions. let in partition1- [1,2,3] , in partition2- [4,5,6] and in partition3- [7,8,9].
//         Integer::sum -> partition[1]=6, partition[2]=15, partition[3]=24. 
//         Integer::max -> max(6,15,24)=24. 

        System.out.println("Total no. of partitions"+rdd.getNumPartitions());
        System.out.println("All elements: " + allElements);
        System.out.println("Element count: " + elementCount);
        System.out.println("First five elements: " + firstFive);
        System.out.println("First element: " + firstElement);
        System.out.println("Sum of all elements: " + sumAll);
        System.out.println("Sum of all elements starting from 0: " + sumAllFolded);
        System.out.println("max of all partitions sum: "+ maxOfPartitionsSum);

	}
}
