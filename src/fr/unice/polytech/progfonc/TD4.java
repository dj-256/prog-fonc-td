package fr.unice.polytech.progfonc;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public class TD4 {

    static IntPredicate isPrime = x -> true;

    // Returns the maximum of a stream of comparable.
    public static <T extends Comparable<T>> Optional<T> max(BasicStream<T> s) {
        return s.reduce((a, b) -> a.compareTo(b) > 0 ? a : b);
    }

    // Returns a String composed of a representation of the stream elements joined together with specified delimiter.
    public static <T> String join(BasicStream<T> s, String delimiter) {
        return s.map(Objects::toString)
                .reduce((a, b) -> a + delimiter + b)
                .orElse("");
    }

    // Returns the average of all the even numbers in a given stream of integers. 0 if none.
    public static double averageEven(BasicStream<Integer> s) {
        AtomicInteger sum = new AtomicInteger(0);
        AtomicInteger count = new AtomicInteger(0);
        s.filter(i -> i % 2 == 0)
                .forEach(i -> {
                    sum.addAndGet(i);
                    count.incrementAndGet();
                });
        return count.get() == 0 ? 0 : (double) sum.get() / count.get();
    }

    // Returns a stream based on a Lst list.
    public static <T> BasicStream<T> toStream(Lst<T> l) {
        return new SupplierStream<>(new Supplier<>() {
            Lst<T> list = l;

            @Override
            public Optional<T> get() {
                if (list == null) return Optional.empty();
                T element = list.car();
                list = list.cdr();
                return Optional.of(element);
            }
        });
    }

    // Returns the sum of n first numbers containing only digits 1 and 0 that are greater or equal than m
    public static long sum10(long m, int n) {
        return longStream()
                .filter(e -> e >= m)
                .filter(e -> TD2.reduce((a, res) -> (a == '0' || a == '1') && res, TD3.toLST(e.toString()), true))
                .limit(n)
                .reduce(Long::sum)
                .orElse(0L);
    }

    // Returns a stream of successive integers from 0 to "infinity"
    public static BasicStream<Long> longStream() {
        return new SupplierStream<>(new Supplier<>() {
            long count = 0;

            @Override
            public Optional<Long> get() {
                return Optional.of(count++);
            }
        });
    }

    // Returns the square of all even numbers in a given list of integers.
    public static List<Integer> squareOfEven(List<Integer> l) {
        List<Integer> res = new ArrayList<>();
        toStream(l)
                .filter(x -> x % 2 == 0)
                .map(x -> x * x)
                .forEach(res::add);
        return res;
    }

    // Returns a stream based on a list.
    public static <T> BasicStream<T> toStream(List<T> l) {
        return new SupplierStream<>(new Supplier<>() {
            int count = 0;

            @Override
            public Optional<T> get() {
                if (count < l.size()) {
                    return Optional.of(l.get(count++));
                } else {
                    return Optional.empty();
                }
            }
        });
    }

    // Returns the average of all the numbers in a given list of integers.
    public static OptionalDouble average(List<Integer> l) {
        Optional<Double> sum = toStream(l)
                .map(Integer::doubleValue)
                .reduce(Double::sum);
        return sum.map(aDouble -> OptionalDouble.of(aDouble / l.size())).orElseGet(OptionalDouble::empty);
    }

    // Returns the longest word in a given list of strings.
    public static Optional<String> longest(List<String> l) {
        return toStream(l)
                .reduce((s1, s2) -> s1.length() > s2.length() ? s1 : s2);
    }

    // Returns the most common element in a given list of integers.
    public static <T> Optional<T> mostCommon(List<T> l) {
        return toStream(l)
                .map(x -> new Pair<>(x, TD2.countOccurence(TD1.fromArray(l.toArray()), x)))
                .reduce((a, b) -> a.value() > b.value() ? a : b)
                .map(Pair::key);
    }

    // Returns the first n Fibonacci numbers.
    public static List<Integer> fibs(int n) {
        AtomicInteger a = new AtomicInteger(0);
        AtomicInteger b = new AtomicInteger(1);
        return collect(longStream().limit(n)
                        .map(Long::intValue)
                        .map(x -> {
                            if (x == 0) return 0;
                            if (x == 1) return 1;
                            int v = a.get();
                            return a.getAndSet(b.get()) + b.getAndSet(v + b.get());
                        }),
                n);
    }

    // Returns a list that contains the n first elements of a stream.
    public static <T> List<T> collect(BasicStream<T> s, int n) {
        ArrayList<T> res = new ArrayList<>();
        s.limit(n).forEach(res::add);
        return res;
    }

    // Returns an int stream of prime numbers based on eratosthenes sieve.
    public static IntStream eratosthenes() {
        AtomicReference<Predicate<Integer>> isPrime = new AtomicReference<>(x -> true);
        return IntStream.iterate(2, x -> x + 1)
                .filter(x -> isPrime.get().test(x))
                .peek(x -> isPrime.set(isPrime.get().and(v -> v % x != 0)));
    }
}



