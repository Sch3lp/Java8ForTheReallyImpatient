package chapter2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Ch2 {

    /**
     * Exercise 4
     */
    public static IntStream intStream(int... intVals) {
        //return Stream.of(intVals);//Stream of ints boxed to Integers
        return IntStream.of(intVals); //Stream of ints
    }

    /**
     * Exercise 5
     */
    public static Stream<Long> randomInfiniteStream(long a, int c, double m) {
        return Stream.iterate(0L, (x) -> (a * x + c) % Double.doubleToLongBits(m));
    }

    /**
     * Exercise 8
     */
    public static <T> Stream<T> zip(Stream<T> first, Stream<T> second) {
        Iterator<T> itFirst = first.iterator();
        Iterator<T> itSecond = second.iterator();

        Iterator<T> zippedIterator = new ZippedIterator<>(itFirst, itSecond);
        return asStream(zippedIterator);
    }

    private static class ZippedIterator<T> implements Iterator<T> {
        private final Iterator<T> itFirst;
        private final Iterator<T> itSecond;
        boolean firstsTurn;

        public ZippedIterator(Iterator<T> itFirst, Iterator<T> itSecond) {
            this.itFirst = itFirst;
            this.itSecond = itSecond;
            firstsTurn = true;
        }

        @Override
        public boolean hasNext() {
            return !firstsTurn || (itFirst.hasNext() && itSecond.hasNext());
        }

        @Override
        public T next() {
            T nextVal = firstsTurn ? itFirst.next() : itSecond.next();
            firstsTurn = !firstsTurn;
            return nextVal;
        }
    }

    //from http://stackoverflow.com/questions/24511052/java8-iterator-to-stream
    private static <T> Stream<T> asStream(Iterator<T> sourceIterator) {
        Iterable<T> iterable = () -> sourceIterator;
        return StreamSupport.stream(iterable.spliterator(), false);
    }

    /**
     * Exercise 9
     */
    public static ArrayList<String> reduceList_1(Stream<ArrayList<String>> input) {
        return input.reduce((prev, cur)-> {
            prev.addAll(cur);
            return prev;
        }).orElse(new ArrayList<>());
    }

    public static ArrayList<String> reduceList_2(Stream<ArrayList<String>> input) {
        return input.reduce(new ArrayList<>(),
                (prev, cur) -> {
                    prev.addAll(cur);
                    return prev;
                });
    }

    public static ArrayList<String> reduceList_3(Stream<ArrayList<String>> input) {
        return input.reduce(new ArrayList<>(),
                (initialArray, oneOfTheArrayLists) -> {
                    initialArray.addAll(oneOfTheArrayLists);
                    return initialArray;
                },
                (prev, cur) -> {
                    prev.addAll(cur);
                    return prev;
                });
    }
}
