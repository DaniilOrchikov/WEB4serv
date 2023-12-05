package com.example.web4;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 4800)
@RestController
@RequestMapping("/api/points")
public class CollectionController {
    Collection collection;
    @Autowired
    public CollectionController(PointRepository pointRepository){
        collection = new Collection(pointRepository);
    }

    @GetMapping
    public ResponseEntity<List<Point>> points() {
        return ResponseEntity.ok(collection.getAllPoints());
    }

    @PostMapping
    public ResponseEntity<Boolean> form(@RequestBody PointForm form) {
        double x = Double.parseDouble(form.x());
        double y = Double.parseDouble(form.y());
        int r = Integer.parseInt(form.r());
        boolean hit = hit(x, y, r);
        Point point = new Point(x, y, r, hit);
        collection.addPoint(point);
        return ResponseEntity.ok(hit);
    }
    @DeleteMapping
    public void clear() {
        collection.clearCollection();
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
