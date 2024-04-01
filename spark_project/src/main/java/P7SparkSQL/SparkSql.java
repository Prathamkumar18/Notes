package P7SparkSQL;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.*;

import static org.apache.spark.sql.functions.*;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FilterFunction;


public class SparkSql {
	public static void main(String args[]) {
		SparkSession ss=SparkSession.builder().master("local[*]").appName("SparkSQL").getOrCreate();
				
		Dataset<Row> df= ss.read().option("header","true").csv("C:/Users/pratham.kumar/eclipse-workspace/spark_projects/src/main/resources/demo.csv");
		System.out.println("===PRINT OUT SCHEMA ===");
		df.printSchema();
		
		System.out.println("===Print 20 records===");
		df.show(20);
		
		System.out.println("===Print the name,income and increment age by 1===");
		df.select(col("Name"),col("Income"),col("Age").plus(1)).show();
		
		System.out.println("===Age greater than 34===");
		df.filter(col("Age").gt(34)).show();
		
		System.out.println("===count of male and female===");
		df.groupBy(col("Gender")).count().show();
		
		System.out.println("===print avg salary and max age");
		df.select(max(col("Age")),avg(col("Income"))).show();
		
		System.out.println("===group by Gender and aggregate by avg income and max age");
		df.groupBy(col("Gender")).agg(avg(col("Income")),max(col("Age"))).show();
		
		System.out.println("===Sort by age in descending order===");
		df.orderBy(col("age").desc()).show();
		
		System.out.println("===Group by location and order by count in asc order");
		df.groupBy("Location").count().orderBy(count(col("Location"))).show();
		
			
		//House problem: To read house data from RealEstate.csv, group by loc. aggregate the avg. price per sq ft and max price and sort by avg price per sq ft.
		Dataset<Row> houseData= ss.read().option("header","true").csv("C:/Users/pratham.kumar/eclipse-workspace/spark_projects/src/main/resources/RealEstate.csv");
		houseData.groupBy(col("Location")).agg(avg(col("Price")),max(col("Price"))).orderBy(avg(col("Price")).desc()).show();

		//JOINS:
		Dataset<Row> emp= ss.read().option("header","true").csv("C:/Users/pratham.kumar/eclipse-workspace/spark_projects/src/main/resources/employees.csv");
		Dataset<Row> dpt= ss.read().option("header","true").csv("C:/Users/pratham.kumar/eclipse-workspace/spark_projects/src/main/resources/departments.csv");
		Dataset<Row> innerJoin = emp.join(dpt, emp.col("department_id").equalTo(dpt.col("department_id")), "inner");
		innerJoin.show();
		Dataset<Row> left_outer= emp.join(dpt,dpt.col("department_id").equalTo(emp.col("department_id")),"leftouter");
		left_outer.show();
		Dataset<Row> right_outer= emp.join(dpt,dpt.col("department_id").equalTo(emp.col("department_id")),"rightouter");
		right_outer.show();
		Dataset<Row> full_outer= emp.join(dpt,dpt.col("department_id").equalTo(emp.col("department_id")),"fullouter");
		full_outer.show();
		
		//Conversion from dataset to RDD:
		Dataset<Row> data=ss.read().option("header","true").csv("C:/Users/pratham.kumar/eclipse-workspace/spark_projects/src/main/resources/employees.csv").cache();
		JavaRDD<Row> rddFromDataset= data.rdd().toJavaRDD();
		rddFromDataset.collect().forEach(System.out::println);
		
		//Conversion from RDD to dataset
		JavaSparkContext sc = new JavaSparkContext(ss.sparkContext());
		JavaRDD<String> rddData=sc.textFile("C:/Users/pratham.kumar/eclipse-workspace/spark_projects/src/main/resources/employees.csv");
	    Dataset<String> dataset = ss.createDataset(rddData.rdd(), Encoders.STRING());
	    dataset.show();
      
		//STRONGLY-TYPED:
		StructType schema = new StructType()
                .add("id", DataTypes.IntegerType)
                .add("name", DataTypes.StringType)
                .add("age", DataTypes.IntegerType);
		Dataset<Row> personDataset=ss.read().option("header","true").schema(schema).csv("C:/Users/pratham.kumar/eclipse-workspace/spark_projects/src/main/resources/person.csv");
        Dataset<Person> person = personDataset.as(Encoders.bean(Person.class));
        person.show();
        
        Dataset<Person> filteredPerson = person.filter((FilterFunction<Person>)p -> p.getAge() > 30);
        filteredPerson.show();
        
        Dataset<Row> selectedData = person.select("age", "name");
        selectedData.show();
        
        Dataset<Person> orderedPerson = person.orderBy(col("age").desc());
        orderedPerson.show();
	}
}
