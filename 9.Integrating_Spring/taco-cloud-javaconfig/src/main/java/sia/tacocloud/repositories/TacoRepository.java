package sia.tacocloud.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import sia.tacocloud.domain.Taco;

@Repository
public interface TacoRepository extends CrudRepository<Taco, Long> {
}
