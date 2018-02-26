package com.treedbscan.naive;

import org.junit.Test;
import org.junit.Assert;

public class NaivePointTest
{

    private static final double delta = 0.1;

    @Test
    public void testGetDistanceGivesCorrectResult()
    {
        NaivePoint a = new NaivePoint(1.0, 1.0);
        NaivePoint b = new NaivePoint(-1.0, -1.0);
        Assert.assertEquals(2.82, a.getDistance(b), delta);
    }

    public void testNaivePointDistanceIsMetric()
    {
        NaivePoint a = new NaivePoint(1.0, 1.0);
        NaivePoint b = new NaivePoint(-1.0, -1.0);
        double distAB = a.getDistance(b);
        double distBA = b.getDistance(a);
        Assert.assertEquals(distAB, distBA, delta);
    }

}
