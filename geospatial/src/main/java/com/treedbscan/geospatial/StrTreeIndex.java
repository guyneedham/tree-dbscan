package com.treedbscan.geospatial;

import com.treedbscan.core.AbstractTree;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.index.strtree.STRtree;

import java.util.List;

public class StrTreeIndex
    extends AbstractTree<LatLng>
{

    private final STRtree tree;

    public StrTreeIndex(List<LatLng> points)
    {
        tree = new STRtree();
        points.forEach(p -> tree.insert(new Envelope(new Coordinate(p.getLongitude(), p.getLatitude())), p));
    }

    @Override
    @SuppressWarnings("unchecked")  // TODO: type safety check
    public List<LatLng> query(LatLng point, double distance)
    {
        return (List<LatLng>) tree.query(
            CircleFactory.createCircle(point.getLongitude(), point.getLatitude(), distance).getEnvelopeInternal()
        );
    }
}
