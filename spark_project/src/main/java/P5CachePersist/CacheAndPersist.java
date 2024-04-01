package P5CachePersist;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.storage.StorageLevel;

public class CacheAndPersist {
	public static void main(String args[]) {
		SparkConf conf=new SparkConf().setMaster("local[*]").setAppName("Cache_Persist");
		JavaSparkContext sc=new JavaSparkContext(conf);
		
        JavaRDD<String> inputFile=sc.textFile("C:/Users/pratham.kumar/eclipse-workspace/spark_projects/src/main/resources/words.txt");
        inputFile.cache().persist(StorageLevel.MEMORY_ONLY());
        
        //Note: Cache and persist is effective only for the larger Datasets.
	}
}