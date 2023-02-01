package fr.unice.polytech.progfonc;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.*;

public class TD3 {

    // Tail recursive factorial function
    public static int fact(int n) {
        return fact(n, 1);
    }

    private static int fact(int n, int acc) {
        if (n == 0) return acc;
        return fact(n - 1, n * acc);
    }

    // Tail recursive Fibonacci function
    public static int fib(int n) {
        return fib(n, 0, 1);
    }

    private static int fib(int n, int a, int b) {
        if (n == 0) return a;
        return fib(n - 1, a + b, a);
    }

    // Tail recursive function to calculate the greatest common divisor of two integers using Euclid algorithm
    public static int gcd(int a, int b) {
        if (b == 0) return a;
        return gcd(b, a % b);
    }

    public static String binary(int n) {
        return binary(n, "");
    }

    private static String binary(int n, String acc) {
        if (n == 0) return "0" + acc;
        if (n == 1) return "1" + acc;
        return binary(n / 2, acc + ((n % 2 == 0) ? "0" : "1"));
    }

    // Tail recursive function to find minimum element in a list
    public static <T extends Comparable<T>> T min(Lst<T> lst) {
        return min(lst.cdr(), lst.car());
    }

    private static <T extends Comparable<T>> T min(Lst<T> lst, T currentMin) {
        if (lst == null) return currentMin;
        T head = lst.car();
        if (head.compareTo(currentMin) < 0) {
            return min(lst.cdr(), head);
        }
        return min(lst.cdr(), currentMin);
    }

    // Tail recursive function to reverse a linked list
    public static <T> Lst<T> reverse(Lst<T> current) {
        return reverse(current, null);
    }

    private static <T> Lst<T> reverse(Lst<T> current, Lst<T> reversed) {
        if (current == null) return reversed;
        return reverse(current.cdr(), new Lst<>(current.car(), reversed));
    }

    // Tail recursive function to test if a list is a palindrome
    public static <T> boolean palindrome(Lst<T> l) {
        return palindrome(l, reverse(l), TD1.length(l));
    }

    private static <T> boolean palindrome(Lst<T> l, Lst<T> reversed, int size) {
        if (TD1.length(l) == size / 2) return true;
        if (l.car() != reversed.car()) return false;
        return palindrome(l.cdr(), reversed.cdr(), size);
    }

    public static <T> Lst<T> flatten(Lst<?> lst) {
        return null;
    }

//    private static <T> Lst<T> flatten(Lst<?> lst, Lst<T> current) {
//        if (lst == null) return current;
//
//    }

    // Returns a new list that contains only the elements of the input list that are greater than n
    public static Lst<Integer> sup(Lst<Integer> l, int n) {
        return TD2.filter(e -> e.compareTo(n) > 0, l);
    }

    // Returns a list of n occurrences of e
    public static <T> Lst<T> nlist(int n, T e) {
        if (n == 0) return null;
        return new Lst<>(e, nlist(n - 1, e));
    }

    // Produces the list of integers [0, 1, 2, ... n-1]
    public static Lst<Integer> indexes(int n) {
        final AtomicInteger i = new AtomicInteger(0);
        return TD2.map(e -> i.getAndIncrement(), nlist(n, 0));
    }

    // Returns a closure that says if a character is a vowel.
    public static Predicate<Character> isVowel(String vowels) {
        return c -> vowels.contains(String.valueOf(c));
    }

    public static Lst<Character> toLST(String s) {
        return TD2.map(x -> s.charAt(x), indexes(s.length()));
    }

    // Returns a closure that counts the number of vowels in a string.
    public static Function<String, Integer> countVowels(String vowels) {
        return s -> TD2.count(isVowel(vowels), toLST(s));
    }

    // Returns a closure that computes if the string s contains any of the elements of l.
    public static Predicate<String> containsAny(Lst<String> l) {
        return s -> TD2.reduce((s1, res) -> contains(s1).test(s) || res, l, false);
    }

    // Returns a closure that computes if a string contains the substring.
    public static Predicate<String> contains(String sub) {
        return s -> s.contains(sub);
    }

    public static UnaryOperator<Integer> accumulator() {
        AtomicInteger acc = new AtomicInteger(0);
        return i -> acc.addAndGet(i);
    }

    public static int sumAcc(Lst<Integer> list) {
        UnaryOperator<Integer> acc = accumulator();
        return sumAcc(list, acc);
    }

    private static int sumAcc(Lst<Integer> list, UnaryOperator<Integer> acc) {
        if (list == null) return acc.apply(0);
        acc.apply(list.car());
        return sumAcc(list.cdr(), acc);
    }

    // Returns a non-safe closure for a list iterator.
    public static <T> Supplier<T> iterator(Lst<T> l) {
        AtomicReference<Lst<T>> list = new AtomicReference<>(l);
        return () -> list.get() == null ? null : list.getAndSet(list.get().cdr()).car();
    }

    // Returns the memoization of a function.
    public static <T, R> Function<T, R> memo(Function<T, R> f) {
        AtomicReference<Lst<Pair<T, R>>> values = new AtomicReference<>(null);
        return e -> TD1.has(values.get(), e) ? TD1.get(values.get(), e) : TD1.get(values.updateAndGet(l -> new Lst<>(new Pair<>(e, f.apply(e)), l)), e);
    }

    public static <T, U, R> Function<T, Function<U, R>> curried(BiFunction<T, U, R> f) {
        return e -> x -> f.apply(e, x);
    }

    public static <T, U, R> BiFunction<T, U, R> unCurried(Function<T, Function<U, R>> f) {
        return (e, x) -> f.apply(e).apply(x);
    }
}
