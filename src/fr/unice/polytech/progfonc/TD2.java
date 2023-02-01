package fr.unice.polytech.progfonc;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public class TD2 {

    public static <T, R> Lst<R> map(Function<T, R> f, Lst<T> l) {
        if (l == null) return null;
        else return new Lst<>(f.apply(l.car()), map(f, l.cdr()));
    }

    public static Lst<Integer> squares(Lst<Integer> l) {
        return map(i -> i * i, l);
    }

    public static Lst<Integer> sizeOfStrings(Lst<String> l) {
        return map(String::length, l);
    }

    public static <T> Lst<T> filter(Predicate<T> f, Lst<T> l) {
        if (l == null) return null;
        if (f.test(l.car())) {
            return new Lst<>(l.car(), filter(f, l.cdr()));
        } else {
            return filter(f, l.cdr());
        }
    }

    public static Lst<String> lowers(Lst<String> l) {
        return filter(s -> Character.isLowerCase(s.charAt(0)), l);
    }

    public static <T> int count(Predicate<T> f, Lst<T> l) {
        return TD1.length(filter(f, l));
    }

    public static int nbPositives(Lst<Integer> l) {
        return count(i -> i >= 0, l);
    }

    public static <T, R> R reduce(BiFunction<T, R, R> f, Lst<T> l, R init) {
        if (l == null) return init;
        else return reduce(f, l.cdr(), f.apply(l.car(), init));
    }

    public static int sum(Lst<Integer> l) {
        return reduce(Integer::sum, l, 0);
    }

    public static <T extends Comparable<T>> T min(Lst<T> l) {
        return reduce((i1, i2) -> i1.compareTo(i2) < 0 ? i1 : i2, l, l.car());
    }

    public static int sumLengthLowers(Lst<String> l) {
        return sumLength(lowers(l));
    }

    public static int sumLength(Lst<String> l) {
        return sum(sizeOfStrings(l));
    }

    public static <T> String repr(Lst<T> l) {
        return reduce((i, s) -> s + i.toString() + " ", l, "(") + ")";
    }

    public static <T> Lst<T> concat(Lst<Lst<T>> ll) {
        return reduce((l, res) -> TD1.append(res, l), ll, null);
    }

    public static <T> Lst<T> union(Lst<T> s1, Lst<T> s2) {
        return toSet(TD1.append(s1, s2));
    }

    public static <T> Lst<T> toSet(Lst<T> l) {
        return TD1.unique(l);
    }

    public static <T> Predicate<T> equalsTo(T x) {
        return t -> t.equals(x);
    }

    public static <T extends Comparable<T>> Predicate<T> between(T a, T b) {
        return t -> t.compareTo(a) >= 0 && t.compareTo(b) <= 0;
    }

    public static <T> int countOccurence(Lst<T> l, T e) {
        return TD1.length(filter(equalsTo(e), l));
    }
}
