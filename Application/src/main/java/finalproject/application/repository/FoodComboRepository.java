package finalproject.application.repository;

import finalproject.application.entity.FoodCombo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodComboRepository extends JpaRepository<FoodCombo, Integer> {
    FoodCombo getFoodComboById(int id);
}
