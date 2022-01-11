package h05;

import com.google.common.collect.Range;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ThreadLocalRandomTester {
    private static final InheritableThreadLocal<ThreadLocalRandomTester> factory = new InheritableThreadLocal<>();
    private final boolean loop;
    private final ArrayList<Range<Integer>> usedRanges = new ArrayList<>();
    private final ArrayList<Integer> results = new ArrayList<>();
    private final ArrayList<Integer> numberOverwrites;
    private int currentIndex;

    public ThreadLocalRandomTester(ArrayList<Integer> numberOverwrites, boolean loop) {
        this.numberOverwrites = numberOverwrites;
        this.loop = loop;
    }

    public ThreadLocalRandomTester() {
        this(null, false);
    }

    public static void initialize(ArrayList<Integer> numberOverwrites, boolean loop) {
        factory.set(new ThreadLocalRandomTester(numberOverwrites, loop));
    }

    public static void initialize() {
        initialize(null, false);
    }

    public static void initializeOriginal() {
        factory.set(new ThreadLocalRandomTester());
    }

    public static void removeCurrentTester() {
        factory.remove();
    }

    /**
     * Replaces {@link ThreadLocalRandom#current()}
     */
    public static ThreadLocalRandomTester current() {
        return factory.get();
    }

    public ArrayList<Integer> getNumberOverwrites() {
        return numberOverwrites;
    }

    public boolean isLoop() {
        return loop;
    }

    public ArrayList<Integer> getResults() {
        return results;
    }

    public ArrayList<Range<Integer>> getUsedRanges() {
        return usedRanges;
    }

    /**
     * Replaces {@link ThreadLocalRandom#nextInt(int, int)}
     */
    public int nextInt(int lower, int upper) {
        usedRanges.add(Range.closed(lower, upper));
        int result;
        if (numberOverwrites != null && numberOverwrites.size() > 0 && currentIndex < numberOverwrites.size()) {
            results.add(
                result = numberOverwrites.get(loop ? (currentIndex++ % numberOverwrites.size()) : currentIndex++));
        } else {
            results.add(result = ThreadLocalRandom.current().nextInt(lower, upper));
        }
        return result;
    }

    /**
     * Replaces {@link ThreadLocalRandom#ints(int, int)}
     */
    public IntStream ints(int lower, int upper) {
        usedRanges.add(Range.closed(lower, upper));
        IntStream result;
        if (numberOverwrites != null && numberOverwrites.size() > 0) {
            results.addAll(numberOverwrites);
            result = numberOverwrites.stream().mapToInt(Integer::intValue);
        } else {
            var numbers = ThreadLocalRandom.current().ints(100).boxed()
                .collect(Collectors.toCollection(ArrayList::new));
            results.addAll(numbers);
            result = numbers.stream().mapToInt(Integer::intValue);
        }
        return result;
    }

    /**
     * Replaces {@link ThreadLocalRandom#nextInt(int)}
     */
    public int nextInt(int a) {
        return nextInt(0, a);
    }

    /**
     * Replaces {@link ThreadLocalRandom#nextInt(int)}
     */
    public boolean nextBoolean() {
        return nextInt(0, 2) != 0;
    }

    /**
     * Replaces {@link ThreadLocalRandom#nextInt()}
     */
    public int nextInt() {
        return nextInt(0, 200);
    }

    /**
     * Replaces {@link ThreadLocalRandom#nextFloat()}
     */
    public float nextFloat() {
        throw new UnsupportedOperationException("nextFloat() was called. Test manually.");
    }

    /**
     * Replaces {@link ThreadLocalRandom#nextDouble()}
     */
    public double nextDouble() {
        throw new UnsupportedOperationException("nextDouble() was called. Test manually.");
    }

    /**
     * Replaces {@link ThreadLocalRandom#nextDouble(double, double)}
     */
    public double nextDouble(double a, double b) {
        throw new UnsupportedOperationException("nextDouble(double, double) was called. Test manually.");
    }

    /**
     * Replaces {@link ThreadLocalRandom#nextDouble(double)}
     */
    public double nextDouble(double a) {
        throw new UnsupportedOperationException("nextDouble(double) was called. Test manually.");
    }
}
