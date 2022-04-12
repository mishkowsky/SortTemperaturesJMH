package org.spbstu.aleksandrov;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Benchmark)
@Fork(value = 1, jvmArgs = {"-Xms2G", "-Xmx2G"})
@Warmup(iterations = 5)
@Measurement(iterations = 10)
public class MyBenchmark {

    static Random random = new Random();

    @Param({"7731", "112000", "223000", "334000", "445000", "556000", "667000", "778000", "889000", "1000000"})
    public int n = 7731;
    @Param({"false", "true"})
    public boolean edge = false;
    public SortTemperatures.Temperature[] t;

    @Setup(Level.Invocation)
    public void setup() {
        t = new SortTemperatures.Temperature[n];
        if (edge) {
            int bucket = n / (2730 + 1 + 5000);
            int index = 0;
            for (int i = -2730; i < 5001; i++) {
                for (int k = 0; k < bucket; k++) {
                    t[index] = new SortTemperatures.Temperature(i);
                    index++;
                }
            }
            while (index < n) {
                t[index] = new SortTemperatures.Temperature(5000);
                index++;
            }
        } else {
            for (int i = 0; i < n; i++) {
                t[i] = new SortTemperatures.Temperature(random.nextInt(7731) - 2730);
            }
        }
    }

    @Benchmark
    public void benchmarkLibSort(Blackhole bh) {
        SortTemperatures.libSortTemperatures(t);
        bh.consume(t);
    }

    @Benchmark
    public void benchmarkFastestSort(Blackhole bh) {
        SortTemperatures.fastestSortTemperatures(t);
        bh.consume(t);
    }

    @Benchmark
    public void benchmarkFastSort(Blackhole bh) {
        SortTemperatures.fastSortTemperatures(t);
        bh.consume(t);
    }

    @Benchmark
    public void benchmarkInsertionSort(Blackhole bh) {
        SortTemperatures.insertionSortTemperatures(t);
        bh.consume(t);
    }
}
