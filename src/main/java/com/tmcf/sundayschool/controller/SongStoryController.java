package com.tmcf.sundayschool.controller;

import com.tmcf.sundayschool.entity.SongStory;
import com.tmcf.sundayschool.service.SongStoryService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/songs-stories")
public class SongStoryController {

    private final SongStoryService songStoryService;

    public SongStoryController(SongStoryService songStoryService) {
        this.songStoryService = songStoryService;
    }

    // ✅ 1. Add a song or story
    @PostMapping
    public ResponseEntity<SongStory> addItem(@RequestBody SongStory songStory) {
        return ResponseEntity.ok(songStoryService.addItem(songStory));
    }

    // ✅ 2. Update a song or story
    @PutMapping("/{id}")
    public ResponseEntity<SongStory> updateItem(
            @PathVariable("id") Long id,
            @RequestBody SongStory songStory) {
        return ResponseEntity.ok(songStoryService.updateItem(id, songStory));
    }

    // ✅ 3. Delete a song or story
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteItem(@PathVariable("id") Long id) {
        songStoryService.deleteItem(id);
        return ResponseEntity.ok(Map.of("message", "Item deleted successfully"));
    }

    // ✅ 4. Get all songs and stories
    @GetMapping
    public ResponseEntity<List<SongStory>> getAllItems() {
        return ResponseEntity.ok(songStoryService.getAllItems());
    }

    // ✅ 5. Get items by type (SONG / STORY)
    @GetMapping("/type/{type}")
    public ResponseEntity<List<SongStory>> getItemsByType(@PathVariable("type") String type) {
        return ResponseEntity.ok(songStoryService.getItemsByType(type));
    }
}
