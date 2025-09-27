package plat.dev.video.service;
import plat.dev.video.entity.Video;
import org.springframework.data.domain.*;

import java.util.Optional;

public interface VideoService {
  Page<Video> search(String q, Pageable pageable);
  Page<Video> findAll(Pageable pageable);
  Optional<Video> findById(Long id);
  Video save(Video v);
  void deleteById(Long id);
}