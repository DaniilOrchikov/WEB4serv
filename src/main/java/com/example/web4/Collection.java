package com.example.web4;

import java.util.ArrayList;
import java.util.List;

public class Collection {
    private final List<Point> points = new ArrayList<>();
    private final PointRepository pointRepository;

    public Collection(PointRepository pointRepository) {
        this.pointRepository = pointRepository;
        points.addAll(this.pointRepository.findAll());
    }

    public void clearCollection() {
        pointRepository.deleteAll();
        points.clear();
    }

    public void addPoint(Point point) {
        pointRepository.save(point);
        points.add(point);
    }

    public List<Point> getAllPoints() {
        return points;
    }
}
