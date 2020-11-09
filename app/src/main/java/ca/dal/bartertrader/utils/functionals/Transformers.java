package ca.dal.bartertrader.utils.functionals;

import java.util.List;
import java.util.function.Predicate;

public class Transformers {

    public static Boolean every(List<Boolean> booleans) {
        return booleans.stream().allMatch(bool -> bool != null & bool);
    }

}