package fr.unice.polytech.progfonc;

import java.util.Optional;
import java.util.function.*;

public class SupplierStream<T> implements BasicStream<T> {

    private final Supplier<Optional<T>> supplier;

    SupplierStream(Supplier<Optional<T>> f) {
        this.supplier = f;
    }


    @Override
    public void forEach(Consumer<T> action) {
        Optional<T> element = supplier.get();
        while (element.isPresent()) {
            action.accept(element.get());
            element = supplier.get();
        }
    }

    @Override
    public Optional<T> reduce(BinaryOperator<T> accumulator) {
        Optional<T> element1 = supplier.get();
        Optional<T> element2 = supplier.get();
        if (element1.isEmpty() || element2.isEmpty()) return Optional.empty();
        T res = accumulator.apply(element1.get(), element2.get());
        Optional<T> element = supplier.get();
        while (element.isPresent()) {
            res = accumulator.apply(element.get(), res);
            element = supplier.get();
        }
        return Optional.of(res);
    }

    @Override
    public BasicStream<T> filter(Predicate<T> predicate) {
        return new SupplierStream<>(() -> {
            Optional<T> element = supplier.get();
            while (element.isPresent() && !predicate.test(element.get())) {
                element = supplier.get();
            }
            return element;
        });
    }

    @Override
    public BasicStream<T> limit(long maxSize) {
        return new SupplierStream<>(new Supplier<>() {
            int i = 0;

            @Override
            public Optional<T> get() {
                if (i++ < maxSize) {
                    Optional<T> element = supplier.get();
                    if (element.isPresent()) return element;
                }
                return Optional.empty();
            }
        });
    }

    @Override
    public <R> BasicStream<R> map(Function<T, R> mapper) {
        return new SupplierStream<>(() -> {
            Optional<T> element = supplier.get();
            return element.map(mapper);
        });
    }
}
