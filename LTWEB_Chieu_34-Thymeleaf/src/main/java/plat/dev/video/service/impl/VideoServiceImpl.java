package plat.dev.video.service.impl;

import plat.dev.video.entity.Video;
import plat.dev.video.repository.VideoRepository;
import plat.dev.video.service.VideoService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@Service
public class VideoServiceImpl implements VideoService {
  @Autowired private VideoRepository repo;

  @Autowired
  private VideoRepository videoRepository;

  @Override
  public Page<Video> findAll(Pageable pageable) {
      if (pageable == null) {
          pageable = PageRequest.of(0, 5, Sort.by("id").descending()); // tránh null
      }
      return videoRepository.findAll(pageable);
  }

  @Override
  public Page<Video> search(String keyword, Pageable pageable) {
      if (pageable == null) {
          pageable = PageRequest.of(0, 5, Sort.by("id").descending());
      }
      return videoRepository.findByTitleContainingIgnoreCase(keyword, pageable);
  }
  public Optional<Video> findById(Long id){ return repo.findById(id); }
  public Video save(Video u){ return repo.save(u); }
  public void deleteById(Long id){ repo.deleteById(id); }
}
