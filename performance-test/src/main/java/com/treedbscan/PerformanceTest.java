/*
 * Copyright (c) 2014, Oracle America, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 *  * Neither the name of Oracle nor the names of its contributors may be used
 *    to endorse or promote products derived from this software without
 *    specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.treedbscan;

import com.treedbscan.core.DBScanner;
import com.treedbscan.geospatial.StrTreeIndex;
import com.treedbscan.geospatial.KdTreeIndex;
import com.treedbscan.geospatial.LatLng;
import com.treedbscan.naive.NaivePoint;
import com.treedbscan.naive.NaiveTree;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@State(Scope.Benchmark)
public class PerformanceTest
{
    @Param({"100"})
    public int points;

    public List<Tuple> generatePoints(int npoints)
    {
        double min = -10.0;
        double max = 10.0;

        double m1 = 0.5;
        double v1 = 0.3;

        double m2 = -0.5;
        double v2 = 0.1;

        List<Tuple> xs = new ArrayList<>(25000);

        int half = (int) Math.round(npoints / 2.0);
        int tenth = (int) Math.round(half / 10.0);

        IntStream.range(0, half).forEach(i -> xs.add(new Tuple(getGaussian(m1, v1), getGaussian(m1, v1))));
        IntStream.range(0, half).forEach(i -> xs.add(new Tuple(getGaussian(m2, v2), getGaussian(m2, v2))));
        IntStream.range(0, tenth).forEach(i -> xs.add(new Tuple(boundedRandom(min, max), boundedRandom(min, max))));

        return xs;
    }

    private double getGaussian(double mean, double variance)
    {
        Random fRandom = new Random();
        return mean + fRandom.nextGaussian() * variance;
    }

    private double boundedRandom(double min, double max)
    {
        Random fRandom = new Random();
        return fRandom.nextDouble() * (max - min);
    }

    private List<LatLng> generateLatLngs(int npoints)
    {
        return generatePoints(npoints).stream().map(t -> new LatLng(t.getX(), t.getY())).collect(Collectors.toList());
    }

    private List<NaivePoint> generateNaivePoints(int npoints)
    {
        return generatePoints(npoints).stream().map(t -> new NaivePoint(t.getX(), t.getY())).collect(Collectors.toList());
    }

    @Benchmark
    public void testNaive()
    {
        List<NaivePoint> naivePoints = generateNaivePoints(points);
        NaiveTree nt = new NaiveTree(naivePoints);
        DBScanner<NaivePoint> dbScanner = new DBScanner<>(0.2, 100, naivePoints, nt);
        dbScanner.scan();
    }

    @Benchmark
    public void testStrTreeIndex()
    {
        List<LatLng> latLngs = generateLatLngs(points);
        StrTreeIndex strTreeIndex = new StrTreeIndex(latLngs);
        DBScanner<LatLng> dbScanner = new DBScanner<>(0.2, 100, latLngs, strTreeIndex);
        dbScanner.scan();
    }

    @Benchmark
    public void testKdTreeIndex()
    {
        List<LatLng> latLngs = generateLatLngs(points);
        KdTreeIndex kdTreeIndex = new KdTreeIndex(latLngs);
        DBScanner<LatLng> dbScanner = new DBScanner<>(0.2, 100, latLngs, kdTreeIndex);
        dbScanner.scan();
    }

    public static void main(String[] args) throws RunnerException
    {
        Options opt = new OptionsBuilder()
            .include(PerformanceTest.class.getSimpleName())
            .param("points")
            .build();
        new Runner(opt).run();
    }

}
