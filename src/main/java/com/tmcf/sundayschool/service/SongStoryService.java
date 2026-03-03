package com.tmcf.sundayschool.service;

import com.tmcf.sundayschool.entity.SongStory;

import java.util.List;

public interface SongStoryService {

    // 1️⃣ Add song or story
    SongStory addItem(SongStory songStory);

    // 2️⃣ Update song or story
    SongStory updateItem(Long itemId, SongStory updatedSongStory);

    // 3️⃣ Delete song or story
    void deleteItem(Long itemId);

    // 4️⃣ Get all items
    List<SongStory> getAllItems();

    // 5️⃣ Get items by type (SONG / STORY)
    List<SongStory> getItemsByType(String type);
}
