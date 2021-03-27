package sia.tacocloud.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import sia.tacocloud.domain.entity.Taco;

@Repository
public interface TacoRepository extends CrudRepository<Taco, Long> {

    public Page<Taco> findAll(Pageable pageable);
}