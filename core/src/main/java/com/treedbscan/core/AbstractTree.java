package com.treedbscan.core;

import java.util.List;

public abstract class AbstractTree<P extends AbstractPoint>
{

    //public AbstractTree() {}

    public abstract List<P> query(P point, double distance);

}
