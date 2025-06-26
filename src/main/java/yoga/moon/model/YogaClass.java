package main.java.yoga.moon.model;

import jakarta.validation.constraints.*;
import jakarta.persistence.*;

@Entity
public class YogaClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    @NotNull
    @NotBlank(message = "Duration is required")
    private String duration;

    @NotNull
    @NotBlank(message = "Intensity is required")
    private String intensity;

    @NotNull
    @NotBlank(message = "Instructor is required")
    private String instructor;

    @NotNull
    @NotBlank(message = "VideoUrl is required")
    private String videoUrl;

    @ManyToOne
    @JoinColumn (name = "category_id")
    private Category category;

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
    public String getDuration() {
        return duration;
    }
    public void setDuration(String duration) {
        this.duration = duration;
    }
    public String getIntensity() {
        return intensity;
    }
    public void setIntensity(String intensity) {
        this.intensity = intensity;
    }

    public String getInstructor() {
        return instructor;
    }
    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }
    public String getVideoUrl() {
        return videoUrl;
    }
    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
    public Category getCategory() {
        return category;
    }
    public void setCategory(Category category) {
        this.category = category;
    }

}
