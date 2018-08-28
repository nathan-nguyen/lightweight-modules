import filter.AndFilter;
import filter.ContainsFilter;
import filter.OrFilter;
import mapper.Mapper;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.SparkSession;
import reducer.Reducer;

public class SparkLocalFileWithCustomFilterMapper {
    public static void main(String[] args) {
        SparkSession sparkSession = SparkSession.builder().master("local").appName("Spark Application").getOrCreate();
        Dataset<String> dataset = sparkSession.read().textFile(args[0]).cache();

        // To be able to process dataset multiple times, we need to cache it
        OrFilter orFilter = new OrFilter();
        AndFilter andFilter = new AndFilter();

        for (int i = 1; i < args.length; ++i) {
            orFilter.add(new ContainsFilter(args[i]));
            andFilter.add(new ContainsFilter(args[i]));
        }

        Mapper orMapper = new Mapper("Spark");
        Mapper andMapper = new Mapper("Document");

        Reducer reducer = new Reducer();

        System.out.println(dataset.filter(orFilter).map(orMapper, Encoders.STRING()).reduce(reducer));
        System.out.println(dataset.filter(andFilter).map(andMapper, Encoders.STRING()).reduce(reducer));

        sparkSession.stop();
    }
}