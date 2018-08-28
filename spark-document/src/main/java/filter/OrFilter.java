package filter;

import org.apache.spark.api.java.function.FilterFunction;

import java.util.ArrayList;
import java.util.List;

public class OrFilter implements FilterFunction<String> {

    private List<FilterFunction<String>> filterFunctionList = new ArrayList<>();

    public void add(FilterFunction<String> filterFunction) {
        filterFunctionList.add(filterFunction);
    }

    @Override
    public boolean call(String s) throws Exception {
        for (FilterFunction<String> filterFunction: filterFunctionList) {
            if (filterFunction.call(s)) return true;
        }
        return false;
    }
}
