package com.treedbscan.core;

import java.util.ArrayList;
import java.util.List;

public class DBScanner<P extends AbstractPoint>
{
    private final double epsilon;
    private final int minpts;
    private final List<P> data;
    private final AbstractTree<P> tree;

    public DBScanner(double epsilon, int minPts, List<P> data, AbstractTree<P> tree)
    {
        this.epsilon = epsilon;
        this.minpts = minPts;
        this.data = data;
        this.tree = tree;
    }

    public List<Cluster<P>> scan()
    {
        List<Cluster<P>> clusters = new ArrayList<>();
        for (P point : data)
        {
            if (point.getProcessed())
            {
                continue;  // skip already encountered points
            }
            Cluster<P> cluster = new Cluster<>();
            process(point, cluster);
            if (cluster.getSize() >= this.minpts)
            {
                clusters.add(cluster);
            }
        }
        return clusters;
    }

    private Cluster<P> process(P point, Cluster<P> cluster)
    {
        if (point.getProcessed())
        {
            return cluster;  // skip already encountered points
        }
        point.setProcessed(true);
        cluster.add(point);
        List<P> neighbours = tree.query(point, epsilon);

        for (P neighbour : neighbours)
        {
            process(neighbour, cluster);
        }

        return cluster;
    }

}
