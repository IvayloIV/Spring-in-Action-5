package sia.tacocloud.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import sia.tacocloud.domain.Order;
import sia.tacocloud.domain.User;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {

    public List<Order> findByUserOrderByPlacedAtDesc(User user, Pageable pageRequest);
}
