package sia.tacocloud.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import sia.tacocloud.domain.Order;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
}
