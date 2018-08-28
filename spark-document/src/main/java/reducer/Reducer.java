package reducer;

import org.apache.spark.api.java.function.ReduceFunction;

public class Reducer implements ReduceFunction<String> {

    @Override
    public String call(String s, String t1) throws Exception {
        return s + " " + t1;
    }
}