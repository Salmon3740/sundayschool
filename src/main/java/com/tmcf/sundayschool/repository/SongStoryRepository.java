package com.tmcf.sundayschool.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tmcf.sundayschool.entity.SongStory;

@Repository
public interface SongStoryRepository extends JpaRepository<SongStory, Long> {

    List<SongStory> findByTypeIgnoreCase(String type);
}
