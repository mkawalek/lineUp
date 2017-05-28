package agh.edu.pl.tai.lineup.infrastructure.utils;

import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collector;

public class Mapper {

    public static <T, LT extends Collection<T>, LR extends Collection<R>, R> LR mapCol(LT t, Function<T, R> fun, Collector<R, ?, LR> collector) {
        return t.stream().map(fun).collect(collector);
    }

}
