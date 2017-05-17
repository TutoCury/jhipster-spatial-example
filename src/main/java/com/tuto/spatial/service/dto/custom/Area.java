package com.tuto.spatial.service.dto.custom;

import java.util.ArrayList;
import java.util.List;

public class Area {
    
    private List<Point> points = new ArrayList<>();

    public Area(List<Point> points) {
        this.points = points;
    }

    public List<Point> getPoints() {
        return points;
    }
    
}
