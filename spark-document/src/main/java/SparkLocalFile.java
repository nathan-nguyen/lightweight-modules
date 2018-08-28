import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.SparkSession;

public class SparkLocalFile {
    public static void main(String[] args) {
        SparkSession sparkSession = SparkSession.builder().master("local").appName("Spark Application").getOrCreate();
        Dataset<String> dataset = sparkSession.read().textFile(args[0]).cache();

        // To be able to process dataset multiple times, we need to cache it
        for (int i = 1; i < args.length; ++i) {
            final String keyWord = args[i];
            long lineCount = dataset.filter(s -> s.contains(keyWord)).count();
            System.out.println("There are " + lineCount + " line(s) contains word: " + args[i]);
        }

        sparkSession.stop();
    }
}