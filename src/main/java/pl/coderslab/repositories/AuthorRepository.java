package pl.coderslab.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.coderslab.entities.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    @Query("select a from Author a where a.id=?1")
    Author findAuthorByIdCustomized(Long id);
}
