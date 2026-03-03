package com.tmcf.sundayschool.service.impl;

import com.tmcf.sundayschool.entity.SongStory;
import com.tmcf.sundayschool.exception.ResourceNotFoundException;
import com.tmcf.sundayschool.repository.SongStoryRepository;
import com.tmcf.sundayschool.service.SongStoryService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SongStoryServiceImpl implements SongStoryService {

    private final SongStoryRepository songStoryRepository;

    public SongStoryServiceImpl(SongStoryRepository songStoryRepository) {
        this.songStoryRepository = songStoryRepository;
    }

    @Override
    public SongStory addItem(SongStory songStory) {
        return songStoryRepository.save(songStory);
    }

    @Override
    public SongStory updateItem(Long itemId, SongStory updatedSongStory) {

        SongStory existing = songStoryRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("SongStory", "id", itemId));

        existing.setTitle(updatedSongStory.getTitle());
        existing.setType(updatedSongStory.getType());
        existing.setLink(updatedSongStory.getLink());

        return songStoryRepository.save(existing);
    }

    @Override
    public void deleteItem(Long itemId) {

        SongStory existing = songStoryRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("SongStory", "id", itemId));

        songStoryRepository.delete(existing);
    }

    @Override
    public List<SongStory> getAllItems() {
        return songStoryRepository.findAll();
    }

    @Override
    public List<SongStory> getItemsByType(String type) {
        return songStoryRepository.findByTypeIgnoreCase(type);
    }
}
