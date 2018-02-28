package com.treedbscan.naive;

import com.treedbscan.core.Cluster;
import com.treedbscan.core.DBScanner;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class NaiveTreeDBScanTest
{

    private static List<NaivePoint> data;
    private double epsilon = 0.3;
    private int minpts = 3;

    static NaivePoint a;
    static NaivePoint b;
    static NaivePoint c;
    static NaivePoint d;
    static NaivePoint e;
    static NaivePoint f;

    @Before
    public void setup()
    {
        data = new ArrayList<>();
        a = new NaivePoint(0.0, 0.0);
        b = new NaivePoint(0.2, 0.0);
        c = new NaivePoint(0.3, 0.0);
        d = new NaivePoint(0.7, 0.5);
        e = new NaivePoint(0.9, 0.5);
        f = new NaivePoint(100, 100);
        data.add(a);
        data.add(b);
        data.add(c);
        data.add(d);
        data.add(e);
        data.add(f);
    }

    @Test
    public void testDBScanWithNaiveTreeFindsOneCluster()
    {
        NaiveTree tree = new NaiveTree(data);
        DBScanner<NaivePoint> dbScanner = new DBScanner<>(epsilon, minpts, data, tree);
        List<Cluster<NaivePoint>> clusters = dbScanner.scan();

        Assert.assertEquals(1, clusters.size());
    }

    @Test
    public void testDBScanWithNaiveTreeClustersPointsCorrectly()
    {
        NaiveTree tree = new NaiveTree(data);
        DBScanner<NaivePoint> dbScanner = new DBScanner<>(epsilon, minpts, data, tree);
        List<Cluster<NaivePoint>> clusters = dbScanner.scan();

        Cluster<NaivePoint> cluster = clusters.get(0);

        Assert.assertEquals(true, cluster.contains(a));
        Assert.assertEquals(true, cluster.contains(b));
        Assert.assertEquals(true, cluster.contains(c));

        Assert.assertEquals(false, cluster.contains(d));
        Assert.assertEquals(false, cluster.contains(e));
        Assert.assertEquals(false, cluster.contains(f));
    }

    @Test
    public void testDBScanWithNaiveTreeHandlesDensityReachablePoint()
    {
        NaivePoint g = new NaivePoint(0.6, 0.0);
        List<NaivePoint> xs = new ArrayList<>();
        xs.add(a);
        xs.add(b);
        xs.add(c);
        xs.add(d);
        xs.add(e);
        xs.add(f);
        xs.add(g);

        NaiveTree tree = new NaiveTree(xs);
        DBScanner<NaivePoint> dbScanner = new DBScanner<>(epsilon, minpts, xs, tree);

        List<Cluster<NaivePoint>> clusters = dbScanner.scan();
        Cluster<NaivePoint> cluster = clusters.get(0);
        Assert.assertEquals(true, cluster.contains(a));
        Assert.assertEquals(true, cluster.contains(g));
    }

}
