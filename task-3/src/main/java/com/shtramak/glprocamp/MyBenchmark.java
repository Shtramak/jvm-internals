package com.shtramak.glprocamp;

import com.shtramak.glprocamp.exception.CustomException;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@State(Scope.Benchmark)
@Fork(value = 1, warmups = 1)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 3)
public class MyBenchmark {
    private static List<Integer> list;

    static {
        list = IntStream.generate(ThreadLocalRandom.current()::nextInt).limit(100)
                .boxed()
                .collect(Collectors.toList());
    }

    @Benchmark
    public List<Integer> methodwithLoop() {
        List<Integer> evenNumbers = new ArrayList<>();
        for (Integer integer : list) {
            if (integer % 2 == 0) {
                evenNumbers.add(integer);
            }
        }
        return evenNumbers;
    }

    @Benchmark
    public List<Integer> methodWithStream() {
        return list.stream()
                .filter(num -> num % 2 == 0)
                .collect(Collectors.toList());
    }

    @Benchmark
    public List<Integer> methodWithStreamParallel() {
        return list.stream()
                .parallel()
                .filter(num -> num % 2 == 0)
                .collect(Collectors.toList());
    }

    @Benchmark
    public List<Integer> methodwithMapToIntStream() {
        return list.stream()
                .mapToInt(Integer::intValue)
                .filter(num -> num % 2 == 0)
                .boxed()
                .collect(Collectors.toList());
    }

    @Benchmark
    public List<Integer> methodwithMapToIntStreamParallel() {
        return list.stream()
                .parallel()
                .mapToInt(Integer::intValue)
                .filter(num -> num % 2 == 0)
                .boxed()
                .collect(Collectors.toList());
    }

    @Benchmark
    public Integer exceptionWithStackTrace() throws RuntimeException {
        int value = ThreadLocalRandom.current().nextInt();
        try {
            throw new CustomException("And the value is... "+value);
        } catch (CustomException e) {
            e.printStackTrace();
        }
        return value;
    }

    @Benchmark
    public Integer exceptionWithoutStackTrace() throws RuntimeException {
        int value = ThreadLocalRandom.current().nextInt();
        try {
            throw new CustomException("And the value is... "+value);
        } catch (CustomException e) {
            // ¯\_(ツ)_/¯
        }
        return value;
    }

}

/*
Benchmark                                     Mode  Cnt     Score      Error  Units
MyBenchmark.exceptionWithStackTrace           avgt    5  4135,009 ? 1178,758  us/op
MyBenchmark.exceptionWithoutStackTrace        avgt    5     2,303 ?    0,900  us/op
MyBenchmark.methodWithStream                  avgt    5     1,139 ?    0,423  us/op
MyBenchmark.methodWithStreamParallel          avgt    5    25,475 ?    1,134  us/op
MyBenchmark.methodwithLoop                    avgt    5     1,148 ?    0,573  us/op
MyBenchmark.methodwithMapToIntStream          avgt    5     2,005 ?    2,460  us/op
MyBenchmark.methodwithMapToIntStreamParallel  avgt    5    28,287 ?    4,397  us/op
*/