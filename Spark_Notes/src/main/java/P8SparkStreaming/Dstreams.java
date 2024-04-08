package P8SparkStreaming;


import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;

import scala.Tuple2;

public class Dstreams {
	public static void main(String args[]) throws InterruptedException {
		Logger.getLogger("org.apache").setLevel(Level.WARN);
		Logger.getLogger("org.apache.spark.storage").setLevel(Level.ERROR);
		
		SparkConf conf=new SparkConf().setMaster("local[*]").setAppName("Dstreams");
		JavaStreamingContext sc=new JavaStreamingContext(conf,Durations.seconds(5));
 		
		 JavaReceiverInputDStream<String> inputData = sc.socketTextStream("localhost",8989);
 		 JavaPairDStream<Object, Long> mappedData = inputData.mapToPair(item -> new Tuple2<>(item.split(",")[0],1L));
 		 mappedData=mappedData.reduceByKeyAndWindow((x, y)->x+y, Durations.seconds(5)); //keyAndWindow will aggregate the sum from the prev. window.
 		 mappedData.print();
 		 
 		 sc.start();
 		 sc.awaitTermination();
 	}
}
