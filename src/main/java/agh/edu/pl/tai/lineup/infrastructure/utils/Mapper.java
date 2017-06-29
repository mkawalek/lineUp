package agh.edu.pl.tai.lineup.infrastructure.utils;

import java.util.Collection;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;

public class Mapper {

    public static <T, LT extends Collection<T>, LR extends Collection<R>, R> LR mapCollection(LT t, Function<T, R> fun, Collector<R, ?, LR> collector) {
        return t.stream().map(fun).collect(collector);
    }

    public static <T, L extends Collection<T>> L filterCollection(L t, Predicate<T> fun, Collector<T, ?, L> collector) {
        return t.stream().filter(fun).collect(collector);
    }

}
