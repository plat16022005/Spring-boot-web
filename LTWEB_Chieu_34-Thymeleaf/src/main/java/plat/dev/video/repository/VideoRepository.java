package plat.dev.video.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.*;
import plat.dev.video.entity.Video;

public interface VideoRepository extends JpaRepository<Video, Long> {
	Page<Video> findByTitleContainingIgnoreCase(String q, Pageable pageable);
}
