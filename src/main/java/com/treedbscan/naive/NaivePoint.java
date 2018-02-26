package com.treedbscan.naive;

import com.treedbscan.core.AbstractPoint;

public class NaivePoint extends AbstractPoint
{
    private final double x;
    private final double y;

    public NaivePoint(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    public double getDistance(NaivePoint that)
    {
        double a = this.x - that.getX();
        double b = this.y - that.getY();
        return Math.sqrt(a*a + b*b);
    }

    public double getX()
    {
        return this.x;
    }

    public double getY()
    {
        return this.y;
    }

}
