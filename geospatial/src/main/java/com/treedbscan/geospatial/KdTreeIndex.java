package com.treedbscan.geospatial;

import com.treedbscan.core.AbstractTree;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.index.kdtree.KdNode;
import com.vividsolutions.jts.index.kdtree.KdTree;

import java.util.List;
import java.util.stream.Collectors;

public class KdTreeIndex
    extends AbstractTree<LatLng>
{
    KdTree tree;

    public KdTreeIndex(List<LatLng> points)
    {
        tree = new KdTree();
        points.forEach(p -> tree.insert(new Coordinate(p.getLongitude(), p.getLatitude()), p));
    }

    @Override
    public List<LatLng> query(LatLng point, double distance)
    {
        List<KdNode> result = (List<KdNode>) tree.query(
            CircleFactory.createCircle(point.getLongitude(), point.getLatitude(), distance).getEnvelopeInternal()
        );
        return result.stream().map(n -> (LatLng) n.getData()).collect(Collectors.toList());
    }
}
