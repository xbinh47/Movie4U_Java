package finalproject.application.repository;

import finalproject.application.entity.FoodCombo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodComboRepository extends JpaRepository<FoodCombo, Integer> {
    List<FoodCombo> findAll();
    FoodCombo findFoodComboById(Integer id);
}
