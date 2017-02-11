import java.util.Arrays;

/**
 * Created by Baoyi Chen on 2017/2/10.
 */
public class CountingBitSort {
    private int[] count;
    private final long[] ary;
    private static final int BITS = 6;
    private static final int MASK = 1 << 6;

    public CountingBitSort(int max) {
        int x = max >> BITS;
        int y = max & (MASK - 1);
        if (y > 0) x = x + 1;
        this.ary = new long[x];
    }

    /**
     * @param i 0->Integer.MAX_VALUE
     */
    public void sort(int i) {
        int x = i >> BITS;
        int y = i & (MASK - 1);
        ary[x] |= (1L << y);
    }

    /**
     * @param i 0->Integer.MAX_VALUE
     */
    public boolean contains(int i) {
        int x = i >> BITS;
        int y = i & (MASK - 1);
        return ((ary[x] >>> y) & 1) != 0;
    }

    public void postSort() {
        this.count = new int[ary.length + 1];
        int total = 0;
        for (int idx = 1; idx < count.length; idx++) {
            total += Long.bitCount(ary[idx - 1]);
            count[idx] = total;
        }
    }

    /**
     * @param i 0->Integer.MAX_VALUE
     */
    public int getIndex(int i) {
        int x = i >> BITS;
        int y = i & (MASK - 1);
        if (((ary[x] >>> y) & 1) == 0) return -1;
        if (y == 0) return count[x];
        return count[x] + Long.bitCount(ary[x] << (MASK - y) >>> (MASK - y));
    }

    public String toString() {
        return Arrays.toString(ary);
    }

    public static void main(String[] args) {
        long et, st = System.currentTimeMillis();
        CountingBitSort cb = new CountingBitSort(Integer.MAX_VALUE);
        for (int i = Integer.MAX_VALUE - 1; i >= 0; i--) {
            cb.sort(i);
        }
        et = System.currentTimeMillis();
        System.out.println("sort:" + (et - st) / 1000d);
        st = System.currentTimeMillis();
        cb.postSort();
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            if (i != cb.getIndex(i)) {
                System.out.println("error" + i);
            }
        }
        et = System.currentTimeMillis();
        System.out.println("sort checking:" + (et - st) / 1000d);
    }
}
