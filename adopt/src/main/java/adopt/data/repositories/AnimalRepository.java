package adopt.data.repositories;

import adopt.data.models.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, String> {

    List<Animal> findAllByIsAdopted(boolean isAdopted);

}
