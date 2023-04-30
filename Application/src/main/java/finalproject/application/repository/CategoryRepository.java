package finalproject.application.repository;

import finalproject.application.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Category findCategoryById(Integer id);

}
