package com.example.web4;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 4800)
@RestController
@PreAuthorize("hasRole('user')")
@RequestMapping("/api/points")
public class CollectionController {
    CollectionService collection;
    PointService collectionService;
    @Autowired
    public CollectionController(CollectionService collection, PointService collectionService) {
        this.collection = collection;
        this.collectionService = collectionService;
    }

    @GetMapping
    public ResponseEntity<List<Point>> points() {
        return ResponseEntity.ok(collection.getAllPoints());
    }

    @PostMapping
    public ResponseEntity<Boolean> form(@RequestBody PointForm form) {
        return ResponseEntity.ok(collectionService.addPointAndCheckHit(Double.parseDouble(form.x()), Double.parseDouble(form.y()), Integer.parseInt(form.r())));
    }

    @DeleteMapping
    public void clear() {
        collection.clearCollection();
    }
}
