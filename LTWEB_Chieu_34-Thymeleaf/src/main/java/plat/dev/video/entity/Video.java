package plat.dev.video.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import plat.dev.category.entity.Category;

@Entity
@Table(name = "videos")
public class Video {
	public Video(Long id, @NotBlank @Size(max = 150) String title, @Size(max = 500) String description,
			@NotBlank String url, boolean active, LocalDateTime createdAt, Category category) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.url = url;
		this.active = active;
		this.createdAt = createdAt;
		this.category = category;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size(max = 150)
	private String title;

	@Size(max = 500)
	private String description;

	@NotBlank
	private String url;

	private boolean active = true;
	private LocalDateTime createdAt = LocalDateTime.now();

	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;


	// getters/setters
}
