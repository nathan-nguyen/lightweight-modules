package mapper;

import org.apache.spark.api.java.function.MapFunction;

public class Mapper implements MapFunction<String, String> {

    private String message;

    public Mapper(String message) {
        this.message = message;
    }

    @Override
    public String call(String s) throws Exception {
        return message;
    }
}
