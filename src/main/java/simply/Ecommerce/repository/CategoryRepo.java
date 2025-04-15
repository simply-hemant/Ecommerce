package simply.Ecommerce.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import simply.Ecommerce.model.Category;

import java.util.List;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Long> {

    Category findByCategoryId(String categoryId);

}
