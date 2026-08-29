package com.takoy3466.manaitapp.util;

public class UniqueUtil {

    @FunctionalInterface
    public interface TriPredicate<A, B, C> {
        boolean test(A a, B b, C c);
    }

    @FunctionalInterface
    public interface FourConsumer<A, B, C, D> {
        void accept(A a, B b, C c, D d);
    }
}
