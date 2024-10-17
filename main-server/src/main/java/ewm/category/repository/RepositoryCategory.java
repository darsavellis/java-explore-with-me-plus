package ewm.category.repository;

import ewm.category.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositoryCategory extends JpaRepository<Category,Long> {
}
