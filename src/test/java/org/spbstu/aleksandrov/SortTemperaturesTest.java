package org.spbstu.aleksandrov;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.spbstu.aleksandrov.SortTemperatures.*;

public class SortTemperaturesTest {

    static Random random = new Random();

    int n = 100000;
    Temperature[] actualTemperatures = new Temperature[n];
    Temperature[] expectedTemperatures = new Temperature[n];

    private void setup() {
        for (int i = 0; i < n; i++) {
            int r = random.nextInt(7731) - 2730;
            actualTemperatures[i] = new Temperature(r);
            expectedTemperatures[i] = new Temperature(r);
        }
    }

    @Test
    void fastestSortTemperatureTest() {
        setup();
        Arrays.sort(expectedTemperatures);
        fastestSortTemperatures(actualTemperatures);
        assertArrayEquals(expectedTemperatures, actualTemperatures);
    }

    @Test
    void fastSortTemperatureTest() {
        setup();
        Arrays.sort(expectedTemperatures);
        fastSortTemperatures(actualTemperatures);
        assertArrayEquals(expectedTemperatures, actualTemperatures);
    }

    @Test
    void insertionSortTemperatureTest() {
        setup();
        insertionSortTemperatures(actualTemperatures);
        Arrays.sort(expectedTemperatures);
        assertArrayEquals(expectedTemperatures, actualTemperatures);
    }
}
