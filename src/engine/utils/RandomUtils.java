package engine.utils;

import java.util.Random;

public class RandomUtils {

    public static int[] getBoundedNonNegativeInts(int bound, int k) {
        return new Random().ints(0, bound).distinct().limit(k).toArray();
    }
}
