package com.treedbscan.geospatial;

import com.treedbscan.core.AbstractPoint;

public class LatLng extends AbstractPoint
{

    private final double latitude;
    private final double longitude;

    public LatLng(double latitude, double longitude)
    {
        //TODO: error checking on valid ranges of Lat/Lng
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude()
    {
        return this.latitude;
    }

    public double getLongitude()
    {
        return this.longitude;
    }

}
