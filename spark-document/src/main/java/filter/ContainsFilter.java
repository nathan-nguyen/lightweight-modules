package filter;

import org.apache.spark.api.java.function.FilterFunction;

// Just one example for Filter class
public class ContainsFilter implements FilterFunction<String> {

    private String key;

    public ContainsFilter(String key) {
        this.key = key;
    }

    @Override
    public boolean call(String s) throws Exception {
        return s.contains(key);
    }
}
