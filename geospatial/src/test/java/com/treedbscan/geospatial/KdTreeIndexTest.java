package com.treedbscan.geospatial;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class KdTreeIndexTest
{

    @Test
    public void testKDTreeFindsNearbyPoints()
    {
        LatLng a = new LatLng(0.0, 0.0);
        LatLng b = new LatLng(0.01, 0.01);
        List<LatLng> points = new ArrayList();
        points.add(a);

        KdTreeIndex gi = new KdTreeIndex(points);
        List<LatLng> neighbours = gi.query(b, 0.01);

        Assert.assertEquals(1, neighbours.size());
        Assert.assertEquals(a, neighbours.get(0));
    }

}
