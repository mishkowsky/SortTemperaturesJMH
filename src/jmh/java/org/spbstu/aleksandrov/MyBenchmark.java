package org.spbstu.aleksandrov;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Benchmark)
@Fork(value = 1, jvmArgs = {"-Xms2G", "-Xmx2G"})
@Warmup(iterations = 10)
@Measurement(iterations = 20)
public class MyBenchmark {

    static Random random = new Random();

    @Param({"1000", "1000000", "10000000"})
    public int n = 1000;
    @Param({"false", "true"})
    public boolean edge = false;
    public SortTemperatures.Temperature[] t;

    @Setup(Level.Invocation)
    public void setup() {
        t = new SortTemperatures.Temperature[n];
        for (int i = 0; i < n; i++) {
            int r;
            if (edge) r = 5000; else r = random.nextInt( 7731) - 2730;
            t[i] = new SortTemperatures.Temperature(r);
        }
    }

    @Benchmark
    public void benchmarkLibSort(Blackhole bh) {
        SortTemperatures.libSortTemperatures(t);
        //bh.consume(t);
    }

    @Benchmark
    public void benchmarkFastestSort(Blackhole bh) {
        SortTemperatures.fastestSortTemperatures(t);
        //bh.consume(t);
    }

    @Benchmark
    public void benchmarkFastSort(Blackhole bh) {
        SortTemperatures.fastSortTemperatures(t);
        //bh.consume(t);
    }

    @Benchmark
    public void benchmarkSlowSort(Blackhole bh) {
        SortTemperatures.slowSortTemperatures(t);
        //bh.consume(t);
    }
}
