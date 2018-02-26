package com.treedbscan.geospatial;

import com.treedbscan.core.AbstractTree;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.index.strtree.STRtree;
import com.vividsolutions.jts.util.GeometricShapeFactory;

import java.util.List;

public class GeoIndex extends AbstractTree<LatLng>
{

    private final STRtree tree;

    public GeoIndex(List<LatLng> points)
    {
        tree = new STRtree();
        for (LatLng p : points)
        {
            tree.insert(new Envelope(new Coordinate(p.getLongitude(), p.getLatitude())), p);
        }
    }

    @Override
    @SuppressWarnings("unchecked")  // TODO: type safety check
    public List<LatLng> query(LatLng point, double distance)
    {
        return (List<LatLng>) tree.query(
            createCircle(point.getLongitude(), point.getLatitude(), distance).getEnvelopeInternal()
        );
    }

    private static Geometry createCircle(double x, double y, final double RADIUS) {
        GeometricShapeFactory shapeFactory = new GeometricShapeFactory();
        shapeFactory.setNumPoints(32);
        shapeFactory.setCentre(new Coordinate(x, y));
        shapeFactory.setSize(RADIUS * 2);
        return shapeFactory.createCircle();
    }
}
