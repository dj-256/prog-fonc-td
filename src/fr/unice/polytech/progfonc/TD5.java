package fr.unice.polytech.progfonc;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;

public class TD5 {

    public static <T> int length(OptionalLst<T> l) {
        return l.cdr().map(TD5::length).orElse(0);
    }

    public static <T> boolean member(T val, OptionalLst<T> l) {
        return l.car().equals(val) || l.cdr().map(cdr -> member(val, cdr)).orElse(false);
    }

    public static <T> OptionalLst<T> append(OptionalLst<T> l1, OptionalLst<T> l2) {
        return l1.cdr().map(cdr -> new OptionalLst<>(l1.car(), Optional.of(append(cdr, l2)))).orElse(l2);
    }

    public static <T, K> OptionalLst<K> map(Function<T, K> f, OptionalLst<T> l) {
        return l.cdr().map(cdr -> new OptionalLst<>(f.apply(l.car()), Optional.of(map(f, cdr)))).orElse(null);
    }

    public static <T> OptionalLst<T> filter(Function<T, Boolean> f, OptionalLst<T> l) {
        return l.cdr().map(cdr -> {
            if (f.apply(l.car())) {
                return new OptionalLst<>(l.car(), Optional.of(filter(f, cdr)));
            } else {
                return filter(f, cdr);
            }
        }).orElse(null);
    }

    public static Map<Character, List<String>> groupByInitial(Stream<String> s) {
        return s.collect(groupingBy(str -> str.charAt(0)));
    }

    public static Map<Class, List<Object>> groupByClass(Stream<Object> s) {
        return s.collect(groupingBy(Object::getClass));
    }

}