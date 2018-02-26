package com.treedbscan.core;

import java.util.HashSet;

public class Cluster<P extends AbstractPoint>
{
    private HashSet<P> points;

    public Cluster()
    {
        this.points = new HashSet<>();
    }

    public void add(P point)
    {
        this.points.add(point);
    }

    public int getSize()
    {
        return points.size();
    }

    public boolean contains(P point)
    {
        return points.contains(point);
    }

}
