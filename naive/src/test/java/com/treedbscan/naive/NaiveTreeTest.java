package com.treedbscan.naive;

import org.junit.Test;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;

public class NaiveTreeTest
{

    @Test
    public void testNaiveTreeQueryFindsNearbyPoints()
    {
        double threshold = 0.3;

        List<NaivePoint> points = new ArrayList<>();
        NaivePoint a = new NaivePoint(0.0, 0.1);
        NaivePoint b = new NaivePoint(0.2, 0.1);
        NaivePoint c = new NaivePoint(0.3, 0.1);
        NaivePoint d = new NaivePoint(0.6, 0.7);
        points.add(a);
        points.add(b);
        points.add(c);
        points.add(d);

        NaiveTree nt = new NaiveTree(points);

        NaivePoint queryPoint = new NaivePoint(0.1, 0.1);

        List<NaivePoint> neighbours = nt.query(queryPoint, threshold);
        Assert.assertEquals(3, neighbours.size());
        Assert.assertEquals(true, neighbours.contains(a));
        Assert.assertEquals(true, neighbours.contains(b));
        Assert.assertEquals(true, neighbours.contains(c));
        Assert.assertEquals(false, neighbours.contains(d));
    }

}
