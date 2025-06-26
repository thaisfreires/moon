package yoga.moon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import yoga.moon.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>  {
    
}
