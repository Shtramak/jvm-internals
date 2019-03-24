package com.shtramak.glprocamp;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@State(Scope.Benchmark)
public class MyBenchmark {
    private static List<Integer> list;

    static {
        list = IntStream.generate(ThreadLocalRandom.current()::nextInt).limit(1000)
                .boxed()
                .collect(Collectors.toList());
    }

    @Fork(value = 1, warmups = 1)
    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @Warmup(iterations = 5)
    public List<Integer> methodwithLoop() {
        List<Integer> evenNumbers = new ArrayList<>();
        for (Integer integer : list) {
            if (integer % 2 == 0) {
                evenNumbers.add(integer);
            }
        }
        return evenNumbers;
    }

    @Fork(value = 1, warmups = 1)
    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @Warmup(iterations = 5)
    public List<Integer> methodWithStream() {
        return list.stream()
                .filter(num -> num % 2 == 0)
                .collect(Collectors.toList());
    }

    @Fork(value = 1, warmups = 1)
    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @Warmup(iterations = 5)
    public List<Integer> methodWithStreamParallel() {
        return list.stream()
                .parallel()
                .filter(num -> num % 2 == 0)
                .collect(Collectors.toList());
    }

    @Fork(value = 1, warmups = 1)
    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @Warmup(iterations = 5)
    public List<Integer> methodwithMapToIntStream() {
        return list.stream()
                .mapToInt(Integer::intValue)
                .filter(num -> num % 2 == 0)
                .boxed()
                .collect(Collectors.toList());
    }

    @Fork(value = 1, warmups = 1)
    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @Warmup(iterations = 5)
    public List<Integer> methodwithMapToIntStreamParallel() {
        return list.stream()
                .parallel()
                .mapToInt(Integer::intValue)
                .filter(num -> num % 2 == 0)
                .boxed()
                .collect(Collectors.toList());
    }

    //todo method throwing and then processing exception with/without stack trace
}
/*
Benchmark                                      Mode  Cnt       Score       Error  Units
MyBenchmark.methodWithStream                  thrpt   20  168701,885 ?  9504,002  ops/s
MyBenchmark.methodWithStreamParallel          thrpt   20   30766,531 ?   302,672  ops/s
MyBenchmark.methodwithLoop                    thrpt   20   80543,129 ? 11968,778  ops/s
MyBenchmark.methodwithMapToIntStream          thrpt   20   89642,592 ?  6390,912  ops/s
MyBenchmark.methodwithMapToIntStreamParallel  thrpt   20   27919,685 ?   141,199  ops/s
*/