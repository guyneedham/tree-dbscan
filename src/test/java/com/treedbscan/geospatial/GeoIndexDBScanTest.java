package com.treedbscan.geospatial;

import com.treedbscan.core.Cluster;
import com.treedbscan.core.DBScanner;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class GeoIndexDBScanTest
{

    private static List<LatLng> data;
    private double epsilon = 0.01;
    private int minpts = 3;

    static LatLng a;
    static LatLng b;
    static LatLng c;
    static LatLng d;
    static LatLng e;
    static LatLng f;

    @Before
    public void setup()
    {
        data = new ArrayList<>();
        a = new LatLng(0.0, 0.0);
        b = new LatLng(0.01, 0.01);
        c = new LatLng(0.0, 0.01);
        d = new LatLng(0.5, 0.0);
        e = new LatLng(0.5, 0.01);
        f = new LatLng(104.0, 130.1);
        data.add(a);
        data.add(b);
        data.add(c);
        data.add(d);
        data.add(e);
        data.add(f);
    }

    @Test
    public void testDBScanWithGeoIndexFindsOneCluster()
    {
        GeoIndex gi = new GeoIndex(data);

        DBScanner<LatLng> dbScanner = new DBScanner<>(epsilon, minpts, data, gi);
        List<Cluster<LatLng>> clusters = dbScanner.scan();

        Assert.assertEquals(1, clusters.size());
    }

    @Test
    public void testDBScanWithGeoIndexClustersPointsCorrectly()
    {
        GeoIndex gi = new GeoIndex(data);

        DBScanner<LatLng> dbScanner = new DBScanner<>(epsilon, minpts, data, gi);
        List<Cluster<LatLng>> clusters = dbScanner.scan();

        Cluster<LatLng> cluster = clusters.get(0);

        Assert.assertEquals(true, cluster.contains(a));
        Assert.assertEquals(true, cluster.contains(b));
        Assert.assertEquals(true, cluster.contains(c));

        Assert.assertEquals(false, cluster.contains(d));
        Assert.assertEquals(false, cluster.contains(e));
        Assert.assertEquals(false, cluster.contains(f));
    }

}
