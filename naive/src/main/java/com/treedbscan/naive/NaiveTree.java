package com.treedbscan.naive;

import com.treedbscan.core.AbstractTree;

import java.util.ArrayList;
import java.util.List;

public class NaiveTree extends AbstractTree<NaivePoint>
{
    private List<NaivePoint> points;

    public NaiveTree(List<NaivePoint> points)
    {
        this.points = points;
    }

    @Override
    public List<NaivePoint> query(NaivePoint point, double distance)
    {
        List<NaivePoint> neighbours = new ArrayList<>();
        points.forEach(p -> {
            double d = p.getDistance(point);
            if (d <= distance)
            {
                neighbours.add(p);
            }
        });
        return neighbours;
    }


}
