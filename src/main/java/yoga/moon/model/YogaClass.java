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

}
