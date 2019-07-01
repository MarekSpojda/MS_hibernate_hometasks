package pl.coderslab.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.coderslab.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(String name);

    @Query("select c from Category c where c.id=?1")
    Category findByIdCustomized(Long id);
}
