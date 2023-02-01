package fr.unice.polytech.progfonc;

public class TD1 {

    public static <T> int length(Lst<T> l) {
        if (l == null) {
            return 0;
        } else {
            return 1 + length(l.cdr());
        }
    }

    public static <T> boolean member(T val, Lst<T> l) {
        if (l == null) {
            return false;
        } else {
            return l.car().equals(val) || member(val, l.cdr());
        }
    }

    public static <T> Lst<T> append(Lst<T> l1, Lst<T> l2) {
        if (l1 == null) {
            return l2;
        } else {
            return new Lst<>(l1.car(), append(l1.cdr(), l2));
        }
    }

    public static int sum(Lst<Integer> l) {
        if (l == null) return 0;
        else return l.car() + sum(l.cdr());
    }

    public static <T> Lst<T> remove(T val, Lst<T> l) {
        if (l == null) {
            return null;
        } else if (l.car().equals(val)) {
            return new Lst<>(l.cdr().car(), l.cdr().cdr());
        } else {
            return new Lst<>(l.car(), remove(val, l.cdr()));
        }
    }

    public static <T> Lst<T> removeAll(T val, Lst<T> l) {
        if (l == null) {
            return null;
        } else if (l.car().equals(val)) {
            return removeAll(val, l.cdr());
        } else {
            return new Lst<>(l.car(), removeAll(val, l.cdr()));
        }
    }

    public static Lst<String> fizzbuzz(int a, int b) {
        if (a >= b) {
            return null;
        } else if (a % 3 == 0 && a % 5 == 0) {
            return new Lst<>("FizzBuzz", fizzbuzz(a + 1, b));
        } else if (a % 3 == 0) {
            return new Lst<>("Fizz", fizzbuzz(a + 1, b));
        } else if (a % 5 == 0) {
            return new Lst<>("Buzz", fizzbuzz(a + 1, b));
        } else {
            return new Lst<>(String.valueOf(a), fizzbuzz(a + 1, b));
        }
    }

    public static <T> Lst<T> fromArray(T[] arr) {
        if (arr.length == 0) {
            return null;
        } else {
            return new Lst<>(arr[0], fromArray(arr, 1));
        }
    }

    private static <T> Lst<T> fromArray(T[] arr, int i) {
        if (i >= arr.length) {
            return null;
        } else {
            return new Lst<>(arr[i], fromArray(arr, i + 1));
        }
    }

    public static <T> Lst<T> reverse(Lst<T> l) {
        if (l == null) return null;
        else return append(reverse(l.cdr()), new Lst<>(l.car(), null));
    }

    public static <T extends Comparable<T>> Lst<T> insert(T val, Lst<T> l) {
        if (l == null) return new Lst<>(val, null);
        else if (val.compareTo(l.car()) < 0) {
            return new Lst<>(val, l);
        } else return new Lst<>(l.car(), insert(val, l.cdr()));
    }

    public static <T extends Comparable<T>> Lst<T> sort(Lst<T> l) {
        if (l == null) return null;
        else return insert(l.car(), sort(l.cdr()));
    }

    public static <T> Lst<T> take(int n, Lst<T> l) {
        if (n == 0 || l == null) return null;
        else return new Lst<>(l.car(), take(n - 1, l.cdr()));
    }

    public static <T> int indexOf(T val, Lst<T> l) {
        if (l == null) return -1;
        if (l.car() == val) return 0;
        else {
            int res = indexOf(val, l.cdr());
            if (res < 0) return res;
            else return 1 + res;
        }
    }

    public static <T> Lst<T> unique(Lst<T> l) {
        if (l == null) return null;
        else {
            T val = l.car();
            if (member(val, l.cdr())) return unique(l.cdr());
            else return new Lst<>(l.car(), unique(l.cdr()));
        }
    }

    public static <T, U> boolean has(Lst<Pair<T, U>> l, T k) {
        if (l == null) return false;
        if (l.car().key() == k) return true;
        return has(l.cdr(), k);
    }

    public static <T, U> U get(Lst<Pair<T, U>> l, T k) {
        if (l == null) return null;
        if (l.car().key() == k) return l.car().value();
        return get(l.cdr(), k);
    }

    public static <T, U> Lst<Pair<T, U>> set(Lst<Pair<T, U>> l, T k, U v) {
        if (l == null) return new Lst<>(new Pair<>(k, v), null);
        if (l.car().key() == k) return new Lst<>(new Pair<>(l.car().key(), v), l.cdr());
        return set(l.cdr(), k, v);
    }
}
