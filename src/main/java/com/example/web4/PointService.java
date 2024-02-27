package com.example.web4;

import org.springframework.stereotype.Service;

@Service
public class PointService {
    CollectionService collection;
    public PointService(CollectionService collection) {
        this.collection = collection;
    }

    public boolean addPointAndCheckHit(double x, double y, int r){
        boolean hit = hit(x, y, r);
        Point point = new Point(x, y, r, hit);
        collection.addPoint(point);
        return hit;
    }

    private static boolean hit(double x, double y, double r) {
        double angle = Math.atan2(y, x);
        if (angle > 0 && angle <= Math.PI / 2) return x <= r && y <= r;
        if (angle > Math.PI / 2 && angle <= Math.PI) return x > (y - r) / 2;
        if (angle < Math.PI / -2 && angle >= -Math.PI)
            return Math.pow(x, 2.0) + Math.pow(y, 2.0) <= Math.pow(r / 2, 2.0);
        return false;
    }
}
