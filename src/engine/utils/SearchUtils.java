package engine.utils;

import java.util.Arrays;

public class SearchUtils {

    public static int search(double[] arr, double key) {
        int idx = Arrays.binarySearch(arr, key);
        /* From the documentation:
            Returns:
            index of the search key, if it is contained in the array;
            otherwise, (-(insertion point) - 1). The insertion point
            is defined as the point at which the key would be inserted
            into the array: the index of the first element greater than
            the key, or a.length if all elements in the array are less
            than the specified key. Note that this guarantees that the
            return value will be >= 0 if and only if the key is found.
         */
        return (idx >= 0) ? (idx) : ((-1) * idx - 1);
    }

}
