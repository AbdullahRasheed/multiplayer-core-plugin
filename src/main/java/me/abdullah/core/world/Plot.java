package me.abdullah.core.world;

import me.abdullah.core.data.GamePlayer;

import java.awt.*;
import java.awt.geom.Path2D;
import java.util.Set;

public class Plot {

    private GamePlayer owner;
    private Set<GamePlayer> subOwners;

    private Shape shape;
    private int minY;

    public Plot(Shape shape, int minY){
        this.shape = shape;
        this.minY = minY;
    }

    public boolean isInside(int x, int y, int z){
        if(y <= minY) return false;

        return shape.contains(x, z);
    }
}
